# Bedrock Offhand Plugin

A Minecraft plugin that provides offhand functionality for Bedrock Edition players on Java Edition servers. Enables crossplay compatibility by simulating offhand usage for Bedrock players through a dedicated hotbar slot.

## Features

- 🔄 **Crossplay Support** - Enables offhand functionality for Bedrock players
- 🎯 **Automatic Detection** - Automatically detects Bedrock players via Floodgate/Geyser
- ⚙️ **Configurable** - Customizable offhand slot and behavior
- 💬 **User-Friendly** - Welcome messages and helpful commands
- 🔒 **Safe** - Prevents accidental offhand item movement
- 🚀 **Lightweight** - Minimal performance impact

## Requirements

- **Minecraft Server**: Paper/Spigot 1.21.8
- **Crossplay**: GeyserMC and Floodgate
- **Java**: Version 21 or higher

## Installation

1. **Install Dependencies:**
    - Install [GeyserMC](https://geysermc.org/)
    - Install [Floodgate](https://github.com/GeyserMC/Floodgate)

2. **Install Plugin:**
    - Place `BedrockOffhand-1.0.0.jar` in your `plugins/` folder
    - Restart the server

3. **Configure (Optional):**
    - Edit `plugins/BedrockOffhand/config.yml` if needed

## Configuration

Default `config.yml`:

```yaml
# Bedrock Offhand Configuration

bedrock:
  # The inventory slot to use for offhand (0-8, where 0 is first hotbar slot)
  offhand-slot: 8
  # Prevent Bedrock players from moving items out of offhand slot
  prevent-offhand-move: true
  # Auto-sync offhand when inventory closes
  auto-sync: true

messages:
  welcome-enabled: true
  welcome-message: "&aOffhand system enabled! Use hotbar slot 9 for offhand items."
  offhand-info: "&eYour offhand item: &6{item}"
  offhand-cleared: "&aYour offhand has been cleared!"
  no-offhand-item: "&cYou don't have an item in your offhand!"

compatibility:
  use-floodgate: true
  use-geyser: true
  use-name-detection: true