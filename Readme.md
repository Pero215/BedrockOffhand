# Bedrock Offhand Plugin

A Minecraft plugin that provides **true simultaneous offhand functionality** for Bedrock Edition players on Java Edition servers. Enables seamless crossplay by allowing Bedrock players to use offhand items exactly like Java players.

## 🎯 Features

- **🔄 True Simultaneous Usage** - Use main hand and offhand items at the same time
- **⚔️ Weapon-Based Activation** - Swords/Maces: Direct right-click | Other items: Crouch + right-click
- **💨 Wind Charge Support** - Launch yourself upward from offhand
- **🛡️ Quick Armor Swapping** - Instant armor equipping with old armor going to offhand
- **🎯 Combat Ready** - Eat food, block with shield, throw potions while fighting
- **🔧 Crossplay Compatible** - Works with GeyserMC and Floodgate

## 🚀 Installation

1. **Install Dependencies:**
   - Install [GeyserMC](https://geysermc.org/)
   - Install [Floodgate](https://github.com/GeyserMC/Floodgate)

2. **Install Plugin:**
   - Place `BedrockOffhand-1.0.0.jar` in your `plugins/` folder
   - Restart the server

3. **Configuration** (Optional):
   - Edit `plugins/BedrockOffhand/config.yml`

## 🎮 How to Use

### **For Bedrock Players:**

#### **Direct Offhand Usage (No Crouch Needed):**
```
Main Hand: ⚔️ Sword / 🗡️ Mace
→ Simply Right-click = Use offhand item
→ Perfect for combat situations
```

#### **Crouch Offhand Usage (Other Items):**
```
Main Hand: ⛏️ Pickaxe / 🏹 Bow / ✂️ Shears  
→ Crouch + Right-click = Use offhand item
```

### **Supported Offhand Items:**

| Item | Function | Activation |
|------|----------|------------|
| 🍖 Food | Eat while fighting | Direct/Crouch |
| 🛡️ Shield | Block attacks | Direct/Crouch |
| 💨 Wind Charge | Launch upward | Direct/Crouch |
| 🛡️ Armor | Quick equip | Direct/Crouch |
| 🧪 Potions | Drink/throw | Direct/Crouch |
| 🔦 Torches | Place light | Crouch only |
| 📯 Ender Pearls | Teleport | Direct/Crouch |

## ⚡ Simultaneous Usage Examples

### **True Dual Wielding:**
```java
// Combat Example:
Main Hand: ⚔️ Sword (Left-click attacking)
Offhand: 🍖 Steak (Right-click eating)
→ Attack continuously while regenerating health
→ No combat interruption for healing
```

### **Combo Attacks:**
```java
// Mace + Wind Charge:
Main Hand: 🗡️ Mace (Smash attacks)  
Offhand: 💨 Wind Charge (Mobility)
→ Launch upward with wind charge
→ Perform powered smash from height
→ Ultimate mobility combat
```

### **Defensive Combat:**
```java
// Sword + Shield:
Main Hand: ⚔️ Sword (Attacking)
Offhand: 🛡️ Shield (Blocking)
→ Block arrows and attacks between swings
→ True offense/defense balance
```

## 🛠️ Commands

- `/offhand info` - Check your current offhand item
- `/offhand clear` - Clear your offhand item

## ⚙️ Configuration

Default `config.yml`:
```yaml
offhand:
  no-crouch-weapons: ["SWORD", "MACE"]
  cooldown-ticks: 5
  cancel-main-hand-action: true

visual-slot:
  enabled: true
  slot: 8

messages:
  welcome-enabled: true
  welcome-message: "&aTrue offhand enabled! &eRight-click &awith sword/mace or &eCrouch + Right-click &afor other items."
```

## 🎯 Technical Details

### **Detection Methods:**
1. **Floodgate API** - Primary detection
2. **Geyser API** - Secondary detection
3. **Username Analysis** - Fallback for Bedrock names
4. **Metadata Check** - Additional verification

### **Compatibility:**
- ✅ **Minecraft**: 1.21.8
- ✅ **Server**: Paper/Spigot
- ✅ **Crossplay**: GeyserMC + Floodgate
- ✅ **Java Players**: Unaffected (native offhand)


## 🐛 Troubleshooting

**Issue**: Bedrock players not detected  
**Solution**: Verify GeyserMC and Floodgate installation

**Issue**: Offhand not working  
**Solution**: Check if player is using sword/mace for direct right-click

**Issue**: Items disappearing  
**Solution**: Enable `prevent-offhand-move` in config

## 📄 License

MIT License - See LICENSE file for details

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a Pull Request

---

**Developer**: noty215  
**Support**: GitHub Issues  
**Version**: 1.0.0

---

**Experience true crossplay offhand functionality!** 🎯  
*Bedrock players finally get the same combat fluidity as Java Edition.*