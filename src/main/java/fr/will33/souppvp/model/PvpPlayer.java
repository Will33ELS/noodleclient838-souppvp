package fr.will33.souppvp.model;

import fr.will33.souppvp.api.AbstractKit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PvpPlayer {

    private final Player player;
    private int credit = 0;
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

    /**
     * Get credit balance
     * @return
     */
    public int getCredit() {
        return credit;
    }

    /**
     * Define credit balance
     * @param credit
     */
    public void setCredit(int credit) {
        this.credit = credit;
    }

    /**
     * Add credit in the balance
     * @param amount Amount to add
     */
    public void addCredit(int amount){
        this.credit += amount;
    }

    /**
     * Take credit in the balance
     * @param amount Amount to take
     */
    public void takeCredit(int amount){
        this.credit -= amount;
    }
}
