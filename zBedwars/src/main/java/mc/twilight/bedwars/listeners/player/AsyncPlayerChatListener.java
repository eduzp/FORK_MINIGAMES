package mc.twilight.bedwars.listeners.player;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.enums.BedWarsMode;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AsyncPlayerChatListener implements Listener {
  
  private static final Map<String, Long> flood = new HashMap<>();
  private static final DecimalFormat df = new DecimalFormat("###.#");
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    
    if (profile != null) {
      String[] args = evt.getMessage().replace("/", "").split(" ");
      
      if (args.length > 0) {
        String command = args[0];
        if (command.equalsIgnoreCase("g") || command.equalsIgnoreCase("global")) {
          BedWars game = profile.getGame(BedWars.class);
          if (game != null && game.getMode().equals(BedWarsMode.SOLO)) {
            return;
          }
          if (game != null && !game.isSpectator(player)) {
            evt.setCancelled(true);
            if (args.length == 1) {
              player.sendMessage("§cUtilize /g [mensagem]");
              return;
            }
            
            if (!player.hasPermission("zbedwars.chat.delay")) {
              long start = flood.containsKey(player.getName()) ? flood.get(player.getName()) : 0;
              if (start > System.currentTimeMillis()) {
                double time = (start - System.currentTimeMillis()) / 1000.0;
                if (time > 0.1) {
                  evt.setCancelled(true);
                  String timeString = df.format(time).replace(",", ".");
                  if (timeString.endsWith("0")) {
                    timeString = timeString.substring(0, timeString.lastIndexOf("."));
                  }
                  
                  player.sendMessage(Language.chat$delay.replace("{time}", timeString));
                  return;
                }
              }
              
              flood.put(player.getName(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3));
            }
            
            
            StringBuilder messages = new StringBuilder();
            boolean first = true;
            for (String arg : args) {
              if (!arg.equals(command)) {
                messages.append(first ? "" : " ").append(arg);
                first = false;
              }
            }
            String message = messages.toString();
            
            Role role = Role.getPlayerRole(player);
            if (player.hasPermission("zbedwars.chat.color")) {
              message = StringUtils.formatColors(message);
            }
            
            if (game.getTeam(player) == null) {
              evt.setCancelled(false);
              return;
            }
            game.broadcastMessage(Language.chat$format$ingame$global.replace("{team}", StringUtils.getFirstColor(game.getTeam(player).getName()) + "[" + game.getTeam(player).getName() + "]").replace("{player}",
                role.getPrefix() + player.getName()).replace("{color}",
                role.isDefault() ? Language.chat$color$default : Language.chat$color$custom).replace("{message}", message));
          }
        }
      } else {
        BedWars game = profile.getGame(BedWars.class);
        if (game != null && !game.isSpectator(player)) {
          evt.setCancelled(true);
          player.sendMessage("§cUtilize /g [mensagem]");
        }
      }
    }
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    flood.remove(evt.getPlayer().getName());
  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void AsyncPlayerChat(AsyncPlayerChatEvent evt) {
    if (evt.isCancelled()) {
      return;
    }
    
    Player player = evt.getPlayer();
    if (!player.hasPermission("zbedwars.chat.delay")) {
      long start = flood.containsKey(player.getName()) ? flood.get(player.getName()) : 0;
      if (start > System.currentTimeMillis()) {
        double time = (start - System.currentTimeMillis()) / 1000.0;
        if (time > 0.1) {
          evt.setCancelled(true);
          String timeString = df.format(time).replace(",", ".");
          if (timeString.endsWith("0")) {
            timeString = timeString.substring(0, timeString.lastIndexOf("."));
          }
          
          player.sendMessage(Language.chat$delay.replace("{time}", timeString));
          return;
        }
      }
      
      flood.put(player.getName(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3));
    }
    
    Role role = Role.getPlayerRole(player);
    if (player.hasPermission("zbedwars.chat.color")) {
      evt.setMessage(StringUtils.formatColors(evt.getMessage()));
    }
    
    Profile profile = Profile.getProfile(player.getName());
    BedWars game = profile.getGame(BedWars.class);
    String suffix = "";
    DataContainer container = profile.getDataContainer("zCoreProfile", "clan");
    if (container != null) {
      suffix = container.getAsString().replace(" ", "") + " ";
      if (suffix.contains("§8")) {
        suffix = "";
      }
    }
    if (game == null) {
      evt.setFormat(
          Language.chat$format$lobby.replace("{player}", role.getPrefix() + "%s").replace("{color}", role.isDefault() ? Language.chat$color$default : Language.chat$color$custom)
              .replace("{message}", "%s"));
    } else if (!game.isSpectator(player)) {
      if (game.getMode().equals(BedWarsMode.SOLO)) {
        evt.setFormat(
            Language.chat$format$ingame$global.replace("{team}", StringUtils.getFirstColor(game.getTeam(player).getName()) + "[" + game.getTeam(player).getName() + "]").replace("{player}",
                role.getPrefix() + "%s").replace("{color}",
                role.isDefault() ? Language.chat$color$default : Language.chat$color$custom).replace("{message}", "%s"));
        
      } else {
        evt.setFormat(
            Language.chat$format$ingame$team.replace("{player}", role.getPrefix() + "%s").replace("{color}", role.isDefault() ? Language.chat$color$default : Language.chat$color$custom)
                .replace("{message}", "%s"));
      }
    } else if (game.isSpectator(player)) {
      evt.setFormat(
          Language.chat$format$spectator.replace("{player}", role.getPrefix() + "%s").replace("{color}", role.isDefault() ? Language.chat$color$default : Language.chat$color$custom)
              .replace("{message}", "%s"));
    }
    evt.getRecipients().clear();
    evt.setFormat((suffix.equals(" ") ? "" : suffix) + "§r" + evt.getFormat());
    for (Player players : player.getWorld().getPlayers()) {
      Profile profiles = Profile.getProfile(players.getName());
      if (profiles != null) {
        if (game == null) {
          if (!profiles.playingGame()) {
            evt.getRecipients().add(players);
          }
        } else if (profiles.playingGame() && profiles.getGame().equals(game)) {
          if (!game.isSpectator(player) && !game.isSpectator(players) && profile.getGame().getTeam(player).hasMember(players) && !profile.getGame(BedWars.class).getMode().equals(BedWarsMode.SOLO)
              && !profiles.getGame(BedWars.class).getMode().equals(BedWarsMode.SOLO)) {
            evt.getRecipients().add(players);
          } else if (!game.isSpectator(player) && !game.isSpectator(players) && profile.getGame(BedWars.class).getMode().equals(BedWarsMode.SOLO)
              && profiles.getGame(BedWars.class).getMode().equals(BedWarsMode.SOLO)) {
            evt.getRecipients().add(players);
          } else if (game.isSpectator(player) && game.isSpectator(players)) {
            evt.getRecipients().add(players);
          }
        }
      }
    }
  }
}
