package fr.will33.souppvp.gui;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KitsGUI {

    public static void openGUI(Player player){
        SoupPvPPlugin instance = SoupPvPPlugin.getInstance();
        Inventory inventory = Bukkit.createInventory(null, 2 * 9, instance.getMessagesConfig().getString("gui.kits.title"));

        for(AbstractKit kit : AbstractKit.kits){
            inventory.addItem(kit.toItemStack());
        }

        player.openInventory(inventory);
    }

}
