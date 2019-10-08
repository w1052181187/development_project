package com.bibinet.biunion.project.models;

import java.io.Serializable;

/**
 * Created by Wh on 2017-12-4.
 */

public class BaseModel implements Serializable{
    private String resCode;
    private String resMessage;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }
}
