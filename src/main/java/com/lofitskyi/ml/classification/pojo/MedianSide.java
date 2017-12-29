package com.lofitskyi.ml.classification.pojo;

public enum MedianSide {
    LOWER_SIDE ("low"),
    HIGHER_SIDE ("high");

    private String value;

    MedianSide(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
