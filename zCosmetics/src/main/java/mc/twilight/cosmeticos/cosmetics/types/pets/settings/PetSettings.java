package mc.twilight.cosmeticos.cosmetics.types.pets.settings;

import mc.twilight.cosmeticos.cosmetics.types.pets.PetType;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.database.data.DataContainer;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

@SuppressWarnings({"unchecked"})
public enum PetSettings {
  ENDERMAN(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aEnderman")}),
  ENDERMITE(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aEndermite")}),
  // Baby | Cogu
  COW(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aVaca"), new PetSettingsEntry("baby", false), new PetSettingsEntry("mushroom", false)}),
  SILVERFISH(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aTraça")}),
  // Baby
  CHICKEN(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aGalinha"), new PetSettingsEntry("baby", false)}),
  // Wither
  SKELETON(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aEsqueleto"), new PetSettingsEntry("wither", false)}),
  IRON_GOLEM(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aGolem de Ferro")}),
  // Baby
  SHEEP(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aOvelha"), new PetSettingsEntry("baby", false), new PetSettingsEntry("sheepcolor", 0), new PetSettingsEntry("rainbow", false)}),
  // Baby | Dye Colors
  WOLF(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aLobo"), new PetSettingsEntry("baby", false), new PetSettingsEntry("dyecolor", 14)}),
  // Baby
  PIG(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aPorco"), new PetSettingsEntry("baby", false)}),
  // Baby | Brown, White, Black, Black and White, Gold, Salt and Pepper, The Killer Bunny
  RABBIT(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aCoelho"), new PetSettingsEntry("baby", false), new PetSettingsEntry("rabbit", 0)}),
  // Baby | Farmer, Librarian, Priest, Blacksmith, Butcher
  VILLAGER(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aAldeão"), new PetSettingsEntry("baby", false), new PetSettingsEntry("profession", 0)}),
  // Spider, Cave Spider
  SPIDER(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aAranha"), new PetSettingsEntry("cave", false)}),
  // Small, Medium, Big | Magma Cube
  SLIME(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aSlime"), new PetSettingsEntry("slimesize", 2), new PetSettingsEntry("slimemagma", false)}),
  // Charged
  CREEPER(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aCreeper"), new PetSettingsEntry("charged", false)}),
  // Baby
  ZOMBIE(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aZumbi"), new PetSettingsEntry("baby", false), new PetSettingsEntry("villager", false)}),
  SQUID(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aLula")}),
  // Baby | White, Creamy, Chestnut, Brown, Black, Gray, Dark Brown | Horse, Donkey, Mule, Undead Horse, Skeleton Horse
  HORSE(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aCavalo"), new PetSettingsEntry("baby", false), new PetSettingsEntry("horsecolor", 0), new PetSettingsEntry("horsetype", 0)}),
  // Baby | Wild, Black, Red, Siamese
  OCELOT(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aGato"), new PetSettingsEntry("baby", false), new PetSettingsEntry("ocelot", 0)}),
  BLAZE(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aBlaze")}),
  BAT(new PetSettingsEntry[]{new PetSettingsEntry("name", "&aMorcego")});
  
  private final PetSettingsEntry[] defaultEntries;
  
  PetSettings(PetSettingsEntry[] entries) {
    this.defaultEntries = entries;
  }
  
  public static void saveEntries(CUser user) {
    DataContainer container = user.getContainer("petSettings");
    JSONObject petSettings = container.getAsJsonObject();
    for (Entry<PetType, List<PetSettingsEntry>> entry : user.getPetSettings().entrySet()) {
      JSONObject settingsMap = new JSONObject();
      for (PetSettingsEntry settingsEntry : entry.getValue()) {
        settingsMap.put(settingsEntry.getKey(), settingsEntry.getValue().get());
      }
      
      petSettings.put(entry.getKey().name().toLowerCase(), settingsMap);
    }
    
    container.set(petSettings.toString());
    petSettings.clear();
  }
  
  public static List<PetSettingsEntry> parseEntriesForType(CUser user, PetType type) {
    List<PetSettingsEntry> entries = new ArrayList<>();
    for (PetSettings settings : values()) {
      if (settings.name().equals(type.name())) {
        DataContainer container = user.getContainer("petSettings");
        JSONObject petSettings = container.getAsJsonObject();
        if (!petSettings.containsKey(type.name().toLowerCase())) {
          petSettings.put(type.name().toLowerCase(), new JSONObject());
        }
        
        JSONObject defaultEntries = (JSONObject) petSettings.get(type.name().toLowerCase());
        for (PetSettingsEntry defaultEntry : settings.getDefaultEntries()) {
          if (!defaultEntries.containsKey(defaultEntry.getKey())) {
            defaultEntries.put(defaultEntry.getKey(), defaultEntry.getValue().get());
          }
        }
        container.set(petSettings.toString());
        
        JSONObject petEntries = (JSONObject) petSettings.get(type.name().toLowerCase());
        for (Object entryObject : petEntries.entrySet()) {
          Entry<String, Object> entry = (Entry<String, Object>) entryObject;
          entries.add(new PetSettingsEntry(entry.getKey(), entry.getValue()));
        }
        
        petSettings.clear();
        defaultEntries.clear();
        petEntries.clear();
        break;
      }
    }
    
    return entries;
  }
  
  public PetSettingsEntry[] getDefaultEntries() {
    return this.defaultEntries;
  }
}
