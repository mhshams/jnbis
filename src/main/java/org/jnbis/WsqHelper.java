package org.jnbis;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 7, 2007
 */
public class WsqHelper {
    /*used to "mask out" n number of bits from data stream*/
    static int[] BITMASK = {0x00, 0x01, 0x03, 0x07, 0x0f, 0x1f, 0x3f, 0x7f, 0xff};

    static final int MAX_DHT_TABLES = 8;
    static final int MAX_HUFFBITS = 16;
    static final int MAX_HUFFCOUNTS_WSQ = 256;

    static final int W_TREELEN = 20;
    static final int Q_TREELEN = 64;

    /* WSQ Marker Definitions */
    static final int SOI_WSQ = 0xffa0;
    static final int EOI_WSQ = 0xffa1;
    static final int SOF_WSQ = 0xffa2;
    static final int SOB_WSQ = 0xffa3;
    static final int DTT_WSQ = 0xffa4;
    static final int DQT_WSQ = 0xffa5;
    static final int DHT_WSQ = 0xffa6;
    static final int DRT_WSQ = 0xffa7;
    static final int COM_WSQ = 0xffa8;

    static final int STRT_SUBBAND_2 = 19;
    static final int STRT_SUBBAND_3 = 52;
    static final int MAX_SUBBANDS = 64;
    static final int NUM_SUBBANDS = 60;
    static final int STRT_SUBBAND_DEL = NUM_SUBBANDS;
    static final int STRT_SIZE_REGION_2 = 4;
    static final int STRT_SIZE_REGION_3 = 51;

    /* Case for getting ANY marker. */
    static final int ANY_WSQ = 0xffff;
    static final int TBLS_N_SOF = 2;
    static final int TBLS_N_SOB = TBLS_N_SOF + 2;

    static class WavletTree {
        int x;
        int y;
        int lenx;
        int leny;
        int invrw;
        int invcl;
    }

    static class TableDTT {
        float lofilt[];
        float hifilt[];
        int losz;
        int hisz;
        int lodef;
        int hidef;
    }

    static class HuffCode {
        int size;
        int code;
    }

    static class HeaderFrm {
        int black;
        int white;
        int width;
        int height;
        float mShift;
        float rScale;
        int wsqEncoder;
        int software;
    }

    static class HuffmanTable {
        int tableLen;
        int bytesLeft;
        int tableId;
        int[] huffbits;
        int[] huffvalues;
    }

    static class TableDHT {
        private static final int MAX_HUFFBITS = 16; /*DO NOT CHANGE THIS CONSTANT!! */
        private static final int MAX_HUFFCOUNTS_WSQ = 256; /* Length of code table: change as needed */

        byte tabdef;
        int[] huffbits = new int[MAX_HUFFBITS];
        int[] huffvalues = new int[MAX_HUFFCOUNTS_WSQ + 1];
    }

    static class Table_DQT {
        public static final int MAX_SUBBANDS = 64;
        float binCenter;
        float[] qBin = new float[MAX_SUBBANDS];
        float[] zBin = new float[MAX_SUBBANDS];
        char dqtDef;
    }

    static class QuantTree {
        int x;    /* UL corner of block */
        int y;
        int lenx;  /* block size */
        int leny;  /* block size */
    }

    static class IntRef {
        int value;

        public IntRef(int value) {
            this.value = value;
        }
    }

    static class Token {
        TableDHT[] tableDHT;
        TableDTT tableDTT;
        Table_DQT tableDQT;

        WavletTree[] wtree;
        QuantTree[] qtree;


        byte[] buffer;
        int pointer;

        Token(byte[] buffer) {
            this.buffer = buffer;
            this.pointer = 0;
        }

        void initialize() {
            tableDTT = new TableDTT();
            tableDQT = new Table_DQT();

            /* Init DHT Tables to 0. */
            tableDHT = new TableDHT[MAX_DHT_TABLES];
            for (int i = 0; i < MAX_DHT_TABLES; i++) {
                tableDHT[i] = new TableDHT();
                tableDHT[i].tabdef = 0;
            }
        }

        long readInt() {
            byte byte1 = buffer[pointer++];
            byte byte2 = buffer[pointer++];
            byte byte3 = buffer[pointer++];
            byte byte4 = buffer[pointer++];

            return (0xffL & byte1) << 24 | (0xffL & byte2) << 16 | (0xffL & byte3) << 8 | (0xffL & byte4);
        }

        int readShort() {
            int byte1 = buffer[pointer++];
            int byte2 = buffer[pointer++];

            return (0xff & byte1) << 8 | (0xff & byte2);
        }

        int readByte() {
            byte byte1 = buffer[pointer++];

            return 0xff & byte1;
        }

        byte[] readBytes(int size) {
            byte[] bytes = new byte[size];

            for (int i = 0; i < size; i++) {
                bytes[i] = buffer[pointer++];
            }

            return bytes;
        }
    }
}
