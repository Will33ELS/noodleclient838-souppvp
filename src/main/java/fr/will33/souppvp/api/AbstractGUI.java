package fr.will33.souppvp.api;

import fr.will33.souppvp.SoupPvPPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGUI {
    protected Map<Integer, String> actions = new HashMap<>();
    protected Inventory inventory;

    /**
     * Open GUI
     * @param player Instance of the player
     */
    public abstract void onOpen(Player player);

    /**
     * Function called when the player clicks on an item in the menu
     * @param player Instance of the player
     * @param currentItem Current item
     */
    public abstract void onClick(Player player, ItemStack currentItem, ClickType clickType, String action);

    /**
     * Get {@link Inventory} instance
     * @return
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Define an item
     * @param item item to add
     * @param slot Number of the slot on which the item is positioned
     * @param action Name of the action to be defined on the item
     */
    public void setSlotData(ItemStack item, int slot, String action){
        this.actions.put(slot, action);
        this.inventory.setItem(slot, item);
    }

    /**
     * Retrieve the action corresponding to the slot number
     * @param slot Number of the slot
     * @return
     */
    public String getAction(int slot){
        return this.actions.get(slot);
    }

    /**
     * Get plugin instance
     * @return
     */
    public SoupPvPPlugin getPluginInstance() {
        return SoupPvPPlugin.getInstance();
    }
}
