package com.createnetworks.network;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NetworkEngineTest {

    @Test
    public void testIPv4ParsingAndSubnets() {
        IPv4Address router = IPv4Address.parse("192.168.1.1");
        IPv4Address pc = IPv4Address.parse("192.168.1.42");
        IPv4Address remote = IPv4Address.parse("10.0.0.5");

        assertTrue(router.isSameSubnet(pc, 24));
        assertFalse(router.isSameSubnet(remote, 24));
        assertTrue(router.isPrivate());
        assertTrue(remote.isPrivate());
        assertEquals("192.168.1.1", router.toString());
    }

    @Test
    public void testDnsRegistry() {
        DnsRegistry dns = new DnsRegistry();
        IPv4Address serverIp = new IPv4Address(10, 0, 1, 100);
        dns.register("create.local", serverIp);

        assertEquals(serverIp, dns.resolve("CREATE.LOCAL").orElseThrow());
        dns.unregister("create.local");
        assertTrue(dns.resolve("create.local").isEmpty());
    }

    @Test
    public void testRadioSignalBars() {
        assertEquals(4, RadioPropagation.rssiToBars(-55.0));
        assertEquals(3, RadioPropagation.rssiToBars(-75.0));
        assertEquals(2, RadioPropagation.rssiToBars(-90.0));
        assertEquals(1, RadioPropagation.rssiToBars(-105.0));
        assertEquals(0, RadioPropagation.rssiToBars(-120.0));
    }
}
