package mc.twilight.core.database.data.container;

import mc.twilight.core.achievements.Achievement;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.interfaces.AbstractContainer;
import org.json.simple.JSONArray;

@SuppressWarnings("unchecked")
public class AchievementsContainer extends AbstractContainer {
  
  public AchievementsContainer(DataContainer dataContainer) {
    super(dataContainer);
  }
  
  public void complete(Achievement achievement) {
    JSONArray achievements = this.dataContainer.getAsJsonArray();
    achievements.add(achievement.getId());
    this.dataContainer.set(achievements.toString());
    achievements.clear();
  }
  
  public boolean isCompleted(Achievement achievement) {
    return this.dataContainer.getAsJsonArray().contains(achievement.getId());
  }
}
