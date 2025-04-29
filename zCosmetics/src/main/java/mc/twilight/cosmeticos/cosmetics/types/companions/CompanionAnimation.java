package mc.twilight.cosmeticos.cosmetics.types.companions;

import org.bukkit.util.EulerAngle;

public class CompanionAnimation {
  
  private final EulerAngle angle;
  
  private final String name;
  private final MovementType type;
  
  public CompanionAnimation(EulerAngle angle, String name, MovementType type) {
    this.angle = angle;
    this.name = name;
    this.type = type;
  }
  
  public String getName() {
    return this.name;
  }
  
  public EulerAngle getAngle() {
    return this.angle;
  }
  
  public MovementType getMovementType() {
    return this.type;
  }
  
  public enum MovementType {
    ARM, ARM2, LEG, LEG2, HEAD
  }
}
