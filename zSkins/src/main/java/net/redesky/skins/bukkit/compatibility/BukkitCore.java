package net.redesky.skins.bukkit.compatibility;

import java.util.logging.Level;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.redesky.skins.Core;
import net.redesky.skins.bukkit.Main;
import net.redesky.skins.plugin.utils.OlymLogger;

public class BukkitCore extends Core {
   public void dL(OlymLogger logger, Level level, String message) {
   }

   public void sM(String playerName, String message) {
      Player player = Bukkit.getPlayerExact(playerName);
      if (player != null) {
         player.sendMessage(message);
      }

   }

   public void sM(String playerName, BaseComponent... components) {
      Player player = Bukkit.getPlayerExact(playerName);
      if (player != null) {
         player.spigot().sendMessage(components);
      }

   }

   public String gS() {
      return Main.getInstance().getDescription().getVersion();
   }
}
