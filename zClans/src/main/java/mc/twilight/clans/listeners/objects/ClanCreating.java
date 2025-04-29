package mc.twilight.clans.listeners.objects;

import java.util.ArrayList;
import java.util.List;

public class ClanCreating {
  private static final List<ClanCreating> CREATING_CLAN = new ArrayList<>();
  
  private final String playerName;
  
  private String name = null;
  
  private String tag = null;
  
  public static boolean isCreating(String playerName) {
    return (findByName(playerName) != null);
  }
  
  public static ClanCreating findByName(String playerName) {
    return CREATING_CLAN.stream().filter(clanCreating -> clanCreating.getPlayerName().equals(playerName)).findFirst().orElse(null);
  }
  
  public static void createClan(String playerName) {
    ClanCreating clanCreating = new ClanCreating(playerName);
    CREATING_CLAN.add(clanCreating);
  }
  
  public static void deleteClan(String playerName) {
    ClanCreating clanCreating = findByName(playerName);
    CREATING_CLAN.remove(clanCreating);
  }
  
  public ClanCreating(String playerName) {
    this.playerName = playerName;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setTag(String tag) {
    this.tag = tag;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getPlayerName() {
    return this.playerName;
  }
  
  public String getTag() {
    return this.tag;
  }
}
