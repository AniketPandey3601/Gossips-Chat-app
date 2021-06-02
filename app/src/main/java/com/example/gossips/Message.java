package com.example.gossips;

public class Message {
    private String messageid, message, senderid;
    private long timestrap;

    public Message() {
    }

    public Message(String message, String senderid, long timestrap) {
        this.message = message;
        this.senderid = senderid;
        this.timestrap = timestrap;
    }


    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public long getTimestrap() {
        return timestrap;
    }

    public void setTimestrap(long timestrap) {
        this.timestrap = timestrap;
    }
}

