package com.createnetworks.ponder;

import com.createnetworks.CreateNetWorks;
import com.createnetworks.registry.CNBlocks;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class CNPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return CreateNetWorks.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(CNBlocks.ROUTER.getId())
                .addStoryBoard("router_setup", CNScenes::routerSetup);

        helper.forComponents(CNBlocks.CELL_TOWER_CORE.getId())
                .addStoryBoard("cell_tower_setup", CNScenes::cellTowerSetup);

        helper.forComponents(CNBlocks.PC_CASE.getId())
                .addStoryBoard("pc_assembly", CNScenes::pcAssembly);

        helper.forComponents(CNBlocks.CRIMPING_PRESS.getId(), CNBlocks.FIBER_EXTRUDER.getId())
                .addStoryBoard("kinetic_cables", CNScenes::kineticCables);
    }
}
