package mc.twilight.bedwars.listeners.player;

import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.cmd.bw.BuildCommand;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.game.GameState;
import mc.twilight.core.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class PlayerRestListener implements Listener {
  
  @EventHandler
  public void onPlayerItemConsume(PlayerItemConsumeEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      BedWars game = profile.getGame(BedWars.class);
      if (game != null && evt.getItem().getType().name().contains("POTION")) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PlayerUtils.removeItem(player.getInventory(), Material.matchMaterial("GLASS_BOTTLE"), 1), 1);
      }
    }
  }
  
  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      BedWars game = profile.getGame(BedWars.class);
      if (game == null) {
        evt.setCancelled(true);
      } else {
        if (game.getState() != GameState.EMJOGO || game.isSpectator(evt.getPlayer())) {
          evt.setCancelled(true);
        } else {
          ItemStack item = evt.getItemDrop().getItemStack();
          if (item.getType().name().contains("_SWORD") || item.getType().name().contains("_HELMET") || item.getType().name().contains("_CHESTPLATE")
              || item.getType().name().contains("_LEGGINGS") || item.getType().name().contains("_BOOTS") || item.getType().name().contains("BOW")
              || item.getType().name().contains("_PICKAXE") || item.getType().name().contains("_AXE") || item.getType().name().contains("SHEARS")
              || item.getType().name().contains("COMPASS")) {
            evt.setCancelled(true);
          }
        }
      }
    }
  }
  
  @EventHandler
  public void onPlayerPickupItem(PlayerPickupItemEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      BedWars game = profile.getGame(BedWars.class);
      if (game == null) {
        evt.setCancelled(true);
      } else {
        if (game.getState() != GameState.EMJOGO || game.isSpectator(evt.getPlayer())) {
          evt.setCancelled(true);
        }
      }
    }
  }
  
  
  @EventHandler
  public void onBlockBreak(BlockBreakEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      BedWars game = profile.getGame(BedWars.class);
      if (game == null) {
        evt.setCancelled(!BuildCommand.hasBuilder(player));
      } else {
        if (game.getState() != GameState.EMJOGO || game.isSpectator(player)) {
          evt.setCancelled(true);
        } else {
          Block block = evt.getBlock();
          if (block.getType().name().contains("BED_BLOCK")) {
            for (BedWarsTeam team : game.listTeams().stream().filter(BedWarsTeam::isAlive).collect(Collectors.toList())) {
              if (team.isBed(block)) {
                if (!team.hasMember(evt.getPlayer())) {
                  game.destroyBed(team, profile);
                  return;
                }
                
                evt.setCancelled(true);
                evt.getPlayer().sendMessage("§cVocê não pode destruir sua própria cama.");
              }
            }
          } else if (!game.getConfig().getCubeId().contains(evt.getBlock().getLocation())) {
            evt.setCancelled(true);
            player.sendMessage("§cVocê não pode quebrar blocos aqui.");
          } else if (game.isPlacedBlock(block)) {
            evt.setCancelled(true);
            player.sendMessage("§cVocê só pode quebrar blocos colocados por jogadores.");
          }
        }
      }
    }
  }
  
  @EventHandler
  public void onTrapEnter(PlayerMoveEvent evt) {
    Player player = evt.getPlayer();
    
    Profile profile = Profile.getProfile(player.getName());
    BedWars game = profile.getGame(BedWars.class);
    if (game != null && game.getState() == GameState.EMJOGO) {
      game.listTeams().stream().filter(t -> !t.listPlayers().contains(player) && t.cubeId.contains(player.getLocation())).findFirst()
          .ifPresent(team -> team.getTraps().stream().findFirst().ifPresent(trap -> trap.onEnter(team, profile)));
    }
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      BedWars game = profile.getGame(BedWars.class);
      if (game == null) {
        evt.setCancelled(!BuildCommand.hasBuilder(evt.getPlayer()));
      } else {
        if (game.getState() != GameState.EMJOGO || game.isSpectator(evt.getPlayer())) {
          evt.setCancelled(true);
        } else if (!game.getConfig().getCubeId().contains(evt.getBlock().getLocation())) {
          evt.setCancelled(true);
        } else if (game.getState() == GameState.EMJOGO && !game.isSpectator(evt.getPlayer())) {
          Block block = evt.getBlock();
          boolean nearZone = game.listGenerators().stream().anyMatch(generator -> generator.getLocation().getBlock().getLocation().distance(block.getLocation()) < 3);
          if (!nearZone) {
            nearZone = game.listTeams().stream().anyMatch(team -> team.nearBlockedZone(block));
          }
          
          evt.setCancelled(nearZone);
          if (evt.isCancelled()) {
            player.sendMessage("§cVocê não pode colocar blocos aqui.");
            return;
          }
          
          if (block.getType().name().contains("TNT")) {
            block.setType(Material.AIR);
            block.getWorld().spawn(block.getLocation(), TNTPrimed.class).setFuseTicks(60);
            return;
          }
          
          game.addPlacedBlock(block);
        }
      }
    }
  }
}
