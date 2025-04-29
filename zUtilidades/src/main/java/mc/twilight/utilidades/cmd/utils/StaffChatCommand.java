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

import java.util.ArrayList;
import java.util.List;

public class StaffChatCommand extends Commands {

    public static List<Player> BAIANOS = new ArrayList<>();

    public StaffChatCommand() {
        super("sc", "staffchat");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("zutilidades.cmd.staffchat")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        if (args.length == 0) {
            if (BAIANOS.contains(player)) {
                player.sendMessage("§cAgora tudo que você escrever no chat não aparecerá no chat staff.");
                BAIANOS.remove(player);
            } else {
                player.sendMessage("§aAgora tudo que você escrever no chat aparecerá no chat staff.");
                BAIANOS.add(player);
            }
            return;
        }

        Bukkit.getOnlinePlayers().stream().filter(playerx -> playerx.hasPermission("zutilidades.cmd.staffchat")).forEach(playerx -> {
            if (Language.staffchat$warning_actionbar) {
                NMS.sendActionBar(playerx, Language.staffchat$actionbar);
            }
            if (Language.staffchat$play_sound) {
                EnumSound.valueOf(Language.staffchat$sound).play(playerx, 1.0F, 1.0F);
            }
            playerx.sendMessage(Language.staffchat$format.replace("{player}", Role.getPrefixed(player.getName(), true)).replace("{message}", StringUtils.join(args, " ").replace("&", "§")));
        });
    }
}
