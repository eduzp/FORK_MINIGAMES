package mc.twilight.cosmeticos.hook;

import mc.twilight.cosmeticos.hook.player.CUser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Users {
  
  private static final Map<String, CUser> USERS = new HashMap<>();
  
  public static CUser loadUser(String name) {
    CUser user = USERS.get(name);
    if (user == null) {
      user = new CUser(name);
      USERS.put(name.toLowerCase(), user);
    }
    
    return user;
  }
  
  public static CUser unloadUser(String name) {
    return USERS.remove(name.toLowerCase());
  }
  
  public static CUser getByName(String name) {
    return USERS.get(name.toLowerCase());
  }
  
  public static Collection<CUser> listUsers() {
    return USERS.values();
  }
}
