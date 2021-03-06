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

public class FrogKit extends AbstractKit {

    private final Map<Player, Long> lastUse = new HashMap<>();

    public FrogKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.frog.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.frog.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.frog.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.frog.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.frog.lore")
        );
    }

    @Override
    public void onDeath(Player player) {
        this.lastUse.remove(player);
    }

    @Override
    public void onSnick(Player player) {
        long lastUse = this.lastUse.getOrDefault(player, 0L);
        if(System.currentTimeMillis() > lastUse + TimeUnit.SECONDS.toMillis(150)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 30, 1));
            this.lastUse.put(player, System.currentTimeMillis());
        }
    }
}
