package mc.twilight.cosmeticos.cmd;

import mc.twilight.cosmeticos.api.CosmeticsAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmeticsMenuCommand extends Commands {
  
  protected CosmeticsMenuCommand() {
    super("cosmetics", "cosmeticos");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      return;
    }
    
    CosmeticsAPI.openMenu((Player) sender);
  }
}
