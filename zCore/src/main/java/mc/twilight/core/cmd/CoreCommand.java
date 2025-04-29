package mc.twilight.core.cmd;

import mc.twilight.core.Core;
import mc.twilight.core.database.Database;
import mc.twilight.core.utils.SlickUpdater;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoreCommand extends Commands {
  
  public CoreCommand() {
    super("zcore", "zc");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (!player.hasPermission("zcore.admin")) {
        player.sendMessage("§6ZCore §bv" + Core.getInstance().getDescription().getVersion() + " §7Feito por eduzp.");
        return;
      }
      
      if (args.length == 0) {
        player.sendMessage(" \n§6/kc atualizar §f- §7Atualizar o ZCore.\n§6/kc converter §f- §7Converter seu Banco de Dados.\n ");
        return;
      }
      
      String action = args[0];
      if (action.equalsIgnoreCase("atualizar")) {
        if (SlickUpdater.UPDATER != null) {
          if (!SlickUpdater.UPDATER.canDownload) {
            player.sendMessage(
                " \n§6§l[zCore]\n \n§aA atualização já está baixada, ela será aplicada na próxima reinicialização do servidor. Caso deseje aplicá-la agora, utilize o comando /stop.\n ");
            return;
          }
          SlickUpdater.UPDATER.canDownload = false;
          SlickUpdater.UPDATER.downloadUpdate(player);
        } else {
          player.sendMessage("§aO plugin já se encontra em sua última versão.");
        }
      } else if (action.equalsIgnoreCase("converter")) {
        player.sendMessage("§fBanco de Dados: " + Database.getInstance().getClass().getSimpleName().replace("Database", ""));
        Database.getInstance().convertDatabase(player);
      } else {
        player.sendMessage(" \n§6/kc atualizar §f- §7Atualizar o ZCore.\n§6/kc converter §f- §7Converter seu Banco de Dados.\n ");
      }
    } else {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
    }
  }
}
