package mc.twilight.utilidades.roles.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.roles.object.Role;
import mc.twilight.core.Core;
import mc.twilight.core.libraries.menu.PagedPlayerMenu;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.player.Profile;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuSetRole extends PagedPlayerMenu {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getCurrentInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {
                Profile profile = Profile.getProfile(this.player.getName());
                if (profile == null) {
                    this.player.closeInventory();
                    return;
                }

                if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
                    ItemStack item = evt.getCurrentItem();

                    if (item != null && item.getType() != Material.AIR) {
                        if (evt.getSlot() == this.previousPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openPrevious();
                        } else if (evt.getSlot() == this.nextPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openNext();
                        } else {
                            Role role = this.roles.get(item);
                            if (role != null) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), role.getCmd().replace("{player}", this.target));
                                Bukkit.getOnlinePlayers().forEach(playerr -> NMS.sendTitle(playerr, Language.roles$titles$header.replace("{player}", StringUtils.getFirstColor(role.getRole().getName()) + " " + target), Language.roles$titles$footer.replace("{role}", role.getRole().getName())));

                                player.sendMessage(Language.roles$set.replace("{player}", target).replace("{role}", role.getRole().getName()));
                                if (Bukkit.getPlayerExact(target) != null) {
                                    if (Bukkit.getPlayerExact(target).isOnline()) {
                                        Bukkit.getPlayerExact(target).sendMessage(Language.roles$notification.replace("{role}", role.getRole().getName()));
                                    }
                                }
                                player.closeInventory();
                            }
                        }
                    }
                }
            }
        }
    }

    private static final KConfig CONFIG = Main.getInstance().getConfig("roles");

    private Map<ItemStack, Role> roles = new HashMap<>();
    private String target;

    public MenuSetRole(Profile profile, String target) {
        super(profile.getPlayer(), CONFIG.getString("title").replace("{player}", target), CONFIG.getBoolean("rows_auto") ? (Role.listRoles().size() / 7) + 3 : CONFIG.getInt("rows"));

        this.previousPage = (this.rows * 9) - 9;
        this.nextPage = (this.rows * 9) - 1;
        this.target = target;
        this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

        List<ItemStack> items = new ArrayList<>();
        for (Role role : Role.listRoles()) {
            ItemStack icon = BukkitUtils.deserializeItemStack(role.getIcon().replace("{role}", role.getRole().getName()).replace("{player}", mc.twilight.core.player.role.Role.getColored(target)));
            items.add(icon);
            this.roles.put(icon, role);
        }

        this.setItems(items);
        items.clear();

        this.register(Core.getInstance());
        this.open();
    }

    public void cancel() {
        HandlerList.unregisterAll(this);
        this.roles.clear();
        this.roles = null;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getCurrentInventory())) {
            this.cancel();
        }
    }
}
