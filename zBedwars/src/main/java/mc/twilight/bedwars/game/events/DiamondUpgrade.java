package mc.twilight.bedwars.game.events;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsEvent;
import mc.twilight.bedwars.game.generators.Generator;
import mc.twilight.core.utils.StringUtils;

public class DiamondUpgrade extends BedWarsEvent {
  
  @Override
  public void execute(BedWars game) {
    Generator diamond = game.listGenerators().stream().filter(collect -> collect.getType().equals(Generator.Type.DIAMOND)).findAny().orElse(null);
    
    game.listGenerators().stream().filter(collect -> collect.getType().equals(Generator.Type.DIAMOND)).forEach(Generator::upgrade);
    game.listPlayers(false).forEach(player -> player.sendMessage(Language.ingame$broadcast$generator_upgrade$diamond.replace("{tier}",
        StringUtils.repeat("I", diamond == null ? 1 : diamond.getTier()))));
  }
  
  @Override
  public String getName() {
    return Language.options$events$diamond;
  }
}
