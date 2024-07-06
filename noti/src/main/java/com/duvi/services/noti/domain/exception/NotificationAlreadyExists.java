package com.duvi.services.noti.domain.exception;

import com.duvi.services.noti.domain.enums.NotiType;

public class NotificationAlreadyExists extends Exception {
    public NotificationAlreadyExists(String accountName, NotiType notiType) {
        super("The account \"%1$s\" is already subscribed to %2$s notifications!".formatted(accountName, notiType));
    };
}
