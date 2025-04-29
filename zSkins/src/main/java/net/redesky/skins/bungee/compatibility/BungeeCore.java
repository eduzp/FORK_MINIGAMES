package net.redesky.skins.bungee.compatibility;

import java.util.logging.Level;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.redesky.skins.Core;
import net.redesky.skins.bungee.Main;
import net.redesky.skins.plugin.utils.OlymLogger;

public class BungeeCore extends Core {
   public void dL(OlymLogger logger, Level level, String message) {
   }

   public void sM(String playerName, String message) {
      ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
      if (player != null) {
         player.sendMessage(TextComponent.fromLegacyText(message));
      }

   }

   public void sM(String playerName, BaseComponent... components) {
      ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
      if (player != null) {
         player.sendMessage(components);
      }

   }

   public String gS() {
      return Main.getInstance().getDescription().getVersion();
   }
}
