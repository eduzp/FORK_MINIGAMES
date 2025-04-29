package mc.twilight.caixas.cmd.mb;

import mc.twilight.caixas.Main;
import mc.twilight.caixas.cmd.SubCommand;
import org.bukkit.entity.Player;

public class SetLocationCommand extends SubCommand {
  
  public SetLocationCommand() {
    super("setlocation", "setlocation", "Setar o local de abertura das caixas.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    Main.setLootChestsLocation(player.getLocation().getBlock().getLocation().add(0.5, 1.0, 0.5));
    player.sendMessage("§aLocal de abertura setado!");
  }
}
