package com.tu.varna.chat.net.handler;

public enum Prefix {
    SMS("sms"),
    GMS("gms"),
    RMS("rms"),
    GFR("gfr"),
    FRI("frh"),
    GUG("gug"),
    LOG("log"),
    REG("reg");

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
