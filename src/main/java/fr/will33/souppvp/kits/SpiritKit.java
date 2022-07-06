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

public class SpiritKit extends AbstractKit {

    private final Map<Player, Long> lastUse = new HashMap<>();

    public SpiritKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.spirit.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.spirit.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.spirit.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.spirit.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.spirit.lore")
        );
    }

    @Override
    public void onSnick(Player player) {
        long lastUse = this.lastUse.getOrDefault(player, 0L);
        if(System.currentTimeMillis() > lastUse + TimeUnit.SECONDS.toMillis(150)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 30, 1));
            this.lastUse.put(player, System.currentTimeMillis());
        }
    }

}
