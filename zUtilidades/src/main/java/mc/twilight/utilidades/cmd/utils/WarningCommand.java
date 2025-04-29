package mc.twilight.utilidades.cmd.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;

public class WarningCommand extends Commands {

    public WarningCommand() {
        super("aviso", "bc", "alert");
    }


    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("zutilidades.cmd.aviso")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        if (args.length == 0) {
            player.sendMessage("§cUtilize /aviso [mensagem]");
            return;
        }
        Bukkit.getOnlinePlayers().forEach(playerr -> {
            if (Language.warning$play_sound) {
                EnumSound.valueOf(Language.warning$sound).play(playerr, 1.0F, 1.0F);
            }
            NMS.sendTitle(playerr, Language.warning$title$header, Language.warning$title$footer.replace("{message}", StringUtils.formatColors(StringUtils.join(args, " "))));
            playerr.sendMessage("");
            playerr.sendMessage(Language.warning$format.replace("{player}", Role.getPrefixed(player.getName(), true)).replace("{message}", StringUtils.join(args, " ").replace("&", "")));
            playerr.sendMessage("");
        });
    }
}
