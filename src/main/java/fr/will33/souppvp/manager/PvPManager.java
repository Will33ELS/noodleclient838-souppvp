package fr.will33.souppvp.manager;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import fr.will33.souppvp.model.PvpPlayer;
import fr.will33.souppvp.task.WaitingSpawnTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class PvPManager {

    private final Map<Player, WaitingSpawnTask> waitingSpawns = new HashMap<>();
    private final Map<Player, Integer> bountyPlayers = new HashMap<>();

    /**
     * Load player data
     * @param pvpPlayer Instance of the player
     */
    public void loadPlayerData(PvpPlayer pvpPlayer){
        Bukkit.getScheduler().runTaskAsynchronously(SoupPvPPlugin.getInstance(), () -> {
            SoupPvPPlugin.getInstance().getPlayerStockage().loadPlayer(pvpPlayer);
            pvpPlayer.getKitsUnlocked().addAll(SoupPvPPlugin.getInstance().getPlayerStockage().getKitUnlocked(pvpPlayer.getPlayer().getUniqueId()));
        });
    }

    /**
     * Teleport player in the spawn
     * @param player Instance of the player
     */
    public void teleportToSpawn(Player player){
        player.teleport(SoupPvPPlugin.getInstance().getConfigurationManager().getSpawnLocation());
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().setItem(0, SoupPvPPlugin.getInstance().getConfigurationManager().getBookItemStack());
        for(PotionEffect effects : player.getActivePotionEffects())
            player.removePotionEffect(effects.getType());
    }

    /**
     * Give kit
     * @param player Instance of the player
     * @param abstractKit Instance of the kit
     */
    public void giveKits(Player player, AbstractKit abstractKit){
        player.closeInventory();
        player.getInventory().clear();
        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.addEnchant(Enchantment.DURABILITY, 20, true);
        sword.setItemMeta(swordMeta);
        player.getInventory().addItem(sword);
        abstractKit.onSelect(player);
        for(int i = 0; i < 4 * 9; i ++)
            if(player.getInventory().getItem(i) == null)
                player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
    }

    /**
     * Retrieve players waiting to be teleported
     * @return
     */
    public Map<Player, WaitingSpawnTask> getWaitingSpawns() {
        return waitingSpawns;
    }

    /**
     * Retrieve bounty players
     * @return
     */
    public Map<Player, Integer> getBountyPlayers() {
        return bountyPlayers;
    }
}
