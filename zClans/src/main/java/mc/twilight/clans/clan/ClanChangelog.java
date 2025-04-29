package mc.twilight.clans.clan;

import java.util.ArrayList;
import java.util.List;

public class ClanChangelog {
  private final String message;
  
  private final long created;
  
  public ClanChangelog(String message, long created) {
    this.message = message;
    this.created = created;
  }
  
  public static List<ClanChangelog> parse(String saved) {
    if (saved.isEmpty())
      return new ArrayList<>(); 
    List<ClanChangelog> list = new ArrayList<>();
    for (String changelog : saved.split(" ,:, "))
      list.add(new ClanChangelog(changelog.split(" ,;,")[0], Long.parseLong(changelog.split(" ,;,")[1]))); 
    return list;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public long getCreated() {
    return this.created;
  }
  
  public String toString() {
    return this.message + " ,;, " + this.created;
  }
}
