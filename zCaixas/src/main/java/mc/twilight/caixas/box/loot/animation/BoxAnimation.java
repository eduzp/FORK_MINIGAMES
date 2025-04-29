package mc.twilight.caixas.box.loot.animation;

import mc.twilight.caixas.box.loot.LootMenu;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public abstract class BoxAnimation {
  
  protected LootMenu menu;
  protected Player player;
  private BukkitTask task;
  
  public void start(LootMenu menu) {
    this.menu = menu;
    this.player = menu.getPlayer();
  }
  
  public void onCancel() {}
  
  protected BukkitTask create(boolean open) {
    return this.create(open, 1);
  }
  
  protected abstract BukkitTask create(boolean open, long amount);
  
  public BukkitTask register(boolean open) {
    return this.register(open, 1);
  }
  
  public BukkitTask register(boolean open, long amount) {
    this.task = create(open, amount);
    return this.task;
  }
  
  public void complete() {
    this.cancel();
    this.menu.start();
  }
  
  public void cancel() {
    this.task.cancel();
    this.task = null;
    this.onCancel();
  }
  
  public Player getPlayer() {
    return player;
  }
  
  public ArmorStand getLootChest() {
    return this.menu.getLootChest();
  }
  
  public Location getLootChestLocation() {
    return this.getLootChest().getLocation();
  }
}