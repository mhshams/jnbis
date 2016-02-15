package org.jnbis.internal.record.writer;

import java.io.IOException;
import java.io.OutputStream;

import org.jnbis.internal.record.BaseRecord;

/**
 * @author argonaut
 */
public abstract class RecordWriter<T extends BaseRecord> {

    abstract public void write(OutputStream out, T record) throws IOException;

}
