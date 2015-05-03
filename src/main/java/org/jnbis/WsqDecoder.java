package org.jnbis;

import java.io.*;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 1, 2007
 */
public class WsqDecoder {

    public Bitmap decode(String fileName) throws IOException {
        return decode(new File(fileName));
    }

    public Bitmap decode(File file) throws IOException {
        return decode(new FileInputStream(file));
    }

    public Bitmap decode(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return decode(buffer.toByteArray());
    }

    public Bitmap decode(final byte[] data) {
        WsqHelper.Token token = new WsqHelper.Token(data);
        token.initialize();

        /* Read the SOI marker. */
        getCMarkerWSQ(token, WsqHelper.SOI_WSQ);

        /* Read in supporting tables up to the SOF marker. */
        int marker = getCMarkerWSQ(token, WsqHelper.TBLS_N_SOF);
        while (marker != WsqHelper.SOF_WSQ) {
            getCTableWSQ(token, marker);
            marker = getCMarkerWSQ(token, WsqHelper.TBLS_N_SOF);
        }

        /* Read in the Frame Header. */
        WsqHelper.HeaderFrm frmHeaderWSQ = getCFrameHeaderWSQ(token);
        int width = frmHeaderWSQ.width;
        int height = frmHeaderWSQ.height;

        int ppi = getCPpiWSQ();

        /* Build WSQ decomposition trees. */
        buildWSQTrees(token, width, height);

        /* Decode the Huffman encoded buffer blocks. */
        int[] qdata = huffmanDecodeDataMem(token, width * height);

        /* Decode the quantize wavelet subband buffer. */
        float[] fdata = unquantize(token, qdata, width, height);

        /* Done with quantized wavelet subband buffer. */
        //noinspection UnusedAssignment
        qdata = null;

        wsqReconstruct(token, fdata, width, height);

        /* Convert floating point pixels to unsigned char pixels. */
        byte[] cdata = convertImage2Byte(fdata, width, height, frmHeaderWSQ.mShift, frmHeaderWSQ.rScale);
        //noinspection UnusedAssignment
        fdata = null;

        //noinspection UnusedAssignment
        token = null;

        return new Bitmap(cdata, width, height, ppi, 8, 1);
    }

    private int intSign(int power) { /* "sign" power */
        int cnt;        /* counter */
        int num = -1;   /* sign return value */

        if (power == 0)
            return 1;

        for (cnt = 1; cnt < power; cnt++)
            num *= -1;

        return num;
    }

    private int getCMarkerWSQ(WsqHelper.Token token, int type) {
        if (token.pointer >= token.buffer.length) {
            throw new RuntimeException("Error, Invalid pointer : " + token.pointer);
        }

        int marker = token.readShort();


        switch (type) {
            case WsqHelper.SOI_WSQ:
                if (marker != WsqHelper.SOI_WSQ) {
                    throw new RuntimeException("ERROR : getCMarkerWSQ : No SOI marker : " + marker);
                }

                return marker;

            case WsqHelper.TBLS_N_SOF:
                if (marker != WsqHelper.DTT_WSQ
                        && marker != WsqHelper.DQT_WSQ
                        && marker != WsqHelper.DHT_WSQ
                        && marker != WsqHelper.SOF_WSQ
                        && marker != WsqHelper.COM_WSQ) {
                    throw new RuntimeException("ERROR : getc_marker_wsq : No SOF, Table, or comment markers : " + marker);
                }

                return marker;

            case WsqHelper.TBLS_N_SOB:
                if (marker != WsqHelper.DTT_WSQ
                        && marker != WsqHelper.DQT_WSQ
                        && marker != WsqHelper.DHT_WSQ
                        && marker != WsqHelper.SOB_WSQ
                        && marker != WsqHelper.COM_WSQ) {
                    throw new RuntimeException("ERROR : getc_marker_wsq : No SOB, Table, or comment markers : " +
                            marker);
                }
                return marker;
            case WsqHelper.ANY_WSQ:
                if ((marker & 0xff00) != 0xff00) {
                    throw new RuntimeException("ERROR : getc_marker_wsq : no marker found : " + marker);
                }

                /* Added by MDG on 03-07-05 */
                if ((marker < WsqHelper.SOI_WSQ) || (marker > WsqHelper.COM_WSQ)) {
                    throw new RuntimeException("ERROR : getc_marker_wsq : not a valid marker : " + marker);
                }

                return marker;
            default:
                throw new RuntimeException("ERROR : getc_marker_wsq : Invalid marker : " + marker);
        }
    }

    private void getCTableWSQ(WsqHelper.Token token, int marker) {
        switch (marker) {
            case WsqHelper.DTT_WSQ:
                getCTransformTable(token);
                return;
            case WsqHelper.DQT_WSQ:
                getCQuantizationTable(token);
                return;
            case WsqHelper.DHT_WSQ:
                getCHuffmanTableWSQ(token);
                return;
            case WsqHelper.COM_WSQ:
                //shams: i don't use return value
                getCComment(token);
                return;
            default:
                throw new RuntimeException("ERROR: getCTableWSQ : Invalid table defined : " + marker);
        }
    }

    private String getCComment(WsqHelper.Token token) {
        int size = token.readShort() - 2;
        return String.valueOf(token.readBytes(size));
    }

