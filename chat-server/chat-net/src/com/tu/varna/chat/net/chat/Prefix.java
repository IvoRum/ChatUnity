package com.tu.varna.chat.net.chat;

public enum Prefix {
    SMS("sms"),
    GMS("gms"),
    GFR("gfr"),
    FRI("frh"),
    GUG("gug");

    private final String code;

    Prefix(String code) {
        this.code = code;
    }

    public String getNameCode() {
        return code;
    }

    public static Prefix getInstance(String code) throws IllegalAccessException {
        for (Prefix prefix : values()) {
            if (prefix.getNameCode().equals(code)) {
                return prefix;
            }
        }
        throw new IllegalAccessException();
    }
}
