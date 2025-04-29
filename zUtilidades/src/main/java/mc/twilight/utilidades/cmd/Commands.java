package mc.twilight.utilidades.cmd;

import mc.twilight.utilidades.cmd.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.cmd.utils.*;
import mc.twilight.utilidades.items.cmd.ItemsCommand;
import mc.twilight.utilidades.items.cmd.LobbyItemsCommand;
import mc.twilight.utilidades.lobby.LobbyCommand;
import mc.twilight.utilidades.roles.cmd.SetRoleCommand;
import mc.twilight.utilidades.upgrades.npc.cmd.UpgradeNPCCommand;
import mc.twilight.core.Core;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * @author Maxter
 */
public abstract class Commands extends Command {

    public Commands(String name, String... aliases) {
        super(name);
        this.setAliases(Arrays.asList(aliases));

        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            simpleCommandMap.register(this.getName(), "zcore", this);
        } catch (ReflectiveOperationException ex) {
            Core.getInstance().getLogger().log(Level.SEVERE, "Cannot register command: ", ex);
        }
    }

    public abstract void perform(CommandSender sender, String label, String[] args);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        this.perform(sender, commandLabel, args);
        return true;
    }

    public static void setupCommands() {
        new SetRoleCommand();
        new PingCommand();
        new OnlineCommand();
        new UpgradeNPCCommand();
        new FlyCommand();
        // new AdminCommand();
        new InvseeCommand();
        new LobbyItemsCommand();
        new GamemodeCommand();
        new LobbyCommand();
        if (!Language.options$bungeecord) {
            new WarningCommand();
            new StaffChatCommand();
            new TellCommand();
            new ReplyCommand();
        }
        new ItemsCommand();
    }
}