package org.jnbis.api;

import org.jnbis.api.handler.NistHandler;
import org.jnbis.api.handler.WsqHandler;

/**
 * The starting point of the API.
 */
public final class Jnbis {
    private static final WsqHandler WSQ_HANDLER = new WsqHandler();
    private static final NistHandler NIST_HANDLER = new NistHandler();

    /**
     * No instance is needed.
     */
    private Jnbis() {
    }

    /**
     * Returns a <code>WsqHandle</code>.
     *
     * @return a WsqHandle, not null
     * @see WsqHandler
     */
    public static WsqHandler wsq() {
        return WSQ_HANDLER;
    }

    /**
     * Returns a <code>NistHandler</code>.
     *
     * @return a NistHandler, not null
     * @see NistHandler
     */
    public static NistHandler nist() {
        return NIST_HANDLER;
    }
}
