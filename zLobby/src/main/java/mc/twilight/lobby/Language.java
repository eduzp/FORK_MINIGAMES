package mc.twilight.lobby;

import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.config.KWriter;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("rawtypes")
public class Language {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger())
      .getModule("LANGUAGE");
  private static final KConfig CONFIG = Main.getInstance().getConfig("language");
  public static long scoreboards$scroller$every_tick = 4;
  public static List<String> scoreboards$scroller$titles = Arrays
      .asList(    // >>> Normalmente (ida)
              "§a§lLOBBY",
              "§f§l§6§lL§a§lOBBY",
              "§f§lL§6§lO§a§lBBY",
              "§f§lLO§6§lB§a§lBY",
              "§f§lLOB§6§lB§a§lY",
              "§f§lLOBB§6§lY",
              "§6§lL§e§lO§a§lB§b§lB§d§lY",
              "§b§lL§a§lO§e§lB§6§lB§f§lY",
              "§d§lL§b§lO§a§lB§e§lB§6§lY",
              "§6§lL§d§lO§b§lB§a§lB§e§lY",
              "§e§lL§a§lO§b§lB§d§lB§6§lY",
              "§b§lL§d§lO§6§lB§a§lB§f§lY",
              "§f§lL§6§lO§e§lB§a§lB§b§lY",
              "§a§lLOBBY",

              // <<< Voltando (inverso)
              "§f§lL§6§lO§e§lB§a§lB§b§lY",
              "§b§lL§d§lO§6§lB§a§lB§f§lY",
              "§e§lL§a§lO§b§lB§d§lB§6§lY",
              "§6§lL§d§lO§b§lB§a§lB§e§lY",
              "§d§lL§b§lO§a§lB§e§lB§6§lY",
              "§b§lL§a§lO§e§lB§6§lB§f§lY",
              "§6§lL§e§lO§a§lB§b§lB§d§lY",
              "§f§lLOBB§6§lY",
              "§f§lLOB§6§lB§a§lY",
              "§f§lLO§6§lB§a§lBY",
              "§f§lL§6§lO§a§lBBY",
              "§f§l§6§lL§a§lOBBY",
              "§a§lLOBBY",

              // >>> Fechando (pontas pra dentro)
              "§c§lL§a§lOBB§c§lY",
              "§c§lLO§a§lBB§c§lY",
              "§c§lLOB§a§lB§c§lY",
              "§c§lLOBB§c§lY",
              "§c§lLOBBY",

              // >>> Abrindo (centro para as pontas)
              "§c§lLOBBY",
              "§c§lLOBB§a§lY",
              "§c§lLOB§a§lB§c§lY",
              "§c§lLO§a§lBB§c§lY",
              "§c§lL§a§lOBB§c§lY",
              "§a§lLOBBY");
  public static List<String> scoreboards$lobby = Arrays
      .asList("", "  Cargo: §a%zCore_role%", "  Tokens: §b%zCore_cash%", "", "  Jogadores: §a%zCore_online%",
          "", "  §7twilightmc.com.br", "");
  public static String chat$delay = "§cEspere mais {time}s para falar novamente.";
  public static String chat$color$default = "§7";
  public static String chat$color$custom = "§f";
  public static String chat$format$lobby = "{player}{color}: {message}";
  public static String lobby$broadcast = "{player} §dpulou para o servidor!";
  public static boolean lobby$tab$enabled = true;
  public static String lobby$tab$header = " \n§b§lTWILIGHT\n  §ftwilightmc.com.br\n ";
  public static String lobby$tab$footer =
      " \n \n§aForúm: §ftwilightmc.com/forum\n§aDiscord: §fdc.gg/twilightsv\n \n                                          §bAdquira VIP acessando nosso discord                                          \n ";
  public static String lobby$npc$deliveries$deliveries = "§c{deliveries} Entrega{s}";
  public static List<String> lobby$npc$deliveries$hologram = Arrays
      .asList("{deliveries}", "§bEntregador", "§e§lCLIQUE DIREITO");
  public static String lobby$npc$deliveries$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1ODM0NTc4OTkzMTksInByb2ZpbGVJZCI6IjIxMWNhN2E4ZWFkYzQ5ZTVhYjBhZjMzMTBlODY0M2NjIiwicHJvZmlsZU5hbWUiOiJNYXh0ZWVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MWU0NTc3OTgzZjEzZGI2YTRiMWMwNzQ1MGUyNzQ2MTVkMDMyOGUyNmI0MGQ3ZDMyMjA3MjYwOWJmZGQ0YTA4IiwibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn19fX0=";
  public static String lobby$npc$deliveries$skin$signature =
      "SXnMF3f9x90fa+FdP2rLk/V6/zvMNuZ0sC4RQpPHF9JxdVWYRZm/+DhxkfjCHWKXV/4FSTN8LPPsxXd0XlYSElpi5OaT9/LGhITSK6BbeBfaYhLZnoD0cf9jG9nl9av38KipnkNXI+cO3wttB27J7KHznAmfrJd5bxdO/M0aGQYtwpckchYUBG6pDzaxN7tr4bFxDdxGit8Tx+aow/YtYSQn4VilBIy2y/c2a4PzWEpWyZQ94ypF5ZojvhaSPVl88Fbh+StdgfJUWNN3hNWt31P68KT4Jhx+SkT2LTuDj0jcYsiuxHP6AzZXtOtPPARqM0/xd53CUHCK+TEF5mkbJsG/PZYz/JRR1B1STk4D2cgbhunF87V4NLmCBtF5WDQYid11eO0OnROSUbFduCLj0uJ6QhNRRdhSh54oES7vTi0ja3DftTjdFhPovDAXQxCn+ROhTeSxjW5ZvP6MpmJERCSSihv/11VGIrVRfj2lo9MaxRogQE3tnyMNKWm71IRZQf806hwSgHp+5m2mhfnjYeGRZr44j21zqnSKudDHErPyEavLF83ojuMhNqTTO43ri3MVbMGix4TbIOgB2WDwqlcYLezENBIIkRsYO/Y1r5BWCA7DJ5IlpxIr9TCu39ppVmOGReDWA/Znyox5GP6JIM53kQoTOFBM3QWIQcmXll4=";
  
  public static void setupLanguage() {
    boolean save = false;
    KWriter writer = Main.getInstance().getWriter(CONFIG.getFile(),
        "Plugin de gestão de lobby feito por eduzp.\nVersão: " + Main.getInstance()
            .getDescription().getVersion());
    for (Field field : Language.class.getDeclaredFields()) {
      if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
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
              
              l = null;
              value = list;
            }
            
            field.set(null, value);
            writer.set(nativeName, new KWriter.YamlEntry(new Object[]{"", CONFIG.get(nativeName)}));
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
              
              l = null;
              value = list;
            }
            
            save = true;
            writer.set(nativeName, new KWriter.YamlEntry(new Object[]{"", value}));
          }
        } catch (ReflectiveOperationException e) {
          LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
        }
      }
    }
    
    if (save) {
      writer.write();
      CONFIG.reload();
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
          () -> LOGGER.info("§bO idioma do plugin foi alterado."));
    }
  }
}
