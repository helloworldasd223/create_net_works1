package com.createnetworks.registry;

import com.createnetworks.CreateNetWorks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateNetWorks.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.createnetworks.main"))
                    .icon(() -> CNBlocks.ROUTER.asStack())
                    .displayItems((params, output) -> {
                        // Networking & Core
                        output.accept(CNBlocks.ROUTER.asStack());
                        output.accept(CNBlocks.NETWORK_SWITCH.asStack());
                        output.accept(CNBlocks.FIBER_MODEM.asStack());
                        output.accept(CNBlocks.MEDIA_CONVERTER.asStack());
                        output.accept(CNBlocks.SERVER_RACK.asStack());
                        output.accept(CNBlocks.PATCH_PANEL.asStack());
                        output.accept(CNBlocks.WALL_JACK.asStack());

                        // Wireless & Cellular
                        output.accept(CNBlocks.CELL_TOWER_CORE.asStack());
                        output.accept(CNBlocks.CELL_ANTENNA.asStack());
                        output.accept(CNBlocks.WIFI_ACCESS_POINT.asStack());
                        output.accept(CNBlocks.SATELLITE_DISH.asStack());

                        // Devices
                        output.accept(CNBlocks.PC_CASE.asStack());
                        output.accept(CNBlocks.PC_MONITOR.asStack());
                        output.accept(CNBlocks.CHARGING_DOCK.asStack());
                        output.accept(CNBlocks.NETWORK_LINK_CONTROLLER.asStack());

                        // Kinetic (Create SU) Machines
                        output.accept(CNBlocks.CRIMPING_PRESS.asStack());
                        output.accept(CNBlocks.FIBER_EXTRUDER.asStack());
                        output.accept(CNBlocks.SERVER_COOLING_FAN.asStack());

                        // Items & Cables
                        output.accept(CNItems.SMARTPHONE.asStack());
                        output.accept(CNItems.SIM_CARD.asStack());
                        output.accept(CNItems.CAT6_CABLE.asStack());
                        output.accept(CNItems.FIBER_OPTIC_CABLE.asStack());
                        output.accept(CNItems.RJ45_CONNECTOR.asStack());
                        output.accept(CNItems.SFP_TRANSCEIVER.asStack());
                        output.accept(CNItems.NETWORK_TESTER.asStack());

                        // Staged Assembly Components
                        output.accept(CNItems.PHONE_CHASSIS.asStack());
                        output.accept(CNItems.PRINTED_CIRCUIT_BOARD.asStack());
                        output.accept(CNItems.SOC_PROCESSOR.asStack());
                        output.accept(CNItems.MODEM_5G_CHIP.asStack());
                        output.accept(CNItems.OLED_DISPLAY.asStack());
                        output.accept(CNItems.LITHIUM_BATTERY.asStack());
                        output.accept(CNItems.PC_MOTHERBOARD.asStack());
                        output.accept(CNItems.RAM_MODULE.asStack());
                        output.accept(CNItems.SSD_DRIVE.asStack());
                        output.accept(CNItems.NETWORK_CARD.asStack());
                    })
                    .build());

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
