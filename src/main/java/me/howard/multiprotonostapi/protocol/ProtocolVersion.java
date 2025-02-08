package me.howard.multiprotonostapi.protocol;

import org.jetbrains.annotations.NotNull;

import java.util.SortedSet;
import java.util.TreeSet;

public final class ProtocolVersion implements Comparable<ProtocolVersion>
{
    public static final SortedSet<ProtocolVersion> PROTOCOL_VERSIONS = new TreeSet<>();
    public static final SortedSet<ProtocolVersion> ALPHA_PROTOCOL_VERSIONS = new TreeSet<>();
    public static final SortedSet<ProtocolVersion> BETA_PROTOCOL_VERSIONS = new TreeSet<>();

    public static final ProtocolVersion BETA_14 = new ProtocolVersion(14, Type.BETA, "1.7", "1.7.3");
    public static final ProtocolVersion BETA_13 = new ProtocolVersion(13, Type.BETA, "1.6", "1.6.6");
    public static final ProtocolVersion BETA_11 = new ProtocolVersion(11, Type.BETA, "1.5", "1.5_01");
    public static final ProtocolVersion BETA_10 = new ProtocolVersion(10, Type.BETA, "1.4", "1.4_01");
    public static final ProtocolVersion BETA_9 = new ProtocolVersion(9, Type.BETA, "1.3", "1.3_01");
    public static final ProtocolVersion BETA_8 = new ProtocolVersion(8, Type.BETA, "1.2", "1.2_02");
    public static final ProtocolVersion BETA_INITIAL_8 = new ProtocolVersion(8, Type.BETA_INITIAL, "1.1_02");
    public static final ProtocolVersion BETA_INITIAL_7 = new ProtocolVersion(7, Type.BETA_INITIAL, "1.0", "1.1_01");

    public final int version;
    public final Type type;
    public final String firstClient;
    public final String lastClient;

    public ProtocolVersion(int version, Type type, String client)
    {
        this(version, type, client, client);
    }

    public ProtocolVersion(int version, Type type, String firstClient, String lastClient)
    {
        this.version = version;
        this.type = type;
        this.firstClient = firstClient;
        this.lastClient = lastClient;
        PROTOCOL_VERSIONS.add(this);
        if (type.alpha) ALPHA_PROTOCOL_VERSIONS.add(this);
        else BETA_PROTOCOL_VERSIONS.add(this);
    }

    public static ProtocolVersion fromString(String s)
    {
        if (s == null || s.isBlank()) return BETA_14;
        String s1 = s.replaceAll("\\s", "");
        return PROTOCOL_VERSIONS.stream().filter(p -> p.toString().equalsIgnoreCase(s1)).findFirst().orElse(BETA_14);
    }

    public String nameRange(boolean abbreviate)
    {
        return (firstClient.equals(lastClient) ? name(firstClient, abbreviate) :
                String.join((abbreviate ? "-" : " - "), name(firstClient, abbreviate), name(lastClient, abbreviate)));
    }

    public String name(boolean abbreviate)
    {
        return name(lastClient, abbreviate);
    }

    private String name(String s, boolean abbreviate)
    {
        return (abbreviate ? type.shortLabel : type.label) + (abbreviate ? "" : " ") + (type.alpha && !abbreviate ? "v" : "") + s;
    }

    @Override
    public String toString()
    {
        return String.join("_", type.toString(), String.valueOf(version)).toLowerCase();
    }

    public boolean isBefore(ProtocolVersion target)
    {
        return this.compareTo(target) < 0;
    }

    @Override
    public int compareTo(@NotNull ProtocolVersion version)
    {
        if (this.type != version.type) return this.type.compareTo(version.type);
        return Integer.compare(this.version, version.version);
    }

    public enum Type
    {
        ALPHA_INITIAL("Alpha", "a", true),
        ALPHA("Alpha", "a", true),
        ALPHA_LATER("Alpha", "a", true),
        BETA_INITIAL("Beta", "b", false),
        BETA("Beta", "b", false);

        public final String label;
        public final String shortLabel;
        public final int majorVersion;
        public final boolean alpha;

        Type(String label, String shortLabel, boolean alpha)
        {
            this.label = label;
            this.shortLabel = shortLabel;
            this.majorVersion = 1;
            this.alpha = alpha;
        }
    }
}
