package fr.will33.souppvp.api;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.model.PvpPlayer;
import fr.will33.souppvp.util.ChatUtil;
import fr.will33.souppvp.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractKit implements Listener {

    public static List<AbstractKit> kits = new ArrayList<>();
    protected final SoupPvPPlugin instance = SoupPvPPlugin.getInstance();
    private final Material material;
    private final String name;
    private final int price;
    private final List<String> lore;

    public AbstractKit(Material material, String name, int price, List<String> lore){
        this.material = material;
        this.name = name;
        this.price = price;
        this.lore = lore;

        kits.add(this);
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

    /**
     * Transform item for display in the GUI
     * @return
     */
    public ItemStack toItemStack(){
        List<String> lore = this.lore.stream().map(ChatUtil::translate).collect(Collectors.toList());
        return new ItemBuilder(this.material, 1, this.name, lore).toItemStack();
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
