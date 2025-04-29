package mc.twilight.caixas.lobby;

import com.google.common.collect.ImmutableList;
import mc.twilight.core.libraries.holograms.HologramLibrary;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.caixas.Language;
import mc.twilight.caixas.Main;
import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.cosmetics.CosmeticType;
import mc.twilight.caixas.cosmetics.types.Opener;
import mc.twilight.caixas.hook.container.SelectedContainer;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.nms.interfaces.entity.IArmorStand;
import mc.twilight.core.player.Profile;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoxNPC {
  
  private static final KConfig CONFIG = Main.getInstance().getConfig("config");
  private static final List<BoxNPC> npcs = new ArrayList<>();
  
  private String id;
  private Location location;
  private Hologram hologram;
  private IArmorStand capsule;
  private String using;
  
  public BoxNPC(String id, Location location) {
    this.id = id;
    this.location = location;
    
    if (!this.location.getChunk().isLoaded()) {
      this.location.getChunk().load(true);
    }
    
    if (this.location.getBlock().getType() == Material.AIR) {
      this.location.getBlock().setType(Material.STAINED_GLASS);
      BlockState state = this.location.getBlock().getState();
      state.getData().setData((byte) 2);
      state.update(true);
    }
    this.refreshCapsuleEntity();
    
    this.hologram = HologramLibrary.createHologram(this.location.clone());
    List<String> lines = new ArrayList<>(Language.lobby$mysterybox$holograms);
    Collections.reverse(lines);
    for (String line : lines) {
      this.hologram.withLine(line);
    }
  }
  
  public static void setupBoxNPCs() {
    if (!CONFIG.contains("locations")) {
      CONFIG.set("locations", new ArrayList<>());
    }
    
    for (String serialized : CONFIG.getStringList("locations")) {
      if (serialized.split("; ").length > 6) {
        String id = serialized.split("; ")[6];
        
        npcs.add(new BoxNPC(id, BukkitUtils.deserializeLocation(serialized)));
      }
    }
    
    Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> listNPCs().forEach(BoxNPC::update), 20, 20);
  }
  
  public static void add(String id, Location location) {
    npcs.add(new BoxNPC(id, location));
    List<String> list = CONFIG.getStringList("locations");
    list.add(BukkitUtils.serializeLocation(location) + "; " + id);
    CONFIG.set("locations", list);
  }
  
  public static void remove(BoxNPC npc) {
    npcs.remove(npc);
    List<String> list = CONFIG.getStringList("locations");
    list.remove(BukkitUtils.serializeLocation(npc.getLocation()) + "; " + npc.getId());
    CONFIG.set("locations", list);
    
    npc.destroy();
  }
  
  public static BoxNPC getById(String id) {
    return npcs.stream().filter(npc -> npc.getId().equals(id)).findFirst().orElse(null);
  }
  
  public static BoxNPC getByLocation(Location location) {
    return npcs.stream().filter(npc -> npc.getLocation().getBlock().equals(location.getBlock())).findFirst().orElse(null);
  }
  
  public static List<BoxNPC> listNPCs() {
    return ImmutableList.copyOf(npcs);
  }
  
  public void enable() {
    this.hologram.spawn();
    this.using = null;
    
    this.refreshCapsuleEntity();
  }
  
  public void disable() {
    this.hologram.despawn();
  }
  
  public void destroy() {
    this.id = null;
    this.location = null;
    
    HologramLibrary.removeHologram(hologram);
    this.hologram = null;
    this.capsule.killEntity();
    this.capsule = null;
  }
  
  public void open(Profile opener, BoxContent box) {
    Player player = opener.getPlayer();
    if (using != null) {
      player.sendMessage("§cEssa caixa já está em uso!");
      return;
    }
    
    this.disable();
    this.using = player.getName();
    Opener opn = opener.getAbstractContainer("zCaixas", "selected", SelectedContainer.class).
        getSelected(CosmeticType.OPENING_ANIMATION, Opener.class);
    opn.execute(this, location.getBlock(), capsule, Profile.getProfile(player.getName()), box, location,
        BoxNPC.this::enable);
    
    //     DefaultOpener.open(capsule, Profile.getProfile(player.getName()), box, location, BoxNPC.this::enable);
  }
  
  public void openMultiples(Profile opener) {
    Player player = opener.getPlayer();
    if (using != null) {
      player.sendMessage("§cEssa caixa já está em uso!");
      return;
    }
    
    this.disable();
    this.using = player.getName();
    
    Opener opn = opener.getAbstractContainer("zCaixas", "selected", SelectedContainer.class).
        getSelected(CosmeticType.OPENING_ANIMATION, Opener.class);
    opn.executeMultiples(capsule, location.getBlock(), opener, location, BoxNPC.this::enable);
  }
  
  public void update() {
    if (using != null) {
      return;
    }
    
    List<String> lines = new ArrayList<>(Language.lobby$mysterybox$holograms);
    int size = lines.size();
    for (int index = size; index > 0; index--) {
      this.hologram.updateLine(size - (index - 1), lines.get(index - 1));
    }
  }
  
  public void refreshCapsuleEntity() {
    if (this.capsule != null) {
      this.capsule.killEntity();
      this.capsule = null;
    }
    this.capsule = NMS.createArmorStand(this.location.clone().subtract(0, 0.5, 0), "", null);
    this.capsule.getEntity().setSmall(true);
    this.capsule.getEntity().setGravity(false);
    this.capsule.getEntity().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
  }
  
  public String getId() {
    return id;
  }
  
  public Location getLocation() {
    return location;
  }
  
  public Hologram getHologram() {
    return hologram;
  }
}
