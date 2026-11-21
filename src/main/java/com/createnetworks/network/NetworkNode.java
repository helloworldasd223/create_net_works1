package com.createnetworks.network;

import net.minecraft.core.BlockPos;

import java.util.List;

/**
 * Common interface for all blocks/entities that participate in the Create: NetWorks IP network.
 */
public interface NetworkNode {
    BlockPos getNodePos();

    IPv4Address getIpAddress();

    void setIpAddress(IPv4Address ip);

    boolean isPowered();

    boolean isRouterOrSwitch();

    List<NetworkLink> getLinks();

    void receivePacket(NetworkPacket packet, NetworkNode fromNode);
}
