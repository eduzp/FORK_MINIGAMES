package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class MarioClothes extends ClothesCosmetic {
  
  public MarioClothes() {
    super("Roupa do Mario", EnumRarity.RARO, "SKULL_ITEM:3 : 1 : nome>Roupa do Mario : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa do Mario. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJhOGQ4ZTUzZDhhNWE3NTc3MGI2MmNjZTczZGI2YmFiNzAxY2MzZGU0YTliNjU0ZDIxM2Q1NGFmOTYxNSJ9fX0=");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aCapacete do Mario : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJhOGQ4ZTUzZDhhNWE3NTc3MGI2MmNjZTczZGI2YmFiNzAxY2MzZGU0YTliNjU0ZDIxM2Q1NGFmOTYxNSJ9fX0="),
        BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&aPeitoral do Mario : pintar>RED"),
        BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&aCalças do Mario : pintar>RED"),
        BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&aBotas do Mario : pintar>RED"));
  }
}
