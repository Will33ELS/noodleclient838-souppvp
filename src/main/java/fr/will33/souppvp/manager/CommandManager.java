package fr.will33.souppvp.manager;

import fr.will33.souppvp.commands.RepairCommand;
import fr.will33.souppvp.commands.SpawnCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {

    /**
     * Register all commands
     * @param javaPlugin Instance of the plugin
     */
    public void registerCommands(JavaPlugin javaPlugin){
        javaPlugin.getCommand("spawn").setExecutor(new SpawnCommand());
        javaPlugin.getCommand("repair").setExecutor(new RepairCommand());
    }

}
