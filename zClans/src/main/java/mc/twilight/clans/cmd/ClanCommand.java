package mc.twilight.clans.cmd;

import mc.twilight.clans.clan.Clan;
import mc.twilight.clans.listeners.objects.ClanCreating;
import mc.twilight.clans.menu.ClanMenu;
import mc.twilight.clans.menu.MembersMenu;
import mc.twilight.clans.menu.RanksMenu;
import mc.twilight.clans.menu.ShopMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanCommand extends Commands {
  public static DecimalFormat df = new DecimalFormat("#,###");
  
  public ClanCommand() {
    super("clan", new String[] { "c" });
  }
  
  public static void sendHelp(Player player) {
    Clan clan = Clan.getClan(player);
    if (clan == null) {
      player.sendMessage(" \n§3/clan perfil [jogador] §f- §7Mostrar as informações do membro de um clan.\n§3/clan info [sigla] §f- §7Mostrar as informações de um clan.\n§3/clan criar §f- §7Crie seu clan.\n§3/clan aceitar [clan] §f- §7Aceitar um convite de clan.\n§3/clan rank §f- §7Visualizar o rank de clans.\n§3/clan recusar [clan] §f- §7Recusar um convite de clan.\n§3/clan convites §f- §7Listar convites de clan.\n§3/c [mensagem] §f- §7Converse com seu clan.\n ");
    } else {
      String role = clan.getRole(player.getName());
      player.sendMessage(" \n§3/clan perfil [jogador] §f- §7Mostrar as informações do membro de um clan.\n§3/clan info [sigla] §f- §7Mostrar as informações de um clan.\n§3/clan rank §f- §7Visualizar o rank de clans.\n§3/clan boletim §f- §7Mostrar o boletim do clan.\n§3/clan membros §f- §7Gerenciar os membros do clan.\n§3/clan loja §f- §7Abrir a loja do clan.\n§3/clan sair §f- §7Saia do seu clan." + (
          
          role.equals("Líder") ? "\n§3/clan convites §f- §7Listar convites ativos do clan.\n§3/clan convidar [jogador] §f- §7Convide alguém para seu clan.\n§3/clan expulsar [jogador] §f- §7Expulse um membro do seu clan.\n§3/clan promover [jogador] §f- §7Promove um membro a oficial.\n§3/clan rebaixar [jogador] §f- §7Rebaixar um oficial a membro.\n§3/clan transferir [jogador] §f- §7Transferir a liderança do clan.\n§3/clan excluir §f- §7Excluir o clan." : (
          
          role.equals("Oficial") ? "\n§3/clan convidar [jogador] §f- §7Convide alguém para seu clan.\n§3/clan expulsar [jogador] §f- §7Expulse um membro do seu clan." : "")) + "\n§3/c [mensagem] §f- §7Converse com seu clan.\n ");
    } 
  }
  
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (label.equalsIgnoreCase("c")) {
        if (args.length == 0) {
          player.sendMessage("§cUtilize /c [mensagem]");
          return;
        } 
        Clan.chatClan(player, StringUtils.join((Object[])args, " "));
        return;
      } 
      if (args.length == 0) {
        new ClanMenu(player, (Clan.getClan(player) == null));
        return;
      } 
      String arg = args[0];
      if (arg.equalsIgnoreCase("rank")) {
        new RanksMenu(Profile.getProfile(player.getName()));
        return;
      } 
      if (arg.equalsIgnoreCase("perfil")) {
        if (args.length == 1) {
          player.sendMessage("§cUtilize /perfil [jogador]");
          return;
        } 
        Clan clan = Clan.getByUserName(args[1]);
        if (clan == null) {
          player.sendMessage("§cUsuário não encontrado.");
          return;
        } 
        player.sendMessage(" \n§3Nick: §7" + args[1] + "\n§3Clan: §7[" + clan.getTag() + "] " + clan
            .getName() + "\n§3Cargo: §7" + clan.getRole(args[1]) + "\n§3Entrou em: §7" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", 
              Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(clan.getUser(args[1]).getJoined())) + "\n ");
      } else if (arg.equalsIgnoreCase("info")) {
        if (args.length == 1) {
          Clan clan1 = Clan.getClan(player);
          player.sendMessage((clan1 != null) ? (" \n§3Nome: §7[" + clan1
              .getTag() + "] " + clan1.getName() + "\n§3Líder: §7" + 
              Role.getPrefixed(clan1.getLeader()) + "\n§3Criado em: §7" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(clan1.getCreated())) + "\n§3Membros: §7" + 
              StringUtils.join(clan1.getPrefixedMembers(), ", ") + "\n§3Oficiais: §7" + 
              StringUtils.join(clan1.getPrefixedOfficers(), ", ") + "\n§3Coins: §7" + df
              .format(clan1.getCoins()) + "\n ") : "§cVocê não faz parte de nenhum clan.");
          return;
        } 
        Clan clan = Clan.getByTag(args[1]);
        if (clan == null) {
          player.sendMessage("§cEste clan não foi encontrado.");
          return;
        } 
        player.sendMessage(" \n§3Nome: §7[" + clan.getTag() + "] " + clan.getName() + "\n§3Líder: §7" + 
            Role.getPrefixed(clan.getLeader()) + "\n§3Criado em: §7" + (new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", 
              Locale.forLanguageTag("pt-BR"))).format(Long.valueOf(clan.getCreated())) + "\n§3Membros: §7" + 
            StringUtils.join(clan.getPrefixedMembers(), ", ") + "\n§3Oficiais: §7" + 
            StringUtils.join(clan.getPrefixedOfficers(), ", ") + "\n§3Coins: §7" + df
            .format(clan.getCoins()) + "\n ");
      } else if (arg.equalsIgnoreCase("criar")) {
        if (!player.hasPermission("utils.clan.create")) {
          player.sendMessage("§cSomente MVP ou superior podem executar este comando.");
          return;
        } 
        ClanCreating.createClan(player.getName());
        player.chat("Creating");
      } else if (arg.equalsIgnoreCase("conversar")) {
        if (args.length < 2) {
          player.sendMessage("§cUtilize /clan conversar [mensagem]");
          return;
        } 
        Clan.chatClan(player, StringUtils.join((Object[])args, 1, " "));
      } else if (arg.equalsIgnoreCase("aceitar")) {
        if (args.length < 2) {
          player.sendMessage("§cUtilize /clan aceitar [nome]");
          return;
        } 
        Clan.acceptClan(player, args[1]);
      } else if (arg.equalsIgnoreCase("recusar")) {
        if (args.length < 2) {
          player.sendMessage("§cUtilize /clan recusar [nome]");
          return;
        } 
        Clan.rejectClan(player, args[1]);
      } else if (arg.equalsIgnoreCase("convites")) {
        Clan.invitesClan(player);
      } else if (arg.equalsIgnoreCase("boletim")) {
        Clan.boletimClan(player);
      } else if (arg.equalsIgnoreCase("membros")) {
        Clan clan = Clan.getClan(player);
        if (clan == null) {
          player.sendMessage("§cVocê não faz parte de nenhum clan.");
          return;
        } 
        new MembersMenu(player, clan);
      } else if (arg.equalsIgnoreCase("loja")) {
        Clan clan = Clan.getClan(player);
        if (clan == null) {
          player.sendMessage("§cVocê não faz parte de nenhum clan.");
          return;
        } 
        new ShopMenu(player, clan);
      } else if (arg.equalsIgnoreCase("sair")) {
        Clan.leaveClan(player);
      } else if (arg.equalsIgnoreCase("convidar")) {
        if (args.length < 2) {
          player.sendMessage("§cUtilize /clan convidar [jogador]");
          return;
        } 
        Player p = Bukkit.getPlayerExact(args[1]);
        if (p == null) {
          player.sendMessage("§cJogador Offline.");
          return;
        } 
        Clan.inviteClan(player, p.getName());
      } else if (arg.equalsIgnoreCase("expulsar")) {
        if (args.length < 2) {
          player.sendMessage("§cUtilize /clan expulsar [jogador]");
          return;
        } 
        Clan.kickClan(player, args[1]);
      } else if (arg.equalsIgnoreCase("promover")) {
        if (args.length < 2) {
          player.sendMessage("§cUtilize /clan promover [jogador]");
          return;
        } 
        Clan.promoteClan(player, args[1]);
      } else if (arg.equalsIgnoreCase("rebaixar")) {
        if (args.length < 2) {
          player.sendMessage("§cUtilize /clan rebaixar [jogador]");
          return;
        } 
        Clan.demoteClan(player, args[1]);
      } else if (arg.equalsIgnoreCase("transferir")) {
        if (args.length < 2) {
          player.sendMessage("§cUtilize /clan transferir [jogador]");
          return;
        } 
        Clan.promoteLeader(player, args[1]);
      } else if (arg.equalsIgnoreCase("excluir")) {
        Clan.deleteClan(player);
      } else {
        sendHelp(player);
      } 
    } 
  }
}
