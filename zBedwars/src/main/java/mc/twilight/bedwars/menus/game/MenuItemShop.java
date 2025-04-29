package mc.twilight.bedwars.menus.game;

import mc.twilight.core.Core;
import mc.twilight.bedwars.hook.container.FavoritesContainer;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.game.improvements.UpgradeType;
import mc.twilight.bedwars.game.object.BedWarsEquipment;
import mc.twilight.bedwars.game.shop.Shop;
import mc.twilight.bedwars.game.shop.ShopCategory;
import mc.twilight.bedwars.game.shop.ShopItem;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuItemShop extends PlayerMenu {
  
  private static final List<Integer> SLOTS = Arrays.asList(19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);
  private ShopCategory category;
  private Map<ItemStack, ShopItem> items = new HashMap<>();
  private Map<Integer, ShopCategory> categories = new HashMap<>();
  
  public MenuItemShop(Profile profile, ShopCategory category) {
    super(profile.getPlayer(), "Loja de Itens" + (category == null ? "" : " - " + category.getName()), 6);
    this.category = category;
    
    BedWarsTeam team = profile.getGame(BedWars.class).getTeam(player);
    
    int id = 1;
    this.setItem(0, BukkitUtils.putProfileOnSkull(player, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&bCompra rápida : desc>&7Para adicionar ou remover um item\n&7da sua compra rápida, clique\n&7no item com o botão direito e shift.\n \n&eClique para ver!")));
    for (ShopCategory sc : Shop.listCategories()) {
      this.setItem(id, sc.getIcon());
      this.categories.put(id++, sc);
    }
    
    int categoryId = Shop.getCategoryId(category);
    for (int i = 0; i < 9; i++) {
      this.setItem(9 + i, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:" +
          (i == categoryId ? "13" : "7") + " : 1 : nome>&8↑ &7Categorias : desc>&8↓ &7Itens"));
    }
    
    FavoritesContainer preferences = profile.getAbstractContainer("zCoreBedWars", "favorites", FavoritesContainer.class);
    List<ShopItem> items = category == null ? null : category.listItems();
    if (category == null) {
      SLOTS.forEach(slot -> {
        if (!preferences.hasQuickBuy(slot)) {
          this.setItem(slot, BukkitUtils.deserializeItemStack(
              "STAINED_GLASS_PANE:14 : 1 : nome>&cSlot vazio! : desc>&7Esse é um slot de Compra Fácil!\n&bShift Clique &7em qualquer item na\n&7loja para adicioná-lo aqui."));
          return;
        }
        
        String fav = preferences.getQuickBuy(slot);
        ShopCategory favCategory = Shop.getCategoryById(Integer.parseInt(fav.split(":")[0]));
        if (favCategory != null) {
          ShopItem item = favCategory.getItem(fav.split(":")[1]);
          if (item != null) {
            BedWarsEquipment equipment = team.getEquipment(player);
            boolean maxTier = item.isTieable() && equipment.getTier(item) >= item.getMaxTier();
            int nextTier = maxTier ? item.getMaxTier() : equipment.getTier(item) + 1;
            
            String color = PlayerUtils.getCountFromMaterial(player.getInventory(), item.getPrice(nextTier).getType()) < item.getPrice(nextTier).getAmount() ? "&c" : "&a";
            int tier = team.getTier(UpgradeType.REINFORCED_ARMOR);
            int tierSwords = team.getTier(UpgradeType.SHARPENED_SWORDS);
            ItemStack icon = BukkitUtils.deserializeItemStack((item.isTieable() ? equipment.getTier(item) == 0 ? item.getIcon() : item.getTier(nextTier).getIcon() : item.getIcon()).replace("{color}", color).replace("{price}", String.valueOf(item.getPrice(nextTier).getAmount()))
                .replace("{tier}", nextTier > 3 ? nextTier == 4 ? "IV" : "V" : StringUtils.repeat("I", nextTier)));
                   /*
                    if (item.isTieable()) {
                        icon.setType(item.getTier(nextTier).getContent().get(0).getType());
                    }

                    */
            ItemMeta meta = icon.getItemMeta();
            List<String> lore = meta.getLore();
            if (item.getCategory().getIcon().getType().name().contains("BOOTS") && team.hasUpgrade(UpgradeType.REINFORCED_ARMOR)) {
              meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
              meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else if (item.getCategory().getIcon().getType().name().contains("SWORD") && team.hasUpgrade(UpgradeType.SHARPENED_SWORDS)) {
              meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
              meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else if (item.isTieable() && item.getTier(nextTier).getContent().get(0).getEnchantments().size() > 0) {
              meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
              meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            lore.add("");
            lore.add("§bShift clique para remover");
            lore.add("§bna compra fácil!");
            lore.add("");
            if (equipment.cantBuy(item)) {
              if (item.isTieable() && (equipment.getTier(item)) >= item.getMaxTier()) {
                lore.add("§aMAXIMIZADO!");
              } else {
                lore.add("§cVocê já possui este item!");
              }
            } else if ("&c".equals(color)) {
              lore.add("§cVocê não possui recursos suficientes!");
            } else {
              lore.add("§eClique para comprar!");
            }
            meta.setLore(lore);
            icon.setItemMeta(meta);
            this.setItem(slot, icon);
            this.items.put(icon, item);
            return;
          }
        }
        
        preferences.setQuickBuy(slot, null);
      });
    } else {
      for (int index = 0; index < SLOTS.size(); index++) {
        if (items.size() == index) {
          break;
        }
        
        ShopItem item = items.get(index);
        BedWarsEquipment equipment = team.getEquipment(player);
        boolean maxTier = item.isTieable() && equipment.getTier(item) >= item.getMaxTier();
        int nextTier = maxTier ? item.getMaxTier() : equipment.getTier(item) + 1;
        String color = PlayerUtils.getCountFromMaterial(player.getInventory(), item.getPrice(nextTier).getType()) < item.getPrice(nextTier).getAmount() ? "&c" : "&a";
        int tier = team.getTier(UpgradeType.REINFORCED_ARMOR);
        int tierSwords = team.getTier(UpgradeType.SHARPENED_SWORDS);
        ItemStack icon = BukkitUtils.deserializeItemStack((item.isTieable() ? equipment.getTier(item) == 0 ? item.getIcon() : item.getTier(nextTier).getIcon() : item.getIcon()).replace("{color}", color).replace("{price}", String.valueOf(item.getPrice(nextTier).getAmount()))
            .replace("{tier}", nextTier > 3 ? nextTier == 4 ? "IV" : "V" : StringUtils.repeat("I", nextTier)));
            /*if (item.isTieable()) {
                icon.setType(item.getTier(nextTier).getContent().get(0).getType());
            }

             */
        ItemMeta meta = icon.getItemMeta();
        List<String> lore = meta.getLore();
        if (item.getCategory().getIcon().getType().name().contains("BOOTS") && team.hasUpgrade(UpgradeType.REINFORCED_ARMOR)) {
          meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
          meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else if (item.getCategory().getIcon().getType().name().contains("SWORD") && team.hasUpgrade(UpgradeType.SHARPENED_SWORDS)) {
          meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
          meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else if (item.isTieable() && item.getTier(nextTier).getContent().get(0).getEnchantments().size() > 0) {
          meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
          meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        
        lore.add("");
        if (preferences.hasQuickBuy(categoryId + ":" + item.getName())) {
          lore.add("§bShift clique para remover");
        } else {
          lore.add("§bShift clique para adicionar");
        }
        lore.add("§bna compra fácil!");
        lore.add("");
        if (equipment.cantBuy(item)) {
          if (item.isTieable() && (equipment.getTier(item)) >= item.getMaxTier()) {
            lore.add("§aMAXIMIZADO!");
          } else {
            lore.add("§cVocê já possui este item!");
          }
        } else if ("&c".equals(color)) {
          lore.add("§cVocê não possui recursos suficientes!");
        } else {
          lore.add("§eClique para comprar!");
        }
        meta.setLore(lore);
        icon.setItemMeta(meta);
        this.setItem(SLOTS.get(index), icon);
        this.items.put(icon, item);
      }
    }
    
    
    this.open();
    this.register(Core.getInstance());
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked() instanceof Player && evt.getWhoClicked().equals(player)) {
        ItemStack item = evt.getCurrentItem();
        BedWars server = null;
        BedWarsTeam team = null;
        Profile profile = Profile.getProfile(player.getName());
        if (profile == null || (server = profile.getGame(BedWars.class)) == null || (team = server.getTeam(player)) == null) {
          player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(evt.getInventory()) && item != null && item.getType() != Material.AIR) {
          ShopItem si;
          if (evt.getSlot() == 0) {
            EnumSound.CLICK.play(player, 0.5F, 1.0F);
            new MenuItemShop(profile, null);
            return;
          }
          ShopCategory category = categories.get(evt.getSlot());
          if (category != null) {
            if (category != this.category) {
              EnumSound.CLICK.play(player, 0.5F, 1.0F);
              new MenuItemShop(profile, category);
            }
          } else if ((si = items.get(item)) != null) {
            BedWarsEquipment equipment = team.getEquipment(player);
            boolean have = equipment.cantBuy(si);
            boolean maxTier = si.isTieable() && equipment.getTier(si) >= si.getMaxTier();
            int nextTier = maxTier ? si.getMaxTier() : equipment.getTier(si) + 1;
            if (evt.getClick().name().contains("SHIFT")) {
              FavoritesContainer preferences = profile.getAbstractContainer("zCoreBedWars", "favorites", FavoritesContainer.class);
              if (this.category == null && preferences.hasQuickBuy(evt.getSlot())) {
                preferences.setQuickBuy(evt.getSlot(), null);
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                new MenuItemShop(profile, this.category);
              } else if (this.category != null) {
                int categoryId = Shop.getCategoryId(this.category);
                if (preferences.hasQuickBuy(categoryId + ":" + si.getName())) {
                  preferences.setQuickBuy(preferences.getQuickBuy(categoryId + ":" + si.getName()), null);
                  player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                  new MenuItemShop(profile, this.category);
                } else {
                  new MenuItemShopSelect(profile, si, item);
                }
              }
              return;
            }
            
            if (!have) {
              if (PlayerUtils.getCountFromMaterial(player.getInventory(), si.getPrice(nextTier).getType()) < si.getPrice(nextTier).getAmount()) {
                player.sendMessage("§cVocê não tem recursos para realizar esta compra!");
                return;
              }
              
              PlayerUtils.removeItem(player.getInventory(), si.getPrice(nextTier).getType(), si.getPrice(nextTier).getAmount());
              
              team.getEquipment(player).addItem(si);
              team.refresh(player);
              
              EnumSound.NOTE_PLING.play(player, 0.5F, 1.0F);
              player.sendMessage("§aVocê comprou §6" + StringUtils.stripColors(item.getItemMeta().getDisplayName()));
              new MenuItemShop(profile, this.category);
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    category = null;
    items.clear();
    items = null;
    categories.clear();
    categories = null;
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}