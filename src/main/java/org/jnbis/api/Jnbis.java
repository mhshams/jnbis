package org.jnbis.api;

import org.jnbis.api.handler.WsqHandler;

/**
 *
 */
public final class Jnbis {
    private static WsqHandler WSQ_HANDLER = new WsqHandler();

    private Jnbis() {
    }

    public static WsqHandler wsq() {
        return WSQ_HANDLER;
    }
}

/*
 Nist nist = Jnbis.nist().decode("my file")
 nist.bitmaps().get(0).toPng().save("file")
 nist.bitmaps().keys()
 nist.bitmaps().values()
 nist.texts().get(1)
 nist.texts().keys()
 nist.texts().values()
 */
