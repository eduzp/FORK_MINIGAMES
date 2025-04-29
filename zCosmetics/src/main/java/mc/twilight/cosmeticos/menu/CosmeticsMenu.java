package mc.twilight.cosmeticos.menu;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.cosmetics.types.*;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import mc.twilight.cosmeticos.menu.cosmetics.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CosmeticsMenu extends PlayerMenu {
  
  private CUser user;
  
  public CosmeticsMenu(CUser user) {
    super(user.getPlayer(), "Cosméticos", 5);
    this.user = user;
    
    long max = Cosmetic.listCosmetics(CompanionCosmetic.class).size();
    long have = Cosmetic.listCosmetics(CompanionCosmetic.class).stream().filter(user::hasCosmetic).count();
    Cosmetic cosmetic = user.getCosmetic(CosmeticType.COMPANION);
    String name = cosmetic.getName();
    String color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    this.setItem(10, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : nome>&aCompanheiros : desc>&7Tenha um companheiro exclusivo\n&7ao seu lado em nossos lobbies!\n \n&fDesbloqueados: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZjNjhlODJhZDMyMmU1OWZhNDUwN2M0YTM4MWIxNmEwMzRmMDI2ZmRkNmQ3OTNiYTZkMGFkNDFlNjUwZGYyMCJ9fX0="));
    
    max = Cosmetic.listCosmetics(HatCosmetic.class).size();
    have = Cosmetic.listCosmetics(HatCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.HAT);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    this.setItem(11, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : nome>&aChapéus : desc>&7Escolha um chapéu para andar\n&7com estilo em nossos lobbies.\n \n&fDesbloqueados: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzI1YWY5NjZhMzI2ZjlkOTg0NjZhN2JmODU4MmNhNGRhNjQ1M2RlMjcxYjNiYzllNTlmNTdhOTliNjM1MTFjNiJ9fX0="));
    
    max = Cosmetic.listCosmetics(AnimatedHatCosmetic.class).size();
    have = Cosmetic.listCosmetics(AnimatedHatCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.ANIMATED_HAT);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    
    this.setItem(15, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : nome>&aChapéus Animados : desc>&7Escolha um chapéu animado para\n&7exibir as mais diversas reações.\n \n&fDesbloqueados: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUzZTIyNzYxYzc2YjRmOGZhZDg5ZGJjODBmM2FmMjAzZTdiODIxMTIzODAxMWJlN2ZmYjgwMjYxZDljNjQifX19"));
    
    max = Cosmetic.listCosmetics(BalloonCosmetic.class).size();
    have = Cosmetic.listCosmetics(BalloonCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.BALLOON);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    
    this.setItem(16, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : nome>&aBalões : desc>&7Passeie em nossos lobbies com\n&7estilo utilizando um balão exclusivo.\n \n&fDesbloqueados: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir! : skin>eyJ0aW1lc3RhbXAiOjE1MTMzMDU0NTM3MjEsInByb2ZpbGVJZCI6IjQxZDNhYmMyZDc0OTQwMGM5MDkwZDU0MzRkMDM4MzFiIiwicHJvZmlsZU5hbWUiOiJNZWdha2xvb24iLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2U2Yjg1Y2YyNWUxNzNjNTdiODlhZTFkMjIzYjZmN2RmYzMyZGQ4YjkyMTFmNmRmMjdiNGVjZThlMDY3NDVhZSJ9fX0="));
    
    max = Cosmetic.listCosmetics(PetCosmetic.class).size();
    have = Cosmetic.listCosmetics(PetCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.PET);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    this.setItem(28, BukkitUtils.deserializeItemStack(
        "BONE : 1 : nome>&aPets : desc>&7Passeie pelo nossos lobbies\n&7acompanhado por um Pet!\n \n&fDesbloqueados: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir!"));
    
    max = Cosmetic.listCosmetics(ClothesCosmetic.class).size();
    have = Cosmetic.listCosmetics(ClothesCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.CLOTHES);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    this.setItem(29, BukkitUtils.deserializeItemStack(
        "LEATHER_CHESTPLATE : 1 : nome>&aGuarda-Roupa : desc>&7Personalize sua armadura\n&7com roupas exclusivas.\n \n&fDesbloqueados: " + color + have + "/" + max + "\n§fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir!"));
    
    max = Cosmetic.listCosmetics(MorphCosmetic.class).size();
    have = Cosmetic.listCosmetics(MorphCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.MORPH);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    this.setItem(31, BukkitUtils.deserializeItemStack(
        "397:2 : 1 : nome>&aMutações : desc>&7Você pode se transformar em um mob\n&7especial enquanto anda pelos lobbies.\n \n&fDesbloqueados: " + color + have + "/" + max + "\n§fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir!"));
    
    max = Cosmetic.listCosmetics(GadgetsCosmetic.class).size();
    have = Cosmetic.listCosmetics(GadgetsCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.GADGET);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    this.setItem(33, BukkitUtils.deserializeItemStack(
        "379 : 1 : nome>&aEngenhocas : desc>&7Escolha uma engenhoca\n&7para se divertir em nossos lobbies!\n \n&fDesbloqueados: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir!"));
    
    max = Cosmetic.listCosmetics(ParticleCosmetic.class).size();
    have = Cosmetic.listCosmetics(ParticleCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.PARTICLE);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    this.setItem(34, BukkitUtils.deserializeItemStack(
        "BLAZE_POWDER : 1 : nome>&aPartículas : desc>&7Passeie em nossos lobbies com\n&7partículas em volta de você.\n \n&fDesbloqueados: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir!"));
    
    max = Cosmetic.listCosmetics(BannerCosmetic.class).size();
    have = Cosmetic.listCosmetics(BannerCosmetic.class).stream().filter(user::hasCosmetic).count();
    cosmetic = user.getCosmetic(CosmeticType.BANNER);
    name = cosmetic.getName();
    color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
    /*
    this.setItem(13, BukkitUtils.deserializeItemStack(
      "BANNER : 1 : nome>&aBanners : desc>&7Escolha banners com diversos\n&7desenhos diferentes para usar\n&7nos lobbies\n \n&fDesbloqueados: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir!"));
     */
    this.setItem(13, BannerCosmetic.getMainIcon(user));
    
    this.register(Main.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        if (user == null) {
          player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          if (evt.getSlot() == 10) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new CompanionsMenu(user);
          } else if (evt.getSlot() == 11) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new HatsMenu(user);
          } else if (evt.getSlot() == 13) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new BannerMenu(user);
          } else if (evt.getSlot() == 15) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new AnimatedHatsMenu(user);
          } else if (evt.getSlot() == 16) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new BalloonsMenu(user);
          } else if (evt.getSlot() == 28) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new PetsMenu(user);
          } else if (evt.getSlot() == 29) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new ClothesMenu(user);
          } else if (evt.getSlot() == 34) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new ParticlesMenu(user);
          } else if (evt.getSlot() == 33) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new GadgetsMenu(user);
          } else if (evt.getSlot() == 31) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new MorphsMenu(user);
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.user = null;
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