    private void getCTransformTable(WsqHelper.Token token) {
        // read header Size;
        token.readShort();

        token.tableDTT.hisz = token.readByte();
        token.tableDTT.losz = token.readByte();

        token.tableDTT.hifilt = new float[token.tableDTT.hisz];
        token.tableDTT.lofilt = new float[token.tableDTT.losz];

        int aSize;
        if (token.tableDTT.hisz % 2 != 0) {
            aSize = (token.tableDTT.hisz + 1) / 2;
        } else {
            aSize = token.tableDTT.hisz / 2;
        }

        float[] aLofilt = new float[aSize];


        aSize--;
        for (int cnt = 0; cnt <= aSize; cnt++) {
            int sign = token.readByte();
            int scale = token.readByte();
            long shrtDat = token.readInt();
            aLofilt[cnt] = (float) shrtDat;

            while (scale > 0) {
                aLofilt[cnt] /= 10.0;
                scale--;
            }

            if (sign != 0) {
                aLofilt[cnt] *= -1.0;
            }

            if (token.tableDTT.hisz % 2 != 0) {
                token.tableDTT.hifilt[cnt + aSize] = intSign(cnt) * aLofilt[cnt];
                if (cnt > 0) {
                    token.tableDTT.hifilt[aSize - cnt] = token.tableDTT.hifilt[cnt + aSize];
                }
            } else {
                token.tableDTT.hifilt[cnt + aSize + 1] = intSign(cnt) * aLofilt[cnt];
                token.tableDTT.hifilt[aSize - cnt] = -1 * token.tableDTT.hifilt[cnt + aSize + 1];
            }
        }

        if (token.tableDTT.losz % 2 != 0) {
            aSize = (token.tableDTT.losz + 1) / 2;
        } else {
            aSize = token.tableDTT.losz / 2;
        }

        float[] aHifilt = new float[aSize];

        aSize--;
        for (int cnt = 0; cnt <= aSize; cnt++) {
            int sign = token.readByte();
            int scale = token.readByte();
            long shrtDat = token.readInt();

            aHifilt[cnt] = (float) shrtDat;

            while (scale > 0) {
                aHifilt[cnt] /= 10.0;
                scale--;
            }

            if (sign != 0) {
                aHifilt[cnt] *= -1.0;
            }

            if (token.tableDTT.losz % 2 != 0) {
                token.tableDTT.lofilt[cnt + aSize] = intSign(cnt) * aHifilt[cnt];
                if (cnt > 0) {
                    token.tableDTT.lofilt[aSize - cnt] = token.tableDTT.lofilt[cnt + aSize];
                }
            } else {
                token.tableDTT.lofilt[cnt + aSize + 1] = intSign(cnt + 1) * aHifilt[cnt];
                token.tableDTT.lofilt[aSize - cnt] = token.tableDTT.lofilt[cnt + aSize + 1];
            }
        }

        token.tableDTT.lodef = 1;
        token.tableDTT.hidef = 1;
    }

    public void getCQuantizationTable(WsqHelper.Token token) {
        token.readShort(); /* header size */
        int scale = token.readByte(); /* scaling parameter */
        int shrtDat = token.readShort(); /* counter and temp short buffer */

        token.tableDQT.binCenter = (float) shrtDat;
        while (scale > 0) {
            token.tableDQT.binCenter /= 10.0;
            scale--;
        }

        for (int cnt = 0; cnt < WsqHelper.Table_DQT.MAX_SUBBANDS; cnt++) {
            scale = token.readByte();
            shrtDat = token.readShort();
            token.tableDQT.qBin[cnt] = (float) shrtDat;
            while (scale > 0) {
                token.tableDQT.qBin[cnt] /= 10.0;
                scale--;
            }

            scale = token.readByte();
            shrtDat = token.readShort();
            token.tableDQT.zBin[cnt] = (float) shrtDat;
            while (scale > 0) {
                token.tableDQT.zBin[cnt] /= 10.0;
                scale--;
            }
        }

        token.tableDQT.dqtDef = 1;
    }

    public void getCHuffmanTableWSQ(WsqHelper.Token token) {
        /* First time, read table len. */
        WsqHelper.HuffmanTable firstHuffmanTable = getCHuffmanTable(token, WsqHelper.MAX_HUFFCOUNTS_WSQ, 0, true);

        /* Store table into global structure list. */
        int tableId = firstHuffmanTable.tableId;
        token.tableDHT[tableId].huffbits = (int[]) firstHuffmanTable.huffbits.clone();
        token.tableDHT[tableId].huffvalues = (int[]) firstHuffmanTable.huffvalues.clone();
        token.tableDHT[tableId].tabdef = 1;

        int bytesLeft = firstHuffmanTable.bytesLeft;
        while (bytesLeft != 0) {
            /* Read next table without rading table len. */
            WsqHelper.HuffmanTable huffmantable = getCHuffmanTable(token, WsqHelper.MAX_HUFFCOUNTS_WSQ, bytesLeft, false);

            /* If table is already defined ... */
            tableId = huffmantable.tableId;
            if (token.tableDHT[tableId].tabdef != 0) {
                throw new RuntimeException("ERROR : getCHuffmanTableWSQ : huffman table already defined.");
            }

            /* Store table into global structure list. */
            token.tableDHT[tableId].huffbits = (int[]) huffmantable.huffbits.clone();
            token.tableDHT[tableId].huffvalues = (int[]) huffmantable.huffvalues.clone();
            token.tableDHT[tableId].tabdef = 1;
            bytesLeft = huffmantable.bytesLeft;
        }
    }

    private WsqHelper.HuffmanTable getCHuffmanTable(WsqHelper.Token token, int maxHuffcounts, int bytesLeft, boolean readTableLen) {

        WsqHelper.HuffmanTable huffmanTable = new WsqHelper.HuffmanTable();

        /* table_len */
        if (readTableLen) {
            huffmanTable.tableLen = token.readShort();
            huffmanTable.bytesLeft = huffmanTable.tableLen - 2;
            bytesLeft = huffmanTable.bytesLeft;
        } else {
            huffmanTable.bytesLeft = bytesLeft;
        }

        /* If no bytes left ... */
        if (bytesLeft <= 0) {
            throw new RuntimeException("ERROR : getCHuffmanTable : no huffman table bytes remaining");
        }

        /* Table ID */
        huffmanTable.tableId = token.readByte();
        huffmanTable.bytesLeft--;


        huffmanTable.huffbits = new int[WsqHelper.MAX_HUFFBITS];
        int numHufvals = 0;
        /* L1 ... L16 */
        for (int i = 0; i < WsqHelper.MAX_HUFFBITS; i++) {
            huffmanTable.huffbits[i] = token.readByte();
            numHufvals += huffmanTable.huffbits[i];
        }
        huffmanTable.bytesLeft -= WsqHelper.MAX_HUFFBITS;

        if (numHufvals > maxHuffcounts + 1) {
            throw new RuntimeException("ERROR : getCHuffmanTable : numHufvals is larger than MAX_HUFFCOUNTS");
        }

        /* Could allocate only the amount needed ... then we wouldn't */
        /* need to pass MAX_HUFFCOUNTS. */
        huffmanTable.huffvalues = new int[maxHuffcounts + 1];

        /* V1,1 ... V16,16 */
        for (int i = 0; i < numHufvals; i++) {
            huffmanTable.huffvalues[i] = token.readByte();
        }
        huffmanTable.bytesLeft -= numHufvals;

        return huffmanTable;
    }

