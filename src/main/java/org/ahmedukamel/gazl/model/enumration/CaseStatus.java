package org.ahmedukamel.gazl.model.enumration;

import lombok.Getter;

@Getter
public enum CaseStatus {
    TODO("Red"),
    DOING("Orange"),
    DONE("Green");

    private final String color;

    CaseStatus(String color) {
        this.color = color;
    }
}