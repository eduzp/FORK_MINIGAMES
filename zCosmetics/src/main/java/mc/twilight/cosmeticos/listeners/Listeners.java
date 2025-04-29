package mc.twilight.cosmeticos.listeners;

import mc.twilight.cosmeticos.Language;
import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetType;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.hook.Users;
import mc.twilight.cosmeticos.hook.mysteryboxes.MysteryBoxesHook;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.menu.CosmeticsMenu;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.balloons.BallonEntity;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class Listeners implements Listener {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("LISTENERS");
  public static final Map<String, Object[]> PET_NAME = new HashMap<>();
  public static final Map<String, CompanionNames> COMPANION_NAME = new HashMap<>();
  private static final Pattern ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9 -_]+$");
  
  public static void setupListeners() {
    Bukkit.getPluginManager().registerEvents(new Listeners(), Main.getInstance());
  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent evt) {
    LOGGER.run(Level.WARNING, "Could not pass AsyncPlayerChatEvent for ${n} v${v}", () -> {
      Player player = evt.getPlayer();
      CUser user = Users.getByName(player.getName());
      if (user != null) {
        String message = StringUtils.formatColors(evt.getMessage());
        if (PET_NAME.containsKey(user.getName())) {
          evt.setCancelled(true);
          if (message.equals("zCosmeticos:cancelchangename")) {
            PET_NAME.remove(player.getName());
            player.sendMessage("§cMudança de apelido cancelada.");
            return;
          }
  
          if (!ALPHA_NUM.matcher(StringUtils.stripColors(message)).matches()) {
            player.sendMessage("§cO apelido não pode conter caracteres especiais.");
            return;
          }
  
          if (message.length() < 4) {
            player.sendMessage("§cApelido muito curto, utilize pelo menos 4 caracteres.");
            return;
          }
  
          if (message.length() > 32) {
            player.sendMessage("§cApelido muito longo, utilize até 32 caracteres.");
            return;
          }
  
          if (StringUtils.stripColors(message).equals("Dinnerbone")) {
            player.sendMessage("§cEsse nome não é permitido.");
            return;
          }
  
          Object[] arr = PET_NAME.remove(user.getName());
          PetType petType = (PetType) arr[0];
          PetSettingsEntry settingsEntry = (PetSettingsEntry) arr[1];
          settingsEntry.getValue().set(StringUtils.deformatColors(message));
          // caso o pet esteja invocado, altera o nome
          if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(petType)) {
            user.getSelectedPet().getPetController().getEntity().setPetCustomName(message);
          }
          player.sendMessage("§aVocê alterou o apelido do seu Pet para: " + message);
        } else if (COMPANION_NAME.containsKey(user.getName())) {
          evt.setCancelled(true);
          if (message.equals("zcosmeticos:cancelchangename")) {
            COMPANION_NAME.remove(player.getName());
            player.sendMessage("§cMudança de apelido cancelada.");
            return;
          }
  
          if (!ALPHA_NUM.matcher(StringUtils.stripColors(message)).matches()) {
            player.sendMessage("§cO apelido não pode conter caracteres especiais.");
            return;
          }
  
          if (message.length() < 4) {
            player.sendMessage("§cApelido muito curto, utilize pelo menos 4 caracteres.");
            return;
          }
  
          if (message.length() > 32) {
            player.sendMessage("§cApelido muito longo, utilize até 32 caracteres.");
            return;
          }
  
          if (StringUtils.stripColors(message).equals("Dinnerbone")) {
            player.sendMessage("§cEsse nome não é permitido, em nosso servidor não há macacos...");
            return;
          }
  
          CompanionNames cName = COMPANION_NAME.remove(user.getName());
          user.getCompanionNames().put(cName, StringUtils.deformatColors(message));
          if (user.getSelectedCompanion() != null && CompanionNames.fromType(user.getSelectedCompanion().getCosmetic().getClass()).equals(cName)) {
            user.getSelectedCompanion().changeName(message);
          }
          player.sendMessage("§aVocê alterou o apelido do seu Companheiro para: " + message);
        } else if (message.equals("zcosmeticos:cancelchangename")) {
          evt.setCancelled(true);
        }
      }
    });
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent evt) {
    LOGGER.run(Level.SEVERE, "Could not pass PlayerJoinEvent for ${n} v${v}", () -> {
      Player player = evt.getPlayer();
      CUser user = Users.loadUser(evt.getPlayer().getName());
      if (Language.settings$mundos.contains(player.getWorld().getName())) {
        user.enable();
        if (Language.settings$item$usar) {
          player.getInventory().setItem(Language.settings$item$slot - 1, BukkitUtils.deserializeItemStack(Language.settings$item$stack));
        }
      }
      if (player.hasPermission("zcosmeticos.cmd.kcs")) {
        if (Main.kMysteryBox) {
          if (MysteryBoxesHook.isUnsynced()) {
            TextComponent component = new TextComponent("");
            for (BaseComponent components : TextComponent
                .fromLegacyText(" \n §6§lMYSTERY BOXES\n \n §7O zCosmeticos aparentemente não está sincronizado com o kMysteryBox, para prosseguir basta clicar ")) {
              component.addExtra(components);
            }
            TextComponent click = new TextComponent("AQUI");
            click.setColor(ChatColor.GREEN);
            click.setBold(true);
            click.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/zcs mb sync"));
            click.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Clique aqui para sincronizar o zCosmeticos com o kMysteryBox.")));
            component.addExtra(click);
            for (BaseComponent components : TextComponent.fromLegacyText("§7.\n ")) {
              component.addExtra(components);
            }
            
            player.spigot().sendMessage(component);
            EnumSound.ORB_PICKUP.play(player, 1.0F, 1.0F);
          }
        }
      }
    });
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerChangedWorld(PlayerChangedWorldEvent evt) {
    Player player = evt.getPlayer();
    
    CUser user = Users.getByName(player.getName());
    if (user != null) {
      if (Language.settings$mundos.contains(player.getWorld().getName())) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
          if (user.getProfile() != null && !user.getProfile().playingGame()) {
            user.enable();
            if (Language.settings$item$usar) {
              player.getInventory().setItem(Language.settings$item$slot - 1, BukkitUtils.deserializeItemStack(Language.settings$item$stack));
            }
          }
        }, 5L);
      } else {
        user.disable();
      }
    }
  }
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent evt) {
    Player player = evt.getPlayer();
    
    if (Language.settings$item$usar && Language.settings$mundos.contains(player.getWorld().getName())) {
      CUser user = Users.getByName(player.getName());
      if (user != null) {
        ItemStack item = player.getItemInHand();
        if (!evt.getAction().name().contains("PHYSICAL") && item != null) {
          if (item.equals(player.getInventory().getItem(Language.settings$item$slot - 1))) {
            if (evt.getAction().name().contains("RIGHT")) {
              evt.setCancelled(true);
              if (user.isEnabled()) {
                new CosmeticsMenu(user);
              }
            }
          } else if (user.getGadget() != null && item.equals(user.getGadget().getItem())) {
            evt.setCancelled(true);
            if (user.isEnabled()) {
              user.getGadget().onClick(user);
            }
            player.updateInventory();
          }
        }
      }
    }
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getWhoClicked() instanceof Player) {
      if (Language.settings$mundos.contains(evt.getWhoClicked().getWorld().getName())) {
        CUser user = Users.getByName(evt.getWhoClicked().getName());
        if (user != null) {
          ItemStack item = evt.getCurrentItem();
          if (item != null && item.getType() != Material.AIR) {
            if (Language.settings$item$usar) {
              if (item.equals(evt.getWhoClicked().getInventory().getItem(Language.settings$item$slot - 1))) {
                evt.setCancelled(true);
                if (user.isEnabled()) {
                  new CosmeticsMenu(user);
                }
                return;
              }
            }
            
            if (user.getGadget() != null && item.equals(user.getGadget().getItem())) {
              evt.setCancelled(true);
            } else {
              ItemStack helmet = evt.getWhoClicked().getInventory().getHelmet();
              ItemStack chestplate = evt.getWhoClicked().getInventory().getChestplate();
              ItemStack leggings = evt.getWhoClicked().getInventory().getLeggings();
              ItemStack boots = evt.getWhoClicked().getInventory().getBoots();
              if (item.equals(helmet) && (user.getCosmetic(CosmeticType.CLOTHES).getUniqueId() != 0 || user.getCosmetic(CosmeticType.HAT).getUniqueId() != 0 || user
                  .getCosmetic(CosmeticType.ANIMATED_HAT).getUniqueId() != 0)) {
                evt.setCancelled(true);
              } else if (item.equals(chestplate) && user.getCosmetic(CosmeticType.CLOTHES, 2).getUniqueId() != 0) {
                evt.setCancelled(true);
              } else if (item.equals(leggings) && user.getCosmetic(CosmeticType.CLOTHES, 3).getUniqueId() != 0) {
                evt.setCancelled(true);
              } else if (item.equals(boots) && user.getCosmetic(CosmeticType.CLOTHES, 4).getUniqueId() != 0) {
                evt.setCancelled(true);
              }
            }
          }
        }
      }
    }
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent evt) {
    CUser user = Users.unloadUser(evt.getPlayer().getName());
    if (user != null) {
      user.save();
      user.destroy();
    }
    
    // Clear
    PET_NAME.remove(evt.getPlayer().getName());
    COMPANION_NAME.remove(evt.getPlayer().getName());
  }
  
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onPlayerUnleashEntity(PlayerUnleashEntityEvent evt) {
    if (NMS.getInstance().getHandle(evt.getEntity()) instanceof BallonEntity) {
      evt.setCancelled(true);
    }
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onCreatureSpawn(CreatureSpawnEvent evt) {
    if (evt.getEntity().hasMetadata("ZCOSMETICOS_ENTITY")) {
      evt.setCancelled(false);
    }
  }
}
