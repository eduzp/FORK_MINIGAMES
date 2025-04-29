package mc.twilight.caixas.cmd.mb;

import mc.twilight.caixas.cmd.SubCommand;
import mc.twilight.caixas.lobby.BoxNPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BoxNPCCommand extends SubCommand {
  
  public BoxNPCCommand() {
    super("capsula", "capsula", "Adicione/remova Cápsulas Mágicas", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (args.length == 0) {
      player.sendMessage(" \n§eAjuda - Cápsulas\n \n§6/mb capsula adicionar [id] §f- §7Adicionar Cápsula.\n§6/mb capsula remover [id] §f- §7Remover Cápsula.\n ");
      return;
    }
    
    String action = args[0];
    if (action.equalsIgnoreCase("adicionar")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /mb capsula adicionar [id] ");
        return;
      }
      
      String id = args[1];
      if (BoxNPC.getById(id) != null) {
        player.sendMessage("§cJá existe uma Cápsula tilizando \"" + id + "\" como ID.");
        return;
      }
      
      Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
      location.setYaw(player.getLocation().getYaw());
      location.setPitch(player.getLocation().getPitch());
      BoxNPC.add(id, location);
      player.sendMessage("§aCaixa adicionada com sucesso.");
    } else if (action.equalsIgnoreCase("remover")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /mb capsula remover [id]");
        return;
      }
      
      String id = args[1];
      BoxNPC npc = BoxNPC.getById(id);
      if (npc == null) {
        player.sendMessage("§cNão existe uma Cápsula utilizando \"" + id + "\" como ID.");
        return;
      }
      
      BoxNPC.remove(npc);
      player.sendMessage("§cCaixa removida com sucesso.");
    } else {
      player.sendMessage(" \n§eAjuda - Cápsulas\n \n§6/mb capsula adicionar [id] §f- §7Adicionar Cápsula.\n§6/mb capsula remover [id] §f- §7Remover Cápsula.\n ");
    }
  }
}