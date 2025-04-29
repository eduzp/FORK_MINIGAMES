package mc.twilight.cosmeticos.nms.interfaces;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.nms.interfaces.companions.CompanionEntity;
import mc.twilight.core.plugin.logger.KLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CompanionEntities {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("COMPANION_ENTITIES");
  private static final Map<Class<? extends CompanionCosmetic>, Class<? extends CompanionEntity>> TYPES = new HashMap<>();
  
  public static void setCompanionType(Class<? extends CompanionCosmetic> type, Class<? extends CompanionEntity> entity) {
    TYPES.put(type, entity);
  }
  
  public static CompanionEntity createForType(Class<? extends CompanionCosmetic> clazz, Companion companion) {
    Class<? extends CompanionEntity> entityClass = TYPES.get(clazz);
    if (entityClass == null) {
      throw new IllegalArgumentException("Tipo de companion desconhecido: " + clazz.getSimpleName());
    }
    
    try {
      return (CompanionEntity) entityClass.getConstructors()[0].newInstance(companion);
    } catch (Exception ex) {
      LOGGER.log(Level.WARNING, "createForType(" + clazz.getSimpleName() + "): ", ex);
    }
    
    return null;
  }
  
  public static boolean existsForType(Class<? extends CompanionEntity> type) {
    return TYPES.containsKey(type);
  }
}
