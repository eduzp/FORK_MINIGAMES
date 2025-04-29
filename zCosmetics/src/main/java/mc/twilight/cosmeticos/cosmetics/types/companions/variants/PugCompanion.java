package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class PugCompanion extends CompanionCosmetic {
  
  public PugCompanion() {
    super("Pug", 5, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>Pug : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Pug! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdiNGY4NGUxOWI1MmYzMTIxNzcxMmU3YmE5ZjUxZDU2ZGE1OWQyNDQ1YjRkN2YzOWVmNmMzMjNiODE2NiJ9fX0=");
    
    this.frames.createKeyFrame(0, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(183, 500, 14), "tail", CompanionAnimation.MovementType.ARM)));
    
    this.frames.createKeyFrame(1, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(183, 500, 0), "tail", CompanionAnimation.MovementType.ARM)));
    
    this.frames.createKeyFrame(2, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(183, 500, 29), "tail", CompanionAnimation.MovementType.ARM)));
    
    this.frames.addFrames(0, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(1, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(2, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(3, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(4, 0, 1, 2, 2, 1, 0);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.PUG;
  }
}
