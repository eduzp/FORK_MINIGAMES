package mc.twilight.skywars.cmd.sw;

import mc.twilight.core.Core;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.cmd.SubCommand;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends SubCommand {
  
  public SetSpawnCommand() {
    super("setspawn", "setspawn", "Setar o spawn do servidor.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
    location.setYaw(player.getLocation().getYaw());
    location.setPitch(player.getLocation().getPitch());
    Main.getInstance().getConfig().set("spawn", BukkitUtils.serializeLocation(location));
    Main.getInstance().saveConfig();
    Core.setLobby(location);
    player.sendMessage("§aSpawn setado.");
  }
}
