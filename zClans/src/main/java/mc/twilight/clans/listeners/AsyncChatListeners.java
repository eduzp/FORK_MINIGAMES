package mc.twilight.clans.listeners;

import mc.twilight.clans.clan.Clan;
import mc.twilight.clans.listeners.objects.ClanCreating;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListeners implements Listener {
  @EventHandler
  public void onAsyncChatHandler(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();
    String message = event.getMessage().split(" ")[0];
    ClanCreating clanCreating = ClanCreating.findByName(player.getName());
    TextComponent nameCreating = new TextComponent("\n§aQual será o nome do seu clan?\n§7Responda aqui no chat ou clique ");
    TextComponent tagCreating = new TextComponent("\n§aQual será a tag do seu clan?\n§7Responda aqui no chat ou clique ");
    TextComponent cancelar = new TextComponent(" §7para cancelar!\n \n");
    TextComponent component = new TextComponent();
    component.setText("AQUI");
    component.setColor(ChatColor.YELLOW);
    component.setBold(Boolean.valueOf(true));
    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "NAO"));
    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aClique aqui para cancelar!")));
    nameCreating.addExtra((BaseComponent)component);
    nameCreating.addExtra((BaseComponent)cancelar);
    tagCreating.addExtra((BaseComponent)component);
    tagCreating.addExtra((BaseComponent)cancelar);
    if (ClanCreating.isCreating(player.getName())) {
      event.setCancelled(true);
      if (message.equalsIgnoreCase("NAO")) {
        player.sendMessage("§aVocê cancelou a criação de clans com sucesso!");
        ClanCreating.deleteClan(player.getName());
        return;
      } 
      String name = clanCreating.getName();
      String tag = clanCreating.getTag();
      if (!message.equals("Creating"))
        if (name == null) {
          name = message;
          if (Clan.getByName(name) != null) {
            player.sendMessage("§cJá existe um clan com esse nome!");
            return;
          } 
          ClanCreating.findByName(player.getName()).setName(name);
        } else if (tag == null) {
          tag = message;
          if (Clan.getByName(tag) != null) {
            player.sendMessage("§cJá existe um clan com essa tag!");
            return;
          } 
          if (message.length() > 5) {
            player.sendMessage("§cPara setar uma tag, é necessário ter apenas 5 caracteres.");
            return;
          } 
          Clan.createClan(player, tag, message);
        }  
      if (name == null) {
        player.spigot().sendMessage((BaseComponent)nameCreating);
        return;
      } 
      if (tag == null)
        player.spigot().sendMessage((BaseComponent)tagCreating); 
    } 
  }
}
