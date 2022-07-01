package fr.will33.souppvp.manager;

import fr.will33.souppvp.kits.AthleteKit;
import fr.will33.souppvp.kits.FrogKit;
import fr.will33.souppvp.kits.PvpKit;
import fr.will33.souppvp.kits.PyroKit;

public class KitManager {

    /**
     * Register all kits
     */
    public void registerKits(){
        new AthleteKit();
        new FrogKit();
        new PvpKit();
        new PyroKit();
    }

}
