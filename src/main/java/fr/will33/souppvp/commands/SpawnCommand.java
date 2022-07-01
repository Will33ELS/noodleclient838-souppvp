package fr.will33.souppvp.commands;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.task.WaitingSpawnTask;
import fr.will33.souppvp.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            int delay = SoupPvPPlugin.getInstance().getConfig().getInt("spawnTeleportDelayInSeconds");
            player.sendMessage(ChatUtil.translate(
                    SoupPvPPlugin.getInstance().getMessagesConfig().getString("spawn.waiting")
                            .replace("%seconds%", String.valueOf(delay))
            ));
            WaitingSpawnTask waitingSpawnTask = new WaitingSpawnTask(player);
            SoupPvPPlugin.getInstance().getPvPManager().getWaitingSpawns().put(player, waitingSpawnTask);
            waitingSpawnTask.runTaskLater(SoupPvPPlugin.getInstance(), 20L * delay);
        }
        return false;
    }
}
