package mc.twilight.clans;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import mc.twilight.clans.clan.Clan;
import mc.twilight.clans.cmd.Commands;
import mc.twilight.clans.database.Database;
import mc.twilight.clans.listeners.Listeners;
import mc.twilight.core.plugin.KPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Main extends KPlugin {
  public static boolean validInit;
  
  private static Main instance;
  
  public static Main getInstance() {
    return instance;
  }
  
  public static void sendBungee(Player player, String subChannel, String tag, String... strings) {
    if (getInstance().getConfig().getBoolean("bungeecord")) {
      ByteArrayDataOutput out = ByteStreams.newDataOutput();
      out.writeUTF(subChannel);
      out.writeUTF(tag);
      for (String string : strings)
        out.writeUTF(string); 
      player.sendPluginMessage((Plugin)getInstance(), "zClans", out.toByteArray());
    } 
  }
  
  public void start() {
    instance = this;
  }
  
  public void load() {}
  
  public void enable() {
    saveDefaultConfig();
    Database.setupDatabase();
    Commands.setupCommands();
    Listeners.setupListeners();
    if (getConfig().getBoolean("bungeecord")) {
      Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)this, "zClans");
      Bukkit.getMessenger().registerIncomingPluginChannel((Plugin)this, "zClans", (channel, arg1, msg) -> {
            if (channel.equals("zClans")) {
              Clan clan;
              ByteArrayDataInput in = ByteStreams.newDataInput(msg);
              String subChannel = in.readUTF();
              switch (subChannel) {
                case "Create":
                  Clan.loadClan(in.readUTF());
                  break;
                case "Update":
                  clan = Clan.getByTag(in.readUTF());
                  if (clan != null)
                    clan.reload(); 
                  break;
                case "Destroy":
                  clan = Clan.getByTag(in.readUTF());
                  if (clan != null)
                    clan.destroy(false); 
                  break;
                case "Broadcast":
                  clan = Clan.getByTag(in.readUTF());
                  if (clan != null)
                    clan.broadcast(in.readUTF(), Boolean.parseBoolean(in.readUTF()), new String[] { "blockBungee" }); 
                  break;
              } 
            } 
          });
    } 
    validInit = true;
    getLogger().info("O plugin foi ativado.");
  }
  
  public void disable() {
    getLogger().info("O plugin foi desativado.");
  }
}
