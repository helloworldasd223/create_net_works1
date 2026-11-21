package com.createnetworks.network;

/**
 * Pure-math RF helpers with no Minecraft dependencies, so they can be unit tested on a plain JVM.
 */
public final class SignalMath {
    private SignalMath() {}

    /**
     * Free-space path loss approximation scaled for Minecraft blocks.
     */
    public static double freeSpacePathLossDb(double distanceBlocks, double frequencyGhz) {
        double distance = Math.max(1.0, distanceBlocks);
        return 20.0 * Math.log10(distance) + 20.0 * Math.log10(frequencyGhz) + 24.0;
    }

    /**
     * Converts dBm to 0-4 signal bars.
     */
    public static int rssiToBars(double rssiDbm) {
        if (rssiDbm >= -65) return 4;
        if (rssiDbm >= -80) return 3;
        if (rssiDbm >= -95) return 2;
        if (rssiDbm >= -110) return 1;
        return 0;
    }

    /**
     * Combines transmit power, path loss and obstacle attenuation into a received dBm value.
     */
    public static double receivedPowerDbm(double txPowerDbm, double distanceBlocks, double frequencyGhz, double obstacleLossDb) {
        return txPowerDbm - freeSpacePathLossDb(distanceBlocks, frequencyGhz) - obstacleLossDb;
    }
}
