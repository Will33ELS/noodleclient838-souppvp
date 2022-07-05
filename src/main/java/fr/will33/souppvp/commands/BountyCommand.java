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

public class BountyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            PvpPlayer pvpPlayer = SoupPvPPlugin.getInstance().getPvpPlayers().get(player.getUniqueId());
            SoupPvPPlugin instance = SoupPvPPlugin.getInstance();
            if(strings.length != 2){
                player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.bounty.help")));
            } else {
                Player target = Bukkit.getPlayer(strings[0]);
                if(target == null){
                    player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("playerNotOnline")));
                    return false;
                }
                if(target.equals(player)){
                    player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.bounty.noOwn")));
                    return false;
                }
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
                } else if(instance.getPvPManager().getBountyPlayers().containsKey(target)){
                    player.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.bounty.already")));
                } else {
                    pvpPlayer.takeCredit(amount);
                    instance.getPvPManager().getBountyPlayers().put(target, amount);
                    Bukkit.getOnlinePlayers().forEach(pls ->
                            pls.sendMessage(ChatUtil.translate(instance.getMessagesConfig().getString("commands.bounty.broadcast")
                                    .replace("%credit%", StringUtil.formatCurrency(amount))
                                    .replace("%target%", target.getName())
                            )));
                }
            }
        }
        return false;
    }
}