    private WsqHelper.HeaderFrm getCFrameHeaderWSQ(WsqHelper.Token token) {
        WsqHelper.HeaderFrm headerFrm = new WsqHelper.HeaderFrm();

        //noinspection UnusedDeclaration
        int hdrSize = token.readShort(); /* header size */

        headerFrm.black = token.readByte();
        headerFrm.white = token.readByte();
        headerFrm.height = token.readShort();
        headerFrm.width = token.readShort();
        int scale = token.readByte(); /* exponent scaling parameter */
        int shrtDat = token.readShort(); /* buffer pointer */
        headerFrm.mShift = (float) shrtDat;
        while (scale > 0) {
            headerFrm.mShift /= 10.0;
            scale--;
        }

        scale = token.readByte();
        shrtDat = token.readShort();
        headerFrm.rScale = (float) shrtDat;
        while (scale > 0) {
            headerFrm.rScale /= 10.0;
            scale--;
        }

        headerFrm.wsqEncoder = token.readByte();
        headerFrm.software = token.readShort();

        return headerFrm;
    }

    private int getCPpiWSQ() {
        return -1;
    }

    private void buildWSQTrees(WsqHelper.Token token, int width, int height) {
        /* Build a W-TREE structure for the image. */
        buildWTree(token, WsqHelper.W_TREELEN, width, height);
        /* Build a Q-TREE structure for the image. */
        buildQTree(token, WsqHelper.Q_TREELEN);
    }

    private void buildWTree(WsqHelper.Token token, int wtreelen, int width, int height) {
        int lenx, lenx2, leny, leny2;  /* starting lengths of sections of
                                              the image being split into subbands */
        token.wtree = new WsqHelper.WavletTree[wtreelen];
        for (int i = 0; i < wtreelen; i++) {
            token.wtree[i] = new WsqHelper.WavletTree();
            token.wtree[i].invrw = 0;
            token.wtree[i].invcl = 0;
        }

        token.wtree[2].invrw = 1;
        token.wtree[4].invrw = 1;
        token.wtree[7].invrw = 1;
        token.wtree[9].invrw = 1;
        token.wtree[11].invrw = 1;
        token.wtree[13].invrw = 1;
        token.wtree[16].invrw = 1;
        token.wtree[18].invrw = 1;
        token.wtree[3].invcl = 1;
        token.wtree[5].invcl = 1;
        token.wtree[8].invcl = 1;
        token.wtree[9].invcl = 1;
        token.wtree[12].invcl = 1;
        token.wtree[13].invcl = 1;
        token.wtree[17].invcl = 1;
        token.wtree[18].invcl = 1;

        wtree4(token, 0, 1, width, height, 0, 0, 1);

        if ((token.wtree[1].lenx % 2) == 0) {
            lenx = token.wtree[1].lenx / 2;
            lenx2 = lenx;
        } else {
            lenx = (token.wtree[1].lenx + 1) / 2;
            lenx2 = lenx - 1;
        }

        if ((token.wtree[1].leny % 2) == 0) {
            leny = token.wtree[1].leny / 2;
            leny2 = leny;
        } else {
            leny = (token.wtree[1].leny + 1) / 2;
            leny2 = leny - 1;
        }

        wtree4(token, 4, 6, lenx2, leny, lenx, 0, 0);
        wtree4(token, 5, 10, lenx, leny2, 0, leny, 0);
        wtree4(token, 14, 15, lenx, leny, 0, 0, 0);

        token.wtree[19].x = 0;
        token.wtree[19].y = 0;
        if ((token.wtree[15].lenx % 2) == 0)
            token.wtree[19].lenx = token.wtree[15].lenx / 2;
        else
            token.wtree[19].lenx = (token.wtree[15].lenx + 1) / 2;

        if ((token.wtree[15].leny % 2) == 0)
            token.wtree[19].leny = token.wtree[15].leny / 2;
        else
            token.wtree[19].leny = (token.wtree[15].leny + 1) / 2;
    }

    private void wtree4(WsqHelper.Token token, int start1, int start2, int lenx, int leny, int x, int y, int stop1) {
        int evenx, eveny;   /* Check length of subband for even or odd */
        int p1, p2;         /* w_tree locations for storing subband sizes and locations */

        p1 = start1;
        p2 = start2;

        evenx = lenx % 2;
        eveny = leny % 2;

        token.wtree[p1].x = x;
        token.wtree[p1].y = y;
        token.wtree[p1].lenx = lenx;
        token.wtree[p1].leny = leny;

        token.wtree[p2].x = x;
        token.wtree[p2 + 2].x = x;
        token.wtree[p2].y = y;
        token.wtree[p2 + 1].y = y;

        if (evenx == 0) {
            token.wtree[p2].lenx = lenx / 2;
            token.wtree[p2 + 1].lenx = token.wtree[p2].lenx;
        } else {
            if (p1 == 4) {
                token.wtree[p2].lenx = (lenx - 1) / 2;
                token.wtree[p2 + 1].lenx = token.wtree[p2].lenx + 1;
            } else {
                token.wtree[p2].lenx = (lenx + 1) / 2;
                token.wtree[p2 + 1].lenx = token.wtree[p2].lenx - 1;
            }
        }
        token.wtree[p2 + 1].x = token.wtree[p2].lenx + x;
        if (stop1 == 0) {
            token.wtree[p2 + 3].lenx = token.wtree[p2 + 1].lenx;
            token.wtree[p2 + 3].x = token.wtree[p2 + 1].x;
        }
        token.wtree[p2 + 2].lenx = token.wtree[p2].lenx;


        if (eveny == 0) {
            token.wtree[p2].leny = leny / 2;
            token.wtree[p2 + 2].leny = token.wtree[p2].leny;
        } else {
            if (p1 == 5) {
                token.wtree[p2].leny = (leny - 1) / 2;
                token.wtree[p2 + 2].leny = token.wtree[p2].leny + 1;
            } else {
                token.wtree[p2].leny = (leny + 1) / 2;
                token.wtree[p2 + 2].leny = token.wtree[p2].leny - 1;
            }
        }
        token.wtree[p2 + 2].y = token.wtree[p2].leny + y;
        if (stop1 == 0) {
            token.wtree[p2 + 3].leny = token.wtree[p2 + 2].leny;
            token.wtree[p2 + 3].y = token.wtree[p2 + 2].y;
        }
        token.wtree[p2 + 1].leny = token.wtree[p2].leny;
    }

