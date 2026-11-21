package com.createnetworks.content.devices;

import com.createnetworks.content.router.AbstractNetworkBlockEntity;
import com.createnetworks.network.IPv4Address;
import com.createnetworks.registry.CNItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Personal Computer System Unit: requires staged installation of Motherboard, RAM, SSD, and NIC.
 */
public class PcCaseBlockEntity extends AbstractNetworkBlockEntity {
    private boolean hasMotherboard = false;
    private boolean hasRam = false;
    private boolean hasSsd = false;
    private boolean hasNic = false;

    public PcCaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, 15000, 35);
        this.ipAddress = IPv4Address.UNASSIGNED;
    }

    @Override
    public boolean isRouterOrSwitch() {
        return false;
    }

    public boolean isFullyAssembled() {
        return hasMotherboard && hasRam && hasSsd && hasNic;
    }

    public boolean tryInstallComponent(ItemStack stack) {
        if (!hasMotherboard && stack.is(CNItems.PC_MOTHERBOARD.get())) {
            hasMotherboard = true;
            setChanged();
            return true;
        }
        if (hasMotherboard && !hasRam && stack.is(CNItems.RAM_MODULE.get())) {
            hasRam = true;
            setChanged();
            return true;
        }
        if (hasMotherboard && !hasSsd && stack.is(CNItems.SSD_DRIVE.get())) {
            hasSsd = true;
            setChanged();
            return true;
        }
        if (hasMotherboard && !hasNic && stack.is(CNItems.NETWORK_CARD.get())) {
            hasNic = true;
            setChanged();
            return true;
        }
        return false;
    }

    public String getAssemblyStatus() {
        return String.format("MB:%s RAM:%s SSD:%s NIC:%s",
                hasMotherboard ? "OK" : "-",
                hasRam ? "OK" : "-",
                hasSsd ? "OK" : "-",
                hasNic ? "OK" : "-");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("HasMB", hasMotherboard);
        tag.putBoolean("HasRAM", hasRam);
        tag.putBoolean("HasSSD", hasSsd);
        tag.putBoolean("HasNIC", hasNic);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        hasMotherboard = tag.getBoolean("HasMB");
        hasRam = tag.getBoolean("HasRAM");
        hasSsd = tag.getBoolean("HasSSD");
        hasNic = tag.getBoolean("HasNIC");
    }
}
