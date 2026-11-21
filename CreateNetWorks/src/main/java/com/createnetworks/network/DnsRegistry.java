package com.createnetworks.network;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-game DNS registry mapping human-readable domains (e.g. "create.local") to IPv4 addresses.
 */
public class DnsRegistry {
    private final Map<String, IPv4Address> records = new ConcurrentHashMap<>();

    public void register(String domain, IPv4Address ip) {
        if (domain != null && !domain.isBlank() && ip != null && !ip.isUnassigned()) {
            records.put(domain.toLowerCase().trim(), ip);
        }
    }

    public void unregister(String domain) {
        if (domain != null) {
            records.remove(domain.toLowerCase().trim());
        }
    }

    public Optional<IPv4Address> resolve(String domain) {
        if (domain == null) return Optional.empty();
        return Optional.ofNullable(records.get(domain.toLowerCase().trim()));
    }

    public Map<String, IPv4Address> getAllRecords() {
        return Map.copyOf(records);
    }

    public void clear() {
        records.clear();
    }
}
