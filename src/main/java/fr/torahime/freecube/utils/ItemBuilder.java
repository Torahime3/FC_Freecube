package fr.torahime.freecube.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack item;

    private List<Pattern> patterns = new ArrayList<Pattern>();

    public ItemBuilder(final ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(final Material material) {
        this.item = new ItemStack(material);
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

    public ItemBuilder addPattern(Pattern pattern){
        this.patterns.add(pattern);
        return this;
    }

    public ItemBuilder addPattern(Pattern... pattern){
        this.patterns.addAll(Arrays.asList(pattern));
        return this;
    }

    public ItemBuilder addPattern(PatternType patternType, DyeColor color){
        this.patterns.add(new Pattern(color, patternType));
        return this;
    }

    public ItemBuilder applyPatterns(){
        BannerMeta im = (BannerMeta) this.item.getItemMeta();
        im.setPatterns(this.patterns);
        this.item.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(final Component... lores) {
        return this.setLore(Arrays.asList(lores));
    }

    public ItemBuilder addLore(final Component lore) {
        final ItemMeta meta = this.item.getItemMeta();
        final List<Component> lores = meta.lore();
        if(lores == null){
            return setLore(lore);
        }

        lores.add(lore);
        meta.lore(lores);

        return this.setMeta(meta);
    }

    public ItemBuilder addLore(final Component... lore) {
        final ItemMeta meta = this.item.getItemMeta();
        final List<Component> lores = meta.lore();
        if(lores == null){
            return setLore(lore);
        }
        lores.addAll(Arrays.asList(lore));
        meta.lore(lores);
        return this.setMeta(meta);
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
