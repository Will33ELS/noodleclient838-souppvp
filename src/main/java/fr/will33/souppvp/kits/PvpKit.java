package fr.will33.souppvp.kits;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PvpKit extends AbstractKit {
    public PvpKit() {
        super(
                new ItemStack(Material.getMaterial(SoupPvPPlugin.getInstance().getConfig().getString("kits.pvp.material")), 1, (byte) SoupPvPPlugin.getInstance().getConfig().getInt("kits.pvp.data")),
                SoupPvPPlugin.getInstance().getConfig().getString("kits.pvp.name"),
                SoupPvPPlugin.getInstance().getConfig().getInt("kits.pvp.price"),
                SoupPvPPlugin.getInstance().getConfig().getStringList("kits.pvp.lore")
        );
    }
}
