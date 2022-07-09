package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ShamanKit extends AbstractKit {
    private final Map<Player, Long> lastUse = new HashMap<>();

    public ShamanKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.shaman.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.snail.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.shaman.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.shaman.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.shaman.lore")
        );
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player victim = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            PvpPlayer pDamager = SoupPvPPlugin.getInstance().getPvpPlayers().get(damager.getUniqueId());
            if(pDamager.getKitSelected() != null && pDamager.getKitSelected().equals(this)){
                int chance = new Random().nextInt(100);
                if(chance < 10) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 5 * 20, 0));
                }
            }
        }
    }

    @Override
    public void onDeath(Player player) {
        this.lastUse.remove(player);
    }

    @Override
    public void onSnick(Player player) {
        long lastUse = this.lastUse.getOrDefault(player, 0L);
        if(System.currentTimeMillis() > lastUse + TimeUnit.SECONDS.toMillis(60 * 3)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 90, 1));
            this.lastUse.put(player, System.currentTimeMillis());
        }
    }

}
