package fr.torahime.freecube.controllers.menus;

import fr.torahime.freecube.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu implements Listener {

    protected Player player;
    protected ArrayList<ItemStack> menuItems;
    protected TextComponent invName;
    protected Inventory inv;
    protected HashMap<Integer, Runnable> interactions;
    protected Menu lastMenu = null;


    public Menu(){
        this(null, Component.text("Menu"), 9, null);
    }

    public Menu(Player player, TextComponent invName, int size){
        this(player, invName, size, null);
    }
    public Menu(Player player, TextComponent invName, int size, Menu lastMenu){
        if(size % 9 != 0){
            throw new IllegalArgumentException("La taille de l'inventaire doit être un multiple de 9");
        }
        this.player = player;
        this.invName = invName;
        this.lastMenu = lastMenu;
        this.inv = Bukkit.createInventory(null, size, this.invName);
        this.menuItems = new ArrayList<>();
        this.interactions = new HashMap<>();
    }

    public void openMenu(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("Freecube"));
        fillInventory();
        this.player.openInventory(this.inv);
    }
    protected void fillInventory(){

        if(lastMenu != null){
            ItemBuilder lastMenuButton = new ItemBuilder(Material.SPRUCE_DOOR);
            lastMenuButton.setDisplayName(Component.text("Retour").color(NamedTextColor.RED));
            this.addItem(lastMenuButton.getItem(), 53, () -> {
                lastMenu.openMenu();
            });

        } else {

            ItemBuilder defaultItem = new ItemBuilder(Material.BARRIER);
            defaultItem.setDisplayName(Component.text("Default Item").color(NamedTextColor.RED));

            for (int i = 0; i < 9; i++) {
                this.addItem(defaultItem.getItem(), i);
            }
        }
    }
    protected void addItem(ItemStack item, int slot){
        this.menuItems.add(item);
        this.inv.setItem(slot, item);
        if(lastMenu != null && item.getType() != Material.SPRUCE_DOOR && slot == 53){
            throw new IllegalArgumentException("Le slot 53 est réservé pour le bouton de retour");
        }
    }

    protected void addItem(ItemStack item, int slot, Runnable interaction){
        this.addItem(item, slot);
        this.interactions.put(slot, interaction);
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event){
        if(this.player != event.getWhoClicked()) return;

        ItemStack item = event.getCurrentItem();
        int slot = event.getRawSlot();
        if(item == null) return;


        if(this.interactions.containsKey(slot)) this.interactions.get(slot).run();

    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        if(this.player != event.getPlayer()) return;

        ItemStack itemDrop = event.getItemDrop().getItemStack();

        for(ItemStack item : menuItems){
            if(itemDrop.equals(item)){
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(this.player != event.getWhoClicked()) return;
        if(event.getClickedInventory() == null) return;

        final ItemStack itemClick = event.getCurrentItem();

        if(itemClick == null) return;

        for(ItemStack item : menuItems){
            if(itemClick.equals(item)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        if(this.player != event.getPlayer()) return;
        if(event.getInventory().equals(this.inv)){
            HandlerList.unregisterAll(this);
        }
    }
}
