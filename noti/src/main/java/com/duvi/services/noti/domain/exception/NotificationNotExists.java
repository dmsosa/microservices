package com.duvi.services.noti.domain.exception;

import com.duvi.services.noti.domain.enums.NotiType;

public class NotificationNotExists extends Exception {
    public NotificationNotExists(String accountName, NotiType notiType) {
        super("The account \"%1$s\" either does not exist or does not have any %2$s notifications!".formatted(accountName, notiType));
    };
    public NotificationNotExists(String accountName) {
        super("The account \"%1$s\" does not exist!".formatted(accountName));
    };
}