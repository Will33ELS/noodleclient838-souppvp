package fr.will33.souppvp.commands;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.model.PvpPlayer;
import fr.will33.souppvp.util.ChatUtil;
import fr.will33.souppvp.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            SoupPvPPlugin instance = SoupPvPPlugin.getInstance();
            Player player = (Player) commandSender;
            PvpPlayer pvpPlayer = instance.getPvpPlayers().get(player.getUniqueId());
            if(strings.length != 2){
                player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.pay.help")));
            } else {
                Player target = Bukkit.getPlayer(strings[0]);
                if(target == null){
                    player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("playerNotOnline")));
                } else {
                    PvpPlayer pvpTarget = instance.getPvpPlayers().get(target.getUniqueId());
                    int amount;
                    try{
                        amount = Integer.parseInt(strings[1]);
                        if(amount <= 0) throw new NumberFormatException();
                    } catch (NumberFormatException err){
                        player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("amountNotNumber")));
                        return false;
                    }
                    if(amount > pvpPlayer.getCredit()){
                        player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("enoughCredit")));
                    } else {
                        pvpPlayer.takeCredit(amount);
                        pvpTarget.addCredit(amount);
                        instance.getPlayerStockage().updateCredit(pvpPlayer);
                        instance.getPlayerStockage().updateCredit(pvpTarget);
                        player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.pay.from")
                                .replace("%amount%", StringUtil.formatCurrency(amount))
                                .replace("%target%", target.getName())
                        ));
                        player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.pay.target")
                                .replace("%amount%", StringUtil.formatCurrency(amount))
                                .replace("%from%", player.getName())
                        ));
                    }
                }
            }
        }
        return false;
    }
}
