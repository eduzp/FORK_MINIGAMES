package mc.twilight.utilidades.upgrades.npc.cmd;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.utilidades.upgrades.npc.NPCUpgrade;

public class UpgradeNPCCommand extends Commands {

    public UpgradeNPCCommand() {
        super("npcupgrade");
    }


    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        if (!player.hasPermission("zutilidades.cmd.npcupgrade")) {
            player.sendMessage("§cVocê não tem permissão para isto.");
            return;
        }
        if (args.length == 0) {
            player.sendMessage(" \n§eAjuda\n \n§3/npcupgrade adicionar [id] §f- §7Adicionar NPC.\n§3/npcupgrade remover [id] §f- §7Remover NPC.\n ");
            return;
        }

        String action = args[0];
        if (action.equalsIgnoreCase("adicionar")) {
            if (args.length <= 1) {
                player.sendMessage("§cUtilize /npcupgrade adicionar [id]");
                return;
            }

            String id = args[1];
            if (NPCUpgrade.getById(id) != null) {
                player.sendMessage("§cJá existe um NPC de Upgrades utilizando \"" + id + "\" como ID.");
                return;
            }

            Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
            location.setYaw(player.getLocation().getYaw());
            location.setPitch(player.getLocation().getPitch());
            NPCUpgrade.add(id, location);
            player.sendMessage("§aNPC de Upgrades adicionado com sucesso.");
        } else if (action.equalsIgnoreCase("remover")) {
            if (args.length <= 1) {
                player.sendMessage("§cUtilize /npcupgrade remover [id]");
                return;
            }

            String id = args[1];
            NPCUpgrade npc = NPCUpgrade.getById(id);
            if (npc == null) {
                player.sendMessage("§cNão existe um NPC de Upgrades utilizando \"" + id + "\" como ID.");
                return;
            }

            NPCUpgrade.remove(npc);
            player.sendMessage("§cNPC de Upgrades removido com sucesso.");
        } else {
            player.sendMessage(" \n§eAjuda\n \n§3/npcupgrade adicionar [id] §f- §7Adicionar NPC.\n§3/npcupgrade remover [id] §f- §7Remover NPC.\n ");
        }
    }
}
