package mc.twilight.bedwars.game.improvements.traps.types;

import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.game.improvements.traps.Trap;
import mc.twilight.core.game.Game;
import mc.twilight.core.game.GameTeam;
import mc.twilight.core.player.Profile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MinerFatigueTrap extends Trap {
  
  public MinerFatigueTrap() {
    super("IRON_PICKAXE : 1 : esconder>tudo : nome>{color}Armadilha de Cansaço! : desc>&7Inflige Fadiga I por 10\n&7segundos.",
        Material.DIAMOND);
  }
  
  @Override
  public void onEnter(BedWarsTeam owner, Profile ap) {
    super.onEnter(owner, ap);
    BedWars game = ap.getGame(BedWars.class);
    if (game == null) {
      return;
    }
    
    if (!owner.equals(game.getTeam(ap.getPlayer())) && ap.playingGame()) {
      owner.removeTrap(this);
      Player player = ap.getPlayer();
      player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
    }
  }
}