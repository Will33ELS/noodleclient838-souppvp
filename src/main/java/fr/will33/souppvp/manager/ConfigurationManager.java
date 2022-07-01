package fr.will33.souppvp.manager;

import fr.will33.souppvp.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ConfigurationManager {

    private Location spawnLocation;
    private ItemStack bookItemStack;

    /**
     * Load config.yml
     * @param config Instance of the configuration
     */
    public void loadConfiguration(FileConfiguration config){
        this.spawnLocation = new Location(
                Bukkit.getWorld(config.getString("spawn.world")),
                config.getDouble("spawn.x"),
                config.getDouble("spawn.y"),
                config.getDouble("spawn.z"),
                config.getLong("spawn.yaw"),
                config.getLong("spawn.pitch")
        );

        this.bookItemStack = new ItemBuilder(
                Material.getMaterial(config.getString("book.material")),
                1,
                ChatColor.translateAlternateColorCodes('&', config.getString("book.displayName")),
                null).toItemStack();
    }

    /**
     * Get spawn location
     * @return
     */
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    /**
     * Get book item
     * @return
     */
    public ItemStack getBookItemStack() {
        return bookItemStack;
    }
}
