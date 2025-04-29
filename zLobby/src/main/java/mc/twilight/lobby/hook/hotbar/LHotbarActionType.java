package mc.twilight.lobby.hook.hotbar;


import mc.twilight.lobby.menus.MenuLobbies;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.HotbarActionType;

public class LHotbarActionType extends HotbarActionType {
  
  @Override
  public void execute(Profile profile, String action) {
    if (action.equalsIgnoreCase("lobbies")) {
      new MenuLobbies(profile);
    }
  }
}
