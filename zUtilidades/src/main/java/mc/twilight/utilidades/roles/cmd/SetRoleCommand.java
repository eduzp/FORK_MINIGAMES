package mc.twilight.utilidades.roles.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.utilidades.roles.menu.MenuSetRole;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;

public class SetRoleCommand extends Commands {

    public SetRoleCommand() {
        super("setarcargo", "darcargo");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("zutilidades.cmd.setar")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        if (args.length == 0) {
            player.sendMessage("§6Utilize /setarcargo [player] ou /darcargo [player]");
            return;
        }
        if (args.length == 1) {
            new MenuSetRole(Profile.getProfile(player.getName()), args[0]);
        } else {
            if (Role.getRoleByName(args[1]) == null) {
                player.sendMessage("§cUtilize apenas grupos válidos.");
                return;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent set " + args[1]);
            player.sendMessage(Language.roles$set.replace("{player}", args[0]).replace("{role}", Role.getRoleByName(args[1]).getName()));
            Bukkit.getOnlinePlayers().forEach(playerr -> NMS.sendTitle(playerr, Language.roles$titles$header.replace("{player}", StringUtils.getFirstColor(Role.getRoleByName(args[1]).getName()) + " " + args[0]), Language.roles$titles$footer.replace("{role}", Role.getRoleByName(args[1]).getName())));
            if (Bukkit.getPlayerExact(args[0]) != null) {
                if (Bukkit.getPlayerExact(args[0]).isOnline()) {
                    Bukkit.getPlayerExact(args[0]).sendMessage(Language.roles$notification.replace("{role}", Role.getRoleByName(args[1]).getName()));
                }
            }
        }
    }
}
