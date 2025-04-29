package mc.twilight.clans.clan;

public class ClanUser {
  private final String name;
  
  private final long joined;
  
  public ClanUser(String name, long joined) {
    this.name = name;
    this.joined = joined;
  }
  
  public void update() {}
  
  public String getName() {
    return this.name;
  }
  
  public long getJoined() {
    return this.joined;
  }
}
