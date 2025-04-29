package mc.twilight.utilidades.lobby;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.core.Core;
import mc.twilight.core.game.Game;
import mc.twilight.core.game.GameTeam;
import mc.twilight.core.player.Profile;

public class LobbyCommand extends Commands {

    public LobbyCommand() {
        super("lobby");
    }


    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        Profile profile = Profile.getProfile(player.getName());
        if (Language.options$bungeecord) {
            if (!profile.playingGame()) {
                Core.sendServer(Profile.getProfile(player.getName()), Main.getInstance().getConfig().getString("lobby_servername"));
                return;
            }
        }
        if (profile.playingGame()) {
            if (profile.getGame() != null) {
                profile.getGame().leave(profile, null);
            }
        }
        player.sendMessage(Language.lobby$spigot$no_playing);
    }
}
