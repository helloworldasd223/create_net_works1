package com.createnetworks;

import com.createnetworks.compat.powergrid.PowerGridCompat;
import com.createnetworks.network.NetworkManager;
import com.createnetworks.ponder.CNPonderPlugin;
import com.createnetworks.registry.CNBlockEntities;
import com.createnetworks.registry.CNBlocks;
import com.createnetworks.registry.CNCreativeTabs;
import com.createnetworks.registry.CNDataComponents;
import com.createnetworks.registry.CNItems;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateNetWorks.MOD_ID)
public class CreateNetWorks {
    public static final String MOD_ID = "createnetworks";
    public static final String NAME = "Create: NetWorks";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public CreateNetWorks(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        REGISTRATE.registerEventListeners(modEventBus);

        CNDataComponents.register(modEventBus);
        CNCreativeTabs.register(modEventBus);
        CNBlocks.register();
        CNItems.register();
        CNBlockEntities.register();

        modEventBus.addListener(this::commonSetup);
        if (dist.isClient()) {
            modEventBus.addListener(this::clientSetup);
        }

        NeoForge.EVENT_BUS.addListener(this::onServerTick);
        NeoForge.EVENT_BUS.addListener(this::onServerStopping);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PowerGridCompat.init();
        LOGGER.info("{} initialized! Realistic IP, Fiber, Cellular & Power Grid integration ready.", NAME);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new CNPonderPlugin());
    }

    private void onServerTick(ServerTickEvent.Post event) {
        NetworkManager.getInstance().tick(event.getServer());
    }

    private void onServerStopping(ServerStoppingEvent event) {
        NetworkManager.getInstance().clear();
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
