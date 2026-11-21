package com.createnetworks.network;

import java.util.UUID;

/**
 * Simulated network packet transmitted between NetworkNodes.
 */
public class NetworkPacket {
    public enum Protocol {
        ICMP_PING,
        ICMP_PONG,
        DHCP_DISCOVER,
        DHCP_OFFER,
        DNS_QUERY,
        DNS_RESPONSE,
        HTTP_GET,
        HTTP_RESPONSE,
        CHAT_MESSAGE,
        CONTROL_SIGNAL
    }

    private final UUID packetId;
    private final IPv4Address sourceIp;
    private final IPv4Address destinationIp;
    private final Protocol protocol;
    private final String payload;
    private final int sizeBytes;
    private int ttl;
    private int accumulatedLatencyMs;
    private final long createdAtTick;

    public NetworkPacket(IPv4Address sourceIp, IPv4Address destinationIp, Protocol protocol, String payload, int sizeBytes, long createdAtTick) {
        this.packetId = UUID.randomUUID();
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.protocol = protocol;
        this.payload = payload;
        this.sizeBytes = Math.max(64, sizeBytes);
        this.ttl = 64;
        this.accumulatedLatencyMs = 0;
        this.createdAtTick = createdAtTick;
    }

    public UUID getPacketId() { return packetId; }
    public IPv4Address getSourceIp() { return sourceIp; }
    public IPv4Address getDestinationIp() { return destinationIp; }
    public Protocol getProtocol() { return protocol; }
    public String getPayload() { return payload; }
    public int getSizeBytes() { return sizeBytes; }
    public int getTtl() { return ttl; }
    public int getAccumulatedLatencyMs() { return accumulatedLatencyMs; }
    public long getCreatedAtTick() { return createdAtTick; }

    public boolean decrementTtl() {
        ttl--;
        return ttl > 0;
    }

    public void addLatency(int ms) {
        this.accumulatedLatencyMs += Math.max(0, ms);
    }
}
