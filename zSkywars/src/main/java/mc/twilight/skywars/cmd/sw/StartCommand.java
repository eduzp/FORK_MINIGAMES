package mc.twilight.skywars.cmd.sw;

import mc.twilight.core.game.GameState;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.cmd.SubCommand;
import mc.twilight.skywars.game.AbstractSkyWars;
import org.bukkit.entity.Player;

public class StartCommand extends SubCommand {
  
  public StartCommand() {
    super("iniciar", "iniciar", "Iniciar a partida.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null) {
        if (game.getState() == GameState.AGUARDANDO) {
          game.start();
          player.sendMessage("§aVocê iniciou a partida!");
        } else {
          player.sendMessage("§cA partida já está em andamento.");
        }
      }
    }
  }
}
