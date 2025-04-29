package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class PugEntity extends CompanionSlime {
  
  public PugEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-1.0, 0.0, 0.245), false);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdiNGY4NGUxOWI1MmYzMTIxNzcxMmU3YmE5ZjUxZDU2ZGE1OWQyNDQ1YjRkN2YzOWVmNmMzMjNiODE2NiJ9fX0="));
    
    CompanionStructure front_Left = this.buildPart("front_Left", new RelativeLocation(-0.7, 0.3, 0.0), true);
    front_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MyNDM3OWMyYThmOWFjNWQwODYyZmZkNTllYWFmZGUwNzgwODc5OWZiZWRkMThlZGIzZjE1MzM4ZWU2N2UifX19"));
    
    CompanionStructure middle_Belly = this.buildPart("middle_Belly", new RelativeLocation(-0.5, 0.1, -0.688), true);
    middle_Belly.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ5YjQ4ZGE4ZWQyNTQ1ZmMxMzE1ZjBkMGI1ZjdhNDk0NzRlMGM0OWNlYTBmYTE5NTdiYmU2NWExZTU3ZTkifX19"));
    
    CompanionStructure front_Right = this.buildPart("front_Right", new RelativeLocation(-0.7, -0.3, 0.0), true);
    front_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MyNDM3OWMyYThmOWFjNWQwODYyZmZkNTllYWFmZGUwNzgwODc5OWZiZWRkMThlZGIzZjE1MzM4ZWU2N2UifX19"));
    
    CompanionStructure middle_Belly3 = this.buildPart("middle_Belly3", new RelativeLocation(-0.5, 0.1, -0.288), true);
    middle_Belly3.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ5YjQ4ZGE4ZWQyNTQ1ZmMxMzE1ZjBkMGI1ZjdhNDk0NzRlMGM0OWNlYTBmYTE5NTdiYmU2NWExZTU3ZTkifX19"));
    
    CompanionStructure middle_Belly2 = this.buildPart("middle_Belly2", new RelativeLocation(-0.5, -0.1, -0.688), true);
    middle_Belly2.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ5YjQ4ZGE4ZWQyNTQ1ZmMxMzE1ZjBkMGI1ZjdhNDk0NzRlMGM0OWNlYTBmYTE5NTdiYmU2NWExZTU3ZTkifX19"));
    
    CompanionStructure tail = this.buildPart("tail", new RelativeLocation(-0.4, -0.05, -1.15), true);
    tail.getStand().setRightArmPose(EulerAngle(183, 500, 13));
    tail.getStand().setItemInHand(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ5YjQ4ZGE4ZWQyNTQ1ZmMxMzE1ZjBkMGI1ZjdhNDk0NzRlMGM0OWNlYTBmYTE5NTdiYmU2NWExZTU3ZTkifX19"));
    
    CompanionStructure middle_Belly4 = this.buildPart("middle_Belly4", new RelativeLocation(-0.5, -0.1, -0.288), true);
    middle_Belly4.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ5YjQ4ZGE4ZWQyNTQ1ZmMxMzE1ZjBkMGI1ZjdhNDk0NzRlMGM0OWNlYTBmYTE5NTdiYmU2NWExZTU3ZTkifX19"));
    
    CompanionStructure back_Left = this.buildPart("back_Left", new RelativeLocation(-0.7, 0.3, -1.0), true);
    back_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MyNDM3OWMyYThmOWFjNWQwODYyZmZkNTllYWFmZGUwNzgwODc5OWZiZWRkMThlZGIzZjE1MzM4ZWU2N2UifX19"));
    
    CompanionStructure back_Right = this.buildPart("back_Right", new RelativeLocation(-0.7, -0.3, -1.0), true);
    back_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MyNDM3OWMyYThmOWFjNWQwODYyZmZkNTllYWFmZGUwNzgwODc5OWZiZWRkMThlZGIzZjE1MzM4ZWU2N2UifX19"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
