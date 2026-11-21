package com.createnetworks.network;

import java.util.Objects;

/**
 * Represents a 32-bit IPv4 address with subnet mask utilities.
 */
public final class IPv4Address {
    public static final IPv4Address UNASSIGNED = new IPv4Address(0);
    public static final IPv4Address LOOPBACK = new IPv4Address(127, 0, 0, 1);

    private final int address;

    public IPv4Address(int address) {
        this.address = address;
    }

    public IPv4Address(int a, int b, int c, int d) {
        this.address = ((a & 0xFF) << 24)
                | ((b & 0xFF) << 16)
                | ((c & 0xFF) << 8)
                | (d & 0xFF);
    }

    public static IPv4Address parse(String ip) {
        String[] parts = ip.trim().split("\\.");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid IPv4 address: " + ip);
        }
        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[1]);
        int c = Integer.parseInt(parts[2]);
        int d = Integer.parseInt(parts[3]);
        if (a < 0 || a > 255 || b < 0 || b > 255 || c < 0 || c > 255 || d < 0 || d > 255) {
            throw new IllegalArgumentException("IPv4 octet out of range: " + ip);
        }
        return new IPv4Address(a, b, c, d);
    }

    public boolean isSameSubnet(IPv4Address other, int prefixLength) {
        int mask = prefixLength == 0 ? 0 : (0xFFFFFFFF << (32 - prefixLength));
        return (this.address & mask) == (other.address & mask);
    }

    public boolean isPrivate() {
        int first = (address >>> 24) & 0xFF;
        int second = (address >>> 16) & 0xFF;
        return first == 10
                || (first == 172 && second >= 16 && second <= 31)
                || (first == 192 && second == 168);
    }

    public boolean isUnassigned() {
        return address == 0;
    }

    public int toInt() {
        return address;
    }

    @Override
    public String toString() {
        return ((address >>> 24) & 0xFF) + "."
                + ((address >>> 16) & 0xFF) + "."
                + ((address >>> 8) & 0xFF) + "."
                + (address & 0xFF);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IPv4Address that)) return false;
        return address == that.address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
