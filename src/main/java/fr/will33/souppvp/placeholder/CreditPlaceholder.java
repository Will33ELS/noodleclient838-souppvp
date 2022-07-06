package fr.will33.souppvp.placeholder;

import com.google.common.base.Joiner;
import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.model.PvpPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreditPlaceholder extends PlaceholderExpansion {

    private final SoupPvPPlugin instance = SoupPvPPlugin.getInstance();

    @Override
    public @NotNull String getIdentifier() {
        return this.instance.getDescription().getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return Joiner.on(", ").join(this.instance.getDescription().getAuthors());

    }

    @Override
    public @NotNull String getVersion() {
        return this.instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        PvpPlayer pvpPlayer = this.instance.getPvpPlayers().get(player.getUniqueId());
        return String.valueOf(pvpPlayer == null ? 0 : pvpPlayer.getCredit());
    }
}
