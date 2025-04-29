package mc.twilight.skywars.lobby.leaderboards;

import mc.twilight.core.database.Database;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.exception.ProfileLoadException;
import mc.twilight.skywars.Language;
import mc.twilight.skywars.lobby.Leaderboard;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Location;

import java.util.*;

public class WinsLeaderboard extends Leaderboard {
  
  public WinsLeaderboard(Location location, String id) {
    super(location, id);
  }
  
  @Override
  public String getType() {
    return "vitorias";
  }
  
  @Override
  public List<String[]> getSplitted() {
    List<String[]> list = Database.getInstance().getLeaderBoard("zCoreSkyWars", (canSeeMonthly() ?
        Collections.singletonList("monthlywins") : Arrays.asList("1v1wins", "2v2wins", "rankedwins")).toArray(new String[0]));
 
    
    while (list.size() < 10) {
      list.add(new String[]{Language.lobby$leaderboard$empty, "0"});
    }
    return list;
  }
  
  @Override
  public List<String> getHologramLines() {
    return Language.lobby$leaderboard$wins$hologram;
  }
}