    private void buildQTree(WsqHelper.Token token, int qtreelen) {
        token.qtree = new WsqHelper.QuantTree[qtreelen];
        for (int i = 0; i < token.qtree.length; i++) {
            token.qtree[i] = new WsqHelper.QuantTree();
        }

        qtree16(token, 3, token.wtree[14].lenx, token.wtree[14].leny, token.wtree[14].x, token.wtree[14].y, 0, 0);
        qtree16(token, 19, token.wtree[4].lenx, token.wtree[4].leny, token.wtree[4].x, token.wtree[4].y, 0, 1);
        qtree16(token, 48, token.wtree[0].lenx, token.wtree[0].leny, token.wtree[0].x, token.wtree[0].y, 0, 0);
        qtree16(token, 35, token.wtree[5].lenx, token.wtree[5].leny, token.wtree[5].x, token.wtree[5].y, 1, 0);
        qtree4(token, 0, token.wtree[19].lenx, token.wtree[19].leny, token.wtree[19].x, token.wtree[19].y);
    }

    private void qtree16(WsqHelper.Token token, int start, int lenx, int leny, int x, int y, int rw, int cl) {
        int tempx, temp2x;   /* temporary x values */
        int tempy, temp2y;   /* temporary y values */
        int evenx, eveny;    /* Check length of subband for even or odd */
        int p;               /* indicates subband information being stored */

        p = start;
        evenx = lenx % 2;
        eveny = leny % 2;

        if (evenx == 0) {
            tempx = lenx / 2;
            temp2x = tempx;
        } else {
            if (cl != 0) {
                temp2x = (lenx + 1) / 2;
                tempx = temp2x - 1;
            } else {
                tempx = (lenx + 1) / 2;
                temp2x = tempx - 1;
            }
        }

        if (eveny == 0) {
            tempy = leny / 2;
            temp2y = tempy;
        } else {
            if (rw != 0) {
                temp2y = (leny + 1) / 2;
                tempy = temp2y - 1;
            } else {
                tempy = (leny + 1) / 2;
                temp2y = tempy - 1;
            }
        }

        evenx = tempx % 2;
        eveny = tempy % 2;

        token.qtree[p].x = x;
        token.qtree[p + 2].x = x;
        token.qtree[p].y = y;
        token.qtree[p + 1].y = y;
        if (evenx == 0) {
            token.qtree[p].lenx = tempx / 2;
            token.qtree[p + 1].lenx = token.qtree[p].lenx;
            token.qtree[p + 2].lenx = token.qtree[p].lenx;
            token.qtree[p + 3].lenx = token.qtree[p].lenx;
        } else {
            token.qtree[p].lenx = (tempx + 1) / 2;
            token.qtree[p + 1].lenx = token.qtree[p].lenx - 1;
            token.qtree[p + 2].lenx = token.qtree[p].lenx;
            token.qtree[p + 3].lenx = token.qtree[p + 1].lenx;
        }
        token.qtree[p + 1].x = x + token.qtree[p].lenx;
        token.qtree[p + 3].x = token.qtree[p + 1].x;
        if (eveny == 0) {
            token.qtree[p].leny = tempy / 2;
            token.qtree[p + 1].leny = token.qtree[p].leny;
            token.qtree[p + 2].leny = token.qtree[p].leny;
            token.qtree[p + 3].leny = token.qtree[p].leny;
        } else {
            token.qtree[p].leny = (tempy + 1) / 2;
            token.qtree[p + 1].leny = token.qtree[p].leny;
            token.qtree[p + 2].leny = token.qtree[p].leny - 1;
            token.qtree[p + 3].leny = token.qtree[p + 2].leny;
        }
        token.qtree[p + 2].y = y + token.qtree[p].leny;
        token.qtree[p + 3].y = token.qtree[p + 2].y;


        evenx = temp2x % 2;

        token.qtree[p + 4].x = x + tempx;
        token.qtree[p + 6].x = token.qtree[p + 4].x;
        token.qtree[p + 4].y = y;
        token.qtree[p + 5].y = y;
        token.qtree[p + 6].y = token.qtree[p + 2].y;
        token.qtree[p + 7].y = token.qtree[p + 2].y;
        token.qtree[p + 4].leny = token.qtree[p].leny;
        token.qtree[p + 5].leny = token.qtree[p].leny;
        token.qtree[p + 6].leny = token.qtree[p + 2].leny;
        token.qtree[p + 7].leny = token.qtree[p + 2].leny;
        if (evenx == 0) {
            token.qtree[p + 4].lenx = temp2x / 2;
            token.qtree[p + 5].lenx = token.qtree[p + 4].lenx;
            token.qtree[p + 6].lenx = token.qtree[p + 4].lenx;
            token.qtree[p + 7].lenx = token.qtree[p + 4].lenx;
        } else {
            token.qtree[p + 5].lenx = (temp2x + 1) / 2;
            token.qtree[p + 4].lenx = token.qtree[p + 5].lenx - 1;
            token.qtree[p + 6].lenx = token.qtree[p + 4].lenx;
            token.qtree[p + 7].lenx = token.qtree[p + 5].lenx;
        }
        token.qtree[p + 5].x = token.qtree[p + 4].x + token.qtree[p + 4].lenx;
        token.qtree[p + 7].x = token.qtree[p + 5].x;


        eveny = temp2y % 2;

        token.qtree[p + 8].x = x;
        token.qtree[p + 9].x = token.qtree[p + 1].x;
        token.qtree[p + 10].x = x;
        token.qtree[p + 11].x = token.qtree[p + 1].x;
        token.qtree[p + 8].y = y + tempy;
        token.qtree[p + 9].y = token.qtree[p + 8].y;
        token.qtree[p + 8].lenx = token.qtree[p].lenx;
        token.qtree[p + 9].lenx = token.qtree[p + 1].lenx;
        token.qtree[p + 10].lenx = token.qtree[p].lenx;
        token.qtree[p + 11].lenx = token.qtree[p + 1].lenx;
        if (eveny == 0) {
            token.qtree[p + 8].leny = temp2y / 2;
            token.qtree[p + 9].leny = token.qtree[p + 8].leny;
            token.qtree[p + 10].leny = token.qtree[p + 8].leny;
            token.qtree[p + 11].leny = token.qtree[p + 8].leny;
        } else {
            token.qtree[p + 10].leny = (temp2y + 1) / 2;
            token.qtree[p + 11].leny = token.qtree[p + 10].leny;
            token.qtree[p + 8].leny = token.qtree[p + 10].leny - 1;
            token.qtree[p + 9].leny = token.qtree[p + 8].leny;
        }
        token.qtree[p + 10].y = token.qtree[p + 8].y + token.qtree[p + 8].leny;
        token.qtree[p + 11].y = token.qtree[p + 10].y;


        token.qtree[p + 12].x = token.qtree[p + 4].x;
        token.qtree[p + 13].x = token.qtree[p + 5].x;
        token.qtree[p + 14].x = token.qtree[p + 4].x;
        token.qtree[p + 15].x = token.qtree[p + 5].x;
        token.qtree[p + 12].y = token.qtree[p + 8].y;
        token.qtree[p + 13].y = token.qtree[p + 8].y;
        token.qtree[p + 14].y = token.qtree[p + 10].y;
        token.qtree[p + 15].y = token.qtree[p + 10].y;
        token.qtree[p + 12].lenx = token.qtree[p + 4].lenx;
        token.qtree[p + 13].lenx = token.qtree[p + 5].lenx;
        token.qtree[p + 14].lenx = token.qtree[p + 4].lenx;
        token.qtree[p + 15].lenx = token.qtree[p + 5].lenx;
        token.qtree[p + 12].leny = token.qtree[p + 8].leny;
        token.qtree[p + 13].leny = token.qtree[p + 8].leny;
        token.qtree[p + 14].leny = token.qtree[p + 10].leny;
        token.qtree[p + 15].leny = token.qtree[p + 10].leny;
    }

