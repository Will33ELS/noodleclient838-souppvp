package fr.will33.souppvp.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder implements Cloneable{

    protected ItemStack item;
    protected ItemMeta itemM;

    /**
     * Create item
     * @param material Type of the item
     * @param amont Amount of the stack
     * @param displayname Displayname of the item
     * @param lore Lore of this item
     */
    public ItemBuilder(Material material, int amont, String displayname, List<String> lore) {
        this.item = new ItemStack(material, amont);
        this.itemM = this.item.getItemMeta();
        this.itemM.setDisplayName(displayname);
        this.itemM.setLore(lore);
        this.item.setItemMeta(this.itemM);
    }

    /**
     * Get {@link ItemStack} instance
     * @return itemStack
     */
    public ItemStack toItemStack() {
        return this.item;
    }

    @Override
    public ItemBuilder clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return (ItemBuilder) o;
    }
}