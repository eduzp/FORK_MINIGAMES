package mc.twilight.cosmeticos.cosmetics;

import com.mojang.authlib.GameProfile;
import mc.twilight.cosmeticos.cosmetics.types.*;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.reflection.Accessors;
import mc.twilight.core.reflection.MinecraftReflection;
import mc.twilight.core.reflection.acessors.FieldAccessor;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Cosmetic {
  
  public static final Cosmetic NONE_COMPANION = new Cosmetic(CosmeticType.COMPANION), NONE_BANNER = new Cosmetic(CosmeticType.BANNER), NONE_PARTICLE = new Cosmetic(CosmeticType.PARTICLE), NONE_HAT = new Cosmetic(CosmeticType.HAT), NONE_ANIMATED_HAT =
      new Cosmetic(CosmeticType.ANIMATED_HAT), NONE_BALLOON = new Cosmetic(CosmeticType.BALLOON), NONE_PET = new Cosmetic(CosmeticType.PET), NONE_CLOTHES =
      new Cosmetic(CosmeticType.CLOTHES), NONE_GADGET = new Cosmetic(CosmeticType.GADGET), NONE_MORPH = new Cosmetic(CosmeticType.MORPH);
  protected static final FieldAccessor<GameProfile> SKULL_META_PROFILE =
      Accessors.getField(MinecraftReflection.getCraftBukkitClass("inventory.CraftMetaSkull"), "profile", GameProfile.class);
  private static final List<Cosmetic> COSMETICS = new ArrayList<>();
  private final long id;
  private final String name;
  private final CosmeticType type;
  private EnumRarity rarity;
  
  private Cosmetic(CosmeticType type) {
    this.id = 0;
    this.type = type;
    this.name = "Nenhum";
  }
  
  public Cosmetic(String name, EnumRarity rarity, CosmeticType type) {
    this.id = listCosmetics(type).size() + 1;
    this.type = type;
    this.name = name;
    this.rarity = rarity;
  }
  
  public static void setupCosmetics() {
    CompanionCosmetic.setupCompanions();
    HatCosmetic.setupHats();
    AnimatedHatCosmetic.setupAnimatedHats();
    BalloonCosmetic.setupBalloons();
    MorphCosmetic.setupMorphs();
    PetCosmetic.setupPets();
    BannerCosmetic.setupBanners();
    ClothesCosmetic.setupClothes();
    ParticleCosmetic.setupParticles();
    GadgetsCosmetic.setupGadgets();
  }
  
  public static void addCosmetic(Cosmetic cosmetic) {
    COSMETICS.add(cosmetic);
  }
  
  public static Cosmetic findById(String lootChestID) {
    return COSMETICS.stream().filter(cosmetic -> cosmetic.getLootChestsID().equals(lootChestID)).findFirst().orElse(null);
  }
  
  public static Collection<Cosmetic> listCosmetics() {
    return COSMETICS;
  }

  public static Collection<Cosmetic> listCosmetics(CosmeticType type) {
    return COSMETICS.stream().filter(cosmetic -> cosmetic.getType().equals(type)).collect(Collectors.toList());
  }

  public static <T extends Cosmetic> Collection<T> listCosmetics(Class<T> cosmeticClass) {
    return COSMETICS.stream().filter(cosmetic -> cosmetic.getClass().equals(cosmeticClass) || cosmetic.getClass().getSuperclass().equals(cosmeticClass))
        .map(cosmetic -> (T) cosmetic).sorted((c1, c2) -> Integer.compare(c2.getRarity().ordinal(), c1.getRarity().ordinal())).collect(Collectors.toList());
  }
  
  public void onSelect(CUser client) {
  }
  
  public long getUniqueId() {
    return this.id;
  }
  
  public String getLootChestsID() {
    return "cm" + this.type.ordinal() + "-" + this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public CosmeticType getType() {
    return this.type;
  }
  
  public EnumRarity getRarity() {
    return this.rarity;
  }
  
}
