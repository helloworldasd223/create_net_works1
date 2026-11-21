# Create: NetWorks

**Realistic Internet, Cellular Networks, Fiber Optics, Routers, PCs and Smartphones for Create & Create: Power Grid.**

Minecraft **1.21.1** · **NeoForge** · built with the Create ecosystem (Create, Ponder, Flywheel, Registrate).

---

## Features

### Networking core
- **IPv4 addressing** — parsing, subnet math (`/24` etc.), private-range detection.
- **DNS registry** — register/resolve hostnames such as `create.local` (case-insensitive).
- **Network manager** — server-side tick loop that drives nodes, links and packets.
- **Link media** — CAT6 copper, fiber optic, plus wireless links with distance-based attenuation.
- **Radio propagation** — RSSI maths with signal-bar display (`0–4` bars).
- **Packets** — routed payloads with TTL/hop tracking.

### Blocks
| Category | Blocks |
| --- | --- |
| Core networking | Router, Network Switch, Fiber Modem, Media Converter, Server Rack, Patch Panel, Wall Jack |
| Wireless & cellular | Cell Tower Core, Cell Antenna, Wi-Fi Access Point, Satellite Dish |
| Devices | PC Case, PC Monitor, Charging Dock, Network Link Controller |
| Kinetic (Create SU) | Crimping Press, Fiber Extruder, Server Cooling Fan |

### Items
Smartphone, SIM Card, CAT6 Cable, Fiber Optic Cable, RJ45 Connector, SFP Transceiver, Network Tester,
plus staged-assembly components: Phone Chassis, Printed Circuit Board, SoC Processor, 5G Modem Chip,
OLED Display, Lithium Battery, PC Motherboard, RAM Module, SSD Drive, Network Card.

### Integrations
- **Create: Power Grid** — optional compile-time compat layer (`PowerGridCompat`), loaded only when present.
- **Ponder** — in-game scenes explaining the network machines.
- **Registrate** — data-driven registration for blocks, items and block entities.

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.200+
- Create 6.0.9+
- Create: Power Grid 0.6.x *(optional)*

## Building

```bash
./gradlew build
```

The mod jar is produced at:

```
build/libs/createnetworks-neoforge-1.21.1-1.0.0.jar
```

A GitHub Actions workflow (**Build Mod**) compiles the project on every push to `main` and uploads the jar as a workflow artifact. You can also trigger it manually from the *Actions* tab (`workflow_dispatch`).

## Development

```bash
./gradlew runClient   # launch a dev client
./gradlew runServer   # launch a dev server
./gradlew test        # run the network engine unit tests
```

Unit tests live in `src/test/java` and cover IPv4/subnet logic, the DNS registry and radio signal-bar conversion.

## License

MIT
