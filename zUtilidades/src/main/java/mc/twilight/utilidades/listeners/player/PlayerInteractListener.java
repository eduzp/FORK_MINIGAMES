package mc.twilight.utilidades.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.upgrades.menu.MenuUpgrades;
import mc.twilight.core.libraries.npclib.NPCLibrary;
import mc.twilight.core.libraries.npclib.api.event.NPCLeftClickEvent;
import mc.twilight.core.libraries.npclib.api.event.NPCRightClickEvent;
import mc.twilight.core.libraries.npclib.api.npc.NPC;
import mc.twilight.core.player.Profile;
import mc.twilight.core.plugin.config.KConfig;

public class PlayerInteractListener implements Listener {

    private static final KConfig config = Main.getInstance().getConfig("upgrades");

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent evt) {
        Player player = evt.getPlayer();
        Profile profile = Profile.getProfile(player.getName());

        if (profile != null) {
            NPC npc = evt.getNPC();
            if (npc.data().has("n-upgrade")) {
                new MenuUpgrades(profile);
            }
        }
    }

}
