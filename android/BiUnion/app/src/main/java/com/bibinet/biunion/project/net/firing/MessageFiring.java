package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.models.MessageModel;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.MessageEmit;
import com.bibinet.biunion.project.net.ready.MessageReady;

/**
 * Created by bibinet on 2017-6-1.
 */
public class MessageFiring extends BaseFiring {
    private MessageReady messageReady;
    private MessageEmit messageEmit;

    public MessageFiring(MessageEmit messageEmit) {
        super(messageEmit);
        this.messageEmit = messageEmit;
        this.messageReady = new MessageReady();
    }

    public void getMessageData(int page, String type) {
        messageReady.getMessageData(page, type, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                MessageModel model = gson.fromJson(s, MessageModel.class);
                if (model.getData() != null) {
                    messageEmit.loadMoreSuccess(model.getData().getKnowledgeInfos());
                } else {
                    messageEmit.loadMoreFail(model.getResMessage());
                }
            }

            @Override
            protected void onRequestFail(String resMessage) {
                messageEmit.loadMoreFail(resMessage);
            }
        });
    }

}
