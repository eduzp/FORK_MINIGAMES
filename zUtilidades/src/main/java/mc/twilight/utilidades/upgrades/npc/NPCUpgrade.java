package mc.twilight.utilidades.upgrades.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.upgrades.npc.trait.NPCSkinTrait;
import mc.twilight.core.libraries.holograms.HologramLibrary;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.libraries.npclib.NPCLibrary;
import mc.twilight.core.libraries.npclib.api.npc.NPC;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NPCUpgrade {

    private String id;
    private Location location;

    private NPC npc;
    private Hologram hologram;

    public NPCUpgrade(Location location, String id) {
        this.location = location;
        this.id = id;if (!this.location.getChunk().isLoaded()) {
            this.location.getChunk().load(true);
        }

        this.spawn();
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
        for (int index = Language.npc$upgrades$hologram.size(); index > 0; index--) {
            this.hologram.withLine(Language.npc$upgrades$hologram.get(index - 1));
        }

        this.npc = NPCLibrary.createNPC(EntityType.PLAYER, "§8[NPC] ");
        this.npc.data().set("n-upgrade", true);
        this.npc.data().set(NPC.HIDE_BY_TEAMS_KEY, true);
        this.npc.addTrait(new NPCSkinTrait(this.npc, Language.npc$upgrades$skin$value, Language.npc$upgrades$skin$signature));
        this.npc.spawn(this.location);
    }

    public void update() {
    }

    public void destroy() {
        this.id = null;
        this.location = null;

        this.npc.destroy();
        this.npc = null;
        HologramLibrary.removeHologram(this.hologram);
        this.hologram = null;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return this.location;
    }

    private static final KConfig CONFIG = Main.getInstance().getConfig("npcs");
    private static final List<NPCUpgrade> NPCS = new ArrayList<>();

    public static void setupNPCs() {
        if (!CONFIG.contains("upgrade")) {
            CONFIG.set("upgrade", new ArrayList<>());
        }

        for (String serialized : CONFIG.getStringList("upgrade")) {
            if (serialized.split("; ").length > 6) {
                String id = serialized.split("; ")[6];

                NPCS.add(new NPCUpgrade(BukkitUtils.deserializeLocation(serialized), id));
            }
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> listNPCs().forEach(NPCUpgrade::update), 20, 20);
    }

    public static void add(String id, Location location) {
        NPCS.add(new NPCUpgrade(location, id));
        List<String> list = CONFIG.getStringList("upgrade");
        list.add(BukkitUtils.serializeLocation(location) + "; " + id);
        CONFIG.set("upgrade", list);
    }

    public static void remove(NPCUpgrade npc) {
        NPCS.remove(npc);
        List<String> list = CONFIG.getStringList("upgrade");
        list.remove(BukkitUtils.serializeLocation(npc.getLocation()) + "; " + npc.getId());
        CONFIG.set("play", list);

        npc.destroy();
    }

    public static NPCUpgrade getById(String id) {
        return NPCS.stream().filter(npc -> npc.getId().equals(id)).findFirst().orElse(null);
    }

    public static Collection<NPCUpgrade> listNPCs() {
        return NPCS;
    }
}
