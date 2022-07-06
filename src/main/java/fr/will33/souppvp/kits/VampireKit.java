package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import fr.will33.souppvp.model.PvpPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class VampireKit extends AbstractKit {

    public VampireKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.vampire.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.vampire.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.vampire.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.vampire.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.vampire.lore")
        );
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player damager = (Player) event.getDamager();
            PvpPlayer pDamager = SoupPvPPlugin.getInstance().getPvpPlayers().get(damager.getUniqueId());
            if(pDamager.getKitSelected() != null && pDamager.getKitSelected().equals(this)){
                int chance = new Random().nextInt(100);
                if(chance < 15){
                    if(damager.getHealth() + 1 > damager.getMaxHealth())
                        damager.setHealth(damager.getMaxHealth());
                    else
                        damager.setHealth(damager.getHealth() + 1);
                }
            }
        }
    }

}
