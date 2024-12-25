package nl.fontys.pawconnect.business.impl.converter;

import nl.fontys.pawconnect.domain.Image;
import nl.fontys.pawconnect.persistence.entity.ImageEntity;

public final class ImageConverter {
    private ImageConverter() {}

    public static Image convert(ImageEntity imageEntity) {
        return Image.builder()
                .id(imageEntity.getId())
                .url(imageEntity.getUrl())
                .build();
    }
}