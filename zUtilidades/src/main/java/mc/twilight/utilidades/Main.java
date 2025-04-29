package mc.twilight.utilidades;

import org.bukkit.entity.Player;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.utilidades.items.object.AbstractItem;
import mc.twilight.utilidades.listeners.Listeners;
import mc.twilight.utilidades.roles.object.Role;
import mc.twilight.utilidades.upgrades.npc.NPCUpgrade;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.plugin.KPlugin;
import mc.twilight.core.reflection.Accessors;
import mc.twilight.core.reflection.acessors.FieldAccessor;

import java.util.HashMap;

public class Main extends KPlugin {

    public static HashMap<Player, Player> reply = new HashMap<>();
    private static Main instance;
    public static boolean validInit;

    @Override
    public void start() {
        instance = this;
    }

    @Override
    public void load() {}

    @Override
    public void enable() {

        saveDefaultConfig();

        if (!CashManager.CASH) {
            this.getLogger().warning("Ative o cash do zCore para que o plugin funcione.");
            System.exit(0);
        }

        AbstractItem.setupItems();

        NPCUpgrade.setupNPCs();
        Listeners.setupListeners();

        Language.setupLanguage();
        Commands.setupCommands();
        Role.setupRoles();

        validInit = true;
        this.getLogger().info("O plugin foi ativado.");
    }

    @Override
    public void disable() {
        if (validInit) {
            NPCUpgrade.listNPCs().forEach(NPCUpgrade::destroy);
        }

        this.getLogger().info("O plugin foi desativado.");
    }

    public static Main getInstance() {
        return instance;
    }

}
