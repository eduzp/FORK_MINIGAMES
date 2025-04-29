package mc.twilight.clans.clan;

import com.google.common.collect.ImmutableList;
import mc.twilight.clans.Main;
import mc.twilight.clans.database.Database;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.Validator;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.sql.rowset.CachedRowSet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Clan {
  private static final KLogger LOGGER = ((KLogger)Main.getInstance().getLogger()).getModule("CLANS");
  
  private static final Map<String, Clan> clans = new HashMap<>();
  
  public boolean tagPermission;
  
  public boolean tagPermissionPlus;
  
  private String tag;
  
  private String name;
  
  private String leader;
  
  private int slots;
  
  private int coins;
  
  private final long created;
  
  private List<String> members;
  
  private List<String> officers;
  
  private List<String> invites;
  
  private List<mc.twilight.clans.clan.ClanUser> users;
  
  private List<mc.twilight.clans.clan.ClanChangelog> changelogs;
  
  public Clan(String tag, String name, int slots, String leader, long created, boolean tagPermission, boolean tagPermissionPlus) {
    this.tag = tag;
    this.name = name;
    this.created = created;
    this.leader = leader.split(":")[0];
    this.slots = slots;
    this.tagPermission = tagPermission;
    this.tagPermissionPlus = tagPermissionPlus;
    this.members = new ArrayList<>();
    this.officers = new ArrayList<>();
    this.invites = new ArrayList<>();
    this.users = new ArrayList<>();
    this.users.add(new mc.twilight.clans.clan.ClanUser(this.leader, Long.parseLong(leader.split(":")[1])));
    this.changelogs = new ArrayList<>();
  }
  
  public static void setupClans() {
    try {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `zClans`", new Object[0]);
      if (rs != null) {
        rs.beforeFirst();
        while (rs.next()) {
          String tag = rs.getString("tag");
          String name = rs.getString("name");
          String leader = rs.getString("leader");
          boolean tagPermission = rs.getBoolean("tagPermission");
          boolean tagPermissionPlus = rs.getBoolean("tagPermissionPlus");
          int slots = rs.getInt("slots");
          Clan clan = new Clan(tag, name, slots, leader, rs.getLong("created"), tagPermission, tagPermissionPlus);
          clan.coins = rs.getInt("coins");
          clans.put(tag, clan);
          for (String member : rs.getString("members").split(", ")) {
            if (!member.isEmpty()) {
              clan.members.add(member.split(":")[0]);
              clan.users.add(new mc.twilight.clans.clan.ClanUser(member.split(":")[0], Long.parseLong(member.split(":")[1])));
            } 
          } 
          for (String officer : rs.getString("officers").split(", ")) {
            if (!officer.isEmpty()) {
              clan.officers.add(officer.split(":")[0]);
              clan.users.add(new mc.twilight.clans.clan.ClanUser(officer.split(":")[0], 
                    Long.parseLong(officer.split(":")[1])));
            } 
          } 
          for (String invite : rs.getString("invites").split(", ")) {
            if (!invite.isEmpty())
              clan.getInvites().add(invite); 
          } 
          for (String changelog : rs.getString("changelog").split(" ,:, ")) {
            if (!changelog.isEmpty())
              clan.changelogs.add(new mc.twilight.clans.clan.ClanChangelog(changelog.split(" ,;, ")[0], 
                    Long.parseLong(changelog.split(" ,;, ")[1]))); 
          } 
        } 
      } 
    } catch (SQLException ex) {
      LOGGER.log(Level.WARNING, "Error while load clans: ", ex);
    } 
  }
  
  public static void loadClan(String tag) {
    try {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `zClans` WHERE `tag` = ?", new Object[] { tag });
      if (rs != null) {
        String name = rs.getString("name");
        String leader = rs.getString("leader");
        boolean tagPermission = rs.getBoolean("tagPermission");
        boolean tagPermissionPlus = rs.getBoolean("tagPermissionPlus");
        int slots = rs.getInt("slots");
        Clan clan = new Clan(tag, name, slots, leader, rs.getLong("created"), tagPermission, tagPermissionPlus);
        clan.addCoins(rs.getInt("coins"));
        clans.put(tag, clan);
        for (String member : rs.getString("members").split(", ")) {
          if (!member.isEmpty()) {
            clan.members.add(member.split(":")[0]);
            clan.users.add(new mc.twilight.clans.clan.ClanUser(member.split(":")[0], Long.parseLong(member.split(":")[1])));
          } 
        } 
        for (String officer : rs.getString("officers").split(", ")) {
          if (!officer.isEmpty()) {
            clan.officers.add(officer.split(":")[0]);
            clan.users.add(new mc.twilight.clans.clan.ClanUser(officer.split(":")[0], Long.parseLong(officer.split(":")[1])));
          } 
        } 
        for (String invite : rs.getString("invites").split(", ")) {
          if (!invite.isEmpty())
            clan.getInvites().add(invite); 
        } 
        for (String changelog : rs.getString("changelog").split(" ,:, ")) {
          if (!changelog.isEmpty())
            clan.changelogs.add(new mc.twilight.clans.clan.ClanChangelog(changelog.split(" ,;, ")[0], 
                  Long.parseLong(changelog.split(" ,;, ")[1]))); 
        } 
      } 
    } catch (SQLException ex) {
      LOGGER.log(Level.WARNING, "Error while load clan(" + tag + "): ", ex);
    } 
  }
  
  public static void createClan(Player leader, String tag, String name) {
    tag = tag.toUpperCase();
    Clan clan = getClan(leader);
    if (clan != null) {
      leader.sendMessage("§cVocê já está em um clan.");
      return;
    } 
    if (tag.length() > 5 || tag.length() < 3) {
      leader.sendMessage("§cA tag do clan precisa conter de 3 a 5 caracteres.");
      return;
    } 
    if (name.length() > 16 || name.length() < 4) {
      leader.sendMessage("§cO nome do clan precisa conter de 4 a 16 caracteres.");
      return;
    } 
    if (getByTag(tag) != null) {
      leader.sendMessage("§cUm clan com esta tag já existe.");
      return;
    } 
    if (getByName(name) != null) {
      leader.sendMessage("§cUm clan com este nome já existe.");
      return;
    } 
    if (!Validator.isValidUsername(tag)) {
      leader.sendMessage("§cA tag não pode conter caracteres especiais.");
      return;
    } 
    clan = new Clan(tag, name, 5, leader.getName() + ":" + System.currentTimeMillis() + ":" + System.currentTimeMillis(), System.currentTimeMillis(), false, false);
    clans.put(tag, clan);
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(clan.getCreated())) + "] §r";
    clan.changelogs.add(new mc.twilight.clans.clan.ClanChangelog(date + leader.getName() + " criou o clan.", clan.getCreated()));
    Database.getInstance().execute("INSERT INTO `zClans` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { 
          tag, name, leader
          
          .getName() + ":" + clan.getCreated() + ":" + clan.getCreated(), "", "", "", 
          StringUtils.join(Collections.singletonList(((mc.twilight.clans.clan.ClanChangelog)clan.changelogs.get(0)).toString()), " ,:, "), 
          Long.valueOf(clan.getCreated()), Integer.valueOf(5), Boolean.valueOf(false), 
          Boolean.valueOf(false), Integer.valueOf(0), "", Long.valueOf(clan.getCreated()) });
    leader.sendMessage("§aClan §f[{tag}] {name}§a criado com sucesso.".replace("{tag}", tag).replace("{name}", name));
    Main.sendBungee(leader, "Create", tag, new String[0]);
  }
  
  public static void inviteClan(Player player, String name) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de nenhum clan.");
      return;
    } 
    if (player.getName().equals(name))
      return; 
    if (!clan.getLeader().equals(player.getName()) && !clan.officers.contains(player.getName())) {
      player.sendMessage("§cSomente o líder ou oficiais podem convidar novos membros.");
      return;
    } 
    Player target = Bukkit.getPlayerExact(name);
    if (target == null) {
      player.sendMessage("§cUsuário não encontrado.");
      return;
    } 
    Clan clan2 = getClan(target);
    if (clan2 != null) {
      player.sendMessage("§c" + Role.getPrefixed(target.getName(), true) + " §cjá faz parte de um clan.");
      return;
    } 
    if (clan.getSlots() <= clan.getAll().size()) {
      player.sendMessage("§cO clan já atingiu o seu limite máximo de membros.");
      return;
    } 
    if (clan.getInvites().contains(target.getName())) {
      player.sendMessage("§cO jogador " + Role.getPrefixed(target.getName(), true) + " §cjá foi convidado para o clan.");
      return;
    } 
    clan.getInvites().add(name);
    clan.save();
    List<BaseComponent> list = new ArrayList<>(Arrays.asList(TextComponent.fromLegacyText(" \n" + Role.getPrefixed(player.getName()) + " §aquer que você participe do clan [" + clan
            .getTag() + "] " + clan.getName() + "!\n")));
    Collections.addAll(list, TextComponent.fromLegacyText("§aClique "));
    TextComponent accept = new TextComponent("§a§lAQUI");
    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
          
          TextComponent.fromLegacyText("§eClique aqui para aceitar.\n§7/clan aceitar " + clan.getTag())));
    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan aceitar " + clan.getTag()));
    list.add(accept);
    list.add(new TextComponent(" §apara aceitar e "));
    TextComponent reject = new TextComponent("§c§lAQUI");
    reject.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
          
          TextComponent.fromLegacyText("§eClique aqui para recusar.\n§7/clan recusar " + clan.getTag())));
    reject.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan recusar " + clan.getTag()));
    list.add(reject);
    list.add(new TextComponent(" §apara negar.\n"));
    target.spigot().sendMessage(list.<BaseComponent>toArray(new BaseComponent[0]));
    player.sendMessage("\n" + Role.getPrefixed(target.getName()) + " §afoi convidado para o clan.\n \n");
  }
  
  public static void chatClan(Player player, String message) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de um clan.");
      return;
    } 
    clan.broadcast("§3[Clan] " + Role.getPrefixed(player.getName(), true) + "§f: " + StringUtils.formatColors(message), true, new String[0]);
  }
  
  public static void kickClan(Player player, String target) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de um clan.");
      return;
    } 
    if (player.getName().equals(target))
      return; 
    if (!clan.getLeader().equals(player.getName()) && !clan.officers.contains(player.getName())) {
      player.sendMessage("§cSomente o líder ou oficiais podem expulsar membros.");
      return;
    } 
    if (!clan.members.contains(target)) {
      player.sendMessage("§c" + target + " não é um membro do seu clan.");
      return;
    } 
    clan.remove(target);
    clan.save();
    long created = System.currentTimeMillis();
    Player tplayer = Bukkit.getPlayerExact(target);
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(created)) + "] §r";
    if (tplayer != null) {
      clan.addChangelog(date + tplayer
          .getName() + " foi expulso do clan por " + player.getName() + ".", created);
      clan.broadcast("\n" + Role.getPrefixed(tplayer.getName(), true) + " §afoi expulso do clan por " + Role.getColored(player.getName()) + " §a.\n ", true, new String[0]);
      tplayer.sendMessage("\n§aVocê foi expulso do clan §f[" + clan
          .getTag() + "] " + clan.getName() + " §apor " + player.getName() + "§a.\n ");
    } else {
      clan.addChangelog(date + target + " foi expulso do clan por " + player.getName() + ".", created);
      clan.broadcast("\n" + Role.getPrefixed(target, true) + " §afoi expulso do clan por " + Role.getPrefixed(player.getName(), true) + "§a.\n ", true, new String[0]);
    } 
  }
  
  public static void acceptClan(Player player, String tag) {
    if (getClan(player) != null) {
      player.sendMessage("§cVocê já faz parte de um clan.");
      return;
    } 
    Clan clan = getByTag(tag);
    if (clan == null) {
      player.sendMessage("§cClan não encontrado.");
      return;
    } 
    if (!clan.getInvites().contains(player.getName())) {
      player.sendMessage("§cVocê não foi convidado por este clan.");
      return;
    } 
    clan.getInvites().remove(player.getName());
    if (clan.getSlots() <= clan.getAll().size()) {
      clan.save();
      player.sendMessage("§cO clan já atingiu o seu limite máximo de membros.");
      return;
    } 
    clans.values().stream().filter(c -> c.getInvites().contains(player.getName())).forEach(c -> {
          c.getInvites().remove(player.getName());
          c.save();
        });
    clan.add(player.getName());
    long created = System.currentTimeMillis();
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(created)) + "] §r";
    clan.addChangelog(date + player.getName() + " entrou no clan.", created);
    clan.broadcast(" \n" + Role.getPrefixed(player.getName()) + " §aentrou no clan.\n ", true, new String[0]);
  }
  
  public static void rejectClan(Player player, String tag) {
    if (getClan(player) != null) {
      player.sendMessage("§cVocê já faz parte de um clan.");
      return;
    } 
    Clan clan = getByTag(tag);
    if (clan == null) {
      player.sendMessage("§cClan não encontrado.");
      return;
    } 
    if (!clan.getInvites().contains(player.getName())) {
      player.sendMessage("§cVocê não foi convidado por este clan.");
      return;
    } 
    clan.getInvites().remove(player.getName());
    clan.save();
    clan.sendLeader(" \n" + Role.getPrefixed(player.getName()) + " §arecusou o convite de clan.\n ");
    player.sendMessage("§aVocê recusou o convite do clan §f[" + clan.getTag() + "] " + clan.getName() + "§a.");
  }
  
  public static void invitesClan(Player player) {
    Clan clan = getClan(player);
    if (clan == null || !clan.getLeader().equals(player.getName())) {
      List<Clan> invites = new ArrayList<>();
      for (Clan c : clans.values()) {
        if (c.getInvites().contains(player.getName()))
          invites.add(c); 
      } 
      if (clan != null) {
        player.sendMessage("§cApenas o líder pode visualizar os convites enviados.");
      } else if (invites.isEmpty()) {
        player.sendMessage("§cVocê não possui convites.");
      } else {
        List<BaseComponent> list = new ArrayList<>(Arrays.asList(TextComponent.fromLegacyText(" \n§fLista de convites:\n")));
        for (Clan c : invites) {
          for (BaseComponent comp : TextComponent.fromLegacyText("§6[" + c.getTag() + "]")) {
            comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                  
                  TextComponent.fromLegacyText("§3Líder do Clan: §7" + 
                    Role.getPrefixed(c.getLeader()) + "\n§3Quantidade de Membros: §7" + c
                    .getAll().size() + "/" + c.getSlots())));
            list.add(comp);
          } 
          list.add(new TextComponent(" "));
          TextComponent accept = new TextComponent("§a§lACEITAR");
          accept
            .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                TextComponent.fromLegacyText("§eClique para aceitar!")));
          accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan aceitar " + c.getTag()));
          list.add(accept);
          list.add(new TextComponent(" §eou "));
          TextComponent reject = new TextComponent("§c§lRECUSAR");
          reject
            .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                TextComponent.fromLegacyText("§eClique para recusar!")));
          reject.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan recusar " + c.getTag()));
          list.add(reject);
          list.add(new TextComponent("\n"));
        } 
        player.spigot().sendMessage(list.<BaseComponent>toArray(new BaseComponent[0]));
      } 
    } else if (clan.getInvites().isEmpty()) {
      player.sendMessage("§cNão existe nenhum convite ativo.");
    } else {
      List<BaseComponent> list = new ArrayList<>();
      Collections.addAll(list, 
          TextComponent.fromLegacyText(" \n§fLista de convites ativos:\n"));
      for (String invited : clan.getInvites())
        list.add(new TextComponent(" §f* §7" + invited + "\n")); 
      player.spigot().sendMessage(list.<BaseComponent>toArray(new BaseComponent[0]));
    } 
  }
  
  public static void boletimClan(Player player) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de nenhum clan.");
      return;
    } 
    List<mc.twilight.clans.clan.ClanChangelog> list = clan.getChangelogs();
    if (list.isEmpty()) {
      player.sendMessage("§cNão foi encontrada nenhuma notícia.");
      return;
    } 
    player.sendMessage("");
    player.sendMessage("§eBoletim ");
    player.sendMessage("");
    for (mc.twilight.clans.clan.ClanChangelog ccl : list)
      player.sendMessage(ccl.getMessage()); 
    player.sendMessage("");
  }
  
  public static void leaveClan(Player player) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de nenhum clan.");
      return;
    } 
    if (clan.getLeader().equals(player.getName())) {
      player.sendMessage("§cVocê é o líder, exclua o clan caso deseje sair.");
      return;
    } 
    clan.remove(player.getName());
    long created = System.currentTimeMillis();
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(created)) + "] §r";
    clan.addChangelog(date + player.getName() + " saiu do clan.", created);
    clan.broadcast(" \n" + Role.getPrefixed(player.getName()) + " §asaiu do clan.\n ", true, new String[0]);
    player.sendMessage(" \n§cVocê saiu do clan [" + clan.getTag() + "] " + clan.getName() + "\n ");
  }
  
  public static void deleteClan(Player player) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de nenhum clan.");
      return;
    } 
    if (!clan.getLeader().equals(player.getName())) {
      player.sendMessage("§cApenas o líder pode excluir o clan.");
      return;
    } 
    player.sendMessage(" \n§cVocê excluiu o clan [" + clan.getTag() + "] " + clan.getName() + " \n ");
    clan.destroy();
  }
  
  public static void promoteClan(Player player, String target) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de nenhum clan.");
      return;
    } 
    if (player.getName().equals(target))
      return; 
    if (!clan.getLeader().equals(player.getName())) {
      player.sendMessage("§cApenas o líder pode promover alguém.");
      return;
    } 
    if (!clan.getMembers().contains(target)) {
      player.sendMessage("§cEste jogador não é um membro do clan.");
      return;
    } 
    clan.setOfficer(target);
    long created = System.currentTimeMillis();
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(created)) + "] §r";
    clan.addChangelog(date + target + " foi promovido a oficial do clan.", created);
    clan.broadcast(" \n" + Role.getPrefixed(target) + " §afoi promovido a oficial do clan por " + Role.getPrefixed(player.getName()) + "§a.\n ", true, new String[0]);
  }
  
  public static void demoteClan(Player player, String target) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de nenhum clan.");
      return;
    } 
    if (player.getName().equals(target))
      return; 
    if (!clan.getLeader().equals(player.getName())) {
      player.sendMessage("§cApenas o líder pode demotar alguém.");
      return;
    } 
    if (!clan.officers.contains(target)) {
      player.sendMessage(Role.getColored(target) + " §cnão é um oficial.");
      return;
    } 
    clan.officers.remove(target);
    clan.members.add(target);
    clan.save();
    long created = System.currentTimeMillis();
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(created)) + "] §r";
    clan.addChangelog(date + target + " foi rebaixado a membro do clan.", created);
    clan.broadcast(" \n" + Role.getPrefixed(target) + " §afoi rebaixado a membro do clan por " + Role.getPrefixed(player.getName()) + "§a.\n ", true, new String[0]);
  }
  
  public static void promoteLeader(Player player, String target) {
    Clan clan = getClan(player);
    if (clan == null) {
      player.sendMessage("§cVocê não faz parte de nenhum clan.");
      return;
    } 
    if (player.getName().equals(target))
      return; 
    if (!clan.getLeader().equals(player.getName())) {
      player.sendMessage("§cApenas o líder pode transferir o clan.");
      return;
    } 
    if (!clan.getAll().contains(target)) {
      player.sendMessage("§c" + target + " não é um membro do clan.");
      return;
    } 
    clan.setLeader(target);
    long created = System.currentTimeMillis();
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(created)) + "] §r";
    clan.addChangelog(date + player.getName() + " transferiu a liderença para " + target + ".", created);
    clan.broadcast(" \n" + Role.getPrefixed(target) + " §atransferiu a liderança do clan para " + Role.getPrefixed(player.getName()) + "§a.\n ", true, new String[0]);
  }
  
  public static Clan getByTag(String tag) {
    return clans.get(tag);
  }
  
  public static Clan getByName(String name) {
    for (Clan clan : clans.values()) {
      if (clan.getName().equals(name))
        return clan; 
    } 
    return null;
  }
  
  public static Clan getByUserName(String name) {
    for (Clan clan : clans.values()) {
      if (clan.contains(name))
        return clan; 
    } 
    return null;
  }
  
  public static Clan getClan(Player player) {
    for (Clan clan : clans.values()) {
      if (clan.contains(player.getName()))
        return clan; 
    } 
    return null;
  }
  
  public static Clan getClans(ProxiedPlayer player) {
    for (Clan clan : clans.values()) {
      if (clan.contains(player.getName()))
        return clan; 
    } 
    return null;
  }
  
  public static List<Clan> listClans() {
    return (List<Clan>)ImmutableList.copyOf(clans.values());
  }
  
  public void addCoins(int coins) {
    this.coins += coins;
    save();
  }
  
  public void removeCoins(int coins) {
    this.coins -= coins;
    save();
  }
  
  public void addTagColor(boolean upgrade) {
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(this.created)) + "] §r";
    addChangelog(date + (upgrade ? "Tag no chat aprimorada" : "Tag no chat adicionada"), 
        System.currentTimeMillis());
    if (upgrade) {
      this.tagPermissionPlus = true;
    } else {
      this.tagPermission = true;
    } 
  }
  
  public void addSlots() {
    this.slots += 5;
    String date = "§7[" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(this.created)) + "] §r";
    addChangelog(date + "Limite de Membros ampliado para " + this.slots, 
        System.currentTimeMillis());
  }
  
  public void addChangelog(String message, long created) {
    if (this.changelogs.size() == 8)
      this.changelogs.remove(0); 
    this.changelogs.add(new mc.twilight.clans.clan.ClanChangelog(message, created));
    save();
  }
  
  public void setOfficer(String player) {
    this.members.remove(player);
    this.officers.add(player);
    save();
  }
  
  public void add(String name) {
    this.members.add(name);
    this.users.add(new mc.twilight.clans.clan.ClanUser(name, System.currentTimeMillis()));
  }
  
  public void remove(String name) {
    this.officers.remove(name);
    this.members.remove(name);
    this.users.remove(getUser(name));
  }
  
  public String getRole(String name) {
    return this.members.contains(name) ? "Membro" : (this.officers.contains(name) ? "Oficial" : "Líder");
  }
  
  public boolean contains(String name) {
    return (this.leader.equals(name) || this.members.contains(name) || this.officers.contains(name));
  }
  
  public mc.twilight.clans.clan.ClanUser getUser(String name) {
    for (mc.twilight.clans.clan.ClanUser user : this.users) {
      if (user.getName().equals(name))
        return user; 
    } 
    return null;
  }
  
  public List<mc.twilight.clans.clan.ClanChangelog> getChangelogs() {
    return this.changelogs;
  }
  
  public void reload() {
    try {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `zClans` WHERE `tag` = ?", new Object[] { this.tag });
      if (rs != null) {
        this.coins = rs.getInt("coins");
        this.tagPermission = rs.getBoolean("tagPermission");
        this.tagPermissionPlus = rs.getBoolean("tagPermissionPlus");
        this.slots = rs.getInt("slots");
        this.users.clear();
        this.leader = rs.getString("leader").split(":")[0];
        this.users.add(new mc.twilight.clans.clan.ClanUser(this.leader, Long.parseLong(rs.getString("leader").split(":")[1])));
        this.members.clear();
        for (String member : rs.getString("members").split(", ")) {
          if (!member.isEmpty()) {
            this.members.add(member.split(":")[0]);
            this.users.add(new mc.twilight.clans.clan.ClanUser(member.split(":")[0], Long.parseLong(member.split(":")[1])));
          } 
        } 
        this.officers.clear();
        for (String officer : rs.getString("officers").split(", ")) {
          if (!officer.isEmpty()) {
            this.officers.add(officer.split(":")[0]);
            this.users.add(new mc.twilight.clans.clan.ClanUser(officer.split(":")[0], Long.parseLong(officer.split(":")[1])));
          } 
        } 
        this.invites.clear();
        for (String invite : rs.getString("invites").split(", ")) {
          if (!invite.isEmpty())
            this.invites.add(invite); 
        } 
        this.changelogs.clear();
        for (String changelog : rs.getString("changelog").split(" ,:, ")) {
          if (!changelog.isEmpty())
            this.changelogs.add(new mc.twilight.clans.clan.ClanChangelog(changelog.split(" ,;, ")[0], Long.parseLong(changelog.split(" ,;, ")[1]))); 
        } 
      } 
    } catch (SQLException ex) {
      LOGGER.log(Level.WARNING, "Error while reloading clan (" + this.tag + "): ", ex);
    } 
  }
  
  public void destroy() {
    destroy(true);
  }
  
  public void destroy(boolean executeDb) {
    clans.remove(this.tag);
    if (executeDb) {
      Main.sendBungee(Bukkit.getOnlinePlayers().iterator().next(), "Destroy", this.tag, new String[0]);
      broadcast("\n§aO seu clan §7[" + this.tag + "] " + this.name + " §afoi excluído!\n", false, new String[0]);
      Database.getInstance().execute("DELETE FROM `zClans` WHERE `tag` = ?", new Object[] { this.tag });
    } 
    this.tag = null;
    this.name = null;
    this.leader = null;
    this.slots = 0;
    this.members.clear();
    this.members = null;
    this.officers.clear();
    this.officers = null;
    this.users.clear();
    this.users = null;
    this.changelogs.clear();
    this.changelogs = null;
    this.invites.clear();
    this.invites = null;
  }
  
  public void sendLeader(String message) {
    Player target = null;
    if ((target = Bukkit.getPlayerExact(this.leader)) != null)
      target.sendMessage(message); 
  }
  
  public void broadcast(String message, boolean leader, String... blockBungee) {
    Player target = null;
    if (leader)
      sendLeader(message); 
    List<String> m = new ArrayList<>();
    m.addAll(this.members);
    m.addAll(this.officers);
    for (String member : m) {
      if ((target = Bukkit.getPlayerExact(member)) != null)
        target.sendMessage(message); 
    } 
    if (blockBungee.length == 0)
      Main.sendBungee(Bukkit.getOnlinePlayers().iterator().next(), "Broadcast", this.tag, new String[] { message, String.valueOf(leader) }); 
  }
  
  public void save() {
    StringBuilder joined = new StringBuilder();
    for (int slot = 0; slot < this.members.size(); slot++)
      joined.append(this.members.get(slot)).append(":").append(getUser(this.members.get(slot)).getJoined()).append(":").append((slot + 1 == this.members.size()) ? "" : ", "); 
    StringBuilder joined2 = new StringBuilder();
    for (int i = 0; i < this.officers.size(); i++)
      joined2.append(this.officers.get(i)).append(":").append(getUser(this.officers.get(i)).getJoined()).append(":").append((i + 1 == this.officers.size()) ? "" : ", "); 
    Database.getInstance().execute("UPDATE `zClans` SET `members` = ?, `officers` = ?, `invites` = ?, `changelog` = ?, `leader` = ?, `coins` = ?, `slots` = ?, `tagPermission` = ?, `tagPermissionPlus` = ? WHERE `tag` = ?", new Object[] { joined
          
          .toString(), joined2.toString(), StringUtils.join(this.invites, ", "), 
          StringUtils.join(this.changelogs, " ,:, "), this.leader + ":" + 
          getUser(this.leader).getJoined(), Integer.valueOf(this.coins), 
          Integer.valueOf(this.slots), Boolean.valueOf(this.tagPermission), Boolean.valueOf(this.tagPermissionPlus), this.tag });
    Main.sendBungee(Bukkit.getOnlinePlayers().iterator().next(), "Update", this.tag, new String[0]);
  }
  
  public String getTag() {
    return this.tag;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getLeader() {
    return this.leader;
  }
  
  public void setLeader(String player) {
    this.members.add(this.leader);
    this.officers.remove(player);
    this.members.remove(player);
    this.leader = player;
    save();
  }
  
  public long getCreated() {
    return this.created;
  }
  
  public int getSlots() {
    return this.slots;
  }
  
  public int getCoins() {
    return this.coins;
  }
  
  public List<String> getInvites() {
    return this.invites;
  }
  
  public List<String> getMembers() {
    return this.members;
  }
  
  public List<String> getOfficers() {
    return this.officers;
  }
  
  public List<String> getTaggedMembers() {
    return (List<String>)this.members.stream().map(Role::getColored).collect(Collectors.toList());
  }
  
  public List<String> getTaggedOfficers() {
    return (List<String>)this.officers.stream().map(Role::getColored).collect(Collectors.toList());
  }
  
  public List<String> getPrefixedMembers() {
    return (List<String>)this.members.stream().map(Role::getPrefixed).collect(Collectors.toList());
  }
  
  public List<String> getPrefixedOfficers() {
    return (List<String>)this.officers.stream().map(Role::getPrefixed).collect(Collectors.toList());
  }
  
  public List<String> getAll() {
    List<String> all = new ArrayList<>();
    all.addAll(this.members);
    all.addAll(this.officers);
    all.add(this.leader);
    return all;
  }
}
