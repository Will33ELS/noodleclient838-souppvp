package fr.will33.souppvp.api;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;

public abstract class AbstractKit implements Listener {

    protected final SoupPvPPlugin instance = SoupPvPPlugin.getInstance();
    private final Material material;
    private final String name;
    private final int price;

    public AbstractKit(Material material, String name, int price){
        this.material = material;
        this.name = name;
        this.price = price;

        Bukkit.getPluginManager().registerEvents(this, SoupPvPPlugin.getInstance());
    }

    /**
     * Method called when the player selects the kit
     * @param player Instance of the player
     */
    public void onSelect(Player player) {}

    /**
     * Method called when the player death
     * @param player Instance of the player
     */
    public void onDeath(Player player) {}

    /**
     * Method called when a player snick
     * @param player
     */
    public void onSnick(Player player) {}

    /**
     * Get material of the item
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Get kit name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get kit price
     * @return
     */
    public int getPrice() {
        return price;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        PvpPlayer pvpPlayer = this.instance.getPvpPlayers().get(player.getUniqueId());
        if(pvpPlayer.getKitSelected() != null && pvpPlayer.getKitSelected().equals(this)){
            this.onDeath(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSnick(PlayerToggleSneakEvent event){
        if(event.isSneaking()){
            Player player = event.getPlayer();
            PvpPlayer pvpPlayer = this.instance.getPvpPlayers().get(player.getUniqueId());
            if(pvpPlayer.getKitSelected() != null && pvpPlayer.getKitSelected().equals(this)){
                this.onSnick(player);
            }
        }
    }

}
