package com.createnetworks.content.router;

import com.createnetworks.network.IPv4Address;
import com.createnetworks.network.NetworkLink;
import com.createnetworks.network.NetworkManager;
import com.createnetworks.network.NetworkNode;
import com.createnetworks.network.NetworkPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Router: requires an uplink (WAN cable) to provide local LAN/Wi-Fi and assign DHCP IPs.
 */
public class RouterBlockEntity extends AbstractNetworkBlockEntity {
    private final AtomicInteger nextHostOctet = new AtomicInteger(10);

    public RouterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, 20000, 40);
        this.ipAddress = new IPv4Address(192, 168, 1, 1);
    }

    @Override
    public boolean isRouterOrSwitch() {
        return true;
    }

    public boolean hasWanUplink() {
        return links.stream().anyMatch(link ->
                link.getMedium() == NetworkLink.Medium.CAT6_COPPER
                        || link.getMedium() == NetworkLink.Medium.FIBER_OPTIC);
    }

    public IPv4Address allocateDhcpIp() {
        int host = nextHostOctet.getAndIncrement();
        if (host > 250) {
            nextHostOctet.set(10);
            host = 10;
        }
        return new IPv4Address(192, 168, 1, host);
    }

    @Override
    public void receivePacket(NetworkPacket packet, NetworkNode fromNode) {
        super.receivePacket(packet, fromNode);
        if (packet.getProtocol() == NetworkPacket.Protocol.DHCP_DISCOVER && hasWanUplink()) {
            IPv4Address assigned = allocateDhcpIp();
            NetworkManager.getInstance().updateNodeIp(fromNode, assigned);
            NetworkPacket offer = new NetworkPacket(
                    this.ipAddress,
                    assigned,
                    NetworkPacket.Protocol.DHCP_OFFER,
                    assigned.toString(),
                    128,
                    level != null ? level.getGameTime() : 0
            );
            NetworkManager.getInstance().sendPacket(this, offer);
        }
    }
}
