package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;
import java.util.Collections;

public class ChimpCompanion extends CompanionCosmetic {
  
  public ChimpCompanion() {
    super("Chimpanzé", 5, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>Chimpanzé : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Chimpanzé! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQzOTI4ODFhMTNhNDJiMDhhZWI4ODhmMzk5ZTFkZWJkYWNlMDRiYzVhMGUwMWMyMjFjMGI1ZDExZDQwIn19fQ==");
    
    this.frames.createKeyFrame(0, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Rightup", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Leftup", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.createKeyFrame(1, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Rightup", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Leftup", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.createKeyFrame(2, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(35, 0, 0), "front_Rightup", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Leftup", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD)));
    
    
    this.frames.addFrames(0, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(1, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(2, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(3, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(4, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(5, 0, 1, 2, 2, 1, 0);
    
    this.frames.createIdleKeyFrame(0, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0.0D, 0.0D, 0.0D), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createIdleKeyFrame(1, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(-2.5D, 0.0D, 0.0D), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createIdleKeyFrame(2, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(-5.0D, 0.0D, 0.0D), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createIdleKeyFrame(3, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(2.5D, 0.0D, 0.0D), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createIdleKeyFrame(4, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(5.0D, 0.0D, 0.0D), "head", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.addIdleFrames(0, 0, 1, 2, 1, 0, 3, 4, 3);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.CHIMP;
  }
}
