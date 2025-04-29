package mc.twilight.utilidades.items.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;

public class LobbyItemsCommand extends Commands {

    public LobbyItemsCommand() {
        super("lobbyitems");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("zutilidades.cmd.items")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        player.getActivePotionEffects().clear();
        Profile.getProfile(player.getName()).setHotbar(Hotbar.getHotbarById("lobby"));
        Profile.getProfile(player.getName()).getHotbar().apply(Profile.getProfile(player.getName()));
    }
}
