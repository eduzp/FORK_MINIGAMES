package mc.twilight.caixas.api;

import mc.twilight.caixas.box.Box;
import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.box.loot.LootMenu;
import mc.twilight.core.player.Profile;
import org.bukkit.entity.Player;

public class MysteryBoxAPI {
  
  public static void reloadContents() {
    BoxContent.setupRewards();
  }
  
  public static long getMysteryBoxes(Profile profile) {
    return profile.getStats("zCaixas", "magic");
  }
  
  public static void openMenu(Player player) {
    new LootMenu(player, Box.getFirst());
  }
}
