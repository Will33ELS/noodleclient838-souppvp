package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PyroKit extends AbstractKit {

    public PyroKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.pyro.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.pyro.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.pyro.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.pyro.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.pyro.lore")
        );
    }

    @Override
    public void onSnick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 120, 1));
    }
}
