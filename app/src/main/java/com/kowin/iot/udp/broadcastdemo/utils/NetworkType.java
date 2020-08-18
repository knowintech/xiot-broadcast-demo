package com.kowin.iot.udp.broadcastdemo.utils;

public enum NetworkType {

    UNDEFINED("undefined"),
    WIFI("wifi"),
    LAN("lan"),
    WAN("wan");

    private String value;

    NetworkType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static NetworkType fromString(String v) {
        for (NetworkType t : values()) {
            if (t.value.equals(v)) {
                return t;
            }
        }

        return UNDEFINED;
    }
}
