package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.cosmetics.types.BalloonCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.balloons.BallonEntity;
import org.bukkit.entity.Player;

public class Balloon {
  
  private Player owner;
  private BallonEntity bat;
  private BallonEntity armorstand;
  
  public Balloon(CUser owner, BalloonCosmetic cosmetic) {
    this.owner = owner.getPlayer();
    this.bat = NMS.getInstance().createBalloonBat(this.owner);
    this.armorstand = NMS.getInstance().createBalloonArmorStand(this.owner, this.bat, cosmetic.listFrames());
  }
  
  public void despawn() {
    this.owner = null;
    if (this.bat != null) {
      this.bat.kill();
      this.bat = null;
    }
    if (this.armorstand != null) {
      this.armorstand.kill();
      this.armorstand = null;
    }
  }
}
