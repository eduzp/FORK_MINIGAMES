package mc.twilight.bedwars.hook.hotbar;

import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.enums.BedWarsMode;
import mc.twilight.bedwars.menus.MenuLobbies;
import mc.twilight.bedwars.menus.MenuPlay;
import mc.twilight.bedwars.menus.MenuShop;
import mc.twilight.bedwars.menus.MenuSpectator;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.HotbarActionType;

public class BWHotbarActionType extends HotbarActionType {
  
  @Override
  public void execute(Profile profile, String action) {
    if (action.equalsIgnoreCase("loja")) {
      new MenuShop(profile);
    } else if (action.equalsIgnoreCase("lobbies")) {
      new MenuLobbies(profile);
    } else if (action.equalsIgnoreCase("espectar")) {
      BedWars game = profile.getGame(BedWars.class);
      if (game != null) {
        new MenuSpectator(profile.getPlayer(), game);
      }
    } else if (action.equalsIgnoreCase("jogar")) {
      new MenuPlay(profile, profile.getGame(BedWars.class) == null ? BedWarsMode.SOLO : profile.getGame(BedWars.class).getMode());
    } else if (action.equalsIgnoreCase("sair")) {
      profile.getGame(BedWars.class).leave(profile, null);
    }
  }
}
