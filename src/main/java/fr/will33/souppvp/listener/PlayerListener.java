package fr.will33.souppvp.listener;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.gui.KitsGUI;
import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PvpPlayer pvpPlayer = new PvpPlayer(player);
        SoupPvPPlugin.getInstance().getPvpPlayers().put(player.getUniqueId(), pvpPlayer);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        SoupPvPPlugin soupPvPPlugin = SoupPvPPlugin.getInstance();
        if(soupPvPPlugin.getPvPManager().getWaitingSpawns().containsKey(player)){
            soupPvPPlugin.getPvPManager().getWaitingSpawns().get(player).cancel();
            soupPvPPlugin.getPvPManager().getWaitingSpawns().remove(player);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        Action action = event.getAction();
        SoupPvPPlugin soupPvPPlugin = SoupPvPPlugin.getInstance();
        if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
            if(itemStack.getType() == soupPvPPlugin.getConfigurationManager().getBookItemStack().getType() && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() &&
            itemStack.getItemMeta().getDisplayName().equals(soupPvPPlugin.getConfigurationManager().getBookItemStack().getItemMeta().getDisplayName())){
                event.setCancelled(true);
                KitsGUI.openGUI(player);
            }
        }
    }

}
