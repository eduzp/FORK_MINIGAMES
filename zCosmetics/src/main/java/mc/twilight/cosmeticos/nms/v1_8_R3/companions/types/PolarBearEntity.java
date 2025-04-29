package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

public class PolarBearEntity extends CompanionSlime {
  
  public PolarBearEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure front_Left = this.buildPart("front_Left", new RelativeLocation(-0.7, 0.3, 0.0), true);
    front_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly10 = this.buildPart("middle_Belly10", new RelativeLocation(-0.25, 0.25, -0.55), false);
    middle_Belly10.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure front_Rightup = this.buildPart("front_Rightup", new RelativeLocation(-0.3, -0.3, 0.0), true);
    front_Rightup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure front_Right = this.buildPart("front_Right", new RelativeLocation(-0.7, -0.3, 0.0), true);
    front_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly12 = this.buildPart("middle_Belly12", new RelativeLocation(-0.25, 0.25, -0.05), false);
    middle_Belly12.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure tail = this.buildPart("tail", new RelativeLocation(0.4, 0.0, -1.4), true);
    tail.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly11 = this.buildPart("middle_Belly11", new RelativeLocation(-0.25, -0.25, -0.55), false);
    middle_Belly11.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure back_Leftup = this.buildPart("back_Leftup", new RelativeLocation(-0.3, 0.3, -1.0), true);
    back_Leftup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure back_Right = this.buildPart("back_Right", new RelativeLocation(-0.7, -0.3, -1.0), true);
    back_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-0.375, 0.0, 0.4), false);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQ2ZDIzZjA0ODQ2MzY5ZmEyYTM3MDJjMTBmNzU5MTAxYWY3YmZlODQxOTk2NjQyOTUzM2NkODFhMTFkMmIifX19"));
    
    CompanionStructure middle_Belly = this.buildPart("middle_Belly", new RelativeLocation(-0.65, 0.25, -1.0), false);
    middle_Belly.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly3 = this.buildPart("middle_Belly3", new RelativeLocation(-0.65, 0.25, -0.55), false);
    middle_Belly3.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly2 = this.buildPart("middle_Belly2", new RelativeLocation(-0.65, -0.25, -1.0), false);
    middle_Belly2.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly7 = this.buildPart("middle_Belly7", new RelativeLocation(-0.25, -0.25, -0.05), false);
    middle_Belly7.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly6 = this.buildPart("middle_Belly6", new RelativeLocation(-0.65, -0.25, -0.05), false);
    middle_Belly6.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly5 = this.buildPart("middle_Belly5", new RelativeLocation(-0.65, 0.25, -0.05), false);
    middle_Belly5.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly4 = this.buildPart("middle_Belly4", new RelativeLocation(-0.65, -0.25, -0.55), false);
    middle_Belly4.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure back_Left = this.buildPart("back_Left", new RelativeLocation(-0.7, 0.3, -1.0), true);
    back_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure back_Rightup = this.buildPart("back_Rightup", new RelativeLocation(-0.3, -0.3, -1.0), true);
    back_Rightup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly9 = this.buildPart("middle_Belly9", new RelativeLocation(-0.25, -0.25, -1.0), false);
    middle_Belly9.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure front_Leftup = this.buildPart("front_Leftup", new RelativeLocation(-0.3, 0.3, 0.0), true);
    front_Leftup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    CompanionStructure middle_Belly8 = this.buildPart("middle_Belly8", new RelativeLocation(-0.25, 0.25, -1.0), false);
    middle_Belly8.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQxMDk0MmE4YjY4NTI0Mzg2MDg0MzhhNGMzODI4MzY5ZWU2YzAxY2VjMzMzMzliYzViMjVjOWRkZjU4ZTkifX19"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(9).getStand())).setCompanionName(name);
  }
}
