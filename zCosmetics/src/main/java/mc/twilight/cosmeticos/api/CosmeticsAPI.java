package mc.twilight.cosmeticos.api;

import mc.twilight.cosmeticos.hook.Users;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.menu.CosmeticsMenu;
import org.bukkit.entity.Player;

public class CosmeticsAPI {
  
  public static void openMenu(Player player) {
    CUser user = Users.getByName(player.getName());
    if (user != null) {
      new CosmeticsMenu(user);
    }
  }
  
  public static void enable(Player player) {
    CUser user = Users.getByName(player.getName());
    if (user != null) {
      user.enable();
    }
  }
  
  public static void disable(Player player) {
    CUser user = Users.getByName(player.getName());
    if (user != null) {
      user.disable();
    }
  }
}
