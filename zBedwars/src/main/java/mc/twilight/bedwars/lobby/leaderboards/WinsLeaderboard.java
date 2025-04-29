package mc.twilight.bedwars.lobby.leaderboards;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.lobby.Leaderboard;
import mc.twilight.core.database.Database;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.exception.ProfileLoadException;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Location;

import java.util.*;

public class WinsLeaderboard extends Leaderboard {
  
  public WinsLeaderboard(Location location, String id) {
    super(location, id);
  }
  
  @Override
  public List<String> getHologramLines() {
    return Language.lobby$leaderboard$wins$hologram;
  }
  
  @Override
  public List<String[]> getSplitted() {
    List<String[]> list = Database.getInstance().getLeaderBoard("zCoreBedWars", (this.canSeeMonthly() ?
        Collections.singletonList("monthlywins") : Arrays.asList("1v1wins", "2v2wins", "4v4wins")).toArray(new String[0]));
    while (list.size() < 10) {
      list.add(new String[]{Language.lobby$leaderboard$empty, "0"});
    }
    return list;
  }
  
  
  @Override
  public String getType() {
    return "vitorias";
  }
}