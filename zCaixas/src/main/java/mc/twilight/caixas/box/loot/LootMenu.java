package mc.twilight.caixas.box.loot;

import mc.twilight.core.game.FakeGame;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.libraries.holograms.api.HologramLine;
import mc.twilight.caixas.Main;
import mc.twilight.caixas.api.MysteryBoxAPI;
import mc.twilight.caixas.box.Box;
import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.box.loot.animation.BoxAnimation;
import mc.twilight.caixas.hook.container.MysteryBoxQueueContainer;
import mc.twilight.caixas.nms.NMS;
import mc.twilight.caixas.utils.PlayerUtils;
import mc.twilight.core.nms.interfaces.entity.IArmorStand;
import mc.twilight.core.nms.interfaces.entity.ISlime;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.cosmeticos.api.CosmeticsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class LootMenu implements Listener {
  
  protected Player player;
  protected Hotbar hotbar;
  protected Location returns;
  protected Profile profile;
  protected boolean blocked;
  protected Box chest;
  protected BoxContent reward;
  protected BukkitTask task;
  protected Entity cart;
  protected IArmorStand icon;
  protected LootHolograms holograms;
  
  protected Location lootChest;
  protected HologramLine targeting;
  
  public LootMenu(Player player, Box chest) {
    this(player, chest, null);
  }
  
  public LootMenu(Player player, Box chest, BoxContent reward) {
    this.player = player;
    this.returns = player.getLocation();
    this.profile = Profile.getProfile(player.getName());
    this.chest = chest;
    this.reward = reward != null ? reward.equals(BoxContent.ALL) ? null : reward : null;
    this.holograms = new LootHolograms(this);
    
    this.hotbar = profile.getHotbar();
    profile.setGame(FakeGame.FAKE_GAME);
    profile.setHotbar(null);
    for (Player players : Bukkit.getOnlinePlayers()) {
      players.hidePlayer(player);
    }
    
    if (Main.zCosmeticos) {
      CosmeticsAPI.disable(this.player);
    }
    
    Location playerLocation = Main.getLootChestsLocation().clone();
    player.teleport(playerLocation);
    
    this.blocked = true;
    this.cart = NMS.createAttachedCart(player.getName(), playerLocation.clone());
    this.lootChest = playerLocation.clone().add(0, -2.0, 3);
    
    this.icon = NMS.createAttachedArmorStand(player.getName(), this.lootChest, "");
    this.getLootChest().setGravity(false);
    this.getLootChest().setSmall(false);
    this.getLootChest().setHelmet(this.chest.getIcon());
    
    new BukkitRunnable() {
      @Override
      public void run() {
        NMS.sendFakeSpectator(player, cart);
        playAnimation(chest.getAnimation(), reward != null ? reward.equals(BoxContent.ALL) ? 2 : 1 : 0);
      }
    }.runTaskLater(Main.getInstance(), 3L);
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  protected void playAnimation(BoxAnimation animation, long amount) {
    animation.start(this);
    this.task = animation.register(false);
    if (amount > 0) {
      animation.complete();
      long remove = amount == 2 ? MysteryBoxAPI.getMysteryBoxes(this.profile) : 1;
      this.profile.addStats("zCaixas", -remove, "magic");
      if (amount == 2) {
        List<BoxContent> rewardList = new ArrayList<>();
        for (int i = 0; i < remove; i++) {
          BoxContent reward = BoxContent.getRandom();
          if (reward != null) {
            if (reward.canBeDispatched()) {
              reward.dispatch(this.profile);
            } else {
              profile.getAbstractContainer("zCaixas", "queueRewards", MysteryBoxQueueContainer.class).addToQueue(reward);
            }
            rewardList.add(reward);
          }
        }
        
        PlayerUtils.createBook(player, rewardList);
        rewardList.clear();
      } else {
        if (this.reward.canBeDispatched()) {
          this.reward.dispatch(this.profile);
        } else {
          this.profile.getAbstractContainer("zCaixas", "queueRewards", MysteryBoxQueueContainer.class).addToQueue(this.reward);
        }
      }
    }
  }
  
  public void open(BoxAnimation animation) {
    this.open(animation, 1);
  }
  
  public void open(BoxAnimation animation, long amount) {
    this.blocked = true;
    Entity oldCart = this.cart;
    this.cart = NMS.createAttachedCart(this.player.getName(), this.cart.getLocation().add(0, 1, 0));
    NMS.sendFakeSpectator(this.player, this.cart);
    oldCart.remove();
    mc.twilight.core.nms.NMS.sendActionBar(this.player, "");
    this.task.cancel();
    this.task = null;
    this.holograms.listHolograms().forEach(Hologram::despawn);
    
    animation.start(this);
    this.task = animation.register(true, amount);
  }
  
  public void start() {
    this.blocked = false;
    Entity oldCart = this.cart;
    this.cart = NMS.createAttachedCart(this.player.getName(), this.cart.getLocation().subtract(0, 1, 0));
    NMS.sendFakeSpectator(this.player, null);
    this.cart.setPassenger(this.player);
    oldCart.remove();
    this.holograms.createOpenHologram();
    this.holograms.createShopHologram();
    this.holograms.createCloseHologram(true);
    this.holograms.createCloseHologram(false);
    
    this.task = new BukkitRunnable() {
      private int time = 0;
      private int update;
      
      @Override
      public void run() {
        if (cart == null || cart.getPassenger() == null || time++ >= 120) {
          cancel();
          task = null;
          Bukkit.getScheduler().runTask(Main.getInstance(), LootMenu.this::cancel);
          return;
        }
        
        Entity entity = PlayerUtils.getLookingFor(player, holograms.listHolograms());
        if (entity != null) {
          if (targeting == null || !targeting.equals(((ISlime) entity).getLine())) {
            ISlime slime = (ISlime) entity;
            if (targeting != null) {
              if (targeting.getHologram().getLines().size() > 3) {
                targeting.getHologram().getLine(6).setLine("");
              }
              holograms.zoomout();
              targeting = null;
            }
            
            targeting = slime.getLine();
            if (targeting.getHologram().getLines().size() > 3) {
              targeting.getHologram().getLine(6).setLine("§aClique para abrir!");
            }
            holograms.zoom();
          }
        } else if (targeting != null) {
          if (targeting.getHologram().getLines().size() > 3) {
            targeting.getHologram().getLine(6).setLine("");
          }
          holograms.zoomout();
          targeting = null;
        }
        
        if (this.update++ > 5) {
          this.update = 0;
          holograms.updateLootChests();
        }
        
        mc.twilight.core.nms.NMS.sendActionBar(player, "§c§lAgache-se para fechar o menu!");
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, 10);
  }
  
  public void cancel() {
    this.cancel(true);
  }
  
  private void cancel(boolean online) {
    HandlerList.unregisterAll(this);
    if (online && this.profile != null && this.player != null && this.player.isOnline()) {
      mc.twilight.core.nms.NMS.sendActionBar(this.player, "");
      NMS.sendFakeSpectator(this.player, null);
      this.player.setAllowFlight(this.player.hasPermission("zcore.fly"));
      this.profile.setGame(null);
      this.profile.setHotbar(this.hotbar);
      this.profile.refreshPlayers();
      this.player.teleport(this.returns);
      
      if (Main.zCosmeticos) {
        CosmeticsAPI.enable(player);
      }
      
    }
    this.returns = null;
    this.chest = null;
    this.player = null;
    this.profile = null;
    if (this.task != null) {
      this.task.cancel();
      this.task = null;
    }
    if (this.cart != null) {
      this.cart.remove();
      this.cart = null;
    }
    if (this.icon != null) {
      this.icon.killEntity();
      this.icon = null;
    }
    if (this.holograms != null) {
      this.holograms.destroy();
      this.holograms = null;
    }
  }
  
  public Box getChest() {
    return this.chest;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public ArmorStand getLootChest() {
    return this.icon.getEntity();
  }
  
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      evt.setCancelled(true);
      if (!this.blocked) {
        this.cancel();
      }
    }
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onPluginDisable(PluginDisableEvent evt) {
    if (evt.getPlugin().getName().equals("zCaixas")) {
      this.cancel(false);
    }
  }
}