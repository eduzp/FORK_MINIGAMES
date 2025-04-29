package mc.twilight.bedwars;

import mc.twilight.core.Core;
import mc.twilight.bedwars.cmd.Commands;
import mc.twilight.bedwars.cosmetics.Cosmetic;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.hook.BWCoreHook;
import mc.twilight.bedwars.listeners.Listeners;
import mc.twilight.bedwars.lobby.*;
import mc.twilight.bedwars.utils.tagger.TagUtils;
import mc.twilight.core.libraries.MinecraftVersion;
import mc.twilight.core.plugin.KPlugin;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;

public class Main extends KPlugin {
  
  public static boolean kCosmetics, kMysteryBox;
  public static String currentServerName;
  private static Main instance;
  private static boolean validInit;
  
  public static Main getInstance() {
    return instance;
  }
  
  @Override
  public void start() {
    instance = this;
  }
  
  @Override
  public void load() {}
  
  @Override
  public void enable() {
    if (MinecraftVersion.getCurrentVersion().getCompareId() != 183) {
      this.setEnabled(false);
      this.getLogger().warning("O plugin apenas funciona na versao 1_8_R3 (Atual: " + MinecraftVersion.getCurrentVersion().getVersion() + ")");
      return;
    }
   
    saveDefaultConfig();
    currentServerName = getConfig().getString("lobby");
    if (getConfig().getString("spawn") != null) {
      Core.setLobby(BukkitUtils.deserializeLocation(getConfig().getString("spawn")));
    }
    kMysteryBox = Bukkit.getPluginManager().getPlugin("kMysteryBox") != null;
    kCosmetics = Bukkit.getPluginManager().getPlugin("kCosmetics") != null;
    
    BedWars.setupGames();
    Language.setupLanguage();
    
    Listeners.setupListeners();
    BWCoreHook.setupHook();
    Cosmetic.setupCosmetics();
    PlayNPC.setupNPCs();
    Commands.setupCommands();
    
    StatsNPC.setupNPCs();
    DeliveryNPC.setupNPCs();
    Lobby.setupLobbies();
    Leaderboard.setupLeaderboards();
    
    validInit = true;
    this.getLogger().info("O plugin foi ativado.");
  }
  
  @Override
  public void disable() {
    if (validInit) {
      TagUtils.reset();
      DeliveryNPC.listNPCs().forEach(DeliveryNPC::destroy);
      PlayNPC.listNPCs().forEach(PlayNPC::destroy);
      Leaderboard.listLeaderboards().forEach(Leaderboard::destroy);
    }
    
    File update = new File("plugins/zBedWars/update", "zBedWars.jar");
    if (update.exists()) {
      try {
        this.getFileUtils().deleteFile(new File("plugins/" + update.getName()));
        this.getFileUtils().copyFile(new FileInputStream(update), new File("plugins/" + update.getName()));
        this.getFileUtils().deleteFile(update.getParentFile());
        this.getLogger().info("Update do zBedWars aplicada.");
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    this.getLogger().info("O plugin foi desativado.");
  }
}
