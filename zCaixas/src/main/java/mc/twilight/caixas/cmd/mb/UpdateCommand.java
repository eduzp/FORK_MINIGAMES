package mc.twilight.caixas.cmd.mb;

import mc.twilight.caixas.cmd.SubCommand;
import org.bukkit.entity.Player;

public class UpdateCommand extends SubCommand {
  
  public UpdateCommand() {
    super("atualizar", "atualizar", "Atualizar o zCaixas.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    player.sendMessage("§aO plugin já se encontra em sua última versão.");
  }
}
