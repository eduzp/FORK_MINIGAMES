package mc.twilight.bedwars.lobby;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.enums.BedWarsMode;
import mc.twilight.bedwars.lobby.trait.NPCSkinTrait;
import mc.twilight.core.libraries.holograms.HologramLibrary;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.libraries.npclib.NPCLibrary;
import mc.twilight.core.libraries.npclib.api.npc.NPC;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayNPC {
  
  private static final KConfig CONFIG = Main.getInstance().getConfig("npcs");
  private static final List<PlayNPC> NPCS = new ArrayList<>();
  
  private String id;
  private BedWarsMode mode;
  private Location location;
  private NPC npc;
  private Hologram hologram;
  
  public PlayNPC(Location location, String id, BedWarsMode mode) {
    this.location = location;
    this.id = id;
    this.mode = mode;
    if (!this.location.getChunk().isLoaded()) {
      this.location.getChunk().load(true);
    }
    
    this.spawn();
  }
  
  public static void setupNPCs() {
    if (!CONFIG.contains("play")) {
      CONFIG.set("play", new ArrayList<>());
    }
    
    for (String serialized : CONFIG.getStringList("play")) {
      if (serialized.split("; ").length > 6) {
        String id = serialized.split("; ")[6];
        BedWarsMode mode = BedWarsMode.fromName(serialized.split("; ")[7]);
        if (mode == null) {
          continue;
        }
        
        NPCS.add(new PlayNPC(BukkitUtils.deserializeLocation(serialized), id, mode));
      }
    }
    
    Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> listNPCs().forEach(PlayNPC::update), 20, 20);
  }
  
  public static void add(String id, Location location, BedWarsMode mode) {
    NPCS.add(new PlayNPC(location, id, mode));
    List<String> list = CONFIG.getStringList("play");
    list.add(BukkitUtils.serializeLocation(location) + "; " + id + "; " + mode);
    CONFIG.set("play", list);
  }
  
  public static void remove(PlayNPC npc) {
    NPCS.remove(npc);
    List<String> list = CONFIG.getStringList("play");
    list.remove(BukkitUtils.serializeLocation(npc.getLocation()) + "; " + npc.getId() + "; " + npc.getMode());
    CONFIG.set("play", list);
    
    npc.destroy();
  }
  
  public static PlayNPC getById(String id) {
    return NPCS.stream().filter(npc -> npc.getId().equals(id)).findFirst().orElse(null);
  }
  
  public static Collection<PlayNPC> listNPCs() {
    return NPCS;
  }

  public void spawn() {
    if (this.npc != null) {
      this.npc.destroy();
      this.npc = null;
    }
    
    if (this.hologram != null) {
      HologramLibrary.removeHologram(this.hologram);
      this.hologram = null;
    }
    
    this.hologram = HologramLibrary.createHologram(this.location.clone().add(0, 0.5, 0));
    for (int index = (this.mode == BedWarsMode.DUPLA ? Language.lobby$npc$play$dupla$hologram.size() : this.mode == BedWarsMode.SOLO ? Language.lobby$npc$play$solo$hologram.size() : Language.lobby$npc$play$quarteto$hologram.size()); index > 0; index--) {
      this.hologram.withLine((this.mode == BedWarsMode.DUPLA ? Language.lobby$npc$play$dupla$hologram : this.mode == BedWarsMode.SOLO ? Language.lobby$npc$play$solo$hologram : Language.lobby$npc$play$quarteto$hologram).get(index - 1).replace("{players}",
          StringUtils.formatNumber(BedWars.getWaiting(this.mode) + BedWars.getPlaying(this.mode))));
    }
    
    this.npc = NPCLibrary.createNPC(EntityType.PLAYER, "§8[NPC] ");
    this.npc.data().set("play-npc", this.mode.name());
    this.npc.data().set(NPC.HIDE_BY_TEAMS_KEY, true);
    if (this.mode == BedWarsMode.DUPLA) {
      this.npc.addTrait(new NPCSkinTrait(this.npc, Language.lobby$npc$play$dupla$skin$value, Language.lobby$npc$play$dupla$skin$signature));
    } else if (this.mode == BedWarsMode.QUARTETO) {
      this.npc.addTrait(new NPCSkinTrait(this.npc, Language.lobby$npc$play$quarteto$skin$value, Language.lobby$npc$play$quarteto$skin$signature));
    } else {
      this.npc.addTrait(new NPCSkinTrait(this.npc, Language.lobby$npc$play$solo$skin$value, Language.lobby$npc$play$solo$skin$signature));
    }
    this.npc.spawn(this.location);
  }
  
  public void update() {
    int size = this.mode == BedWarsMode.DUPLA ? Language.lobby$npc$play$dupla$hologram.size() : this.mode == BedWarsMode.SOLO ? Language.lobby$npc$play$solo$hologram.size() : Language.lobby$npc$play$quarteto$hologram.size();
    for (int index = size; index > 0; index--) {
      this.hologram.updateLine(size - (index - 1), (this.mode == BedWarsMode.DUPLA ? Language.lobby$npc$play$dupla$hologram : this.mode == BedWarsMode.SOLO ? Language.lobby$npc$play$solo$hologram : Language.lobby$npc$play$quarteto$hologram).get(index - 1)
          .replace("{players}", StringUtils.formatNumber(BedWars.getWaiting(this.mode) + BedWars.getPlaying(this.mode))));
    }
  }
  
  public void destroy() {
    this.id = null;
    this.mode = null;
    this.location = null;
    
    this.npc.destroy();
    this.npc = null;
    HologramLibrary.removeHologram(this.hologram);
    this.hologram = null;
  }
  
  public String getId() {
    return id;
  }
  
  public BedWarsMode getMode() {
    return this.mode;
  }
  
  public Location getLocation() {
    return this.location;
  }
}
