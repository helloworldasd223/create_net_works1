package com.createnetworks.network;

import net.minecraft.core.BlockPos;

/**
 * Physical or wireless link between two network nodes.
 */
public class NetworkLink {
    public enum Medium {
        CAT6_COPPER(1000, 2, 64, 0.001f),       // 1 Gbps, 2ms base, max 64 blocks
        FIBER_OPTIC(10000, 1, 1024, 0.0001f),   // 10 Gbps, 1ms base, max 1024 blocks
        WIFI_2_4GHZ(150, 8, 48, 0.01f),         // 150 Mbps, 8ms, 48 blocks
        WIFI_5GHZ(600, 4, 24, 0.005f),          // 600 Mbps, 4ms, 24 blocks
        CELLULAR_4G(100, 25, 256, 0.015f),      // 100 Mbps, 25ms, 256 blocks
        CELLULAR_5G(1200, 8, 128, 0.005f),      // 1.2 Gbps, 8ms, 128 blocks
        SATELLITE(50, 280, 4096, 0.03f);        // 50 Mbps, 280ms, long range

        public final int maxBandwidthMbps;
        public final int baseLatencyMs;
        public final int maxRangeBlocks;
        public final float basePacketLoss;

        Medium(int maxBandwidthMbps, int baseLatencyMs, int maxRangeBlocks, float basePacketLoss) {
            this.maxBandwidthMbps = maxBandwidthMbps;
            this.baseLatencyMs = baseLatencyMs;
            this.maxRangeBlocks = maxRangeBlocks;
            this.basePacketLoss = basePacketLoss;
        }
    }

    private final BlockPos nodeA;
    private final BlockPos nodeB;
    private final Medium medium;
    private int currentLoadMbps;

    public NetworkLink(BlockPos nodeA, BlockPos nodeB, Medium medium) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.medium = medium;
        this.currentLoadMbps = 0;
    }

    public BlockPos getNodeA() { return nodeA; }
    public BlockPos getNodeB() { return nodeB; }
    public Medium getMedium() { return medium; }

    public int getEffectiveLatencyMs() {
        double distance = Math.sqrt(nodeA.distSqr(nodeB));
        int distancePenalty = (int) (distance * 0.05);
        int congestionPenalty = currentLoadMbps > (medium.maxBandwidthMbps * 0.8) ? 25 : 0;
        return medium.baseLatencyMs + distancePenalty + congestionPenalty;
    }

    public boolean isOverloaded() {
        return currentLoadMbps >= medium.maxBandwidthMbps;
    }

    public void addLoad(int mbps) {
        this.currentLoadMbps += mbps;
    }

    public void resetLoad() {
        this.currentLoadMbps = 0;
    }
}
