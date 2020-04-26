package com.kmb.skript;


import java.sql.Date;

/**
 * This class represents SMS.
 */
public class SMSData{

    private String _id;
    // Number from which the sms was sent
    private String number;
    // SMS text body
    private String body;

    private String summary;

    private String person;

    private int type;

    private int thread_id;

    private long date;

    private String subject;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {

        return date;

    }

    public void setDate(long date) {
        this.date = date;

    }

    /**
     *  Get summary of Conversation
     **/
    public String getSummary() {
        return summary;
    }

    /**
     *  Set summary of Conversation
     **/
    public void setSummary(String summary) {
        this.summary = summary;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    }
