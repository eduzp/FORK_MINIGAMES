package mc.twilight.utilidades.bungee.cmd.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import mc.twilight.utilidades.bungee.cmd.Commands;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;

public class WarningCommand extends Commands {

    public WarningCommand() {
        super("aviso", "bc");
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cApenas jogadores podem utilizar este comando."));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission("zutilidades.cmd.warning")) {
            player.sendMessage(TextComponent.fromLegacyText("§cVocê não tem permissão para executar este comando."));
            return;
        }
        if (args.length == 0) {
            player.sendMessage(TextComponent.fromLegacyText("§cUtilize /aviso [mensagem]"));
            return;
        }
        String msg = StringUtils.join(args, " ");
        BungeeCord.getInstance().getPlayers().forEach(ppr -> {
            ppr.sendMessage(TextComponent.fromLegacyText(""));
            ppr.sendMessage(TextComponent.fromLegacyText(" " + Role.getPrefixed(player.getName()) + "§f: " + StringUtils.formatColors(msg)));
            ppr.sendMessage(TextComponent.fromLegacyText(""));

        });
    }
}
