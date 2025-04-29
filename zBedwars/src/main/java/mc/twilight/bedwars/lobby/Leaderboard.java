package mc.twilight.bedwars.lobby;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.lobby.leaderboards.BedsLeaderboard;
import mc.twilight.bedwars.lobby.leaderboards.KillsLeaderboard;
import mc.twilight.bedwars.lobby.leaderboards.WinsLeaderboard;
import mc.twilight.core.libraries.holograms.HologramLibrary;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.player.Profile;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Leaderboard {
  
  private static final KConfig CONFIG = Main.getInstance().getConfig("leaderboards");
  private static final List<Leaderboard> LEADERBOARDS = new ArrayList<>();
  
  protected String id;
  protected Location location;
  protected Hologram hologram;
  protected boolean monthly;
  
  public Leaderboard(Location location, String id) {
    this.location = location;
    this.id = id;
    
  }
  
  public static void setupLeaderboards() {
    for (String serialized : CONFIG.getStringList("board-list")) {
      if (serialized.split("; ").length > 6) {
        String id = serialized.split("; ")[6];
        String type = serialized.split("; ")[7];
        Leaderboard board = buildByType(BukkitUtils.deserializeLocation(serialized), id, type);
        if (board == null) {
          return;
        }
        
        LEADERBOARDS.add(board);
      }
    }
  
    Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () ->
        Profile.listProfiles().forEach(Profile::saveSync), 0, Language.lobby$leaderboard$minutes * 1200);
  
    Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () ->
        listLeaderboards().forEach(Leaderboard::update), 0, 1);
  }
  
  public static void add(Location location, String id, String type) {
    List<String> list = CONFIG.getStringList("board-list");
    list.add(BukkitUtils.serializeLocation(location) + "; " + id + "; " + type.toLowerCase());
    CONFIG.set("board-list", list);
    
    Leaderboard board = buildByType(location, id, type);
    LEADERBOARDS.add(board);
    if (board != null) {
      board.update();
    }
  }
  
  public static void remove(Leaderboard board) {
    LEADERBOARDS.remove(board);
    List<String> list = CONFIG.getStringList("board-list");
    list.remove(BukkitUtils.serializeLocation(board.getLocation()) + "; " + board.getId() + "; " + board.getType());
    CONFIG.set("board-list", list);
    
    board.destroy();
  }
  
  public static Leaderboard getById(String id) {
    return LEADERBOARDS.stream().filter(board -> board.getId().equals(id)).findFirst().orElse(null);
  }
  
  public static Collection<Leaderboard> listLeaderboards() {
    return LEADERBOARDS;
  }
  
  private static Leaderboard buildByType(Location location, String id, String type) {
    if (type.equalsIgnoreCase("vitorias")) {
      return new WinsLeaderboard(location, id);
    } else if (type.equalsIgnoreCase("abates")) {
      return new KillsLeaderboard(location, id);
    } else if (type.equalsIgnoreCase("camas")) {
      return new BedsLeaderboard(location, id);
    }
    
    return null;
  }
  
  public abstract String getType();

  public abstract List<String[]> getSplitted();
  
  public abstract List<String> getHologramLines();
  
  public void update() {
    List<String> lines = new ArrayList<>();
    
    List<String[]> list = this.getSplitted();
    for (String line : this.getHologramLines()) {
      for (int i = 0; i < list.size(); i++) {
        line = line.replace("{name_" + (i + 1) + "}", list.get(i)[0]).replace("{stats_" + (i + 1) + "}", list.get(i)[1]);
      }
      lines.add(line);
    }
  
    Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
      if (this.hologram == null) {
        this.hologram = HologramLibrary.createHologram(this.location.clone(), lines);
        return;
      }
    
      int index = 1;
      for (String line : lines) {
        line = line.replace("{monthly_color}", this.canSeeMonthly() ? "§a§l" : "§7")
            .replace("{total_color}", this.canSeeMonthly() ? "§7" : "§a§l");
        hologram.updateLine(index, line);
        if (hologram.getLine(index).getLine().equals("")) {
          hologram.getLine(index).setLocation(hologram.getLine(index).getLocation().add(0, Double.MAX_VALUE, 0));
        }
        hologram.getLine(index).setTouchable(this::onTouch);
        index++;
      }
    });
  }
  
  public void destroy() {
    this.monthly = false;
    if (this.hologram != null) {
      HologramLibrary.removeHologram(this.hologram);
      this.hologram = null;
    }
  }
  
  public String getId() {
    return this.id;
  }
  
  public boolean canSeeMonthly() {
    return this.monthly;
  }
  
  public Location getLocation() {
    return this.location;
  }
  
  private void onTouch(Player touch) {
    EnumSound.CLICK.play(touch, 1.5F, 2.0F);
    this.monthly = !monthly;
  }
}