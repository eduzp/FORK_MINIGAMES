package net.redesky.skins.bungee.cmd;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.redesky.skins.bungee.Main;

public abstract class Commands extends Command {
   public Commands(String name, String... aliases) {
      super(name, (String)null, aliases);
      ProxyServer.getInstance().getPluginManager().registerCommand(Main.getInstance(), this);
   }

   public static void makeCommands() {
      new SkinCommand();
   }
}
