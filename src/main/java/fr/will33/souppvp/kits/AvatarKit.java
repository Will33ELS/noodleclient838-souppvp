package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AvatarKit extends AbstractKit {
    private final Map<Player, Long> lastUse = new HashMap<>();

    public AvatarKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.avatar.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.avatar.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.avatar.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.avatar.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.avatar.lore")
        );
    }

    @Override
    public void onSnick(Player player) {
        long lastUse = this.lastUse.getOrDefault(player, 0L);
        if(System.currentTimeMillis() > lastUse + TimeUnit.SECONDS.toMillis(35)){
            player.setVelocity(player.getEyeLocation().getDirection().multiply(5));
            this.lastUse.put(player, System.currentTimeMillis());
        }
    }

}
