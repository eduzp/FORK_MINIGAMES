package mc.twilight.skywars;

import mc.twilight.core.Core;
import mc.twilight.core.libraries.MinecraftVersion;
import mc.twilight.core.plugin.KPlugin;
import mc.twilight.skywars.cmd.Commands;
import mc.twilight.skywars.cosmetics.Cosmetic;
import mc.twilight.skywars.cosmetics.object.Seasons;
import mc.twilight.skywars.game.AbstractSkyWars;
import mc.twilight.skywars.game.object.SkyWarsChest;
import mc.twilight.skywars.game.object.SkyWarsLeague;
import mc.twilight.skywars.hook.SWCoreHook;
import mc.twilight.skywars.listeners.Listeners;
import mc.twilight.skywars.lobby.*;
import mc.twilight.skywars.utils.tagger.TagUtils;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Bukkit;

public class Main extends KPlugin {
  
  public static boolean kMysteryBox, kCosmetics, kClans;
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
      this.getLogger().warning("~~~~~~~~ ~~~ ~~~~~  ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~O plugin apenas funciona na versao 1_8_R3 (Atual: " + MinecraftVersion.getCurrentVersion().getVersion() + ")");
      return;
    }
    
    saveDefaultConfig();
    currentServerName = getConfig().getString("lobby");
    kMysteryBox = Bukkit.getPluginManager().getPlugin("kMysteryBox") != null;
    kCosmetics = Bukkit.getPluginManager().getPlugin("kCosmetics") != null;
    kClans = Bukkit.getPluginManager().getPlugin("kClans") != null;
    if (getConfig().getString("spawn") != null) {
      Core.setLobby(BukkitUtils.deserializeLocation(getConfig().getString("spawn")));
    }
    
    AbstractSkyWars.setupGames();
    Language.setupLanguage();
    SWCoreHook.setupHook();
    Listeners.setupListeners();
    SkyWarsLeague.setupLeagues();
    Commands.setupCommands();
    Leaderboard.setupLeaderboards();
    
    DeliveryNPC.setupNPCs();
    Cosmetic.setupCosmetics();
    PlayNPC.setupNPCs();
    StatsNPC.setupNPCs();
    SkyWarsLeague.LeagueDelivery.setupDeliveries();
    SkyWarsChest.ChestType.setupChestTypes();
    Lobby.setupLobbies();
    Seasons.setupSeasons();
  
    validInit = true;
    this.getLogger().info("O plugin foi ativado.");
  }
  
  @Override
  public void disable() {
    if (validInit) {
      DeliveryNPC.listNPCs().forEach(DeliveryNPC::destroy);
      PlayNPC.listNPCs().forEach(PlayNPC::destroy);
      StatsNPC.listNPCs().forEach(StatsNPC::destroy);
      Leaderboard.listLeaderboards().forEach(Leaderboard::destroy);
      TagUtils.reset();
    }
    this.getLogger().info("O plugin foi desativado.");
  }
}