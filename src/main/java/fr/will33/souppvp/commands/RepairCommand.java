package fr.will33.souppvp.commands;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.model.PvpPlayer;
import fr.will33.souppvp.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            SoupPvPPlugin instance = SoupPvPPlugin.getInstance();
            PvpPlayer pvpPlayer = instance.getPvpPlayers().get(player);
            int cost = instance.getConfig().getInt("repair.cost");
            if(pvpPlayer.getCredit() < cost){
                player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("enoughCredit")));
            } else {
                boolean hasWoodSword = false;
                for(ItemStack itemStack : player.getInventory().getContents()){
                    if(itemStack.getType() == Material.WOOD_SWORD){
                        hasWoodSword = true;
                        itemStack.setDurability((short) 0);
                        break;
                    }
                }

                if(!hasWoodSword)
                    player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("repair.noSword")));
                else
                    pvpPlayer.takeCredit(cost);
            }
        }
        return false;
    }
}
