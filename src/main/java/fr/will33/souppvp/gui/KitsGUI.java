package fr.will33.souppvp.gui;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractGUI;
import fr.will33.souppvp.api.AbstractKit;
import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitsGUI extends AbstractGUI {

    @Override
    public void onOpen(Player player) {
        this.inventory = Bukkit.createInventory(null, 2 * 9, this.getPluginInstance().getMessagesConfig().getString("gui.kits.title"));
        int index = 0;
        for(AbstractKit kit : AbstractKit.kits){
            this.setSlotData(kit.toItemStack(), index, kit.getName());
            index ++;
        }

        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack currentItem, ClickType clickType, String action) {
        PvpPlayer pvpPlayer = this.getPluginInstance().getPvpPlayers().get(player.getUniqueId());
        if(pvpPlayer == null){
            player.closeInventory();
            return;
        }
        AbstractKit abstractKit = AbstractKit.kits.stream().filter(kit -> kit.getName().equals(action)).findFirst().orElse(null);
        if(abstractKit != null){
            if(pvpPlayer.getKitsUnlocked().contains(abstractKit)) {
                pvpPlayer.setKitSelected(abstractKit);
                this.getPluginInstance().getPvPManager().giveKits(player, abstractKit);
            } else
                this.getPluginInstance().openGUI(player, new ConfirmBuyGUI(pvpPlayer, abstractKit));
        }
    }
}
