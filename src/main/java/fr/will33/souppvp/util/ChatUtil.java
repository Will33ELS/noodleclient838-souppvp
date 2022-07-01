package fr.will33.souppvp.util;

import org.bukkit.ChatColor;

public class ChatUtil {

    /**
     * Translate color code
     * @param content Content of the message
     * @return
     */
    public static String translate(String content){
        return ChatColor.translateAlternateColorCodes('&', content);
    }

}
