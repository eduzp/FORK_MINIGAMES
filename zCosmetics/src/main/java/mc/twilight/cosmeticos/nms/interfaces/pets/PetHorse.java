package mc.twilight.cosmeticos.nms.interfaces.pets;

import mc.twilight.cosmeticos.utils.enums.MHorseColor;
import mc.twilight.cosmeticos.utils.enums.MHorseType;

public interface PetHorse {
  
  MHorseColor getColor();
  
  void setColor(MHorseColor color);
  
  MHorseType getType();
  
  void setType(MHorseType type);
}
