package net.redesky.skins.bukkit.compatibility;

import org.bukkit.Bukkit;
import net.redesky.skins.plugin.compatibility.ConsoleLogger;

public class BukkitConsoleLogger implements ConsoleLogger {
   public void sendMessage(String message) {
      Bukkit.getConsoleSender().sendMessage(message);
   }
}
