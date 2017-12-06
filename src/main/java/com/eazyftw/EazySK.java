package com.eazyftw;

import ch.njol.skript.lang.ExpressionType;
import com.eazyftw.commands.CMDEazySK;
import com.eazyftw.expressions.ExprMaxPlayers;
import com.eazyftw.expressions.ExprServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.event.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class EazySK extends JavaPlugin implements Listener {

    private SkriptAddon addonInstance;

    private static EazySK instance;


    protected static boolean skript;

    public static EazySK getInstance() {
        return EazySK.instance;
    }

    @Override
    public void onEnable() {
        String Prefix = this.getConfig().getString("Prefix").replace("&", "§");
        skript = this.getServer().getPluginManager().isPluginEnabled("Skript");
        EazySK.instance = this;
        if(!skript) {
            Bukkit.getConsoleSender().sendMessage(Prefix + " §cSkript is not found, disabling EazySK!");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Load();
        addonInstance = Skript.registerAddon(this);
        this.getCommand("eazysk").setExecutor(new CMDEazySK(this));
        Bukkit.getPluginManager().registerEvents( this, this);
        Skript.registerExpression(ExprServerVersion.class, String.class, ExpressionType.PROPERTY, "[eazysk] server[s] version", "[eazysk] version of server");
        Skript.registerExpression(ExprMaxPlayers.class, Integer.class, ExpressionType.PROPERTY, "[eazysk] [server] max players", "[eazysk] max player[s] of server");
    }

    public void Load() {
        final File config = new File(this.getDataFolder(), "config.yml");
        String Prefix = this.getConfig().getString("Prefix").replace("&", "§");
        Bukkit.getConsoleSender().sendMessage(Prefix + " §7Loading config.");
        if (!config.exists()) {
            Bukkit.getConsoleSender().sendMessage(Prefix + " §7Config file not found, creating a config file.");
            this.saveDefaultConfig();
        }
        if(!getDescription().getVersion().equals(this.getConfig().getString("Version").replace("&", "§"))) {
            Bukkit.getConsoleSender().sendMessage(Prefix + " §7Config file is out of date, resetting config.");
            config.delete();
            saveDefaultConfig();
            reloadConfig();
        }
        Bukkit.getConsoleSender().sendMessage(Prefix + " §7Loaded config.");
        Bukkit.getConsoleSender().sendMessage(Prefix + " §7Loaded EazySK with skript version " + Skript.getVersion() + " and minecraft version " + Skript.getMinecraftVersion() + ".");
    }

    public ItemStack newItem(final Material material, final String displayName, final String lore1) {
        final ItemStack stack = new ItemStack(material);
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        final ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add(lore1);
        meta.setLore((List)lore3);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack newItem(final Material material, final String displayName, final String lore1, final byte data) {
        final ItemStack stack = new ItemStack(material, 1, (short)data);
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        final ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add(lore1);
        meta.setLore((List)lore3);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        stack.setItemMeta(meta);
        return stack;
    }

    public void eazySKGUI(final Player player) {
        String GUIName = this.getConfig().getString("GUI-Name").replace("&", "§");
        final Inventory i = Bukkit.createInventory((InventoryHolder)null, 27, GUIName);
        final ItemStack ver = this.newItem(Material.PAPER, "§a§lVersion", "§7§o" + this.getDescription().getVersion());
        final ItemStack rl = this.newItem(Material.REDSTONE, "§a§lReload", "§7§oClick to reload EazySK!");
        i.setItem(12, ver);
        i.setItem(14, rl);
        player.openInventory(i);
    }

    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getItemMeta() == null) {
            return;
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) {
            e.setCancelled(true);
            return;
        }

        if (e.getInventory().getName() == null) {
            return;
        }

        String GUIName = this.getConfig().getString("GUI-Name").replace("&", "§");
        if (e.getInventory().getName().equals(GUIName)) {
            e.setCancelled(true);
            String Prefix = this.getConfig().getString("Prefix").replace("&", "§");

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§a§lVersion")) {
                p.closeInventory();
                p.sendMessage(Prefix + " §7The version of EazySK is §b" + this.getDescription().getVersion() + "§7.");
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§a§lReload")) {
                p.sendMessage(Prefix + " §7Reloaded.");
                p.closeInventory();
                Load();
            }
        }
    }

    @Override
    public void onDisable() {
        String Prefix = this.getConfig().getString("Prefix").replace("&", "§");
        Bukkit.getConsoleSender().sendMessage(Prefix + " §7Disabling EazySK.");
    }
}
