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

public class BarbarianKit extends AbstractKit {
    private final Map<Player, Long> lastUse = new HashMap<>();

    public BarbarianKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.barbarian.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.ninja.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.barbarian.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.barbarian.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.barbarian.lore")
        );
    }

    @Override
    public void onDeath(Player player) {
        this.lastUse.remove(player);
    }

    @Override
    public void onSnick(Player player) {
        long lastUse = this.lastUse.getOrDefault(player, 0L);
        if(System.currentTimeMillis() > lastUse + TimeUnit.SECONDS.toMillis(60 * 2)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 15, 1));
            this.lastUse.put(player, System.currentTimeMillis());
        }
    }

}
