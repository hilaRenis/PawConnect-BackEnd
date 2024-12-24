package nl.fontys.pawconnect.business.impl;

import nl.fontys.pawconnect.domain.Announcement;
import nl.fontys.pawconnect.persistence.entity.AnnouncementEntity;

public final class AnnouncementConverter {
    private AnnouncementConverter() {}

    public static Announcement convert(AnnouncementEntity entity) {
        return Announcement.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .dateMade(entity.getDateMade())
                .announcer(UserConverter.convert(entity.getAnnouncer()))
                .build();
    }
}