package mc.twilight.bedwars.cmd.bw;

import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.cmd.SubCommand;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.enums.BedWarsMode;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.CubeID;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateCommand extends SubCommand {
  
  public static final Map<Player, Object[]> CREATING = new HashMap<>();
  
  public CreateCommand() {
    super("criar", "criar [solo/dupla/quarteto] [nome]", "Criar uma sala.", true);
  }
  
  public static void handleClick(Profile profile, String display, PlayerInteractEvent evt) {
    Player player = profile.getPlayer();
    
    switch (display) {
      case "§aCuboID da Arena": {
        evt.setCancelled(true);
        if (evt.getAction() == Action.LEFT_CLICK_BLOCK) {
          CREATING.get(player)[3] = evt.getClickedBlock().getLocation();
          player.sendMessage("§aBorda da Arena 1 setada.");
        } else if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
          CREATING.get(player)[4] = evt.getClickedBlock().getLocation();
          player.sendMessage("§aBorda da Arena 2 setada.");
        } else {
          player.sendMessage("§cClique em um bloco.");
        }
        break;
      }
      case "§aConfirmar": {
        evt.setCancelled(true);
        if (CREATING.get(player)[3] == null) {
          player.sendMessage("§cBorda da Arena 1 não setada.");
          return;
        }
        
        if (CREATING.get(player)[4] == null) {
          player.sendMessage("§cBorda da Arena 2 não setada.");
          return;
        }
        
        Object[] array = CREATING.get(player);
        World world = player.getWorld();
        KConfig config = Main.getInstance().getConfig("arenas", world.getName());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();
        CREATING.remove(player);
        player.sendMessage("§aCriando sala...");
        
        CubeID cube = new CubeID((Location) array[3], (Location) array[4]);
        
        config.set("name", array[1]);
        config.set("mode", array[2]);
        config.set("minPlayers", 4);
        config.set("cubeId", cube.toString());
        config.set("spawns", new ArrayList<>());
        config.set("generators", new ArrayList<>());
        world.save();
        
        player.sendMessage("§aCriando backup do mapa...");
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
          Main.getInstance().getFileUtils().copyFiles(new File(world.getName()), new File("plugins/zBedWars/mundos/" + world.getName()), "playerdata", "stats", "uid.dat");
          
          profile.setHotbar(Hotbar.getHotbarById("lobby"));
          profile.refresh();
          BedWars.load(config.getFile(), () -> player.sendMessage("§aSala criada com sucesso."));
        }, 60);
        break;
      }
    }
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (BedWars.getByWorldName(player.getWorld().getName()) != null) {
      player.sendMessage("§cJá existe uma sala neste mundo.");
      return;
    }
    
    if (args.length <= 1) {
      player.sendMessage("§cUtilize /bw " + this.getUsage());
      return;
    }
    
    BedWarsMode mode = BedWarsMode.fromName(args[0]);
    if (mode == null) {
      player.sendMessage("§cUtilize /bw " + this.getUsage());
      return;
    }
    
    String name = StringUtils.join(args, 1, " ");
    Object[] array = new Object[5];
    array[0] = player.getWorld();
    array[1] = name;
    array[2] = mode.name();
    CREATING.put(player, array);
    
    player.getInventory().clear();
    player.getInventory().setArmorContents(null);
    
    player.getInventory().setItem(0, BukkitUtils.deserializeItemStack("BLAZE_ROD : 1 : nome>&aCuboID da Arena"));
    player.getInventory().setItem(1, BukkitUtils.deserializeItemStack("STAINED_CLAY:13 : 1 : nome>&aConfirmar"));
    
    player.updateInventory();
    
    Profile.getProfile(player.getName()).setHotbar(null);
  }
}