    private void qtree4(WsqHelper.Token token, int start, int lenx, int leny, int x, int y) {
        int evenx, eveny;    /* Check length of subband for even or odd */
        int p;               /* indicates subband information being stored */

        p = start;
        evenx = lenx % 2;
        eveny = leny % 2;


        token.qtree[p].x = x;
        token.qtree[p + 2].x = x;
        token.qtree[p].y = y;
        token.qtree[p + 1].y = y;
        if (evenx == 0) {
            token.qtree[p].lenx = lenx / 2;
            token.qtree[p + 1].lenx = token.qtree[p].lenx;
            token.qtree[p + 2].lenx = token.qtree[p].lenx;
            token.qtree[p + 3].lenx = token.qtree[p].lenx;
        } else {
            token.qtree[p].lenx = (lenx + 1) / 2;
            token.qtree[p + 1].lenx = token.qtree[p].lenx - 1;
            token.qtree[p + 2].lenx = token.qtree[p].lenx;
            token.qtree[p + 3].lenx = token.qtree[p + 1].lenx;
        }
        token.qtree[p + 1].x = x + token.qtree[p].lenx;
        token.qtree[p + 3].x = token.qtree[p + 1].x;
        if (eveny == 0) {
            token.qtree[p].leny = leny / 2;
            token.qtree[p + 1].leny = token.qtree[p].leny;
            token.qtree[p + 2].leny = token.qtree[p].leny;
            token.qtree[p + 3].leny = token.qtree[p].leny;
        } else {
            token.qtree[p].leny = (leny + 1) / 2;
            token.qtree[p + 1].leny = token.qtree[p].leny;
            token.qtree[p + 2].leny = token.qtree[p].leny - 1;
            token.qtree[p + 3].leny = token.qtree[p + 2].leny;
        }
        token.qtree[p + 2].y = y + token.qtree[p].leny;
        token.qtree[p + 3].y = token.qtree[p + 2].y;
    }

    private int[] huffmanDecodeDataMem(WsqHelper.Token token, int size) {
        int[] qdata = new int[size];

        int[] maxcode = new int[WsqHelper.MAX_HUFFBITS + 1];
        int[] mincode = new int[WsqHelper.MAX_HUFFBITS + 1];
        int[] valptr = new int[WsqHelper.MAX_HUFFBITS + 1];

        WsqHelper.IntRef marker = new WsqHelper.IntRef(getCMarkerWSQ(token, WsqHelper.TBLS_N_SOB));

        WsqHelper.IntRef bitCount = new WsqHelper.IntRef(0); /* bit count for getc_nextbits_wsq routine */
        WsqHelper.IntRef nextByte = new WsqHelper.IntRef(0); /*next byte of buffer*/
        int hufftableId = 0; /* huffman table number */
        int ip = 0;

        while (marker.value != WsqHelper.EOI_WSQ) {

            if (marker.value != 0) {
                while (marker.value != WsqHelper.SOB_WSQ) {
                    getCTableWSQ(token, marker.value);
                    marker.value = getCMarkerWSQ(token, WsqHelper.TBLS_N_SOB);
                }
                hufftableId = getCBlockHeader(token); /* huffman table number */

                if (token.tableDHT[hufftableId].tabdef != 1) {
                    throw new RuntimeException("ERROR : huffmanDecodeDataMem : huffman table undefined.");
                }

                /* the next two routines reconstruct the huffman tables */
                WsqHelper.HuffCode[] hufftable = buildHuffsizes(token.tableDHT[hufftableId].huffbits, WsqHelper.MAX_HUFFCOUNTS_WSQ);
                buildHuffcodes(hufftable);

                /* this routine builds a set of three tables used in decoding */
                /* the compressed buffer*/
                genDecodeTable(hufftable, maxcode, mincode, valptr, token.tableDHT[hufftableId].huffbits);

                bitCount.value = 0;
                marker.value = 0;
            }

            /* get next huffman category code from compressed input buffer stream */
            int nodeptr = decodeDataMem(token, mincode, maxcode, valptr, token.tableDHT[hufftableId].huffvalues, bitCount, marker, nextByte);
            /* nodeptr  pointers for decoding */

            if (nodeptr == -1) {
                continue;
            }

            if (nodeptr > 0 && nodeptr <= 100) {
                for (int n = 0; n < nodeptr; n++) {
                    qdata[ip++] = 0; /* z run */
                }
            } else if (nodeptr > 106 && nodeptr < 0xff) {
                qdata[ip++] = nodeptr - 180;
            } else if (nodeptr == 101) {
                qdata[ip++] = getCNextbitsWSQ(token, marker, bitCount, 8, nextByte);

            } else if (nodeptr == 102) {
                qdata[ip++] = -getCNextbitsWSQ(token, marker, bitCount, 8, nextByte);
            } else if (nodeptr == 103) {
                qdata[ip++] = getCNextbitsWSQ(token, marker, bitCount, 16, nextByte);
            } else if (nodeptr == 104) {
                qdata[ip++] = -getCNextbitsWSQ(token, marker, bitCount, 16, nextByte);
            } else if (nodeptr == 105) {
                int n = getCNextbitsWSQ(token, marker, bitCount, 8, nextByte);
                while (n-- > 0) {
                    qdata[ip++] = 0;
                }
            } else if (nodeptr == 106) {
                int n = getCNextbitsWSQ(token, marker, bitCount, 16, nextByte);
                while (n-- > 0) {
                    qdata[ip++] = 0;
                }
            } else {
                throw new RuntimeException("ERROR: huffman_decode_data_mem : Invalid code (" + nodeptr + ")");
            }
        }

        return qdata;
    }

