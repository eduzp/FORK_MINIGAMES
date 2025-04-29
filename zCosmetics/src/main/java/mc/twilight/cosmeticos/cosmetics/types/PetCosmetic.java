package mc.twilight.cosmeticos.cosmetics.types;

import com.mojang.authlib.properties.Property;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetType;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class PetCosmetic extends Cosmetic {
  
  private final ItemStack icon;
  private final PetType type;
  
  public PetCosmetic(String name, EnumRarity rarity, PetType type, String icon) {
    super(name, rarity, CosmeticType.PET);
    this.type = type;
    this.icon = BukkitUtils.deserializeItemStack(icon);
    Cosmetic.addCosmetic(this);
  }
  
  public static void setupPets() {
    new PetCosmetic("Enderman", EnumRarity.COMUM, PetType.ENDERMAN,
        "SKULL_ITEM:3 : 1 : nome>Enderman : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Enderman! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E1OWJiMGE3YTMyOTY1YjNkOTBkOGVhZmE4OTlkMTgzNWY0MjQ1MDllYWRkNGU2YjcwOWFkYTUwYjljZiJ9fX0=");
    new PetCosmetic("Endermite", EnumRarity.COMUM, PetType.ENDERMITE,
        "SKULL_ITEM:3 : 1 : nome>Endermite : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Endermite! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWExYTA4MzFhYTAzYWZiNDIxMmFkY2JiMjRlNWRmYWE3ZjQ3NmExMTczZmNlMjU5ZWY3NWE4NTg1NSJ9fX0=");
    new PetCosmetic("Vaca", EnumRarity.COMUM, PetType.COW,
        "SKULL_ITEM:3 : 1 : nome>Vaca : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de uma Vaca! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ2YzZlZGE5NDJmN2Y1ZjcxYzMxNjFjNzMwNmY0YWVkMzA3ZDgyODk1ZjlkMmIwN2FiNDUyNTcxOGVkYzUifX19");
    new PetCosmetic("Traça", EnumRarity.COMUM, PetType.SILVERFISH,
        "SKULL_ITEM:3 : 1 : nome>Traça : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de uma Traça! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ==");
    new PetCosmetic("Galinha", EnumRarity.COMUM, PetType.CHICKEN,
        "SKULL_ITEM:3 : 1 : nome>Galinha : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de uma Galinha! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==");
    new PetCosmetic("Esqueleto", EnumRarity.RARO, PetType.SKELETON,
        "SKULL_ITEM:3 : 1 : nome>Esqueleto : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Esqueleto! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==");
    new PetCosmetic("Golem de Ferro", EnumRarity.RARO, PetType.IRON_GOLEM,
        "SKULL_ITEM:3 : 1 : nome>Golem de Ferro : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Golem de Ferro! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwOTFkNzllYTBmNTllZjdlZjk0ZDdiYmE2ZTVmMTdmMmY3ZDQ1NzJjNDRmOTBmNzZjNDgxOWE3MTQifX19");
    new PetCosmetic("Ovelha", EnumRarity.RARO, PetType.SHEEP,
        "SKULL_ITEM:3 : 1 : nome>Ovelha : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de uma Ovelha! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19");
    new PetCosmetic("Lobo", EnumRarity.RARO, PetType.WOLF,
        "SKULL_ITEM:3 : 1 : nome>Lobo : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Lobo! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjlkMWQzMTEzZWM0M2FjMjk2MWRkNTlmMjgxNzVmYjQ3MTg4NzNjNmM0NDhkZmNhODcyMjMxN2Q2NyJ9fX0=");
    new PetCosmetic("Porco", EnumRarity.RARO, PetType.PIG,
        "SKULL_ITEM:3 : 1 : nome>Porco : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Porco! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0=");
    new PetCosmetic("Coelho", EnumRarity.RARO, PetType.RABBIT,
        "SKULL_ITEM:3 : 1 : nome>Coelho : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Coelho! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZlY2M2YjVlNmVhNWNlZDc0YzQ2ZTc2MjdiZTNmMDgyNjMyN2ZiYTI2Mzg2YzZjYzc4NjMzNzJlOWJjIn19fQ==");
    new PetCosmetic("Aldeão", EnumRarity.EPICO, PetType.VILLAGER,
        "SKULL_ITEM:3 : 1 : nome>Aldeão : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Aldeão! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIyZDhlNzUxYzhmMmZkNGM4OTQyYzQ0YmRiMmY1Y2E0ZDhhZThlNTc1ZWQzZWIzNGMxOGE4NmU5M2IifX19");
    new PetCosmetic("Aranha", EnumRarity.EPICO, PetType.SPIDER,
        "SKULL_ITEM:3 : 1 : nome>Aranha : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de uma Aranha! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==");
    new PetCosmetic("Slime", EnumRarity.EPICO, PetType.SLIME,
        "SKULL_ITEM:3 : 1 : nome>Slime : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Slime! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk1YWVlYzZiODQyYWRhODY2OWY4NDZkNjViYzQ5NzYyNTk3ODI0YWI5NDRmMjJmNDViZjNiYmI5NDFhYmU2YyJ9fX0=");
    new PetCosmetic("Creeper", EnumRarity.EPICO, PetType.CREEPER,
        "SKULL_ITEM:3 : 1 : nome>Creeper : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Creeper! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQyNTQ4MzhjMzNlYTIyN2ZmY2EyMjNkZGRhYWJmZTBiMDIxNWY3MGRhNjQ5ZTk0NDQ3N2Y0NDM3MGNhNjk1MiJ9fX0=");
    new PetCosmetic("Zumbi", EnumRarity.EPICO, PetType.ZOMBIE,
        "SKULL_ITEM:3 : 1 : nome>Zumbi : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Zumbi! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==");
    new PetCosmetic("Lula", EnumRarity.EPICO, PetType.SQUID,
        "SKULL_ITEM:3 : 1 : nome>Lula : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de uma Lula! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMDE0MzNiZTI0MjM2NmFmMTI2ZGE0MzRiODczNWRmMWViNWIzY2IyY2VkZTM5MTQ1OTc0ZTljNDgzNjA3YmFjIn19fQ==");
    new PetCosmetic("Cavalo", EnumRarity.DIVINO, PetType.HORSE,
        "SKULL_ITEM:3 : 1 : nome>Cavalo : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Cavalo! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JiNGIyODg5OTFlZmI4Y2EwNzQzYmVjY2VmMzEyNThiMzFkMzlmMjQ5NTFlZmIxYzljMThhNDE3YmE0OGY5In19fQ==");
    new PetCosmetic("Gato", EnumRarity.DIVINO, PetType.OCELOT,
        "SKULL_ITEM:3 : 1 : nome>Gato : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Gato! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFmMDdlM2YyZTVmMjU2YmZhZGU2NjZhOGRlMWI1ZDMwMjUyYzk1ZTk4ZjhhOGVjYzZlM2M3YjdmNjcwOTUifX19");
    new PetCosmetic("Blaze", EnumRarity.DIVINO, PetType.BLAZE,
        "SKULL_ITEM:3 : 1 : nome>Blaze : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Blaze! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==");
    new PetCosmetic("Morcego", EnumRarity.DIVINO, PetType.BAT,
        "SKULL_ITEM:3 : 1 : nome>Morcego : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Morcego! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyZmMzZjcxYjQxNzY5Mzc2YTllOTJmZTNhZGJhYWMzNzcyYjk5OWIyMTljOWQ2YjQ2ODBiYTk5ODNlNTI3In19fQ==");
  }
  
  @Override
  public void onSelect(CUser user) {
    user.selectCosmetic(Cosmetic.NONE_COMPANION);
    user.handleCompanion();
    user.handlePet();
  }
  
  public String getSkinValue() {
    return SKULL_META_PROFILE.get(icon.getItemMeta()).getProperties().get("textures").stream().findFirst().orElse(new Property("textures", "")).getValue();
  }
  
  public ItemStack getIcon(String color, String... loreList) {
    return this.getIcon(color, Arrays.asList(loreList));
  }
  
  public ItemStack getIcon(String color, List<String> loreList) {
    ItemStack icon = this.icon.clone();
    ItemMeta meta = icon.getItemMeta();
    meta.setDisplayName(color + meta.getDisplayName());
    List<String> currentLore = meta.getLore();
    currentLore.add("");
    currentLore.add("§fRaridade: " + this.getRarity().getName());
    currentLore.addAll(loreList);
    meta.setLore(currentLore);
    icon.setItemMeta(meta);
    return icon;
  }
  
  public PetType getPetType() {
    return type;
  }
}
