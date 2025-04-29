package mc.twilight.utilidades.cmd.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;

public class InvseeCommand extends Commands {

    public InvseeCommand() {
        super("inv", "invsee");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("zutilidades.cmd.inv")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        if (args.length == 0) {
            player.sendMessage("Utilize /invsee [player]");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage("§cUsuário não encontrado.");
            return;
        }
        player.openInventory(target.getInventory());
    }

}