    private int getCBlockHeader(WsqHelper.Token token) {
        token.readShort(); /* block header size */
        return token.readByte();
    }

    private WsqHelper.HuffCode[] buildHuffsizes(int[] huffbits, int maxHuffcounts) {
        WsqHelper.HuffCode[] huffcodeTable;    /*table of huffman codes and sizes*/
        int numberOfCodes = 1;     /*the number codes for a given code size*/

        huffcodeTable = new WsqHelper.HuffCode[maxHuffcounts + 1];

        int tempSize = 0;
        for (int codeSize = 1; codeSize <= WsqHelper.MAX_HUFFBITS; codeSize++) {
            while (numberOfCodes <= huffbits[codeSize - 1]) {
                huffcodeTable[tempSize] = new WsqHelper.HuffCode();
                huffcodeTable[tempSize].size = codeSize;
                tempSize++;
                numberOfCodes++;
            }
            numberOfCodes = 1;
        }

        huffcodeTable[tempSize] = new WsqHelper.HuffCode();
        huffcodeTable[tempSize].size = 0;

        return huffcodeTable;
    }

    private void buildHuffcodes(WsqHelper.HuffCode[] huffcodeTable) {
        short tempCode = 0;  /*used to construct code word*/
        int pointer = 0;     /*pointer to code word information*/

        int tempSize = huffcodeTable[0].size;
        if (huffcodeTable[pointer].size == 0) {
            return;
        }

        do {
            do {
                huffcodeTable[pointer].code = tempCode;
                tempCode++;
                pointer++;
            } while (huffcodeTable[pointer].size == tempSize);

            if (huffcodeTable[pointer].size == 0)
                return;

            do {
                tempCode <<= 1;
                tempSize++;
            } while (huffcodeTable[pointer].size != tempSize);
        } while (huffcodeTable[pointer].size == tempSize);
    }

    private void genDecodeTable(WsqHelper.HuffCode[] huffcodeTable, int[] maxcode, int[] mincode, int[] valptr, int[] huffbits) {
        for (int i = 0; i <= WsqHelper.MAX_HUFFBITS; i++) {
            maxcode[i] = 0;
            mincode[i] = 0;
            valptr[i] = 0;
        }

        int i2 = 0;
        for (int i = 1; i <= WsqHelper.MAX_HUFFBITS; i++) {
            if (huffbits[i - 1] == 0) {
                maxcode[i] = -1;
                continue;
            }
            valptr[i] = i2;
            mincode[i] = huffcodeTable[i2].code;
            i2 = i2 + huffbits[i - 1] - 1;
            maxcode[i] = huffcodeTable[i2].code;
            i2++;
        }
    }

    private int decodeDataMem(WsqHelper.Token token, int[] mincode, int[] maxcode, int[] valptr, int[] huffvalues, WsqHelper.IntRef bitCount, WsqHelper.IntRef marker, WsqHelper.IntRef nextByte) {

        short code = (short) getCNextbitsWSQ(token, marker, bitCount, 1, nextByte);   /* becomes a huffman code word  (one bit at a time)*/
        if (marker.value != 0) {
            return -1;
        }

        int inx;
        for (inx = 1; code > maxcode[inx]; inx++) {
            int tbits = getCNextbitsWSQ(token, marker, bitCount, 1, nextByte);  /* becomes a huffman code word  (one bit at a time)*/
            code = (short) ((code << 1) + tbits);

            if (marker.value != 0) {
                return -1;
            }
        }

        int inx2 = valptr[inx] + code - mincode[inx];  /*increment variables*/
        return huffvalues[inx2];
    }

    private int getCNextbitsWSQ(WsqHelper.Token token, WsqHelper.IntRef marker, WsqHelper.IntRef bitCount, int bitsReq, WsqHelper.IntRef nextByte) {
        if (bitCount.value == 0) {
            nextByte.value = token.readByte();

            bitCount.value = 8;
            if (nextByte.value == 0xFF) {
                int code2 = token.readByte();  /*stuffed byte of buffer*/

                if (code2 != 0x00 && bitsReq == 1) {
                    marker.value = (nextByte.value << 8) | code2;
                    return 1;
                }
                if (code2 != 0x00) {
                    throw new RuntimeException("ERROR: getCNextbitsWSQ : No stuffed zeros.");
                }
            }
        }

        int bits, tbits;  /*bits of current buffer byte requested*/
        int bitsNeeded; /*additional bits required to finish request*/

        if (bitsReq <= bitCount.value) {
            bits = (nextByte.value >> (bitCount.value - bitsReq)) & (WsqHelper.BITMASK[bitsReq]);
            bitCount.value -= bitsReq;
            nextByte.value &= WsqHelper.BITMASK[bitCount.value];
        } else {
            bitsNeeded = bitsReq - bitCount.value; /*additional bits required to finish request*/
            bits = nextByte.value << bitsNeeded;
            bitCount.value = 0;
            tbits = getCNextbitsWSQ(token, marker, bitCount, bitsNeeded, nextByte);
            bits |= tbits;
        }

        return bits;
    }

