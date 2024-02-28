package com.duvi.services.noti.domain;

public enum NotiType {
    BACKUP("backup.email.subject", "backup.email.text", "backup.email.attachment"),
    REMIND("backup.email.subject", "backup.email.text", null);

    private String subject;
    private String text;
    private String attachment;

    NotiType(String subject, String text, String attachment) {
        this.subject = subject;
        this.text = text;
        this.attachment = attachment;
    }

    public String getSubject() {
        return subject;
    };
    public String getText() {
        return text;
    };
    public String getAttachment() {
        return attachment;
    };
}
