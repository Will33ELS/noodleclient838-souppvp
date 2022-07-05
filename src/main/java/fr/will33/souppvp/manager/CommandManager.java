package fr.will33.souppvp.manager;

import fr.will33.souppvp.commands.BountyCommand;
import fr.will33.souppvp.commands.GiveCreditCommand;
import fr.will33.souppvp.commands.RepairCommand;
import fr.will33.souppvp.commands.SpawnCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {

    /**
     * Register all commands
     * @param javaPlugin Instance of the plugin
     */
    public void registerCommands(JavaPlugin javaPlugin){
        javaPlugin.getCommand("bounty").setExecutor(new BountyCommand());
        javaPlugin.getCommand("givecredit").setExecutor(new GiveCreditCommand());
        javaPlugin.getCommand("repair").setExecutor(new RepairCommand());
        javaPlugin.getCommand("spawn").setExecutor(new SpawnCommand());
    }

}
