package mc.twilight.bedwars.game.improvements.traps.types;

import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.game.improvements.traps.Trap;
import mc.twilight.core.game.Game;
import mc.twilight.core.game.GameTeam;
import mc.twilight.core.player.Profile;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class AlarmTrap extends Trap {
  
  public AlarmTrap() {
    super("REDSTONE_TORCH_ON : 1 : nome>{color}Alarme : desc>&7Revela jogadores invisíveis que\n&7entrarem em sua base.",
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
      ap.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
    }
  }
}