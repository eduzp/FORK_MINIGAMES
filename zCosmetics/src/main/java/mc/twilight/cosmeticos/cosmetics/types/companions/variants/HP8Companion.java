package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Collections;

public class HP8Companion extends CompanionCosmetic {
  
  public HP8Companion() {
    super("HP-8", 3, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>HP-8 : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um HP-8! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZjNjhlODJhZDMyMmU1OWZhNDUwN2M0YTM4MWIxNmEwMzRmMDI2ZmRkNmQ3OTNiYTZkMGFkNDFlNjUwZGYyMCJ9fX0=");
    
    this.frames.createKeyFrame(0, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 0, 269.863122), "body", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(1, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 44.9771869, 269.863122), "body", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(2, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 74.4845134, 269.863122), "body", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(3, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 91.6732472, 269.863122), "body", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.addFrames(0, 1, 2, 3);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.HP8;
  }
}
