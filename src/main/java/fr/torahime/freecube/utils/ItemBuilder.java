package fr.torahime.freecube.utils;

import fr.torahime.freecube.controllers.menus.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack item;

    private Menu menuToOpen = null;

    public ItemBuilder(final ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(final Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder(final Material material, final Menu menu) {
        this.item = new ItemStack(material);
        this.menuToOpen = menu;
    }

    public ItemBuilder(final Material material, final int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemBuilder setDisplayName(final Component name) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.displayName(name);
        return this.setMeta(meta);
    }

    public ItemBuilder setLore(final List<Component> lores) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.lore(lores);
        return this.setMeta(meta);
    }

    public ItemBuilder setLore(final Component... lores) {
        return this.setLore(Arrays.asList(lores));
    }

    public ItemBuilder addEnchant(final Enchantment enchantment, final int level) {
        this.item.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchant(final Enchantment enchantment, final int level, final boolean b) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(enchantment, level, b);
        return this.setMeta(meta);
    }

    public ItemBuilder addItemFlags(final ItemFlag... itemFlags) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(itemFlags);
        return this.setMeta(meta);
    }

    public ItemBuilder setUnbreakable(final boolean unbreakable) {
        this.item.getItemMeta().setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setMeta(final ItemMeta meta) {
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack getItem() {
        return this.item;
    }

}
