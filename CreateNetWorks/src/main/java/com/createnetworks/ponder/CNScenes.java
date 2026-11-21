package com.createnetworks.ponder;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;

public class CNScenes {

    public static void routerSetup(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("router_setup", "Setting up a Network Router");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        scene.idle(10);
        scene.world().showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.overlay().showText(60)
                .text("The Router creates a local network (LAN & Wi-Fi) and assigns IP addresses via DHCP.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(2, 1, 2));
        scene.idle(70);

        scene.overlay().showText(60)
                .text("Power it using Create: Power Grid (FE) and connect an incoming WAN cable from a Modem or Switch.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(2, 1, 2));
        scene.idle(70);
    }

    public static void cellTowerSetup(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("cell_tower_setup", "Cellular Base Station & 5G Waves");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        scene.idle(10);
        scene.world().showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.overlay().showText(60)
                .text("The Cell Tower Core broadcasts 4G/5G radio waves to Smartphones.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(2, 1, 2));
        scene.idle(70);

        scene.overlay().showText(70)
                .text("Signal strength (dBm) decreases with distance and solid walls like Iron or Stone. Connect Fiber Optic cable for backbone access.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(2, 1, 2));
        scene.idle(80);
    }

    public static void pcAssembly(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("pc_assembly", "Staged PC Assembly");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        scene.idle(10);
        scene.world().showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.overlay().showText(70)
                .text("Right-click the PC Case with components in order: Motherboard -> RAM -> SSD -> Network Card.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(2, 1, 2));
        scene.idle(80);

        scene.overlay().showText(60)
                .text("Once assembled, connect it to a Router with Cat6 Cable and power it with Create: Power Grid.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(2, 1, 2));
        scene.idle(70);
    }

    public static void kineticCables(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("kinetic_cables", "Manufacturing Cables with Create SU");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        scene.idle(10);
        scene.world().showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.overlay().showText(70)
                .text("The Crimping Press and Fiber Extruder run on Create rotational force (SU) to craft Cat6 and Fiber Optic cables.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(2, 1, 2));
        scene.idle(80);
    }
}
