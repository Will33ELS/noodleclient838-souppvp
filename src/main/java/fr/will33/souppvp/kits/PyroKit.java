package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PyroKit extends AbstractKit {

    public PyroKit() {
        super(
                Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.pyro.material")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.pyro.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.pyro.price")
        );
    }

    @Override
    public void onSnick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 120, 1));
    }
}
