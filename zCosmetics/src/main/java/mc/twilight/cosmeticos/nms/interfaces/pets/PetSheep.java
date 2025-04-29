package mc.twilight.cosmeticos.nms.interfaces.pets;

import mc.twilight.cosmeticos.utils.enums.MWoolColor;

public interface PetSheep {
  
  void setRainbow(boolean rainbow);
  
  MWoolColor getColor();
  
  void setColor(MWoolColor color);
}
