package com.createnetworks.compat.powergrid;

import com.createnetworks.CreateNetWorks;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.energy.EnergyStorage;

/**
 * Integration layer for Create: Power Grid and NeoForge Energy (FE).
 */
public final class PowerGridCompat {
    private static boolean powerGridLoaded = false;

    private PowerGridCompat() {}

    public static void init() {
        powerGridLoaded = ModList.get().isLoaded("powergrid");
        if (powerGridLoaded) {
            CreateNetWorks.LOGGER.info("Create: Power Grid detected! Enabling direct Power Grid energy compatibility.");
        }
    }

    public static boolean isPowerGridLoaded() {
        return powerGridLoaded;
    }

    /**
     * Standard EnergyStorage for Create: NetWorks powered devices.
     */
    public static class CNPowerStorage extends EnergyStorage {
        public CNPowerStorage(int capacity, int maxReceive) {
            super(capacity, maxReceive, 0);
        }

        public boolean consumeEnergy(int amount) {
            if (energy >= amount) {
                energy -= amount;
                return true;
            }
            return false;
        }

        public void setEnergy(int energy) {
            this.energy = Math.max(0, Math.min(capacity, energy));
        }
    }
}
