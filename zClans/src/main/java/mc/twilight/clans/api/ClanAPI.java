package mc.twilight.clans.api;

import mc.twilight.clans.clan.Clan;
import java.util.List;
import org.bukkit.entity.Player;

public class ClanAPI {
  public static Clan getClanByName(String name) {
    return Clan.getByName(name);
  }
  
  public static Clan getClanByPlayerName(String player) {
    return Clan.getByUserName(player);
  }
  
  public static Clan getClanByPlayer(Player player) {
    return Clan.getClan(player);
  }
  
  public static void addCoins(Clan clan, long amount) {
    clan.addCoins(Math.toIntExact(amount));
  }
  
  public static void removeCoins(Clan clan, long amount) {
    clan.removeCoins(Math.toIntExact(amount));
  }
  
  public static String getTagged(Clan clan, boolean name) {
    return name ? clan.getName() : clan.getTag();
  }
  
  public static boolean hasTaggedColor(Clan clan, boolean upgraded) {
    if (upgraded)
      return clan.tagPermissionPlus; 
    return clan.tagPermission;
  }
  
  public static List<Clan> listClans() {
    return Clan.listClans();
  }
}
