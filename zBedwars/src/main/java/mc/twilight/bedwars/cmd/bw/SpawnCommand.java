package mc.twilight.bedwars.cmd.bw;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.cmd.SubCommand;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.CubeID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class SpawnCommand extends SubCommand {
  
  public static final HashMap<Player, Object[]> SPAWN = new HashMap<>();
  
  public SpawnCommand() {
    super("spawn", "spawn", "Adicionar spawns em uma arena.", true);
  }
  
  public static void apply(Profile profile, BedWars game) {
    Player player = profile.getPlayer();
    
    String name = BedWarsTeam.names[game.listTeams().size()];
    profile.setHotbar(null);
    
    player.setGameMode(GameMode.CREATIVE);
    player.getInventory().clear();
    player.getInventory().setArmorContents(null);
    
    player.getInventory().setItem(0, BukkitUtils.deserializeItemStack("BEACON : 1 : nome>&aSpawn"));
    player.getInventory().setItem(1, BukkitUtils.deserializeItemStack("GOLD_BLOCK : 1 : nome>&aGerador"));
    player.getInventory().setItem(2, BukkitUtils.deserializeItemStack("BED : 1 : nome>&aCama"));
    player.getInventory().setItem(3, BukkitUtils.deserializeItemStack("EMERALD : 1 : nome>&aItemShop"));
    player.getInventory().setItem(4, BukkitUtils.deserializeItemStack("ENCHANTMENT_TABLE : 1 : nome>&aUpgradeShop"));
    
    player.getInventory().setItem(5, BukkitUtils.deserializeItemStack("BLAZE_ROD : 1 : nome>&aCuboID da Ilha"));
    
    player.getInventory().setItem(7,
        BukkitUtils.deserializeItemStack("WOOL:" + BedWarsTeam.ids[game.listTeams().size()] + " : 1 : nome>&aTime: " + name));
    player.getInventory().setItem(8, BukkitUtils.deserializeItemStack("STAINED_CLAY:5 : 1 : nome>&aConfirmar"));
    
    player.updateInventory();
  }
  
  public static void handleClick(Profile profile, String display, PlayerInteractEvent evt) {
    Player player = profile.getPlayer();
    BedWars game = BedWars.getByWorldName(player.getWorld().getName());
    
    switch (display) {
      case "§aSpawn": {
        evt.setCancelled(true);
        Location location = player.getLocation().getBlock().getLocation().clone().add(0.5, 0.0, 0.5);
        location.setYaw(player.getLocation().getYaw());
        location.setPitch(player.getLocation().getPitch());
        SPAWN.get(player)[1] = BukkitUtils.serializeLocation(location);
        player.sendMessage("§aSpawn setado.");
        break;
      }
      case "§aGerador": {
        evt.setCancelled(true);
        SPAWN.get(player)[2] = BukkitUtils.serializeLocation(player.getLocation().getBlock().getLocation().clone().add(0.5, 0.0, 0.5));
        player.sendMessage("§aGerador setado.");
        break;
      }
      case "§aCama": {
        evt.setCancelled(true);
        if (evt.getClickedBlock() != null && PlayerUtils.isBedBlock(evt.getClickedBlock())) {
          SPAWN.get(player)[3] = BukkitUtils.serializeLocation(evt.getClickedBlock().getLocation());
          player.sendMessage("§aCama setada.");
        } else {
          player.sendMessage("§cClique em uma cama.");
        }
        break;
      }
      case "§aItemShop": {
        evt.setCancelled(true);
        Location location = player.getLocation().getBlock().getLocation().clone().add(0.5, 0.0, 0.5);
        location.setYaw(player.getLocation().getYaw());
        location.setPitch(player.getLocation().getPitch());
        SPAWN.get(player)[4] = BukkitUtils.serializeLocation(location);
        player.sendMessage("§aLoja de itens setada.");
        break;
      }
      case "§aUpgradeShop": {
        evt.setCancelled(true);
        Location location = player.getLocation().getBlock().getLocation().clone().add(0.5, 0.0, 0.5);
        location.setYaw(player.getLocation().getYaw());
        location.setPitch(player.getLocation().getPitch());
        SPAWN.get(player)[5] = BukkitUtils.serializeLocation(location);
        player.sendMessage("§aLoja de upgrades setada.");
        break;
      }
      case "§aCuboID da Ilha": {
        evt.setCancelled(true);
        if (evt.getAction() == Action.LEFT_CLICK_BLOCK) {
          SPAWN.get(player)[6] = evt.getClickedBlock().getLocation();
          player.sendMessage("§aBorda da Arena 1 setada.");
        } else if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
          SPAWN.get(player)[7] = evt.getClickedBlock().getLocation();
          player.sendMessage("§aBorda da Arena 2 setada.");
        } else {
          player.sendMessage("§cClique em um bloco.");
        }
        break;
      }
      case "§aConfirmar": {
        evt.setCancelled(true);
        if (SPAWN.get(player)[1] == null) {
          player.sendMessage("§cSpawn não setado.");
          return;
        } else if (SPAWN.get(player)[2] == null) {
          player.sendMessage("§cGerador não setado.");
          return;
        } else if (SPAWN.get(player)[3] == null) {
          player.sendMessage("§cCama não setada.");
          return;
        } else if (SPAWN.get(player)[4] == null) {
          player.sendMessage("Loja de itens não setada.");
          return;
        } else if (SPAWN.get(player)[5] == null) {
          player.sendMessage("§cLoja de upgrades não setada.");
          return;
        } else if (SPAWN.get(player)[6] == null) {
          player.sendMessage("§cBorda 1 da Ilha não setada.");
          return;
        } else if (SPAWN.get(player)[7] == null) {
          player.sendMessage("§cBorda 2 da Ilha não setada.");
          return;
        }
        
        Object[] array = SPAWN.get(player);
        CubeID cube = new CubeID((Location) array[6], (Location) array[7]);
        
        JsonObject spawn = new JsonObject();
        spawn.add("spawn", new JsonPrimitive((String) array[1]));
        spawn.add("generator", new JsonPrimitive((String) array[2]));
        spawn.add("bed", new JsonPrimitive((String) array[3]));
        spawn.add("shop", new JsonPrimitive((String) array[4]));
        spawn.add("upgrades", new JsonPrimitive((String) array[5]));
        spawn.add("cubeId", new JsonPrimitive(cube.toString()));
        game.addSpawn(spawn);
        player.sendMessage("§aSpawn adicionado.");
        if (game.listTeams().size() >= 8) {
          SPAWN.remove(player);
          profile.setHotbar(Hotbar.getHotbarById("lobby"));
          profile.refresh();
          return;
        }
        
        SPAWN.get(player)[1] = null;
        SPAWN.get(player)[2] = null;
        SPAWN.get(player)[3] = null;
        SPAWN.get(player)[4] = null;
        SPAWN.get(player)[5] = null;
        SPAWN.get(player)[6] = null;
        SPAWN.get(player)[7] = null;
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> apply(profile, game));
        break;
      }
      case "§cCancelar": {
        evt.setCancelled(true);
        SPAWN.remove(player);
        profile.setHotbar(Hotbar.getHotbarById("lobby"));
        profile.refresh();
        break;
      }
    }
  }
  
  @Override
  public void perform(Player player, String[] args) {
    BedWars game = BedWars.getByWorldName(player.getWorld().getName());
    if (game == null) {
      player.sendMessage("§cNão existe uma arena neste mundo.");
      return;
    }
    
    if (game.listTeams().size() >= 8) {
      player.sendMessage("§cEssa arena já possui o máximo de spawns.");
      return;
    }
    
    Object[] arr = new Object[8];
    arr[0] = game;
    SPAWN.put(player, arr);
    
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> apply(Profile.getProfile(player.getName()), game));
  }
}
