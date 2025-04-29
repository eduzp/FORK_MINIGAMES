package mc.twilight.skywars.hook.hotbar;

import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.HotbarActionType;
import mc.twilight.skywars.cosmetics.types.Perk;
import mc.twilight.skywars.cosmetics.types.kits.NormalKit;
import mc.twilight.skywars.game.AbstractSkyWars;
import mc.twilight.skywars.menus.MenuLobbies;
import mc.twilight.skywars.menus.MenuPlay;
import mc.twilight.skywars.menus.vendinha.MenuShop;
import mc.twilight.skywars.menus.MenuSpectator;
import mc.twilight.skywars.menus.cosmetics.kits.MenuSelectKit;
import mc.twilight.skywars.menus.cosmetics.perks.MenuSelectPerk;

public class SWHotbarActionType extends HotbarActionType {
  
  @Override
  public void execute(Profile profile, String action) {
    if (action.equalsIgnoreCase("loja")) {
      new MenuShop(profile);
    } else if (action.equalsIgnoreCase("lobbies")) {
      new MenuLobbies(profile);
    } else if (action.equalsIgnoreCase("kits")) {
      new MenuSelectKit<>(profile, NormalKit.class);
    } else if (action.equalsIgnoreCase("habilidades")) {
      new MenuSelectPerk<>(profile, Perk.class);
    } else if (action.equalsIgnoreCase("espectar")) {
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null) {
        new MenuSpectator(profile.getPlayer(), game);
      }
    } else if (action.equalsIgnoreCase("jogar")) {
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null) {
        new MenuPlay(profile, game.getMode());
      }
    } else if (action.equalsIgnoreCase("sair")) {
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null) {
        game.leave(profile, null);
      }
    }
  }
}
