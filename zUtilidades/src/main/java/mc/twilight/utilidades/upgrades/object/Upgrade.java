package mc.twilight.utilidades.upgrades.object;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import mc.twilight.utilidades.Main;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Upgrade {

    private String icon, cmd, prefix, permission;
    private int slot;
    private long cash;


    public static void setupUpgrades() {
        if (!new File("plugins/" + Main.getInstance().getDescription().getName() + "/upgrades.yml").exists()) {
            Main.getInstance().saveResource("upgrades.yml", false);
        }
        for (String key : CONFIG.getSection("upgrades").getKeys(false)) {
            Upgrade.listUpgrdaes().add(new Upgrade(key, CONFIG.getString("upgrades." + key + ".role_permission"), CONFIG.getInt("upgrades." + key + ".slot"), CONFIG.getString("upgrades." + key + ".icon"), CONFIG.getString("upgrades." + key + ".cmd"), CONFIG.getString("upgrades." + key + ".prefix")));
        }
    }

    private static final List<Upgrade> UPGRADES = new ArrayList<>();

    public Upgrade(String key, String permission,int slot, String icon, String cmd, String prefix) {
        this.icon = icon;
        this.cmd = cmd;
        this.slot = slot;
        this.permission = permission;
        this.prefix = prefix;
        this.cash = this.getCash(key);
    }

    private static final KConfig CONFIG = Main.getInstance().getConfig("upgrades");


    public long getCash(String key) {
        if (!CONFIG.contains("upgrades." + key + ".cash")) {
            CONFIG.set("upgrades." + key + ".cash", getAbsentProperty("upgrades", "upgrades." + key + ".cash"));
        }

        return (long) CONFIG.getInt("upgrades." + key + ".cash");
    }

    public String getPermission() {
        return this.permission;
    }

    public long getCash() {
        return this.cash;
    }



    public ItemStack getIcon(Profile profile) {
        Player player = profile.getPlayer();
        long cash = profile.getStats("zCoreProfile", "cash");
        ItemStack item = null;

        if (player.hasPermission(this.getPermission())) {
            item = BukkitUtils.deserializeItemStack(this.icon.replace("{cash}", StringUtils.formatNumber(this.getCash())) + "\n \n§eVocê já possui este upgrade!");
        } else {
            if (cash >= this.getCash()) {
                item = BukkitUtils.deserializeItemStack(this.icon.replace("{cash}", StringUtils.formatNumber(this.getCash())) + "\n \n§eClique para comprar!");
            } else {
                item = BukkitUtils.deserializeItemStack(this.icon.replace("{cash}", StringUtils.formatNumber(this.getCash())) + "\n \n§cVocê não tem saldo suficiente.");
            }
        }
        return item;
    }


    public String getCmd() {
        return cmd;
    }

    public Role getRole() {
        return Role.getRoleByPermission(this.getPermission());
    }

    public int getSlot() {
        return slot;
    }

    public static List<Upgrade> listUpgrdaes() {
        return UPGRADES;
    }


    protected static Object getAbsentProperty(String file, String property) {
        InputStream stream = Main.getInstance().getResource(file + ".yml");
        if (stream == null) {
            return null;
        }

        return YamlConfiguration.loadConfiguration(new InputStreamReader(stream, StandardCharsets.UTF_8)).get(property);
    }

}
