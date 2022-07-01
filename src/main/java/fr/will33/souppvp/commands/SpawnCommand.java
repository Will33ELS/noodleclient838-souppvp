package fr.will33.souppvp.commands;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.task.WaitingSpawnTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            WaitingSpawnTask waitingSpawnTask = new WaitingSpawnTask(player);
            SoupPvPPlugin.getInstance().getPvPManager().getWaitingSpawns().put(player, waitingSpawnTask);
            waitingSpawnTask.runTaskLater(SoupPvPPlugin.getInstance(), 20L * SoupPvPPlugin.getInstance().getConfig().getInt("spawnTeleportDelayInSeconds"));
        }
        return false;
    }
}
