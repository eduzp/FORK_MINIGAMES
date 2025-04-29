package mc.twilight.bedwars.cmd.bw;

import mc.twilight.bedwars.cmd.SubCommand;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.generators.Generator;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class GeneratorCommand extends SubCommand {
  
  public static final Map<Player, BedWars> GENERATOR = new HashMap<>();
  
  public GeneratorCommand() {
    super("generator", "generator", "Adicionar geradores à uma sala.", true);
  }
  
  public static void handleClick(Player player, Profile profile, String display, PlayerInteractEvent evt) {
    BedWars game = GENERATOR.get(player);
    if (!game.getWorld().equals(player.getWorld())) {
      return;
    }
    
    if (display.startsWith("§aDiamante")) {
      evt.setCancelled(true);
      Block block = evt.getClickedBlock();
      if (block != null && block.getType().name().contains("DIAMOND_BLOCK")) {
        game.addGenerator(block.getLocation().clone().add(0.5, 0.0, 0.5), Generator.Type.DIAMOND);
        player.sendMessage("§aGerador de Diamante adicionado.");
      } else {
        player.sendMessage("§cClique em um bloco de diamante.");
      }
    } else if (display.startsWith("§aEsmeralda")) {
      evt.setCancelled(true);
      Block block = evt.getClickedBlock();
      if (block != null && block.getType().name().contains("EMERALD_BLOCK")) {
        game.addGenerator(block.getLocation().clone().add(0.5, 0.0, 0.5), Generator.Type.EMERALD);
        player.sendMessage("§aGerador de Esmeralda adicionado.");
      } else {
        player.sendMessage("§cClique em um bloco de esmeralda.");
      }
    } else if (display.startsWith("§cCancelar")) {
      evt.setCancelled(true);
      GENERATOR.remove(player);
      profile.setHotbar(Hotbar.getHotbarById("lobby"));
      profile.refresh();
    }
  }
  
  @Override
  public void perform(Player player, String[] args) {
    Profile profile = Profile.getProfile(player.getName());
    BedWars game = BedWars.getByWorldName(player.getWorld().getName());
    if (game == null) {
      player.sendMessage("§cNão existe uma arena neste mundo.");
      return;
    }
    
    profile.setHotbar(null);
    GENERATOR.put(player, game);
  
    player.setGameMode(GameMode.CREATIVE);
    player.getInventory().clear();
    player.getInventory().setArmorContents(null);
    player.getInventory().setItem(0, BukkitUtils.deserializeItemStack(
        "DIAMOND_BLOCK : 1 : nome>&aDiamante"));
    player.getInventory().setItem(1, BukkitUtils.deserializeItemStack(
        "EMERALD_BLOCK : 1 : nome>&aEsmeralda"));
    player.getInventory().setItem(8, BukkitUtils.deserializeItemStack(
        "STAINED_CLAY:14 : 1 : nome>&cCancelar"));
    player.updateInventory();
  }
  
}
