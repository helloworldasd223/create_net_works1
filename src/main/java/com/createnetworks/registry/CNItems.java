package com.createnetworks.registry;

import com.createnetworks.CreateNetWorks;
import com.createnetworks.content.cable.CableItem;
import com.createnetworks.content.devices.NetworkTesterItem;
import com.createnetworks.content.devices.SmartphoneItem;
import com.createnetworks.network.NetworkLink;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

public class CNItems {
    private static final CreateRegistrate REGISTRATE = CreateNetWorks.REGISTRATE;

    // Functional Devices & Tools
    public static final ItemEntry<SmartphoneItem> SMARTPHONE = REGISTRATE
            .item("smartphone", SmartphoneItem::new)
            .register();

    public static final ItemEntry<Item> SIM_CARD = REGISTRATE
            .item("sim_card", Item::new)
            .register();

    public static final ItemEntry<CableItem> CAT6_CABLE = REGISTRATE
            .item("cat6_cable", p -> new CableItem(p, NetworkLink.Medium.CAT6_COPPER))
            .register();

    public static final ItemEntry<CableItem> FIBER_OPTIC_CABLE = REGISTRATE
            .item("fiber_optic_cable", p -> new CableItem(p, NetworkLink.Medium.FIBER_OPTIC))
            .register();

    public static final ItemEntry<Item> RJ45_CONNECTOR = REGISTRATE
            .item("rj45_connector", Item::new)
            .register();

    public static final ItemEntry<Item> SFP_TRANSCEIVER = REGISTRATE
            .item("sfp_transceiver", Item::new)
            .register();

    public static final ItemEntry<NetworkTesterItem> NETWORK_TESTER = REGISTRATE
            .item("network_tester", NetworkTesterItem::new)
            .register();

    // Staged Assembly Components (Smartphone & PC)
    public static final ItemEntry<Item> PHONE_CHASSIS = REGISTRATE
            .item("phone_chassis", Item::new)
            .register();

    public static final ItemEntry<Item> PRINTED_CIRCUIT_BOARD = REGISTRATE
            .item("printed_circuit_board", Item::new)
            .register();

    public static final ItemEntry<Item> SOC_PROCESSOR = REGISTRATE
            .item("soc_processor", Item::new)
            .register();

    public static final ItemEntry<Item> MODEM_5G_CHIP = REGISTRATE
            .item("modem_5g_chip", Item::new)
            .register();

    public static final ItemEntry<Item> OLED_DISPLAY = REGISTRATE
            .item("oled_display", Item::new)
            .register();

    public static final ItemEntry<Item> LITHIUM_BATTERY = REGISTRATE
            .item("lithium_battery", Item::new)
            .register();

    public static final ItemEntry<Item> PC_MOTHERBOARD = REGISTRATE
            .item("pc_motherboard", Item::new)
            .register();

    public static final ItemEntry<Item> RAM_MODULE = REGISTRATE
            .item("ram_module", Item::new)
            .register();

    public static final ItemEntry<Item> SSD_DRIVE = REGISTRATE
            .item("ssd_drive", Item::new)
            .register();

    public static final ItemEntry<Item> NETWORK_CARD = REGISTRATE
            .item("network_card", Item::new)
            .register();

    public static void register() {
        CreateNetWorks.LOGGER.info("Registered Create: NetWorks items.");
    }
}
