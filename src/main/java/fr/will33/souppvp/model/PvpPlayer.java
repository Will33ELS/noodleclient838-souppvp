package fr.will33.souppvp.model;

import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PvpPlayer {

    private final Player player;
    private AbstractKit kitSelected;
    private final List<AbstractKit> kitsUnlocked = new ArrayList<>();

    public PvpPlayer(Player player){
        this.player = player;
    }

    /**
     * Get {@link Player} instance
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get kit selected
     * @return
     */
    public AbstractKit getKitSelected() {
        return kitSelected;
    }

    /**
     * Define kit selected
     * @param kitSelected
     * @return
     */
    public PvpPlayer setKitSelected(AbstractKit kitSelected) {
        this.kitSelected = kitSelected;
        return this;
    }

    /**
     * Get {@link AbstractKit} unlocked
     * @return
     */
    public List<AbstractKit> getKitsUnlocked() {
        return kitsUnlocked;
    }
}
