package org.jnbis;

import java.io.Serializable;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Apr 24, 2007
 */
public class Wsq implements Serializable {
    private int id;
    private byte[] data;


    public Wsq(byte[] data, int id) {
        this.data = data;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }
}
