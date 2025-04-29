package mc.twilight.utilidades.cmd.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.cmd.Commands;

public class OnlineCommand extends Commands {

    public OnlineCommand() {
        super("online");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        player.sendMessage("§aHá §e" + Bukkit.getOnlinePlayers().size() + " §ajogadores no seu servidor atual!");
    }
}
