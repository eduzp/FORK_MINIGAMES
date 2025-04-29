package mc.twilight.utilidades.cmd.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;

import static mc.twilight.utilidades.Main.reply;

public class TellCommand extends Commands {

    public TellCommand() {
        super("tell");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage("§cUtilize /tell [player] [mensagem]");
            return;
        }
        if (args[0].equals(player.getName())) {
            player.sendMessage("§cVocê não pode mandar mensagens privadas para sí mesmo.");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage("§cUsuário não encontrado.");
            return;
        }
        reply.put(player, target);
        reply.put(target, player);
        String msg = StringUtils.join(args, 1, " ");
        if (player.hasPermission("zutilidades.tell.color")) {
            msg = msg.replace("&", "§");
        }
        target.sendMessage(Language.tell$format$target.replace("{player}", Role.getPrefixed(player.getName())).replace("{message}", msg));
        player.sendMessage(Language.tell$format$sender.replace("{player}", Role.getPrefixed(target.getName())).replace("{message}", msg));
    }
}
