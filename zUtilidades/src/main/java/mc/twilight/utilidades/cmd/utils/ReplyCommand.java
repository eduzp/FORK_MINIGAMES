package mc.twilight.utilidades.cmd.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;

import static mc.twilight.utilidades.Main.reply;

public class ReplyCommand extends Commands {

    public ReplyCommand() {
        super("r", "reply");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("§cUtilize /tell [mensagem]");
            return;
        }
        Player target = reply.get(player);
        if (target == null) {
            player.sendMessage("§cVocê não tem ninguém para responder.");
            return;
        }
        if (!target.isOnline()) {
            player.sendMessage("§cUsuário não encontrado.");
            return;
        }
        reply.put(target, player);
        String msg = StringUtils.join(args, " ");
        if (player.hasPermission("zutilidades.tell.color")) {
            msg = msg.replace("&", "§");
        }
        target.sendMessage(Language.tell$format$target.replace("{player}", Role.getPrefixed(player.getName())).replace("{message}", msg));
        player.sendMessage(Language.tell$format$sender.replace("{player}", Role.getPrefixed(target.getName())).replace("{message}", msg));
    }
}
