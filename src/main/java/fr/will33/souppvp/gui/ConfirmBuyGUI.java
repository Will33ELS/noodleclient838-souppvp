package fr.will33.souppvp.gui;

import fr.will33.souppvp.api.AbstractGUI;
import fr.will33.souppvp.api.AbstractKit;
import fr.will33.souppvp.model.PvpPlayer;
import fr.will33.souppvp.util.ChatUtil;
import fr.will33.souppvp.util.ItemBuilder;
import fr.will33.souppvp.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class ConfirmBuyGUI extends AbstractGUI {

    private final PvpPlayer pvpPlayer;
    private final AbstractKit abstractKit;

    public ConfirmBuyGUI(PvpPlayer player, AbstractKit abstractKit){
        this.pvpPlayer = player;
        this.abstractKit = abstractKit;
    }

    @Override
    public void onOpen(Player player) {
        this.inventory = Bukkit.createInventory(null, 9, ChatUtil.translate(this.getPluginInstance().getMessagesConfig().getString("gui.confirmBuy.title")));

        this.setSlotData(new ItemBuilder(
                Material.valueOf(this.getPluginInstance().getMessagesConfig().getString("gui.confirmBuy.confirmItem.material")),
                1,
                (byte) 13,
                ChatUtil.translate(this.getPluginInstance().getMessagesConfig().getString("gui.confirmBuy.confirmItem.name")),
                this.getPluginInstance().getMessagesConfig().getStringList("gui.confirmBuy.confirmItem.lore").stream().map(l -> ChatUtil.translate(l)).collect(Collectors.toList())
        ).toItemStack(), 3, "confirm");

        this.setSlotData(new ItemBuilder(
                Material.valueOf(this.getPluginInstance().getMessagesConfig().getString("gui.confirmBuy.cancelItem.material")),
                1,
                (byte) 14,
                ChatUtil.translate(this.getPluginInstance().getMessagesConfig().getString("gui.confirmBuy.cancelItem.name")),
                this.getPluginInstance().getMessagesConfig().getStringList("gui.confirmBuy.cancelItem.lore").stream().map(l -> ChatUtil.translate(l)).collect(Collectors.toList())
        ).toItemStack(), 5, "cancel");

        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(Player player, ItemStack currentItem, ClickType clickType, String action) {
        if(this.pvpPlayer.getKitsUnlocked().contains(this.abstractKit)){
            player.closeInventory();
            this.getPluginInstance().openGUI(player, new KitsGUI());
            return;
        }
        if(this.pvpPlayer.getCredit() >= this.abstractKit.getPrice() && action.equals("confirm")){
            this.pvpPlayer.getKitsUnlocked().add(this.abstractKit);
            this.pvpPlayer.takeCredit(this.abstractKit.getPrice());
            player.closeInventory();
            player.sendMessage(ChatUtil.translate(this.getPluginInstance().getMessagesConfig().getString("buyKit")
                            .replace("%kit%", this.abstractKit.getName())
                            .replace("%credit%", StringUtil.formatCurrency(this.abstractKit.getPrice()))
                    ));
            this.getPluginInstance().openGUI(player, new KitsGUI());
        }
        if(action.equals("cancel")){
            player.closeInventory();
            this.getPluginInstance().openGUI(player, new KitsGUI());
        }
    }
}