    private float[] unquantize(WsqHelper.Token token, int[] sip, int width, int height) {
        float[] fip = new float[width * height];  /* floating point image */

        if (token.tableDQT.dqtDef != 1) {
            throw new RuntimeException("ERROR: unquantize : quantization table parameters not defined!");
        }

        float binCenter = token.tableDQT.binCenter; /* quantizer bin center */

        int sptr = 0;
        for (int cnt = 0; cnt < WsqHelper.NUM_SUBBANDS; cnt++) {
            if (token.tableDQT.qBin[cnt] == 0.0) {
                continue;
            }

            int fptr = (token.qtree[cnt].y * width) + token.qtree[cnt].x;

            for (int row = 0; row < token.qtree[cnt].leny; row++, fptr += width - token.qtree[cnt].lenx) {
                for (int col = 0; col < token.qtree[cnt].lenx; col++) {
                    if (sip[sptr] == 0) {
                        fip[fptr] = 0.0f;
                    } else if (sip[sptr] > 0) {
                        fip[fptr] = (token.tableDQT.qBin[cnt] * (sip[sptr] - binCenter)) + (token.tableDQT.zBin[cnt] / 2.0f);
                    } else if (sip[sptr] < 0) {
                        fip[fptr] = (token.tableDQT.qBin[cnt] * (sip[sptr] + binCenter)) - (token.tableDQT.zBin[cnt] / 2.0f);
                    } else {
                        throw new RuntimeException("ERROR : unquantize : invalid quantization pixel value");
                    }
                    fptr++;
                    sptr++;
                }
            }
        }

        return fip;
    }

    private void wsqReconstruct(WsqHelper.Token token, float[] fdata, int width, int height) {
        if (token.tableDTT.lodef != 1) {
            throw new RuntimeException("ERROR: wsq_reconstruct : Lopass filter coefficients not defined");
        }

        if (token.tableDTT.hidef != 1) {
            throw new RuntimeException("ERROR: wsq_reconstruct : Hipass filter coefficients not defined");
        }

        int numPix = width * height;
        /* Allocate temporary floating point pixmap. */
        float[] fdataTemp = new float[numPix];

        /* Reconstruct floating point pixmap from wavelet subband buffer. */
        for (int node = WsqHelper.W_TREELEN - 1; node >= 0; node--) {
            int fdataBse = (token.wtree[node].y * width) + token.wtree[node].x;
            joinLets(fdataTemp, fdata, 0, fdataBse, token.wtree[node].lenx, token.wtree[node].leny,
                    1, width,
                    token.tableDTT.hifilt, token.tableDTT.hisz,
                    token.tableDTT.lofilt, token.tableDTT.losz,
                    token.wtree[node].invcl);
            joinLets(fdata, fdataTemp, fdataBse, 0, token.wtree[node].leny, token.wtree[node].lenx,
                    width, 1,
                    token.tableDTT.hifilt, token.tableDTT.hisz,
                    token.tableDTT.lofilt, token.tableDTT.losz,
                    token.wtree[node].invrw);
        }
    }

