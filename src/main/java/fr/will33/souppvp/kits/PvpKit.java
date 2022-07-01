package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Material;

public class PvpKit extends AbstractKit {
    public PvpKit() {
        super(
                Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.pvp.material")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.pvp.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.pvp.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.pvp.lore")
        );
    }
}
