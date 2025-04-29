package mc.twilight.bedwars.cmd;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.core.game.GameState;
import mc.twilight.core.player.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand extends Commands {
  
  public JoinCommand() {
    super("entrar");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      Profile profile = Profile.getProfile(player.getName());
      if (!player.hasPermission("zbedwars.cmd.join")) {
        player.sendMessage("§cVocê não possui permissão para utilizar esse comando.");
        return;
      }
      
      if (args.length == 0) {
        player.sendMessage("§cUtilize /entrar [nome]");
        return;
      }
      
      BedWars game = BedWars.getByWorldName(args[0]);
      if (game == null) {
        player.sendMessage("§cSala não encontrada.");
        return;
      } else if (game.getState() == GameState.EMJOGO) {
        player.sendMessage("§cA partida já está em andamento.");
        return;
      }
      
      player.sendMessage(Language.lobby$npc$play$connect);
      game.join(profile);
    }
  }
}