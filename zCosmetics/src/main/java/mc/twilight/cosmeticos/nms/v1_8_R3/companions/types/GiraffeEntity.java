package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

public class GiraffeEntity extends CompanionSlime {
  
  public GiraffeEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure front_Left = this.buildPart("front_Left", new RelativeLocation(-0.7, 0.3, 0.0), true);
    front_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure front_Rightup = this.buildPart("front_Rightup", new RelativeLocation(-0.3, -0.3, 0.0), true);
    front_Rightup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure front_Right = this.buildPart("front_Right", new RelativeLocation(-0.7, -0.3, 0.0), true);
    front_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure neck = this.buildPart("neck", new RelativeLocation(0.6, 0.0, 0.0), true);
    neck.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure back_Leftup = this.buildPart("back_Leftup", new RelativeLocation(-0.3, 0.3, -1.0), true);
    back_Leftup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure neck2 = this.buildPart("neck2", new RelativeLocation(1.0, 0.0, 0.0), true);
    neck2.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure back_Right = this.buildPart("back_Right", new RelativeLocation(-0.7, -0.3, -1.0), true);
    back_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(0.6, 0.0, 0.245), false);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZkZDdiYmNlYWMxMzQ2NDE4Njg0YjFmYTgzMzc3ZDU3OTZjZjMxNTRkYjI5OWYyZDk5OTFiOTZlM2MzZDk5In19fQ=="));
    
    CompanionStructure middle_Belly = this.buildPart("middle_Belly", new RelativeLocation(-0.65, 0.25, -1.0), false);
    middle_Belly.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure middle_Belly3 = this.buildPart("middle_Belly3", new RelativeLocation(-0.65, 0.25, -0.55), false);
    middle_Belly3.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure middle_Belly2 = this.buildPart("middle_Belly2", new RelativeLocation(-0.65, -0.25, -1.0), false);
    middle_Belly2.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure middle_Belly6 = this.buildPart("middle_Belly6", new RelativeLocation(-0.65, -0.25, -0.05), false);
    middle_Belly6.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure middle_Belly5 = this.buildPart("middle_Belly5", new RelativeLocation(-0.65, 0.25, -0.05), false);
    middle_Belly5.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure middle_Belly4 = this.buildPart("middle_Belly4", new RelativeLocation(-0.65, -0.25, -0.55), false);
    middle_Belly4.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure back_Left = this.buildPart("back_Left", new RelativeLocation(-0.7, 0.3, -1.0), true);
    back_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure back_Rightup = this.buildPart("back_Rightup", new RelativeLocation(-0.3, -0.3, -1.0), true);
    back_Rightup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    CompanionStructure front_Leftup = this.buildPart("front_Leftup", new RelativeLocation(-0.3, 0.3, 0.0), true);
    front_Leftup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNjQ1NWMzZGQxMzQzN2Q4ODM0MTBiZDJlNzliMzRlNmRhNjY1ZTRlN2U2NjhlZjBhZjIyOWZiNWE5NjAifX19"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(7).getStand())).setCompanionName(name);
  }
}
