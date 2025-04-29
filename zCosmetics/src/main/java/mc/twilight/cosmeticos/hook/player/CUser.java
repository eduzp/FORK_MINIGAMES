package mc.twilight.cosmeticos.hook.player;

import mc.twilight.cosmeticos.Language;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.cosmetics.object.*;
import mc.twilight.cosmeticos.cosmetics.types.*;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetType;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettings;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.hook.container.CosmeticsContainer;
import mc.twilight.cosmeticos.hook.container.SelectedContainer;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.player.Profile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CUser {
  
  private String name;
  private Profile profile;
  private boolean enabled;
  
  private Companion selectedCompanion;
  private Banner selectedBanner;
  private Hat selectedHat;
  private Particle selectedParticle;
  private AnimatedHat selectedAnimatedHat;
  private Morph selectedMorph;
  private Balloon selectedBalloon;
  private Pet selectedPet;
  private Clothes selectedClothes;
  private GadgetsCosmetic gadget;
  private Map<PetType, List<PetSettingsEntry>> petSettings;
  private Map<CompanionNames, String> companionNames;
  
  public CUser(String name) {
    this.name = name;
    this.profile = Profile.getProfile(name);
    
    this.petSettings = new HashMap<>();
    for (PetType type : PetType.values()) {
      this.petSettings.put(type, PetSettings.parseEntriesForType(this, type));
    }
    this.companionNames = CompanionNames.loadNames(this);
  }
  
  public void selectCosmetic(Cosmetic cosmetic) {
    this.selectCosmetic(cosmetic, 1);
  }
  
  public void selectCosmetic(Cosmetic cosmetic, int index) {
    this.getSelectedContainer().set(cosmetic.getType(), cosmetic.getUniqueId(), index);
    cosmetic.onSelect(this);
  }
  
  public void addCosmetic(Cosmetic cosmetic) {
    this.getCosmeticsContainer().add(cosmetic.getType(), cosmetic.getUniqueId());
  }
  
  public boolean isSelected(Cosmetic cosmetic) {
    return this.isSelected(cosmetic, 1);
  }
  
  public boolean isSelected(Cosmetic cosmetic, int index) {
    return this.getSelectedContainer().get(cosmetic.getType(), index) == cosmetic.getUniqueId();
  }
  
  public boolean hasCosmetic(Cosmetic cosmetic) {
    return getPlayer().hasPermission("zcosmeticos.cosmetics." + cosmetic.getType().name().toLowerCase()) || this.getCosmeticsContainer()
        .has(cosmetic.getType(), cosmetic.getUniqueId());
  }
  
  public Cosmetic getCosmetic(CosmeticType type) {
    return this.getCosmetic(type, 1);
  }
  
  public Cosmetic getCosmetic(CosmeticType type, int index) {
    long cosmeticId = this.getSelectedContainer().get(type, index);
    if (cosmeticId != 0) {
      return Cosmetic.listCosmetics(type).stream().filter(cosmetic -> cosmetic.getUniqueId() == cosmeticId).findFirst().orElse(null);
    }
    
    if (type == CosmeticType.COMPANION) {
      return Cosmetic.NONE_COMPANION;
    } else if (type == CosmeticType.HAT) {
      return Cosmetic.NONE_HAT;
    } else if (type == CosmeticType.ANIMATED_HAT) {
      return Cosmetic.NONE_ANIMATED_HAT;
    } else if (type == CosmeticType.BALLOON) {
      return Cosmetic.NONE_BALLOON;
    } else if (type == CosmeticType.PET) {
      return Cosmetic.NONE_PET;
    } else if (type == CosmeticType.PARTICLE) {
      return Cosmetic.NONE_PARTICLE;
    } else if (type == CosmeticType.BANNER) {
      return Cosmetic.NONE_BANNER;
    } else if (type == CosmeticType.GADGET) {
      return Cosmetic.NONE_GADGET;
    } else if (type == CosmeticType.MORPH) {
      return Cosmetic.NONE_MORPH;
    }
    return Cosmetic.NONE_CLOTHES;
  }
  
  public void enable() {
    this.enabled = true;
    this.handleCompanion();
    this.handleHat();
    this.handleAnimatedHat();
    this.handleBalloon();
    this.handlePet();
    this.handleBanner();
    this.handleParticle();
    this.handleClothes();
    this.handleMorph();
    this.handleGadget();
  }
  
  public void disable() {
    this.enabled = false;
    if (this.selectedBanner != null) {
      this.selectedBanner.destroy();
      this.selectedBanner = null;
    }
    if (this.selectedMorph != null) {
      this.selectedMorph.cancel();
      this.selectedMorph = null;
    }
    if (this.selectedCompanion != null) {
      this.selectedCompanion.despawn();
      this.selectedCompanion = null;
    }
    if (this.selectedHat != null) {
      this.selectedHat.destroy();
      this.selectedHat = null;
    }
    if (this.selectedAnimatedHat != null) {
      this.selectedAnimatedHat.cancel();
      this.selectedAnimatedHat = null;
    }
    if (this.selectedBalloon != null) {
      this.selectedBalloon.despawn();
      this.selectedBalloon = null;
    }
    if (this.selectedPet != null) {
      this.selectedPet.despawn();
      this.selectedPet = null;
    }
    if (this.selectedClothes != null) {
      this.selectedClothes.despawn();
      this.selectedClothes = null;
    }
    if (selectedParticle != null) {
      this.selectedParticle.cancel();
      this.selectedParticle = null;
    }
  }
  
  public void handleBanner() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedBanner != null) {
      this.selectedBanner.destroy();
      this.selectedBanner = null;
    }
    
    long bannerId = this.getSelectedContainer().get(CosmeticType.BANNER);
    if (bannerId != 0) {
      BannerCosmetic bannerCosmetic = Cosmetic.listCosmetics(BannerCosmetic.class).stream().filter(c -> c.getUniqueId() == bannerId).findFirst().orElse(null);
      if (bannerCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.BANNER, 0);
      } else {
        this.selectedBanner = new Banner(this, bannerCosmetic);
      }
    }
  }
  
  public void handleCompanion() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedCompanion != null) {
      this.selectedCompanion.despawn();
      this.selectedCompanion = null;
    }
    
    long companionId = this.getSelectedContainer().get(CosmeticType.COMPANION);
    if (companionId != 0) {
      CompanionCosmetic companionCosmetic = Cosmetic.listCosmetics(CompanionCosmetic.class).stream().filter(c -> c.getUniqueId() == companionId).findFirst().orElse(null);
      if (companionCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.COMPANION, 0);
      } else {
        this.selectedCompanion = companionCosmetic.build(this);
      }
    }
  }
  
  public void handleHat() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedHat != null) {
      this.selectedHat.destroy();
      this.selectedHat = null;
    }
    
    long hatId = this.getSelectedContainer().get(CosmeticType.HAT);
    if (hatId != 0) {
      HatCosmetic hatCosmetic = Cosmetic.listCosmetics(HatCosmetic.class).stream().filter(c -> c.getUniqueId() == hatId).findFirst().orElse(null);
      if (hatCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.HAT, 0);
      } else {
        this.selectedHat = new Hat(this, hatCosmetic);
      }
    }
  }
  
  
  public void handleParticle() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedParticle != null) {
      this.selectedParticle.cancel();
      this.selectedParticle = null;
    }
    
    long particleId = this.getSelectedContainer().get(CosmeticType.PARTICLE);
    if (particleId != 0) {
      ParticleCosmetic particleCosmetic = Cosmetic.listCosmetics(ParticleCosmetic.class).stream().filter(c -> c.getUniqueId() == particleId).findFirst().orElse(null);
      if (particleCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.PARTICLE, 0);
      } else {
        this.selectedParticle = new Particle(this, particleCosmetic);
      }
    }
  }
  
  public void handleMorph() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedMorph != null) {
      this.selectedMorph.cancel();
      this.selectedMorph = null;
    }
    
    long particleId = this.getSelectedContainer().get(CosmeticType.MORPH);
    if (particleId != 0) {
      MorphCosmetic particleCosmetic = Cosmetic.listCosmetics(MorphCosmetic.class).stream().filter(c -> c.getUniqueId() == particleId).findFirst().orElse(null);
      if (particleCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.MORPH, 0);
      } else {
        this.selectedMorph = new Morph(this, particleCosmetic);
      }
    }
  }
  
  public void handleAnimatedHat() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedAnimatedHat != null) {
      this.selectedAnimatedHat.cancel();
      this.selectedAnimatedHat = null;
    }
    
    long animatedHatId = this.getSelectedContainer().get(CosmeticType.ANIMATED_HAT);
    if (animatedHatId != 0) {
      AnimatedHatCosmetic animatedHatCosmetic = Cosmetic.listCosmetics(AnimatedHatCosmetic.class).stream().filter(c -> c.getUniqueId() == animatedHatId).findFirst().orElse(null);
      if (animatedHatCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.ANIMATED_HAT, 0);
      } else {
        this.selectedAnimatedHat = new AnimatedHat(this, animatedHatCosmetic);
      }
    }
  }
  
  public void handleBalloon() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedBalloon != null) {
      this.selectedBalloon.despawn();
      this.selectedBalloon = null;
    }
    
    long balloonId = this.getSelectedContainer().get(CosmeticType.BALLOON);
    if (balloonId != 0) {
      BalloonCosmetic balloonCosmetic = Cosmetic.listCosmetics(BalloonCosmetic.class).stream().filter(c -> c.getUniqueId() == balloonId).findFirst().orElse(null);
      if (balloonCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.BALLOON, 0);
      } else {
        this.selectedBalloon = new Balloon(this, balloonCosmetic);
      }
    }
  }
  
  public void handlePet() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedPet != null) {
      this.selectedPet.despawn();
      this.selectedPet = null;
    }
    
    long petId = this.getSelectedContainer().get(CosmeticType.PET);
    if (petId != 0) {
      PetCosmetic petCosmetic = Cosmetic.listCosmetics(PetCosmetic.class).stream().filter(c -> c.getUniqueId() == petId).findFirst().orElse(null);
      if (petCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.PET, 0);
      } else {
        this.selectedPet = new Pet(this, petCosmetic);
      }
    }
  }
  
  public void handleClothes() {
    if (!this.enabled) {
      return;
    }
    
    if (this.selectedClothes != null) {
      this.selectedClothes.despawn();
      this.selectedClothes = null;
    }
    
    ClothesCosmetic clothesCosmetic = null;
    ItemStack[] armor = new ItemStack[4];
    for (int index = 0; index < 4; index++) {
      long clothId = this.getSelectedContainer().get(CosmeticType.CLOTHES, index + 1);
      if (clothId != 0) {
        clothesCosmetic = Cosmetic.listCosmetics(ClothesCosmetic.class).stream().filter(c -> c.getUniqueId() == clothId).findFirst().orElse(null);
        if (clothesCosmetic == null) {
          this.getSelectedContainer().set(CosmeticType.CLOTHES, 0, index + 1);
        } else {
          armor[index] = clothesCosmetic.getPiece(index);
        }
      }
    }
    
    ItemStack helmet = armor[0];
    ItemStack chestplate = armor[1];
    ItemStack leggings = armor[2];
    ItemStack boots = armor[3];
    if (helmet != null) {
      this.selectCosmetic(Cosmetic.NONE_HAT);
      this.selectCosmetic(Cosmetic.NONE_ANIMATED_HAT);
      this.handleHat();
      this.handleAnimatedHat();
    }
    
    if (helmet != null || chestplate != null || leggings != null || boots != null) {
      this.selectedClothes = new Clothes(this, helmet, chestplate, leggings, boots, clothesCosmetic);
    }
  }
  
  public void handleGadget() {
    if (!this.enabled) {
      return;
    }
    
    long gadgetId = this.getSelectedContainer().get(CosmeticType.GADGET);
    if (gadgetId != 0) {
      GadgetsCosmetic gadgetsCosmetic = Cosmetic.listCosmetics(GadgetsCosmetic.class).stream().filter(c -> c.getUniqueId() == gadgetId).findFirst().orElse(null);
      if (gadgetsCosmetic == null) {
        this.getSelectedContainer().set(CosmeticType.GADGET, 0);
        this.getPlayer().getInventory().setItem(Language.settings$gadget$slot - 1, null);
      } else {
        this.getPlayer().getInventory().setItem(Language.settings$gadget$slot - 1, gadgetsCosmetic.getItem());
      }
      
      this.gadget = gadgetsCosmetic;
    } else if (this.gadget != null) {
      this.gadget = null;
      this.getPlayer().getInventory().setItem(Language.settings$gadget$slot - 1, null);
    }
  }
  
  public void save() {
    PetSettings.saveEntries(this);
    CompanionNames.saveNames(this);
  }
  
  public void destroy() {
    this.disable();
    this.name = null;
    this.profile = null;
    this.petSettings.values().forEach(list -> list.forEach(PetSettingsEntry::destroy));
    this.petSettings.clear();
    this.petSettings = null;
    this.companionNames.clear();
    this.companionNames = null;
  }
  
  public Profile getProfile() {
    return this.profile;
  }
  
  public Companion getSelectedCompanion() {
    return this.selectedCompanion;
  }
  
  public Pet getSelectedPet() {
    return this.selectedPet;
  }
  
  public GadgetsCosmetic getGadget() {
    return this.gadget;
  }
  
  public Map<PetType, List<PetSettingsEntry>> getPetSettings() {
    return this.petSettings;
  }
  
  public List<PetSettingsEntry> getPetSettings(PetType type) {
    return this.petSettings.get(type);
  }
  
  public Map<CompanionNames, String> getCompanionNames() {
    return this.companionNames;
  }
  
  public CosmeticsContainer getCosmeticsContainer() {
    return this.profile.getAbstractContainer("zCosmeticos", "cosmetics", CosmeticsContainer.class);
  }
  
  public SelectedContainer getSelectedContainer() {
    return this.profile.getAbstractContainer("zCosmeticos", "selected", SelectedContainer.class);
  }
  
  public DataContainer getContainer(String key) {
    return this.profile.getDataContainer("zCosmeticos", key);
  }
  
  public String getName() {
    return this.name;
  }
  
  public Player getPlayer() {
    return this.profile.getPlayer();
  }
  
  public boolean isEnabled() {
    return this.enabled;
  }
}
