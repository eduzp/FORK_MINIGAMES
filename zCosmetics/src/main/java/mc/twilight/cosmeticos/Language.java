package mc.twilight.cosmeticos;

import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.config.KWriter;
import mc.twilight.core.plugin.config.KWriter.YamlEntry;
import mc.twilight.core.plugin.config.KWriter.YamlEntryInfo;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings({"rawtypes"})
public class Language {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("LANGUAGE");
  private static final KConfig CONFIG = Main.getInstance().getConfig("language");
  
  public static List<String> settings$mundos = Collections.singletonList("world");
  public static boolean settings$item$usar = true;
  public static boolean settings$command$cosmetics = true;
  public static int settings$coins$duplicated = 500;
  public static int settings$item$slot = 5;
  public static int settings$gadget$slot = 4;
  public static String settings$item$stack = "CHEST : 1 : nome>&bCosméticos";
  
  public static void setupLanguage() {
    boolean save = false;
    KWriter writer = Main.getInstance().getWriter(CONFIG.getFile(), "Gestão de cosméticos feito por eduzp\nVersão: " + Main.getInstance().getDescription().getVersion());
    for (Field field : Language.class.getDeclaredFields()) {
      if (field.isAnnotationPresent(YamlEntryInfo.class)) {
        YamlEntryInfo entryInfo = field.getAnnotation(YamlEntryInfo.class);
        String nativeName = field.getName().replace("$", ".").replace("_", "-");
        
        try {
          Object value;
          
          if (CONFIG.contains(nativeName)) {
            value = CONFIG.get(nativeName);
            if (value instanceof String) {
              value = StringUtils.formatColors((String) value).replace("\\n", "\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.formatColors((String) v).replace("\\n", "\n"));
                } else {
                  list.add(v);
                }
              }
              
              value = list;
            }
            
            field.set(null, value);
            writer.set(nativeName, new YamlEntry(new Object[]{entryInfo.annotation(), CONFIG.get(nativeName)}));
          } else {
            value = field.get(null);
            if (value instanceof String) {
              value = StringUtils.deformatColors((String) value).replace("\n", "\\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.deformatColors((String) v).replace("\n", "\\n"));
                } else {
                  list.add(v);
                }
              }
              
              value = list;
            }
            
            save = true;
            writer.set(nativeName, new YamlEntry(new Object[]{entryInfo.annotation(), value}));
          }
        } catch (ReflectiveOperationException e) {
          LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
        }
      }
    }
    
    if (save) {
      writer.write();
      CONFIG.reload();
      
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () ->
          LOGGER.info("§bO idioma do plugin foi alterado."));
    }
  }
}
