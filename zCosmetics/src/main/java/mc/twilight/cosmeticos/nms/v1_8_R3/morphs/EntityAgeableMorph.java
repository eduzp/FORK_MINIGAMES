package mc.twilight.cosmeticos.nms.v1_8_R3.morphs;

import mc.twilight.cosmeticos.cosmetics.object.Morph;
import mc.twilight.cosmeticos.nms.interfaces.morphs.MorphAgeable;

public class EntityAgeableMorph extends EntityMorph implements MorphAgeable {
  
  public EntityAgeableMorph(Morph pet) {
    super(pet);
  }
  
  @Override
  protected void h() {
    super.h();
    this.datawatcher.a(12, (byte) 0);
  }
  
  @Override
  public void setBaby(boolean baby) {
    this.datawatcher.watch(12, baby ? (byte) -1 : 0);
    this.resetEntitySize(baby);
  }
}
