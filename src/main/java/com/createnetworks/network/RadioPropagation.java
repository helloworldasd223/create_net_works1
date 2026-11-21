package com.createnetworks.network;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Calculates realistic RF attenuation (dBm / RSSI) for Cellular (4G/5G) and Wi-Fi signals.
 */
public final class RadioPropagation {
    private RadioPropagation() {}

    /**
     * Computes received signal strength in dBm (typically -30 dBm excellent to -120 dBm no signal).
     */
    public static double calculateRssiDbm(Level level, BlockPos transmitter, BlockPos receiver, double txPowerDbm, double frequencyGhz) {
        double distance = Math.max(1.0, Math.sqrt(transmitter.distSqr(receiver)));

        // Free-space path loss approximation scaled for Minecraft blocks
        double fspl = 20.0 * Math.log10(distance) + 20.0 * Math.log10(frequencyGhz) + 24.0;

        // Raymarch obstacles between transmitter and receiver
        double obstacleLoss = 0.0;
        if (level != null) {
            Vec3 start = Vec3.atCenterOf(transmitter);
            Vec3 end = Vec3.atCenterOf(receiver);
            Vec3 dir = end.subtract(start).normalize();
            int steps = (int) Math.min(distance, 256);
            BlockPos lastPos = null;
            for (int i = 1; i < steps; i++) {
                Vec3 sample = start.add(dir.scale(i));
                BlockPos pos = BlockPos.containing(sample);
                if (!pos.equals(lastPos) && !pos.equals(transmitter) && !pos.equals(receiver)) {
                    BlockState state = level.getBlockState(pos);
                    obstacleLoss += getBlockAttenuationDb(state, frequencyGhz);
                    lastPos = pos;
                }
            }
        }

        return txPowerDbm - fspl - obstacleLoss;
    }

    public static double getBlockAttenuationDb(BlockState state, double frequencyGhz) {
        if (state.isAir()) return 0.0;
        // High frequency (5G / 5GHz) attenuates much more through solid walls
        double freqFactor = frequencyGhz >= 3.0 ? 1.6 : 1.0;

        if (state.is(Blocks.IRON_BLOCK) || state.is(Blocks.OBSIDIAN) || state.is(Blocks.NETHERITE_BLOCK)) {
            return 12.0 * freqFactor;
        }
        if (state.is(BlockTags.STONE_ORE_REPLACEABLES) || state.is(Blocks.STONE) || state.is(Blocks.DEEPSLATE)) {
            return 4.5 * freqFactor;
        }
        if (state.is(BlockTags.PLANKS) || state.is(BlockTags.LOGS)) {
            return 1.8 * freqFactor;
        }
        if (state.is(Blocks.GLASS) || state.is(BlockTags.LEAVES)) {
            return 0.5 * freqFactor;
        }
        return state.canOcclude() ? 3.0 * freqFactor : 0.2;
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
}
