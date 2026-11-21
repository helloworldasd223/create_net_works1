package com.createnetworks.network;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Central simulation engine for IP routing, packet delivery, DNS, and link congestion.
 */
public class NetworkManager {
    private static final NetworkManager INSTANCE = new NetworkManager();

    private final Map<BlockPos, NetworkNode> nodes = new ConcurrentHashMap<>();
    private final Map<IPv4Address, BlockPos> ipToPos = new ConcurrentHashMap<>();
    private final Queue<PendingPacket> packetQueue = new ConcurrentLinkedQueue<>();
    private final DnsRegistry dnsRegistry = new DnsRegistry();

    private NetworkManager() {}

    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    public DnsRegistry getDnsRegistry() {
        return dnsRegistry;
    }

    public void registerNode(NetworkNode node) {
        nodes.put(node.getNodePos(), node);
        if (node.getIpAddress() != null && !node.getIpAddress().isUnassigned()) {
            ipToPos.put(node.getIpAddress(), node.getNodePos());
        }
    }

    public void unregisterNode(BlockPos pos) {
        NetworkNode removed = nodes.remove(pos);
        if (removed != null && removed.getIpAddress() != null) {
            ipToPos.remove(removed.getIpAddress());
        }
    }

    public void updateNodeIp(NetworkNode node, IPv4Address newIp) {
        if (node.getIpAddress() != null) {
            ipToPos.remove(node.getIpAddress());
        }
        node.setIpAddress(newIp);
        if (newIp != null && !newIp.isUnassigned()) {
            ipToPos.put(newIp, node.getNodePos());
        }
    }

    public Optional<NetworkNode> getNodeByIp(IPv4Address ip) {
        BlockPos pos = ipToPos.get(ip);
        return pos == null ? Optional.empty() : Optional.ofNullable(nodes.get(pos));
    }

    public Optional<NetworkNode> getNodeAt(BlockPos pos) {
        return Optional.ofNullable(nodes.get(pos));
    }

    public void sendPacket(NetworkNode source, NetworkPacket packet) {
        if (source == null || !source.isPowered() || packet == null) return;
        packetQueue.add(new PendingPacket(source.getNodePos(), packet));
    }

    public void tick(MinecraftServer server) {
        // Reset link loads each tick
        for (NetworkNode node : nodes.values()) {
            for (NetworkLink link : node.getLinks()) {
                link.resetLoad();
            }
        }

        int processed = 0;
        while (!packetQueue.isEmpty() && processed < 256) {
            PendingPacket pending = packetQueue.poll();
            if (pending == null) break;
            routePacket(pending);
            processed++;
        }
    }

    private void routePacket(PendingPacket pending) {
        NetworkNode start = nodes.get(pending.sourcePos);
        BlockPos targetPos = ipToPos.get(pending.packet.getDestinationIp());
        if (start == null || targetPos == null) return;

        NetworkNode target = nodes.get(targetPos);
        if (target == null || !target.isPowered()) return;

        // BFS shortest path through powered nodes
        List<BlockPos> path = findPath(pending.sourcePos, targetPos);
        if (path.isEmpty()) return;

        int totalLatency = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            NetworkNode curr = nodes.get(path.get(i));
            BlockPos nextPos = path.get(i + 1);
            if (curr == null || !curr.isPowered()) return;

            for (NetworkLink link : curr.getLinks()) {
                if (link.getNodeB().equals(nextPos) || link.getNodeA().equals(nextPos)) {
                    totalLatency += link.getEffectiveLatencyMs();
                    link.addLoad(Math.max(1, pending.packet.getSizeBytes() / 1024));
                    break;
                }
            }
        }

        pending.packet.addLatency(totalLatency);
        target.receivePacket(pending.packet, start);
    }

    public List<BlockPos> findPath(BlockPos start, BlockPos goal) {
        if (start.equals(goal)) return List.of(start);
        Queue<BlockPos> queue = new ArrayDeque<>();
        Map<BlockPos, BlockPos> cameFrom = new HashMap<>();
        queue.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            if (current.equals(goal)) {
                List<BlockPos> path = new ArrayList<>();
                for (BlockPos at = goal; at != null; at = cameFrom.get(at)) {
                    path.add(at);
                }
                Collections.reverse(path);
                return path;
            }

            NetworkNode node = nodes.get(current);
            if (node == null || !node.isPowered()) continue;

            for (NetworkLink link : node.getLinks()) {
                BlockPos neighbor = link.getNodeA().equals(current) ? link.getNodeB() : link.getNodeA();
                if (!cameFrom.containsKey(neighbor)) {
                    NetworkNode neighborNode = nodes.get(neighbor);
                    if (neighborNode != null && neighborNode.isPowered()) {
                        if (neighbor.equals(goal) || neighborNode.isRouterOrSwitch()) {
                            cameFrom.put(neighbor, current);
                            queue.add(neighbor);
                        }
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    public void clear() {
        nodes.clear();
        ipToPos.clear();
        packetQueue.clear();
        dnsRegistry.clear();
    }

    private record PendingPacket(BlockPos sourcePos, NetworkPacket packet) {}
}
