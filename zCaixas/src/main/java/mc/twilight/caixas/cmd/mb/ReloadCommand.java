package mc.twilight.caixas.cmd.mb;

import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.cmd.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {
  public ReloadCommand() {
    super("reload", "reload", "Recarrega os prêmios da DB do zCaixas.", false);
  }
  
  @Override
  public void perform(CommandSender sender, String[] args) {
    BoxContent.setupRewards();
    sender.sendMessage("§aOs conteúdos das Cápsulas Mágicas foram recarregados!");
  }
}