package org.ahmedukamel.gazl.dto.charity;

public record CharityResponse(
        Integer id,

        String name,

        String number,

        Double latitude,

        Double longitude
) {
}