package fr.will33.souppvp.api;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.model.PvpPlayer;
import fr.will33.souppvp.util.ChatUtil;
import fr.will33.souppvp.util.ItemBuilder;
import fr.will33.souppvp.util.StringUtil;
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
    private final ItemStack itemStack;
    private final String name;
    private final int price;
    private final List<String> lore;

    public AbstractKit(ItemStack itemStack, String name, int price, List<String> lore){
        this.itemStack = itemStack;
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
    public ItemStack getItemStack() {
        return itemStack;
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
        List<String> lore = this.lore.stream().map(l -> ChatUtil.translate(l
                .replace("%price%", StringUtil.formatCurrency(this.getPrice()))
        )).collect(Collectors.toList());
        return new ItemBuilder(this.itemStack.getType(), 1, this.itemStack.getData().getData(), this.name, lore).toItemStack();
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

    /**
     * Get kit
     * @param name Name of the kit
     * @return
     */
    public static AbstractKit getKit(String name){
        return kits.stream().filter(kit -> kit.getName().equals(name)).findFirst().orElse(null);
    }

}
