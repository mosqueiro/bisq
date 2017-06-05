/*
 * This file is part of bisq.
 *
 * bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bisq.core.trade.messages;

import com.google.protobuf.ByteString;
import io.bisq.common.proto.network.NetworkEnvelope;
import io.bisq.generated.protobuffer.PB;
import io.bisq.network.p2p.MailboxMessage;
import io.bisq.network.p2p.NodeAddress;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public final class FiatTransferStartedMessage extends TradeMessage implements MailboxMessage {
    private final String buyerPayoutAddress;
    private final NodeAddress senderNodeAddress;
    private final String uid;
    private final byte[] buyerSignature;

    public FiatTransferStartedMessage(String tradeId,
                                      String buyerPayoutAddress,
                                      NodeAddress senderNodeAddress,
                                      byte[] buyerSignature,
                                      String uid) {
        super(tradeId);
        this.buyerPayoutAddress = buyerPayoutAddress;
        this.senderNodeAddress = senderNodeAddress;
        this.buyerSignature = buyerSignature;
        this.uid = uid;
    }

    @Override
    public PB.NetworkEnvelope toProtoNetworkEnvelope() {
        return NetworkEnvelope.getDefaultBuilder()
                .setFiatTransferStartedMessage(PB.FiatTransferStartedMessage.newBuilder()
                        .setTradeId(tradeId)
                        .setBuyerPayoutAddress(buyerPayoutAddress)
                        .setSenderNodeAddress(senderNodeAddress.toProtoMessage())
                        .setBuyerSignature(ByteString.copyFrom(buyerSignature))
                        .setUid(uid))
                .build();
    }

    public static FiatTransferStartedMessage fromProto(PB.FiatTransferStartedMessage proto) {
        return new FiatTransferStartedMessage(proto.getTradeId(),
                proto.getBuyerPayoutAddress(),
                NodeAddress.fromProto(proto.getSenderNodeAddress()),
                proto.getBuyerSignature().toByteArray(),
                proto.getUid()
        );
    }
}
