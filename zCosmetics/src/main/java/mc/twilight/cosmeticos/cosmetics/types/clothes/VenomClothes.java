package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class VenomClothes extends ClothesCosmetic {
  
  public VenomClothes() {
    super("Roupa do Venom", EnumRarity.EPICO, "SKULL_ITEM:3 : 1 : nome>Roupa do Venom : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa do Venom. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWJhNzQ0YTA2MTQ0MzFlMWU2OTUwZWZhYjY4ZDNjYzNmMjQ5ZmJiNWQ5N2ZkMDY3ZmZjNTg4NWQ4YjRmMWI4MSJ9fX0=");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aCapacete do Venom : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWJhNzQ0YTA2MTQ0MzFlMWU2OTUwZWZhYjY4ZDNjYzNmMjQ5ZmJiNWQ5N2ZkMDY3ZmZjNTg4NWQ4YjRmMWI4MSJ9fX0="),
        BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&aPeitoral do Venom : pintar>BLACK"),
        BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&aCalças do Venom : pintar>BLACK"),
        BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&aBotas do Venom : pintar>BLACK"));
  }
}
