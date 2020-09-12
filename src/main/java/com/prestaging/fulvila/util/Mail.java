package com.prestaging.fulvila.util;

import org.springframework.stereotype.Component;

@Component
public class Mail {
    private String toMail;
    private String Subject;
    private String mailBody;

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }
}