    private void joinLets(
            float[] newdata,
            float[] olddata,
            int newIndex,
            int oldIndex,
            int len1,       /* temporary length parameters */
            int len2,
            int pitch,      /* pitch gives next row_col to filter */
            int stride,    /*           stride gives next pixel to filter */
            float[] hi,
            int hsz,   /* NEW */
            float[] lo,      /* filter coefficients */
            int lsz,   /* NEW */
            int inv)        /* spectral inversion? */ {
        int lp0, lp1;
        int hp0, hp1;
        int lopass, hipass;   /* lo/hi pass image pointers */
        int limg, himg;
        int pix, cl_rw;      /* pixel counter and column/row counter */
        int i, da_ev;         /* if "scanline" is even or odd and */
        int loc, hoc;
        int hlen, llen;
        int nstr, pstr;
        int tap;
        int fi_ev;
        int olle, ohle, olre, ohre;
        int lle, lle2, lre, lre2;
        int hle, hle2, hre, hre2;
        int lpx, lspx;
        int lpxstr, lspxstr;
        int lstap, lotap;
        int hpx, hspx;
        int hpxstr, hspxstr;
        int hstap, hotap;
        int asym, fhre = 0, ofhre;
        float ssfac, osfac, sfac;

        da_ev = len2 % 2;
        fi_ev = lsz % 2;
        pstr = stride;
        nstr = -pstr;
        if (da_ev != 0) {
            llen = (len2 + 1) / 2;
            hlen = llen - 1;
        } else {
            llen = len2 / 2;
            hlen = llen;
        }

        if (fi_ev != 0) {
            asym = 0;
            ssfac = 1.0f;
            ofhre = 0;
            loc = (lsz - 1) / 4;
            hoc = (hsz + 1) / 4 - 1;
            lotap = ((lsz - 1) / 2) % 2;
            hotap = ((hsz + 1) / 2) % 2;
            if (da_ev != 0) {
                olle = 0;
                olre = 0;
                ohle = 1;
                ohre = 1;
            } else {
                olle = 0;
                olre = 1;
                ohle = 1;
                ohre = 0;
            }
        } else {
            asym = 1;
            ssfac = -1.0f;
            ofhre = 2;
            loc = lsz / 4 - 1;
            hoc = hsz / 4 - 1;
            lotap = (lsz / 2) % 2;
            hotap = (hsz / 2) % 2;
            if (da_ev != 0) {
                olle = 1;
                olre = 0;
                ohle = 1;
                ohre = 1;
            } else {
                olle = 1;
                olre = 1;
                ohle = 1;
                ohre = 1;
            }

            if (loc == -1) {
                loc = 0;
                olle = 0;
            }
            if (hoc == -1) {
                hoc = 0;
                ohle = 0;
            }

            for (i = 0; i < hsz; i++)
                hi[i] *= -1.0;
        }


        for (cl_rw = 0; cl_rw < len1; cl_rw++) {
            limg = newIndex + cl_rw * pitch;
            himg = limg;
            newdata[himg] = 0.0f;
            newdata[himg + stride] = 0.0f;
            if (inv != 0) {
                hipass = oldIndex + cl_rw * pitch;
                lopass = hipass + stride * hlen;
            } else {
                lopass = oldIndex + cl_rw * pitch;
                hipass = lopass + stride * llen;
            }


            lp0 = lopass;
            lp1 = lp0 + (llen - 1) * stride;
            lspx = lp0 + (loc * stride);
            lspxstr = nstr;
            lstap = lotap;
            lle2 = olle;
            lre2 = olre;

            hp0 = hipass;
            hp1 = hp0 + (hlen - 1) * stride;
            hspx = hp0 + (hoc * stride);
            hspxstr = nstr;
            hstap = hotap;
            hle2 = ohle;
            hre2 = ohre;
            osfac = ssfac;

            for (pix = 0; pix < hlen; pix++) {
                for (tap = lstap; tap >= 0; tap--) {
                    lle = lle2;
                    lre = lre2;
                    lpx = lspx;
                    lpxstr = lspxstr;

                    newdata[limg] = olddata[lpx] * lo[tap];
                    for (i = tap + 2; i < lsz; i += 2) {
                        if (lpx == lp0) {
                            if (lle != 0) {
                                lpxstr = 0;
                                lle = 0;
                            } else
                                lpxstr = pstr;
                        }
                        if (lpx == lp1) {
                            if (lre != 0) {
                                lpxstr = 0;
                                lre = 0;
                            } else
                                lpxstr = nstr;
                        }
                        lpx += lpxstr;
                        newdata[limg] += olddata[lpx] * lo[i];
                    }
                    limg += stride;
                }
                if (lspx == lp0) {
                    if (lle2 != 0) {
                        lspxstr = 0;
                        lle2 = 0;
                    } else
                        lspxstr = pstr;
                }
                lspx += lspxstr;
                lstap = 1;

                for (tap = hstap; tap >= 0; tap--) {
                    hle = hle2;
                    hre = hre2;
                    hpx = hspx;
                    hpxstr = hspxstr;
                    fhre = ofhre;
                    sfac = osfac;

                    for (i = tap; i < hsz; i += 2) {
                        if (hpx == hp0) {
                            if (hle != 0) {
                                hpxstr = 0;
                                hle = 0;
                            } else {
                                hpxstr = pstr;
                                sfac = 1.0f;
                            }
                        }
                        if (hpx == hp1) {
                            if (hre != 0) {
                                hpxstr = 0;
                                hre = 0;
                                if (asym != 0 && da_ev != 0) {
                                    hre = 1;
                                    fhre--;
                                    sfac = (float) fhre;
                                    if (sfac == 0.0)
                                        hre = 0;
                                }
                            } else {
                                hpxstr = nstr;
                                if (asym != 0)
                                    sfac = -1.0f;
                            }
                        }
                        newdata[himg] += olddata[hpx] * hi[i] * sfac;
                        hpx += hpxstr;
                    }
                    himg += stride;
                }
                if (hspx == hp0) {
                    if (hle2 != 0) {
                        hspxstr = 0;
                        hle2 = 0;
                    } else {
                        hspxstr = pstr;
                        osfac = 1.0f;
                    }
                }
                hspx += hspxstr;
                hstap = 1;
            }


            if (da_ev != 0)
                if (lotap != 0)
                    lstap = 1;
                else
                    lstap = 0;
            else if (lotap != 0)
                lstap = 2;
            else
                lstap = 1;

            for (tap = 1; tap >= lstap; tap--) {
                lle = lle2;
                lre = lre2;
                lpx = lspx;
                lpxstr = lspxstr;

                newdata[limg] = olddata[lpx] * lo[tap];
                for (i = tap + 2; i < lsz; i += 2) {
                    if (lpx == lp0) {
                        if (lle != 0) {
                            lpxstr = 0;
                            lle = 0;
                        } else
                            lpxstr = pstr;
                    }
                    if (lpx == lp1) {
                        if (lre != 0) {
                            lpxstr = 0;
                            lre = 0;
                        } else
                            lpxstr = nstr;
                    }
                    lpx += lpxstr;
                    newdata[limg] += olddata[lpx] * lo[i];
                }
                limg += stride;
            }


            if (da_ev != 0) {
                if (hotap != 0)
                    hstap = 1;
                else
                    hstap = 0;

                if (hsz == 2) {
                    hspx -= hspxstr;
                    fhre = 1;
                }
            } else if (hotap != 0)
                hstap = 2;
            else
                hstap = 1;


            for (tap = 1; tap >= hstap; tap--) {
                hle = hle2;
                hre = hre2;
                hpx = hspx;
                hpxstr = hspxstr;
                sfac = osfac;
                if (hsz != 2)
                    fhre = ofhre;

                for (i = tap; i < hsz; i += 2) {
                    if (hpx == hp0) {
                        if (hle != 0) {
                            hpxstr = 0;
                            hle = 0;
                        } else {
                            hpxstr = pstr;
                            sfac = 1.0f;
                        }
                    }
                    if (hpx == hp1) {
                        if (hre != 0) {
                            hpxstr = 0;
                            hre = 0;
                            if (asym != 0 && da_ev != 0) {
                                hre = 1;
                                fhre--;
                                sfac = (float) fhre;
                                if (sfac == 0.0)
                                    hre = 0;
                            }
                        } else {
                            hpxstr = nstr;
                            if (asym != 0)
                                sfac = -1.0f;
                        }
                    }
                    newdata[himg] += olddata[hpx] * hi[i] * sfac;
                    hpx += hpxstr;
                }
                himg += stride;
            }
        }

        if (fi_ev == 0)
            for (i = 0; i < hsz; i++)
                hi[i] *= -1.0;
    }

    private byte[] convertImage2Byte(float[] img, int width, int height, float mShift, float rScale) {
        byte[] data = new byte[width * height];

        int idx = 0;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                float pixel = (img[idx] * rScale) + mShift;
                pixel += 0.5;

                if (pixel < 0.0) {
                    data[idx] = 0; /* neg pix poss after quantization */
                } else if (pixel > 255.0) {
                    data[idx] = (byte) 255;
                } else {
                    data[idx] = (byte) pixel;
                }
                idx++;
            }
        }

        return data;
    }
}
