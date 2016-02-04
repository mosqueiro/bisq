package io.bitsquare.p2p.peers.messages.peers;

import io.bitsquare.app.Version;
import io.bitsquare.p2p.Message;

public abstract class PeerExchangeMessage implements Message {
    private final int messageVersion = Version.getP2PMessageVersion();

    @Override
    public int getMessageVersion() {
        return messageVersion;
    }

    @Override
    public String toString() {
        return "PeerExchangeMessage{" +
                "messageVersion=" + messageVersion +
                '}';
    }
}
