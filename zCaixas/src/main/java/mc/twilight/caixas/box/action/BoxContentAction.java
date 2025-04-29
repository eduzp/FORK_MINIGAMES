package mc.twilight.caixas.box.action;

import org.bukkit.Bukkit;

public class BoxContentAction {
  
  private String giveCommand;
  private String alreadyCommand;
  
  public BoxContentAction(String giveCommand, String alreadyCommand) {
    this.giveCommand = giveCommand;
    this.alreadyCommand = alreadyCommand;
  }
  
  public void destroy() {
    this.giveCommand = null;
    this.alreadyCommand = null;
  }
  
  public void perform(String looter, boolean alreadyHaves) {
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (alreadyHaves ? this.alreadyCommand : this.giveCommand).replace("/", "").replace("{player}", looter));
  }
}
