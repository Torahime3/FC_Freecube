package fr.torahime.freecube.listeners;

import fr.torahime.freecube.Freecube;
import fr.torahime.freecube.controllers.menus.MainMenu;
import fr.torahime.freecube.controllers.menus.PlotMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private Freecube fc;

    public PlayerInteractListener(Freecube fc) {
        this.fc = fc;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){

        ItemStack item = event.getItemDrop().getItemStack();

        //Check if item dropped is the right iron axe
        if(item.getType() == Material.IRON_AXE && item.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        CraftPlayer player = (CraftPlayer) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        AbstractContainerMenu inv = player.getHandle().containerMenu;

        System.out.println("Print 1");

        if(item == null) return;

        if (item.getType() == Material.IRON_AXE && item.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
            Bukkit.getScheduler().runTaskLater(fc, () -> {
//


//                event.getView().getInventory(event.getRawSlot()).setItem(event.getSlot(), item);


//                System.out.println(event.getWhoClicked().getOpenInventory().getCursor());


//                ClientboundContainerClosePacket packet = new ClientboundContainerClosePacket(inv.containerId);
//                ClientboundOpenScreenPacket packet2 = new ClientboundOpenScreenPacket(inv.containerId, inv.getType(), inv.getTitle());
//                ClientboundContainerSetContentPacket packet = new ClientboundContainerSetContentPacket(inv.containerId, inv.getStateId(), inv.getItems(), CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
//                ServerboundSetCreativeModeSlotPacket packet2 = new ServerboundSetCreativeModeSlotPacket(event.getRawSlot(), CraftItemStack.asNMSCopy(event.getCurrentItem()));
//                ClientboundContainerSetSlotPacket packet3 = new ClientboundContainerSetSlotPacket(-1, 0, 0, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
//
//                player.getHandle().connection.send(packet3);

//                event.getView().setCursor(item);
//                player.getHandle().connection.send(packet2);
//                player.getHandle().connection.send(packet);
//
//                player.getHandle().connection.send(packet3);

//                Int2ObjectOpenHashMap<net.minecraft.world.item.ItemStack> items = new Int2ObjectOpenHashMap<>();
//                items.put(event.getRawSlot(), CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
//
//                ServerboundContainerClickPacket packet2 = new ServerboundContainerClickPacket(inv.containerId, 0, event.getRawSlot(), 0, ClickType.SWAP, CraftItemStack.asNMSCopy(event.getCurrentItem()), items);
//                packet2.handle(player.getHandle().connection);
                ((CraftPlayer) event.getWhoClicked()).updateInventory();
                ((CraftPlayer) event.getWhoClicked()).updateInventory();

                Bukkit.getScheduler().runTaskLater(fc, () -> {
                    ((CraftPlayer) event.getWhoClicked()).updateInventory();
                    ((CraftPlayer) event.getWhoClicked()).updateInventory();
                }, 5);

                System.out.println("Packet sent");
                }, 1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if(item == null) return;

        //Check if player right-clicked with the right iron axe
        if(item.getType() == Material.IRON_AXE && item.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)){
            if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
                new MainMenu(player).openMenu();
            }
        }

    }
}
