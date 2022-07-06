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

import java.util.Random;

public class NinjaKit extends AbstractKit {

    public NinjaKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.ninja.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.ninja.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.ninja.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.ninja.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.ninja.lore")
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
                if(chance < 15) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 1));
                }
            }
        }
    }

}
