package mc.twilight.cosmeticos.cosmetics.types.morphs;

import mc.twilight.cosmeticos.cosmetics.object.Morph;
import mc.twilight.cosmeticos.nms.interfaces.morphs.MorphEntity;

public abstract class MorphController {
  
  private MorphEntity petEntity;
  
  protected abstract MorphEntity createEntity(Morph pet);
  
  public void spawn(Morph pet) {
    this.petEntity = this.createEntity(pet);
  }
  
  public void remove() {
    if (this.petEntity == null) {
      return;
    }
    
    this.petEntity.kill();
    this.petEntity = null;
  }
  
  public MorphEntity getEntity() {
    return this.petEntity;
  }
}
