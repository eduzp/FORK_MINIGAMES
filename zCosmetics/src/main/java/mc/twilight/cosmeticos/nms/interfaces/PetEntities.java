package mc.twilight.cosmeticos.nms.interfaces;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.core.plugin.logger.KLogger;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class PetEntities {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("PET_ENTITIES");
  private static final Map<EntityType, Class<? extends PetController>> TYPES = new HashMap<>();
  
  public static void setPetEntityForType(EntityType type, Class<? extends PetController> controller) {
    TYPES.put(type, controller);
  }
  
  public static PetController createForType(EntityType type) {
    Class<? extends PetController> entityClass = TYPES.get(type);
    if (entityClass == null) {
      throw new IllegalArgumentException("Tipo de entidade desconhecido: " + type.name());
    }
    
    try {
      return entityClass.newInstance();
    } catch (Exception ex) {
      LOGGER.log(Level.WARNING, "createForType(" + type.name() + "): ", ex);
    }
    
    return null;
  }
  
  public static boolean existsForType(EntityType type) {
    return TYPES.containsKey(type);
  }
}
