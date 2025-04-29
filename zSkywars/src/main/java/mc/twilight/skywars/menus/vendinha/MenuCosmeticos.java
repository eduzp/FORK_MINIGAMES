package mc.twilight.skywars.menus.vendinha;

import mc.twilight.skywars.menus.cosmetics.MenuCosmetics;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.cosmetics.types.*;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuCosmeticos extends PlayerMenu {

    public MenuCosmeticos(Profile profile) {
        super(profile.getPlayer(), "Cosméticos", 5);

        this.setItem(20, BukkitUtils.deserializeItemStack(
                "LEASH : 1 : nome>&aBalões : desc>&7Personalize sua ilha com balões estilosos.\n\n&eClique para ver!"));

        this.setItem(21, BukkitUtils.deserializeItemStack(
                "IRON_FENCE : 1 : nome>&aJaulas : desc>&7Escolha jaulas para começar as partidas\n&7com muito estilo.\n\n&eClique para ver!"));

        this.setItem(22, BukkitUtils.deserializeItemStack(
                "DRAGON_EGG : 1 : nome>&aComemorações de Vitória : desc>&7Celebre suas vitórias com grandes efeitos!\n\n&eClique para ver!"));

        this.setItem(23, BukkitUtils.deserializeItemStack(
                "BOOK_AND_QUILL : 1 : nome>&aMensagens de Morte : desc>&7Customize como suas mortes serão anunciadas.\n\n&eClique para ver!"));

        this.setItem(24, BukkitUtils.deserializeItemStack(
                "GHAST_TEAR : 1 : nome>&aGritos de Morte : desc>&7Escolha um grito épico para suas derrotas.\n\n&eClique para ver!"));

        // Botão de voltar no slot 40
        this.setItem(40, BukkitUtils.deserializeItemStack(
                "INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Voltar para o menu anterior."));

        this.register(Main.getInstance());
        this.open();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (!evt.getInventory().equals(this.getInventory())) return;
        evt.setCancelled(true);

        if (!evt.getWhoClicked().equals(this.player)) return;

        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
            this.player.closeInventory();
            return;
        }

        ItemStack item = evt.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;

        switch (evt.getSlot()) {
            case 20:
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuCosmetics<>(profile, "Balões", Balloon.class, () -> new MenuCosmeticos(profile));
                break;
            case 21:
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuCosmetics<>(profile, "Jaulas", Cage.class, () -> new MenuCosmeticos(profile));
                break;
            case 22:
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuCosmetics<>(profile, "Comemorações de Vitória", WinAnimation.class, () -> new MenuCosmeticos(profile));
                break;
            case 23:
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuCosmetics<>(profile, "Mensagens de Morte", DeathMessage.class, () -> new MenuCosmeticos(profile));
                break;
            case 24:
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuCosmetics<>(profile, "Gritos de Morte", DeathCry.class, () -> new MenuCosmeticos(profile));
                break;
            case 40:
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuShop(profile);
                break;
            default:
                break;
        }
    }

    public void cancel() {
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
