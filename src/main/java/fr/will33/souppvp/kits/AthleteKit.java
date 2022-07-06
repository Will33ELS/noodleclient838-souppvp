package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AthleteKit extends AbstractKit {
    public AthleteKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.athlete.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.athlete.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.athlete.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.athlete.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.athlete.lore")
        );
    }

    @Override
    public void onSelect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
    }

    @Override
    public void onDeath(Player player) {
        player.removePotionEffect(PotionEffectType.SPEED);
    }
}
