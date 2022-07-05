package fr.will33.souppvp.listener;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractGUI;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        AbstractGUI abstractGUI = SoupPvPPlugin.getInstance().getOpenGUI().get(player.getUniqueId());
        if(abstractGUI != null) {
            InventoryAction inventoryAction = event.getAction();
            if(inventoryAction == InventoryAction.COLLECT_TO_CURSOR || inventoryAction == InventoryAction.MOVE_TO_OTHER_INVENTORY){
                event.setCancelled(true);
            }
            if (inventory != null && abstractGUI.getInventory().equals(inventory) && event.getCurrentItem() != null) {
                event.setCancelled(true);
                String action = abstractGUI.getAction(event.getSlot());
                if (action != null) {
                    abstractGUI.onClick(player, event.getCurrentItem(), event.getClick(), action);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        AbstractGUI abstractGUI = SoupPvPPlugin.getInstance().getOpenGUI().get(player.getUniqueId());
        if(abstractGUI != null)
            if(inventory != null && abstractGUI.getInventory().equals(inventory)){
                SoupPvPPlugin.getInstance().getOpenGUI().remove(player.getUniqueId());
            }
    }

}
