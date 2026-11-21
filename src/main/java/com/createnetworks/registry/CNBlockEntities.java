package com.createnetworks.registry;

import com.createnetworks.CreateNetWorks;
import com.createnetworks.content.devices.PcCaseBlockEntity;
import com.createnetworks.content.router.RouterBlockEntity;
import com.createnetworks.content.wireless.CellTowerBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CNBlockEntities {
    private static final CreateRegistrate REGISTRATE = CreateNetWorks.REGISTRATE;

    public static final BlockEntityEntry<RouterBlockEntity> ROUTER = REGISTRATE
            .blockEntity("router", RouterBlockEntity::new)
            .validBlocks(CNBlocks.ROUTER, CNBlocks.NETWORK_SWITCH, CNBlocks.FIBER_MODEM)
            .register();

    public static final BlockEntityEntry<CellTowerBlockEntity> CELL_TOWER = REGISTRATE
            .blockEntity("cell_tower", CellTowerBlockEntity::new)
            .validBlocks(CNBlocks.CELL_TOWER_CORE)
            .register();

    public static final BlockEntityEntry<PcCaseBlockEntity> PC_CASE = REGISTRATE
            .blockEntity("pc_case", PcCaseBlockEntity::new)
            .validBlocks(CNBlocks.PC_CASE)
            .register();

    public static void register() {
        CreateNetWorks.LOGGER.info("Registered Create: NetWorks block entities.");
    }
}
