package mc.twilight.utilidades.upgrades.menu;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.upgrades.object.Upgrade;
import mc.twilight.core.Core;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;

import java.util.HashMap;
import java.util.Map;

public class MenuUpgrades extends PlayerMenu {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {
                Profile profile = Profile.getProfile(this.player.getName());
                if (profile == null) {
                    this.player.closeInventory();
                    return;
                }

                if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
                    ItemStack item = evt.getCurrentItem();

                    if (item != null && item.getType() != Material.AIR) {
                        Upgrade upgrade = upgrades.get(item);
                        if (upgrade != null) {
                            if (player.hasPermission(upgrade.getRole().getPermission())) {
                                return;
                            }
                            long cash = profile.getStats("zCoreProfile", "cash");

                            if (cash < upgrade.getCash()) {
                                EnumSound.ENDERMAN_TELEPORT.play(this.player, 1.0F, 1.0F);
                                return;
                            }
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            new MenuBuyUpgrade(profile, upgrade);
                        }
                    }
                }
            }
        }
    }

    private Map<ItemStack, Upgrade> upgrades = new HashMap<>();
    private static final KConfig CONFIG = Main.getInstance().getConfig("upgrades");

    public MenuUpgrades(Profile profile) {
        super(profile.getPlayer(), CONFIG.getString("title"), CONFIG.getInt("rows"));

        Upgrade.listUpgrdaes().forEach(upgrade -> {
            this.upgrades.put(upgrade.getIcon(profile), upgrade);
            this.setItem(upgrade.getSlot(), upgrade.getIcon(profile));
        });

        this.setItem(Main.getInstance().getConfig("upgrades").getInt("options.info_slot"), BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(player, Main.getInstance().getConfig("upgrades").getString("options.info"))));

        this.register(Core.getInstance());
        this.open();
    }

    public void cancel() {
        this.upgrades.clear();
        this.upgrades = null;
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
            this.cancel();
        }
    }
}
