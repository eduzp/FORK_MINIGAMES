package net.redesky.skins.bungee.compatibility;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.redesky.skins.plugin.compatibility.ConsoleLogger;

public class BungeeConsoleLogger implements ConsoleLogger {
   public void sendMessage(String message) {
      ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(message));
   }
}
