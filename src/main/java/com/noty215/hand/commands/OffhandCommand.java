package com.noty215.hand.commands;

import com.noty215.hand.BedrockOffhandPlugin;
import com.noty215.hand.managers.OffhandManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class OffhandCommand implements CommandExecutor {

    private final OffhandManager offhandManager;

    public OffhandCommand() {
        this.offhandManager = BedrockOffhandPlugin.getInstance().getOffhandManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            showOffhandInfo(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "info":
                showOffhandInfo(player);
                break;
            case "clear":
                clearOffhand(player);
                break;
            case "set":
                if (args.length > 1) {
                    setOffhandFromCommand(player, args[1]);
                } else {
                    setOffhandFromHand(player);
                }
                break;
            default:
                player.sendMessage(ChatColor.RED + "Usage: /offhand [info|clear|set]");
                break;
        }

        return true;
    }

    private void showOffhandInfo(Player player) {
        ItemStack offhandItem = offhandManager.getOffhandItem(player);

        if (offhandManager.isOffhandEmpty(player)) {
            player.sendMessage(ChatColor.YELLOW + "Your offhand is empty.");
        } else {
            String itemName = offhandItem.getType().toString().toLowerCase().replace("_", " ");
            player.sendMessage(ChatColor.GREEN + "Offhand item: " + ChatColor.AQUA + itemName);
        }
    }

    private void clearOffhand(Player player) {
        offhandManager.setOffhandItem(player, null);
        player.sendMessage(ChatColor.GREEN + "Offhand cleared!");
    }

    private void setOffhandFromHand(Player player) {
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (handItem == null || handItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You must be holding an item in your main hand!");
            return;
        }

        offhandManager.setOffhandItem(player, handItem.clone());
        player.sendMessage(ChatColor.GREEN + "Item set in offhand: " +
                ChatColor.AQUA + handItem.getType().toString().toLowerCase().replace("_", " "));
    }

    private void setOffhandFromCommand(Player player, String itemName) {
        try {
            Material material = Material.valueOf(itemName.toUpperCase());
            offhandManager.setOffhandItem(player, new ItemStack(material));
            player.sendMessage(ChatColor.GREEN + "Item set in offhand: " + ChatColor.AQUA + itemName.toLowerCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Invalid item name: " + itemName);
        }
    }
}