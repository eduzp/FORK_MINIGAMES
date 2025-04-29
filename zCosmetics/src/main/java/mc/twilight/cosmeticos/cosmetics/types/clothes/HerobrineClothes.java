package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class HerobrineClothes extends ClothesCosmetic {
  
  public HerobrineClothes() {
    super("Roupa do Herobrine", EnumRarity.DIVINO, "SKULL_ITEM:3 : 1 : nome>Roupa do Herobrine : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa do Herobrine. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiN2NhM2M3ZDMxNGE2MWFiZWQ4ZmMxOGQ3OTdmYzMwYjZlZmM4NDQ1NDI1YzRlMjUwOTk3ZTUyZTZjYiJ9fX0=");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aCapacete do Herobrine : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiN2NhM2M3ZDMxNGE2MWFiZWQ4ZmMxOGQ3OTdmYzMwYjZlZmM4NDQ1NDI1YzRlMjUwOTk3ZTUyZTZjYiJ9fX0="),
        BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&aPeitoral do Herobrine : pintar>0:175:175"),
        BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&aCalças do Herobrine : pintar>68:55:166"),
        BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&aBotas do Herobrine : pintar>107:107:107"));
  }
}
