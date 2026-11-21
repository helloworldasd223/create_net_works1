package com.createnetworks.content.router;

import com.createnetworks.compat.powergrid.PowerGridCompat;
import com.createnetworks.network.IPv4Address;
import com.createnetworks.network.NetworkLink;
import com.createnetworks.network.NetworkManager;
import com.createnetworks.network.NetworkNode;
import com.createnetworks.network.NetworkPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNetworkBlockEntity extends BlockEntity implements NetworkNode {
    protected final PowerGridCompat.CNPowerStorage energyStorage;
    protected final List<NetworkLink> links = new ArrayList<>();
    protected IPv4Address ipAddress = IPv4Address.UNASSIGNED;
    protected final int energyPerTick;

    public AbstractNetworkBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int capacity, int energyPerTick) {
        super(type, pos, state);
        this.energyStorage = new PowerGridCompat.CNPowerStorage(capacity, capacity / 10);
        this.energyPerTick = energyPerTick;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            NetworkManager.getInstance().registerNode(this);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (level != null && !level.isClientSide) {
            NetworkManager.getInstance().unregisterNode(worldPosition);
        }
    }

    public void serverTick() {
        if (level == null || level.isClientSide) return;
        energyStorage.consumeEnergy(energyPerTick);
    }

    @Override
    public BlockPos getNodePos() {
        return worldPosition;
    }

    @Override
    public IPv4Address getIpAddress() {
        return ipAddress;
    }

    @Override
    public void setIpAddress(IPv4Address ip) {
        this.ipAddress = ip;
        setChanged();
    }

    @Override
    public boolean isPowered() {
        return energyStorage.getEnergyStored() >= energyPerTick;
    }

    @Override
    public List<NetworkLink> getLinks() {
        return links;
    }

    public PowerGridCompat.CNPowerStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public void receivePacket(NetworkPacket packet, NetworkNode fromNode) {
        if (packet.getProtocol() == NetworkPacket.Protocol.ICMP_PING) {
            NetworkPacket pong = new NetworkPacket(
                    this.ipAddress,
                    packet.getSourceIp(),
                    NetworkPacket.Protocol.ICMP_PONG,
                    "PONG",
                    64,
                    level != null ? level.getGameTime() : 0
            );
            NetworkManager.getInstance().sendPacket(this, pong);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag.putInt("IpAddress", ipAddress.toInt());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        energyStorage.setEnergy(tag.getInt("Energy"));
        ipAddress = new IPv4Address(tag.getInt("IpAddress"));
    }
}
