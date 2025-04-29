package mc.twilight.bedwars.menus;

import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.cosmetics.Cosmetic;
import mc.twilight.bedwars.cosmetics.types.*;
import mc.twilight.bedwars.menus.cosmetics.MenuCosmetics;
import mc.twilight.bedwars.menus.cosmetics.animations.MenuAnimations;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MenuShop extends PlayerMenu {
  
  public MenuShop(Profile profile) {
    super(profile.getPlayer(), "Loja - Bed Wars", 5);
  
    long max = 0;
    long owned = 0;
    long percentage = 100;
    String color = "&a";
    this.setItem(11, BukkitUtils.deserializeItemStack(
        "EXP_BOTTLE : 1 : nome>&aHabilidades : desc>&7Tenha vantagens únicas para\n&7auxiliar você nas partidas.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou evoluir!"));
    
    List<ShopkeeperSkin> skins = Cosmetic.listByType(ShopkeeperSkin.class);
    max = skins.size();
    owned = skins.stream().filter(shopkeeperSkin -> shopkeeperSkin.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    skins.clear();
    this.setItem(13, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : nome>&aSkins de Loja : desc>&7Altere a skin dos Vendedores\n&7durante a sua partida.\n \n&8Em modos de times maiores a skin\n&8será escolhida aleatoriamente pelo\n&8sistema. Um pré-requisito é o jogador\n&8ter uma skin selecionada. Sendo assim,\n&8você ou seu companheiro poderá ter a\n&8skin do npc da sua ilha.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar! : skin>ewogICJ0aW1lc3RhbXAiIDogMTYyMzI1Mzg3NzQwNSwKICAicHJvZmlsZUlkIiA6ICJhMDVkZWVjMDdhMGU0MDc2ODdjYmRjNWRjYWNhODU4NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJWaWxsYWdlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83YWY3YzA3ZDFkZWQ2MWIxZDMzMTI2ODViMzJlNDU2OGZmZGRhNzYyZWM4ZDgwODg5NWNjMzI5YTkzZDYwNmUwIgogICAgfQogIH0KfQ"));
  
    List<Balloon> balloons = Cosmetic.listByType(Balloon.class);
    max = balloons.size();
    owned = balloons.stream().filter(cage -> cage.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    balloons.clear();
    this.setItem(15, BukkitUtils.deserializeItemStack(
        "LEASH : 1 : nome>&aBalões : desc>&7Decore sua ilha com maravilhosos balões.\n \n&8Em modos de times maiores o balão\n&8será escolhido aleatoriamente pelo\n&8sistema. Um pré-requesito é o jogador\n&8ter um balão selecionado. Sendo assim,\n&8você ou seu companheiro poderá ter o\n&8balão spawnado em sua ilha.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
  
    List<DeathMessage> messages = Cosmetic.listByType(DeathMessage.class);
    max = messages.size();
    owned = messages.stream().filter(deathMessage -> deathMessage.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    messages.clear();
    this.setItem(29, BukkitUtils.deserializeItemStack(
        "BOOK_AND_QUILL : 1 : nome>&aMensagens de Morte : desc>&7Anuncie o abate do seu inimigo de\n&7uma forma estilosa com mensagens de morte.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    List<Cage> cages = Cosmetic.listByType(Cage.class);
    max = cages.size();
    owned = cages.stream().filter(cage -> cage.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    cages.clear();
    this.setItem(30, BukkitUtils.deserializeItemStack(
        "IRON_FENCE : 1 : nome>&aJaulas : desc>&7Esbanje estilo antes mesmo da partida\n&7começar com as suas jaulas.\n \n&8Lembrando que as jaulas só funcionam\n&8no modo solo. Em modo de time maiores a\n&8jaula será padronizada.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
  
    List<WinAnimation> animations = Cosmetic.listByType(WinAnimation.class);
    max = animations.size();
    owned = animations.stream().filter(animation -> animation.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    animations.clear();
    this.setItem(32, BukkitUtils.deserializeItemStack(
        "DRAGON_EGG : 1 : nome>&aComemorações de Vitória : desc>&7Esbanje estilo nas suas vitórias\n&7com comemorações exclusivas.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
  
    List<DeathCry> deathcries = Cosmetic.listByType(DeathCry.class);
    max = deathcries.size();
    owned = deathcries.stream().filter(deathcry -> deathcry.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    deathcries.clear();
    this.setItem(33, BukkitUtils.deserializeItemStack(
        "GHAST_TEAR : 1 : nome>&aGritos de Morte : desc>&7Gritos de mortes são sons que\n&7irão ser reproduzidos toda vez\n&7que você morrer.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    this.setItem(44, BukkitUtils.deserializeItemStack("INK_SACK:10 : 1 : nome>&aPágina 2"));
    
    this.register(Main.getInstance());
    this.open();
  }
  
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
            if (evt.getSlot() == 15) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Balões", Balloon.class);
            } else if (evt.getSlot() == 13) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Skins de Loja", ShopkeeperSkin.class);
            } else if (evt.getSlot() == 11) {
              EnumSound.ENDERMAN_TELEPORT.play(this.player, 1.0f, 1.0f);
              player.sendMessage("§cEm breve.");
            } else if (evt.getSlot() == 29) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Mensagens de Morte", DeathMessage.class);
            } else if (evt.getSlot() == 30) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Jaulas", Cage.class);
            } else if (evt.getSlot() == 32) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Comemorações", WinAnimation.class);
            } else if (evt.getSlot() == 33) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Gritos de Morte", DeathCry.class);
            } else if (evt.getSlot() == 44) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuShopSecondPage(profile);
            }
          }
        }
      }
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
  
  static class MenuShopSecondPage extends PlayerMenu {
    
    public MenuShopSecondPage(Profile profile) {
      super(profile.getPlayer(), "Loja - Bed Wars", 4);
  
      List<Cosmetic> totaleffects = new ArrayList<>();
      totaleffects.addAll(Cosmetic.listByType(KillEffect.class));
      totaleffects.addAll(Cosmetic.listByType(FallEffect.class));
      totaleffects.addAll(Cosmetic.listByType(BreakEffect.class));
      long max = totaleffects.size();
      long owned = totaleffects.stream().filter(killEffect -> killEffect.has(profile)).count();
      long percentage = max == 0 ? 100 : (owned * 100) / max;
      String color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
      totaleffects.clear();
      this.setItem(12, BukkitUtils.deserializeItemStack(
          "321 : 1 : nome>&aAnimações : desc>&7Adicione animações às suas ações\n&7para se destacar dentro do jogo.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
  
      List<WoodTypes> types = Cosmetic.listByType(WoodTypes.class);
      max = types.size();
      owned = types.stream().filter(killEffect -> killEffect.has(profile)).count();
      percentage = max == 0 ? 100 : (owned * 100) / max;
      color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
      types.clear();
      this.setItem(14, BukkitUtils.deserializeItemStack(
          "WOOD : 1 : nome>&aTipos de Madeira : desc>&7Modifique o tipo da madeira que\n&7você irá receber dos vendedores!\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
  
      this.setItem(27, BukkitUtils.deserializeItemStack("INK_SACK:8 : 1 : nome>&aPágina 1"));
    
      this.register(Main.getInstance());
      this.open();
    }
  
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
              if (evt.getSlot() == 12) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuAnimations(profile);
              } else if (evt.getSlot() == 14) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuCosmetics<>(profile, "Tipos de Madeira", WoodTypes.class);
              } else if (evt.getSlot() == 27) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuShop(profile);
              }
            }
          }
        }
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
}
