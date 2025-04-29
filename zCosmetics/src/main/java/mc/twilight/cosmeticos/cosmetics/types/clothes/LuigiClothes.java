package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class LuigiClothes extends ClothesCosmetic {
  
  public LuigiClothes() {
    super("Roupa do Luigi", EnumRarity.RARO, "SKULL_ITEM:3 : 1 : nome>Roupa do Luigi : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa do Luigi. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmYxNTMzODcxZTQ5ZGRhYjhmMWNhODJlZGIxMTUzYTVlMmVkMzc2NGZkMWNlMDI5YmY4MjlmNGIzY2FhYzMifX19");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aCapacete do Luigi : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmYxNTMzODcxZTQ5ZGRhYjhmMWNhODJlZGIxMTUzYTVlMmVkMzc2NGZkMWNlMDI5YmY4MjlmNGIzY2FhYzMifX19"),
        BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&aPeitoral do Luigi : pintar>GREEN"),
        BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&aCalças do Luigi : pintar>GREEN"),
        BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&aBotas do Luigi : pintar>GREEN"));
  }
}
