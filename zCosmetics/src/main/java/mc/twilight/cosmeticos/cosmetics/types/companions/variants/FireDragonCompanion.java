package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class FireDragonCompanion extends CompanionCosmetic {
  
  public FireDragonCompanion() {
    super("Dragão de Fogo", 5, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>Dragão de Fogo : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Dragão de Fogo! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGIzNmQ3OWJhNDA3MDUzYzI1MWJkNGFhOTEzYTVlMjgxY2NkMjRkOGQ5ZWJlOTNkMjhiYjYyZjE0MGFkYzUifX19");
    
    this.frames.createKeyFrame(0, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 165), "l_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 165), "l_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 180), "l_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 180), "l_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 164), "l_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 178), "l_wing6", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 15), "r_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 15), "r_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 0), "r_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 0), "r_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 16), "r_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 2), "r_wing6", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.createKeyFrame(1, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 150), "l_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 150), "l_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 165), "l_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 165), "l_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 149), "l_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 163), "l_wing6", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 30), "r_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 30), "r_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 15), "r_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 15), "r_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 31), "r_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 17), "r_wing6", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.createKeyFrame(2, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 135), "l_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 135), "l_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 150), "l_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 150), "l_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 134), "l_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 148), "l_wing6", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 45), "r_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 45), "r_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 30), "r_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 30), "r_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 46), "r_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 32), "r_wing6", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.createKeyFrame(3, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 150), "l_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 150), "l_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 165), "l_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 165), "l_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 149), "l_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 163), "l_wing6", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 30), "r_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 30), "r_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 15), "r_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 15), "r_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 31), "r_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 17), "r_wing6", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.addFrames(0, 0, 1, 2, 3);
    this.frames.addFrames(1, 0, 1, 2, 3);
    this.frames.addFrames(2, 0, 1, 2, 3);
    this.frames.addFrames(3, 0, 1, 2, 3);
    this.frames.addFrames(4, 0, 1, 2, 3);
    this.frames.addFrames(5, 0, 1, 2, 3);
    this.frames.addFrames(6, 0, 1, 2, 3);
    this.frames.addFrames(7, 0, 1, 2, 3);
    this.frames.addFrames(8, 0, 1, 2, 3);
    this.frames.addFrames(9, 0, 1, 2, 3);
    this.frames.addFrames(10, 0, 1, 2, 3);
    this.frames.addFrames(11, 0, 1, 2, 3);
    
    this.frames.createIdleKeyFrame(0, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 165), "l_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 165), "l_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 180), "l_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 180), "l_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 164), "l_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 178), "l_wing6", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 15), "r_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 15), "r_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 0), "r_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 0), "r_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 16), "r_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 2), "r_wing6", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.createIdleKeyFrame(1, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 150), "l_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 150), "l_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 165), "l_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 165), "l_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 149), "l_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 163), "l_wing6", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 30), "r_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 30), "r_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 15), "r_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 15), "r_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 31), "r_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 17), "r_wing6", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.createIdleKeyFrame(2, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 135), "l_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 135), "l_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 150), "l_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 150), "l_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 134), "l_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 148), "l_wing6", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 45), "r_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 45), "r_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 30), "r_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 30), "r_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 46), "r_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 32), "r_wing6", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.createIdleKeyFrame(3, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 150), "l_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 150), "l_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 70, 165), "l_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 110, 165), "l_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 149), "l_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(90, 90, 163), "l_wing6", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 30), "r_wing1", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 30), "r_wing2", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -70, 15), "r_wing3", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -110, 15), "r_wing4", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 31), "r_wing5", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-90, -90, 17), "r_wing6", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.addIdleFrames(0, 0, 1, 2, 3);
    this.frames.addIdleFrames(1, 0, 1, 2, 3);
    this.frames.addIdleFrames(2, 0, 1, 2, 3);
    this.frames.addIdleFrames(3, 0, 1, 2, 3);
    this.frames.addIdleFrames(4, 0, 1, 2, 3);
    this.frames.addIdleFrames(5, 0, 1, 2, 3);
    this.frames.addIdleFrames(6, 0, 1, 2, 3);
    this.frames.addIdleFrames(7, 0, 1, 2, 3);
    this.frames.addIdleFrames(8, 0, 1, 2, 3);
    this.frames.addIdleFrames(9, 0, 1, 2, 3);
    this.frames.addIdleFrames(10, 0, 1, 2, 3);
    this.frames.addIdleFrames(11, 0, 1, 2, 3);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.FIREDRAGON;
  }
}
