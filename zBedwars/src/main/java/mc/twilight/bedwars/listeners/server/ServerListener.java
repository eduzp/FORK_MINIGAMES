package mc.twilight.bedwars.listeners.server;

import mc.twilight.bedwars.game.BedWars;
import mc.twilight.core.game.GameState;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;

public class ServerListener implements Listener {
  
  @EventHandler
  public void onBlockIgnite(BlockIgniteEvent evt) {
    BedWars game = BedWars.getByWorldName(evt.getBlock().getWorld().getName());
    if (game == null || game.getState() != GameState.EMJOGO || game.isPlacedBlock(evt.getBlock())) {
      evt.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBlockBurn(BlockBurnEvent evt) {
    BedWars game = BedWars.getByWorldName(evt.getBlock().getWorld().getName());
    if (game == null || game.getState() != GameState.EMJOGO || game.isPlacedBlock(evt.getBlock())) {
      evt.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBlockExplode(BlockExplodeEvent evt) {
    BedWars game = BedWars.getByWorldName(evt.getBlock().getWorld().getName());
    if (game == null || game.getState() != GameState.EMJOGO) {
      evt.setCancelled(true);
    } else {
      for (Block block : new ArrayList<>(evt.blockList())) {
        int protectionBlock = 0;
        for (BlockFace blockface : new BlockFace[]{BlockFace.UP, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH}) {
          if (block.getRelative(blockface).getType().name().contains("GLASS")) {
            protectionBlock++;
          }
        }
        
        if (game.isPlacedBlock(block) || block.getType().name().contains("GLASS") || protectionBlock > 1) {
          evt.blockList().remove(block);
        }
      }
    }
  }
  
  @EventHandler
  public void onLeavesDecay(LeavesDecayEvent evt) {
    evt.setCancelled(true);
  }
  
  @EventHandler
  public void onEntityExplode(EntityExplodeEvent evt) {
    BedWars game = BedWars.getByWorldName(evt.getEntity().getWorld().getName());
    if (game == null || game.getState() != GameState.EMJOGO) {
      evt.setCancelled(true);
    } else {
      for (Block block : new ArrayList<>(evt.blockList())) {
        int protectionBlock = 0;
        for (BlockFace blockface : new BlockFace[]{BlockFace.UP, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH}) {
          if (block.getRelative(blockface).getType().name().contains("GLASS")) {
            protectionBlock++;
          }
        }
        
        if (game.isPlacedBlock(block) || block.getType().name().contains("GLASS") || protectionBlock > 1) {
          evt.blockList().remove(block);
        }
      }
    }
  }
  
  @EventHandler
  public void onWeatherChange(WeatherChangeEvent evt) {
    evt.setCancelled(evt.toWeatherState());
  }
}
