package mc.twilight.clans.menu;

import mc.twilight.clans.Main;
import mc.twilight.clans.clan.Clan;
import mc.twilight.clans.clan.ClanChangelog;
import mc.twilight.clans.listeners.objects.ClanCreating;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ClanMenu extends PlayerMenu implements Listener {
  private boolean create;
  
  public ClanMenu(Player player, boolean create) {
    super(player, create ? "Clans" : ("Clan - [" + Clan.getClan(player).getTag() + "] " + Clan.getClan(player).getName()), create ? 3 : 5);
    this.create = create;
    if (create) {
      setItem(11, BukkitUtils.deserializeItemStack("SIGN : 1 : nome>&6Criar um Clan : desc>&7Clique para criar um clan.\n \n&7Exclusivo para §6MVP &7ou superior."));
      setItem(15, BukkitUtils.deserializeItemStack("BOOK : 1 : nome>&6Ajuda : desc>&7Você também pode gerenciar seu clan\n&7através de comandos no chat.\n \n&fComando: &7/clan ajuda\n \n&eClique para listar os comandos!"));
    } else {
      Clan clan = Clan.getClan(player);
      List<String> list = (List<String>)clan.getChangelogs().parallelStream().map(cl -> cl.getMessage()).collect(Collectors.toList());
      setItem(10, BukkitUtils.deserializeItemStack("EMERALD : 1 : nome>&6Loja : desc>&7Adquira evoluções e funcionalidades\n&7para todo o seu clan.\n \n&fComando: &7/clan loja\n \n&eClique para abrir!"));
      setItem(16, BukkitUtils.deserializeItemStack("266 : 1 : nome>&6Banco do Clan : desc>&fSaldo: &7" + clan.getCoins()));
      setItem(13, BukkitUtils.putProfileOnSkull(this.player, BukkitUtils.deserializeItemStack("397:3 : 1 : nome>&6Membros : desc>&fTotal de membros: &7" + clan.getAll().size() + "/" + clan.getSlots() + "\n \n&8Amplie a capacidade de membros\n&8do seu clan através da Loja.\n \n&fComando: &7/clan membros\n \n&eClique para abrir!")));
      setItem(39, BukkitUtils.deserializeItemStack("397:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19 : nome>&6Ajuda : desc>&7Você também pode gerenciar seu clan\n&7através de comandos no chat.\n \n&fComando: &7/clan ajuda\n \n&eClique para listar os comandos!"));
      if (clan.getRole(player.getName()).equals("Líder")) {
        setItem(40, BukkitUtils.deserializeItemStack("BARRIER : 1 : nome>&cExcluir o clan : desc>&7Excluir seu clan permanentemente.\n \n&fComando: &7/clan excluir\n \n&cClique para excluir!"));
      } else {
        setItem(40, BukkitUtils.deserializeItemStack("BARRIER : 1 : nome>&cSair do clan : desc>§7Saia do clan permanentemente.\n \n&fComando: &7/clan sair\n \n&cClique para sair!"));
      } 
      setItem(11, BukkitUtils.deserializeItemStack("PAPER : 1 : nome>&6Boletim : desc>" + StringUtils.join(list, "\n") + "\n \n&fComando: &7/clan boletim\n \n&eClique para ver!"));
      if (clan.getRole(player.getName()).equals("Líder") || clan.getRole(player.getName()).equals("Oficial")) {
        setItem(15, BukkitUtils.deserializeItemStack("APPLE : 1 : nome>&6Convidar Membros : desc>&7Convide novos membros para\n&7participarem do seu clan.\n \n&fComando: &7/clan convidar (jogador) \n \n&eClique para convidar!"));
      } else {
        setItem(15, BukkitUtils.deserializeItemStack("APPLE : 1 : nome>&6Convidar Membros : desc>&7Convide novos membros para\n&7participarem do seu clan.\n \n&fComando: &7/clan convidar (jogador) \n \n§cSomente o líder ou oficiais podem convidar novos membros."));
      } 
      String date = (new SimpleDateFormat("dd/MM/yyyy hh:mm")).format(Long.valueOf(clan.getCreated()));
      setItem(41, 
          BukkitUtils.deserializeItemStack("BOOK : 1 : nome>&6Informações : desc>&fNome: &7" + clan
            .getName() + "\n&fDono: &7" + Role.getPrefixed(clan.getLeader(), true) + "\n&fCriado em: &7" + date + "\n&fMembros: &7" + clan
            .getAll().size() + "/" + clan.getSlots() + "\n \n&fComando: &7/clan info\n \n&eClique para ver!"));
    } 
    player.openInventory(getInventory());
    Bukkit.getPluginManager().registerEvents(this, (Plugin)Main.getInstance());
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(getInventory())) {
      evt.setCancelled(true);
      if (evt.getWhoClicked().equals(this.player) && 
        evt.getWhoClicked() instanceof Player && evt.getWhoClicked().equals(this.player)) {
        Clan clan = Clan.getClan(this.player);
        if (this.create && clan != null) {
          this.player.closeInventory();
        } else if (!this.create && clan == null) {
          this.player.closeInventory();
        } else {
          ItemStack item = evt.getCurrentItem();
          if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(evt.getInventory()) && item != null && item.getType() != Material.AIR)
            if (this.create) {
              if (evt.getSlot() == 11) {
                this.player.closeInventory();
                if (!this.player.hasPermission("utils.clan.use")) {
                  this.player.sendMessage("§cSomente MVP ou superior podem executar este comando.");
                  return;
                } 
                ClanCreating.createClan(this.player.getName());
                this.player.chat("Creating");
              } else if (evt.getSlot() == 15) {
                this.player.closeInventory();
                this.player.performCommand("clan ajuda");
              } 
            } else if (evt.getSlot() == 10) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.player.performCommand("clan loja");
            } else if (evt.getSlot() == 13) {
              EnumSound.CLICK.play(this.player, 1.0F, 2.0F);
              this.player.performCommand("clan membros");
            } else if (evt.getSlot() == 11) {
              EnumSound.CLICK.play(this.player, 1.0F, 2.0F);
              this.player.performCommand("clan boletim");
              this.player.closeInventory();
            } else if (evt.getSlot() == 15) {
              if (clan.getRole(this.player.getName()).equals("Líder") || clan.getRole(this.player.getName()).equals("Oficial")) {
                EnumSound.CLICK.play(this.player, 1.0F, 2.0F);
                this.player.performCommand("clan convidar");
                this.player.closeInventory();
              } else {
                this.player.sendMessage("§cSomente o líder ou oficiais podem convidar novos membros.");
                this.player.closeInventory();
              } 
            } else if (evt.getSlot() == 39) {
              EnumSound.CLICK.play(this.player, 1.0F, 2.0F);
              this.player.performCommand("clan ajuda");
              this.player.closeInventory();
            } else if (evt.getSlot() == 40) {
              EnumSound.CLICK.play(this.player, 1.0F, 2.0F);
              if (clan.getRole(this.player.getName()).equals("Líder")) {
                new ConfirmMenu(this.player, true);
              } else {
                new ConfirmMenu(this.player, false);
              } 
            } else if (evt.getSlot() == 16) {
              EnumSound.CLICK.play(this.player, 1.0F, 2.0F);
            } else if (evt.getSlot() == 41) {
              EnumSound.CLICK.play(this.player, 1.0F, 2.0F);
              this.player.performCommand("clan info");
              this.player.closeInventory();
            }  
        } 
      } 
    } 
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player))
      HandlerList.unregisterAll(this); 
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(getInventory()))
      HandlerList.unregisterAll(this); 
  }
}
