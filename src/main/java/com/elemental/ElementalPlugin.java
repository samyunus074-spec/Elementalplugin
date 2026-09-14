package com.elemental;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ElementalPlugin extends JavaPlugin implements Listener {

    private NamespacedKey elementKey;
    private NamespacedKey levelKey;

    private final Set<UUID> claimed = new HashSet<>();

    @Override
    public void onEnable() {
        elementKey = new NamespacedKey(this, "element");
        levelKey = new NamespacedKey(this, "level");

        getServer().getPluginManager().registerEvents(this, this);

        if (getCommand("element") != null) {
            getCommand("element").setExecutor(this);
        }

        getLogger().info("Elemental Plugin enabled!");
    }

    // =========================
    // COMMAND
    // =========================

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.AQUA + "===== ELEMENTAL =====");
            player.sendMessage(ChatColor.YELLOW + "/element fire");
            player.sendMessage(ChatColor.YELLOW + "/element ice");
            player.sendMessage(ChatColor.YELLOW + "/element thunder");
            player.sendMessage(ChatColor.YELLOW + "/element earth");
            player.sendMessage(ChatColor.YELLOW + "/element wind");
            player.sendMessage(ChatColor.YELLOW + "/element water");
            player.sendMessage(ChatColor.YELLOW + "/element nature");
            player.sendMessage(ChatColor.YELLOW + "/element shadow");
            player.sendMessage(ChatColor.YELLOW + "/element upgrade");
            return true;
        }

        String type = args[0].toLowerCase();

        if (type.equals("upgrade")) {
            upgrade(player);
            return true;
        }

        if (!isElement(type)) {
            player.sendMessage(ChatColor.RED + "Unknown element!");
            return true;
        }

        if (claimed.contains(player.getUniqueId())) {
            player.sendMessage(
                    ChatColor.RED +
                    "You already claimed an element!"
            );
            return true;
        }

        claimed.add(player.getUniqueId());
        giveElement(player, type);

        player.sendMessage(
                ChatColor.GREEN +
                "You received the " +
                type.toUpperCase() +
                " element!"
        );

        return true;
    }

    private boolean isElement(String type) {
        return type.equals("fire")
                || type.equals("ice")
                || type.equals("thunder")
                || type.equals("earth")
                || type.equals("wind")
                || type.equals("water")
                || type.equals("nature")
                || type.equals("shadow");
    }

    // =========================
    // CREATE ELEMENT
    // =========================

    private void giveElement(Player player, String element) {

        Material material;

        switch (element) {
            case "fire" -> material = Material.BLAZE_ROD;
            case "ice" -> material =
            
