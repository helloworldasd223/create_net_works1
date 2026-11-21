package com.createnetworks.content.wireless;

import com.createnetworks.content.router.AbstractNetworkBlockEntity;
import com.createnetworks.network.IPv4Address;
import com.createnetworks.network.RadioPropagation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Cellular Base Station / Tower Core: broadcasts 4G/5G waves when powered and connected to backbone.
 */
public class CellTowerBlockEntity extends AbstractNetworkBlockEntity {
    private double txPowerDbm = 43.0; // ~20W macro cell
    private double frequencyGhz = 3.5; // 5G n78 band

    public CellTowerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, 100000, 180);
        this.ipAddress = new IPv4Address(10, 10, 0, 1);
    }

    @Override
    public boolean isRouterOrSwitch() {
        return true;
    }

    public double getSignalAt(BlockPos receiverPos) {
        if (!isPowered() || links.isEmpty()) {
            return -120.0;
        }
        return RadioPropagation.calculateRssiDbm(level, worldPosition, receiverPos, txPowerDbm, frequencyGhz);
    }

    public double getTxPowerDbm() { return txPowerDbm; }
    public double getFrequencyGhz() { return frequencyGhz; }
}
