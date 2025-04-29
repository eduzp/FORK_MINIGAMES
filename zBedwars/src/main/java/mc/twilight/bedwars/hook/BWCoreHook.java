package mc.twilight.bedwars.hook;

import com.comphenix.protocol.ProtocolLibrary;
import mc.twilight.core.Core;
import mc.twilight.core.achievements.Achievement;
import mc.twilight.core.achievements.types.BedWarsAchievement;
import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.game.improvements.Upgrades;
import mc.twilight.bedwars.game.improvements.traps.Trap;
import mc.twilight.bedwars.game.shop.Shop;
import mc.twilight.bedwars.hook.hotbar.BWHotbarActionType;
import mc.twilight.bedwars.hook.protocollib.HologramAdapter;
import mc.twilight.core.game.GameState;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;
import mc.twilight.core.player.hotbar.HotbarAction;
import mc.twilight.core.player.hotbar.HotbarActionType;
import mc.twilight.core.player.hotbar.HotbarButton;
import mc.twilight.core.player.scoreboard.KScoreboard;
import mc.twilight.core.player.scoreboard.scroller.ScoreboardScroller;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class BWCoreHook {
  
  public static void setupHook() {
    Core.minigame = "Bed Wars";
    
    Shop.setupShop();
    Upgrades.setupUpgrades();
    Trap.setupTraps();
    
    setupHotbars();
    new BukkitRunnable() {
      @Override
      public void run() {
        Profile.listProfiles().forEach(profile -> {
          if (profile.getScoreboard() != null) {
            profile.getScoreboard().scroll();
          }
        });
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, Language.scoreboards$scroller$every_tick);
    
    new BukkitRunnable() {
      @Override
      public void run() {
        Profile.listProfiles().forEach(profile -> {
          if (!profile.playingGame() && profile.getScoreboard() != null) {
            profile.update();
          }
        });
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
    
    ProtocolLibrary.getProtocolManager().addPacketListener(new HologramAdapter());
  }
  
  public static void checkAchievements(Profile profile) {
    Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
      Achievement.listAchievements(BedWarsAchievement.class).stream().filter(bwa -> bwa.canComplete(profile)).forEach(bwa -> {
        bwa.complete(profile);
        profile.getPlayer().sendMessage(Language.lobby$achievement.replace("{name}", bwa.getName()));
      });
    });
  }
  
  public static void reloadScoreboard(Profile profile) {
    if (!profile.playingGame()) {
      checkAchievements(profile);
    }
    
    Player player = profile.getPlayer();
    BedWars game = profile.getGame(BedWars.class);
    List<String> lines = game == null ? Language.scoreboards$lobby :
        game.getState() == GameState.AGUARDANDO ? Language.scoreboards$waiting : (Language.scoreboards$ingame);
    profile.setScoreboard(new KScoreboard() {
      @Override
      public void update() {
        for (int index = 0; index < Math.min(lines.size(), 15); index++) {
          String line = lines.get(index);
          
          if (game != null) {
            if (game.getState() == GameState.EMJOGO || game.getState() == GameState.ENCERRADO) {
              int i = 0;
              for (String team : new String[]{"red", "pink", "aqua", "blue", "white", "orange", "purple", "green"}) {
                if (line.contains("{" + team + "}")) {
                  BedWarsTeam bt = game.listTeams().size() > i ? game.listTeams().get(i) : null;
                  if (bt == null) {
                    line = "removethatline";
                    continue;
                  }
                  
                  // Coisas antes dos {}
                  String an = line.split(Arrays.toString(new String[]{"{"}))[0];
                  
                  line = (an != null ? an : "") + bt.getTag() + " §f" + bt.getRawName() + ": " + (bt.bed() ? bt.isAlive() ? "§8" + bt.listPlayers().size() : "§c✖" : "§a✔") + (bt.hasMember(player) ? " &7*" : "");
                  break;
                }
                
                i++;
              }
              
              if (line.equals("removethatline")) {
                continue;
              }
            }
            
            line = line.replace("{map}", game.getMapName())
                .replace("{server}", game.getGameName())
                .replace("{mode}", game.getMode().getName())
                .replace("{next_event}", game.getEvent())
                .replace("{players}", StringUtils.formatNumber(game.getOnline()))
                .replace("{teams}", StringUtils.formatNumber(game.listTeams().stream().filter(BedWarsTeam::isAlive).count())).replace("{max_players}", StringUtils.formatNumber(game.getMaxPlayers()))
                .replace("{time}", game.getTimer() == 46 ? Language.scoreboards$time$waiting : Language.scoreboards$time$starting.replace("{time}", StringUtils.formatNumber(game.getTimer())))
                .replace("{kills}", StringUtils.formatNumber(game.getKills(player)));
            
            
          } else {
            line = PlaceholderAPI.setPlaceholders(player, line);
          }
          
          this.add(15 - index, line);
        }
      }
    }.scroller(new ScoreboardScroller(Language.scoreboards$scroller$titles)).to(player).build());
    if (game != null && game.getState() != GameState.AGUARDANDO) {
      profile.getScoreboard().health().updateHealth();
    }
    profile.update();
    profile.getScoreboard().scroll();
  }
  
  private static void setupHotbars() {
    HotbarActionType.addActionType("bedwars", new BWHotbarActionType());
    
    KConfig config = Main.getInstance().getConfig("hotbar");
    for (String id : new String[]{"lobby", "waiting", "spectator"}) {
      Hotbar hotbar = new Hotbar(id);
      
      ConfigurationSection hb = config.getSection(id);
      for (String button : hb.getKeys(false)) {
        try {
          hotbar.getButtons().add(new HotbarButton(hb.getInt(button + ".slot"), new HotbarAction(hb.getString(button + ".execute")), hb.getString(button + ".icon")));
        } catch (Exception ex) {
          Main.getInstance().getLogger().log(Level.WARNING, "Falha ao carregar o botao \"" + button + "\" da hotbar \"" + id + "\": ", ex);
        }
      }
      
      Hotbar.addHotbar(hotbar);
    }
  }
  
  public void hotbarGame() {
  }
}
