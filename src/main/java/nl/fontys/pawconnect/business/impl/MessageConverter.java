package nl.fontys.pawconnect.business.impl;

import nl.fontys.pawconnect.domain.Message;
import nl.fontys.pawconnect.persistence.entity.MessageEntity;

public final class MessageConverter {
    private MessageConverter() {}

    public static Message convert(MessageEntity messageEntity) {
        return Message.builder()
                .id(messageEntity.getId())
                .content(messageEntity.getContent())
                .dateSent(messageEntity.getDateSent())
                .sender(UserConverter.convert(messageEntity.getSender()))
                .recipient(UserConverter.convert(messageEntity.getRecipient()))
                .build();
    }
}