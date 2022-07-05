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

public class GiveCreditCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        SoupPvPPlugin instance = SoupPvPPlugin.getInstance();
        if(!commandSender.hasPermission("souppvp.givecredit")){
            commandSender.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("noPermission")));
        } else {
            if(strings.length != 2){
                commandSender.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.giveCredit.help")));
                return false;
            }else {
                Player target = Bukkit.getPlayer(strings[0]);
                if(target == null){
                    commandSender.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("playerNotOnline")));
                    return false;
                }
                int amount;
                try{
                    amount = Integer.parseInt(strings[1]);
                    if(amount <= 0) throw new NumberFormatException();
                } catch (NumberFormatException err){
                    commandSender.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("amountNotNumber")));
                    return false;
                }
                PvpPlayer pTarget = instance.getPvpPlayers().get(target.getUniqueId());
                pTarget.addCredit(amount);
                commandSender.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.giveCredit.success")
                        .replace("%amount%", StringUtil.formatCurrency(amount))
                        .replace("%target%", target.getName())
                ));
            }
        }
        return false;
    }
}
