package mc.twilight.skywars.cmd.sw;

import mc.twilight.skywars.cmd.SubCommand;
import org.bukkit.entity.Player;

public class UpdateCommand extends SubCommand {
  
  public UpdateCommand() {
    super("atualizar", "atualizar", "Atualizar o zSkyWars.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
     player.sendMessage("§aO plugin já se encontra em sua última versão.");
  }
}
