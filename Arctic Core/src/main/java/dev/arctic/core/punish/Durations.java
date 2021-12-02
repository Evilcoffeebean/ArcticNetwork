package dev.arctic.core.punish;

public enum Durations {

    //in hours

    CHAT_SEVERITY_1(1),
    CHAT_SEVERITY_2(6),
    CHAT_SEVERITY_3(24),
    CHAT_SEVERITY_4(48),
    CHAT_SEVERITY_5(9999),

    BAN_SEVERITY_1(12),
    BAN_SEVERITY_2(48),
    BAN_SEVERITY_3(36),
    BAN_SEVERITY_4(120),
    BAN_SEVERITY_5(9999),

    GEN_SEVERITY_1(2),
    GEN_SEVERITY_2(6),
    GEN_SEVERITY_3(24);

    private long duration;

    Durations(long d) {
        this.duration = d;
    }

    public long getDuration() {
        return duration;
    }
}
