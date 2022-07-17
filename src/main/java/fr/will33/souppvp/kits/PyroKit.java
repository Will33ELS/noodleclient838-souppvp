package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PyroKit extends AbstractKit {
    private final Map<Player, Long> lastUse = new HashMap<>();

    public PyroKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.pyro.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.pyro.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.pyro.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.pyro.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.pyro.lore")
        );
    }

    @Override
    public void onDeath(Player player) {
        this.lastUse.remove(player);
    }

    @Override
    public void onSnick(Player player) {
        long lastUse = this.lastUse.getOrDefault(player, 0L);
        if(System.currentTimeMillis() > lastUse + TimeUnit.SECONDS.toMillis(90 * 3)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 120, 1));
            this.lastUse.put(player, System.currentTimeMillis());
        }
    }
}
