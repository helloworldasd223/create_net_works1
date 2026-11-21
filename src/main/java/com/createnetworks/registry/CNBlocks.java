package com.createnetworks.registry;

import com.createnetworks.CreateNetWorks;
import com.createnetworks.content.devices.PcCaseBlockEntity;
import com.createnetworks.content.kinetics.KineticMachineBlock;
import com.createnetworks.content.router.NetworkDeviceBlock;
import com.createnetworks.content.router.RouterBlockEntity;
import com.createnetworks.content.wireless.CellTowerBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class CNBlocks {
    private static final CreateRegistrate REGISTRATE = CreateNetWorks.REGISTRATE;

    // Core Networking (Powered by Create: Power Grid / FE)
    public static final BlockEntry<NetworkDeviceBlock> ROUTER = REGISTRATE
            .block("router", p -> new NetworkDeviceBlock(p, (pos, st) -> new RouterBlockEntity(CNBlockEntities.ROUTER.get(), pos, st)))
            .properties(p -> p.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<NetworkDeviceBlock> NETWORK_SWITCH = REGISTRATE
            .block("network_switch", p -> new NetworkDeviceBlock(p, (pos, st) -> new RouterBlockEntity(CNBlockEntities.ROUTER.get(), pos, st)))
            .properties(p -> p.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<NetworkDeviceBlock> FIBER_MODEM = REGISTRATE
            .block("fiber_modem", p -> new NetworkDeviceBlock(p, (pos, st) -> new RouterBlockEntity(CNBlockEntities.ROUTER.get(), pos, st)))
            .properties(p -> p.mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> MEDIA_CONVERTER = REGISTRATE
            .block("media_converter", Block::new)
            .properties(p -> p.mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> SERVER_RACK = REGISTRATE
            .block("server_rack", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).strength(4.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PATCH_PANEL = REGISTRATE
            .block("patch_panel", Block::new)
            .properties(p -> p.mapColor(MapColor.METAL).strength(1.5f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> WALL_JACK = REGISTRATE
            .block("wall_jack", Block::new)
            .properties(p -> p.mapColor(MapColor.QUARTZ).strength(1.0f).sound(SoundType.STONE))
            .simpleItem()
            .register();

    // Wireless & Cellular
    public static final BlockEntry<NetworkDeviceBlock> CELL_TOWER_CORE = REGISTRATE
            .block("cell_tower_core", p -> new NetworkDeviceBlock(p, (pos, st) -> new CellTowerBlockEntity(CNBlockEntities.CELL_TOWER.get(), pos, st)))
            .properties(p -> p.mapColor(MapColor.METAL).strength(5.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> CELL_ANTENNA = REGISTRATE
            .block("cell_antenna", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(2.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> WIFI_ACCESS_POINT = REGISTRATE
            .block("wifi_access_point", Block::new)
            .properties(p -> p.mapColor(MapColor.QUARTZ).strength(1.5f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> SATELLITE_DISH = REGISTRATE
            .block("satellite_dish", Block::new)
            .properties(p -> p.mapColor(MapColor.METAL).strength(3.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    // Devices
    public static final BlockEntry<NetworkDeviceBlock> PC_CASE = REGISTRATE
            .block("pc_case", p -> new NetworkDeviceBlock(p, (pos, st) -> new PcCaseBlockEntity(CNBlockEntities.PC_CASE.get(), pos, st)))
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).strength(2.5f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PC_MONITOR = REGISTRATE
            .block("pc_monitor", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).strength(1.5f).sound(SoundType.GLASS))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> CHARGING_DOCK = REGISTRATE
            .block("charging_dock", Block::new)
            .properties(p -> p.mapColor(MapColor.METAL).strength(1.5f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> NETWORK_LINK_CONTROLLER = REGISTRATE
            .block("network_link_controller", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_CYAN).strength(2.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    // Kinetic (Create SU) Machines
    public static final BlockEntry<KineticMachineBlock> CRIMPING_PRESS = REGISTRATE
            .block("crimping_press", KineticMachineBlock::new)
            .properties(p -> p.mapColor(MapColor.METAL).strength(3.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<KineticMachineBlock> FIBER_EXTRUDER = REGISTRATE
            .block("fiber_extruder", KineticMachineBlock::new)
            .properties(p -> p.mapColor(MapColor.METAL).strength(3.5f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static final BlockEntry<KineticMachineBlock> SERVER_COOLING_FAN = REGISTRATE
            .block("server_cooling_fan", KineticMachineBlock::new)
            .properties(p -> p.mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.METAL))
            .simpleItem()
            .register();

    public static void register() {
        CreateNetWorks.LOGGER.info("Registered Create: NetWorks blocks.");
    }
}
