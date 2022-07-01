package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FrogKit extends AbstractKit {

    private final Map<Player, Long> lastUse = new HashMap<>();

    public FrogKit() {
        super(
                Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.frog.material")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.frog.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.frog.price")
        );
    }

    @Override
    public void onSnick(Player player) {
        long lastUse = this.lastUse.getOrDefault(player, 0L);
        if(System.currentTimeMillis() - lastUse <= TimeUnit.SECONDS.toMillis(150)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 30, 1));
            this.lastUse.put(player, System.currentTimeMillis());
        }
    }
}
