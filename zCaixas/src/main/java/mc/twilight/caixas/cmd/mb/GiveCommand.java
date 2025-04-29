package mc.twilight.caixas.cmd.mb;

import mc.twilight.caixas.cmd.SubCommand;
import mc.twilight.core.player.Profile;
import org.bukkit.command.CommandSender;

public class GiveCommand extends SubCommand {
  
  public GiveCommand() {
    super("dar", "dar [jogador] [quantia]", "Dar Caixas de Saque para jogadores.", false);
  }
  
  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length <= 1) {
      sender.sendMessage("§cUtilize /mb " + this.getUsage());
      return;
    }
    
    Profile profile = Profile.getProfile(args[0]);
    if (profile == null) {
      sender.sendMessage("§cUsuário não encontrado.");
      return;
    }
    
    int amount;
    try {
      if (args[1].startsWith("-")) {
        throw new NumberFormatException();
      }
      
      amount = Integer.parseInt(args[1]);
    } catch (NumberFormatException ex) {
      sender.sendMessage("§cUtilize números válidos.");
      return;
    }
    
    profile.addStats("zCaixas", amount, "magic");
    sender.sendMessage("§aCaixa de Saque adicionada com sucesso!");
  }
}
