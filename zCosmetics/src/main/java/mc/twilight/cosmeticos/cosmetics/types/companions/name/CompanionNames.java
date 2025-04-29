package mc.twilight.cosmeticos.cosmetics.types.companions.name;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.variants.*;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.database.data.DataContainer;
import org.json.simple.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings({"unchecked"})
public enum CompanionNames {
  HP8(HP8Companion.class, "&6HP-8"),
  DUCK(DuckCompanion.class, "&6Pato"),
  PANDA(PandaCompanion.class, "&6Panda"),
  GORILLA(GorillaCompanion.class, "&6Gorila"),
  GIRAFFE(GiraffeCompanion.class, "&6Girafa"),
  R3D3(R3D3Companion.class, "&6R3D3"),
  PUG(PugCompanion.class, "&6Pug"),
  PENGUIN(PenguinCompanion.class, "&6Pinguim"),
  FIREDRAGON(FireDragonCompanion.class, "&6Dragão de Fogo"),
  ICEDRAGON(IceDragonCompanion.class, "&6Dragão de Gelo"),
  MYTHICDRAGON(MythicDragonCompanion.class, "&6Dragão Mítico"),
  MAGICDRAGON(MagicDragonCompanion.class, "&6Dragão Mágico"),
  POLARBEAR(PolarBearCompanion.class, "&6Urso Polar"),
  DIGLET(DigletCompanion.class, "&6Diglet"),
  KOALA(KoalaCompanion.class, "&6Coala"),
  LION(LionCompanion.class, "&6Leão"),
  MINIME(MiniMeCompanion.class, "&6Mini {player}"),
  PERRYTHEPLATIPUS(PerryThePlatipusCompanion.class, "&6Perry o Ornitorrinco"),
  TURTLE(TurtleCompanion.class, "&6Tartaruga"),
  CHIMP(ChimpCompanion.class, "&6Chimpanzé");
  
  private static final CompanionNames[] VALUES = values();
  private final Class<? extends CompanionCosmetic> type;
  private final String defaultName;
  
  CompanionNames(Class<? extends CompanionCosmetic> type, String defaultName) {
    this.type = type;
    this.defaultName = defaultName;
  }
  
  public static void setupCompanions() {
    for (CompanionNames name : VALUES) {
      try {
        name.type.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void saveNames(CUser user) {
    DataContainer container = user.getContainer("companionsNames");
    JSONObject companionNames = container.getAsJsonObject();
    for (Entry<CompanionNames, String> entry : user.getCompanionNames().entrySet()) {
      companionNames.put(entry.getKey().name().toLowerCase(), entry.getValue());
    }
    
    container.set(companionNames.toString());
    companionNames.clear();
  }
  
  public static Map<CompanionNames, String> loadNames(CUser user) {
    Map<CompanionNames, String> names = new LinkedHashMap<>();
    for (CompanionNames cName : VALUES) {
      DataContainer container = user.getContainer("companionsNames");
      JSONObject companionNames = container.getAsJsonObject();
      if (!companionNames.containsKey(cName.name().toLowerCase())) {
        companionNames.put(cName.name().toLowerCase(), cName.defaultName.replace("{player}", user.getName()));
        container.set(companionNames.toString());
      }
      
      names.put(cName, companionNames.get(cName.name().toLowerCase()).toString());
      companionNames.clear();
    }
    
    return names;
  }
  
  public static CompanionNames fromType(Class<? extends CompanionCosmetic> type) {
    for (CompanionNames name : VALUES) {
      if (name.type.equals(type)) {
        return name;
      }
    }
    
    return null;
  }
}
