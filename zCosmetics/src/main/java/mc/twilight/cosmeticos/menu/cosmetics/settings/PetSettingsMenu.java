package mc.twilight.cosmeticos.menu.cosmetics.settings;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetType;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.hook.Users;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.menu.cosmetics.PetsMenu;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.cosmeticos.nms.interfaces.pets.*;
import mc.twilight.cosmeticos.utils.enums.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static mc.twilight.cosmeticos.listeners.Listeners.PET_NAME;

public class PetSettingsMenu extends PlayerMenu {
  
  private PetType petType;
  private Map<ItemStack, PetSettingsEntry> entries = new HashMap<>();
  public PetSettingsMenu(CUser user, PetType petType) {
    super(user.getPlayer(), "Customizar Pet", 4);
    this.petType = petType;
    
    int slot = 10;
    for (PetSettingsEntry entry : user.getPetSettings(petType)) {
      String stack = "";
      if (entry.getKey().equals("name")) {
        slot = 10;
        stack = "NAME_TAG : 1 : nome>&aAlterar apelido : desc>&7Você pode dar o apelido que\n&7desejar para o seu pet.\n \n&fAtual: " + entry.getValue().getAsString()
            + "\n \n&eClique para alterar!";
      } else if (entry.getKey().equals("baby")) {
        slot = 11;
        stack = "SKULL_ITEM:3 : 1 : nome>&aIdade : desc>&7Você pode selecionar se seu\n&7pet será bebê ou adulto.\n \n&fAtual: &7"
            + (entry.getValue().getAsBoolean() ? "Bebê" : "Adulto")
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWIwNjYxMWI5MmM4NzVhMmYyOWY4MGU5NWQ3M2RhYzU0MmI1NzMyOGZjYTIwNjUzOWQxZWNlNmQxYWZhYTA3NyJ9fX0=";
      } else if (entry.getKey().equals("mushroom")) {
        slot = 12;
        stack = "SKULL_ITEM:3 : 1 : nome>&aCoguvaca : desc>&7Você pode selecionar se sua\n&7ovelha será de cogumelo.\n \n&fAtual: &7"
            + (entry.getValue().getAsBoolean() ? "Cogumelo" : "Normal")
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBiYzYxYjk3NTdhN2I4M2UwM2NkMjUwN2EyMTU3OTEzYzJjZjAxNmU3YzA5NmE0ZDZjZjFmZTFiOGRiIn19fQ==";
      } else if (entry.getKey().equals("wither")) {
        slot = 11;
        stack = "SKULL_ITEM:3 : 1 : nome>&aWither : desc>&7Você pode selecionar se seu\n&7esqueleto será do wither.\n \n&fAtual: &7"
            + (entry.getValue().getAsBoolean() ? "Wither" : "Normal")
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk1M2I2YzY4NDQ4ZTdlNmI2YmY4ZmIyNzNkNzIwM2FjZDhlMWJlMTllODE0ODFlYWQ1MWY0NWRlNTlhOCJ9fX0=";
      } else if (entry.getKey().equals("sheepcolor")) {
        slot = 12;
        MWoolColor color = MWoolColor.getByWoolData(entry.getValue().getAsInt());
        stack = "SKULL_ITEM:3 : 1 : nome>&aCor da Ovelha : desc>&7Você pode selecionar qual será\n&7a cor da sua ovelha.\n \n&fAtual: &7"
            + color.getName() + "\n \n&eClique para alterar! : skin>" + color.getTextures();
      } else if (entry.getKey().equals("rainbow")) {
        slot = 13;
        stack = "SKULL_ITEM:3 : 1 : nome>&aArco-íris : desc>&7Você pode selecionar se sua\n&7ovelha ficará trocando de cor.\n \n&fAtual: &7"
            + (entry.getValue().getAsBoolean() ? "Arco-íris" : "Normal")
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JlNzU0NTI5N2RmZDYyNjZiYmFhMjA1MTgyNWU4ODc5Y2JmYTQyYzdlN2UyNGU1MDc5NmYyN2NhNmExOCJ9fX0=";
      } else if (entry.getKey().equals("cave")) {
        slot = 11;
        stack = "SKULL_ITEM:3 : 1 : nome>&aAranha da Caverna : desc>&7Você pode selecionar se sua\n&7aranha será da caverna.\n \n&fAtual: &7"
            + (entry.getValue().getAsBoolean() ? "Caverna" : "Normal")
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19";
      } else if (entry.getKey().equals("dyecolor")) {
        slot = 12;
        MDyeColor dyeColor = MDyeColor.getByDyeData(entry.getValue().getAsInt());
        stack = "INK_SACK:" + dyeColor.getDyeData() + " : 1 : nome>&aCor da Coleira : desc>&7Você pode selecionar qual será\n&7a cor da coleira do seu lobo.\n \n&fAtual: &7"
            + dyeColor.getName()
            + "\n \n&eClique para alterar!";
      } else if (entry.getKey().equals("rabbit")) {
        slot = 12;
        MRabbitType rabbitType = MRabbitType.getById(entry.getValue().getAsInt());
        stack = "SKULL_ITEM:3 : 1 : nome>&aTipo : desc>&7Você pode selecionar qual será\n&7o tipo do coelho.\n \n&fAtual: &7"
            + rabbitType.getName()
            + "\n \n&eClique para alterar! : skin>" + rabbitType.getTextures();
      } else if (entry.getKey().equals("profession")) {
        slot = 12;
        MVillagerProfession villagerProfession = MVillagerProfession.getById(entry.getValue().getAsInt());
        stack = "SKULL_ITEM:3 : 1 : nome>&aProfissão : desc>&7Você pode selecionar qual será\n&7a profissão do seu villager.\n \n&fAtual: &7"
            + villagerProfession.getName()
            + "\n \n&eClique para alterar! : skin>" + villagerProfession.getTextures();
      } else if (entry.getKey().equals("slimesize")) {
        slot = 11;
        stack = "SKULL_ITEM:3 : 1 : nome>&aTamanho : desc>&7Você pode selecionar qual será\n&7o tamanho do seu slime.\n \n&fAtual: &7"
            + MSlimeSize.getBySize(entry.getValue().getAsInt()).getName()
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5MjU1NDI0OWVhMThjZjY3ODM4ZGRlMTdlNjY2MmY0NmMyMTk0ZjU3NWQyMDNmZjQzMmUzMzdhMDg4ZGYzYyJ9fX0=";
      } else if (entry.getKey().equals("slimemagma")) {
        slot = 12;
        stack = "SKULL_ITEM:3 : 1 : nome>&aMagma : desc>&7Você pode selecionar se seu\n&7slime será de magma.\n \n&fAtual: &7"
            + (entry.getValue().getAsBoolean() ? "Magma" : "Normal")
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MWFhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0=";
      } else if (entry.getKey().equals("charged")) {
        slot = 11;
        stack = "SKULL_ITEM:3 : 1 : nome>&aSobrecarregado : desc>&7Você pode selecionar se seu\n&7creeper será sobrecarregado.\n \n&fAtual: &7"
            + (entry.getValue().getAsBoolean() ? "Sobrecarregado" : "Normal")
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJjZWIzOWRkNGRlMjRhN2FkZmUyOTFhM2EwY2ZjN2NmNGY2NDVkZTU5YjYwM2ZjZmUwNmM2YjVhMDZlMjYifX19";
      } else if (entry.getKey().equals("villager")) {
        slot = 12;
        stack = "SKULL_ITEM:3 : 1 : nome>&aVillager : desc>&7Você pode selecionar se seu\n&7zumbi será villager.\n \n&fAtual: &7"
            + (entry.getValue().getAsBoolean() ? "Villager" : "Normal")
            + "\n \n&eClique para alterar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTVlMDhhODc3NmMxNzY0YzNmZTZhNmRkZDQxMmRmY2I4N2Y0MTMzMWRhZDQ3OWFjOTZjMjFkZjRiZjNhYzg5YyJ9fX0=";
      } else if (entry.getKey().equals("horsecolor")) {
        slot = 12;
        MHorseColor horseColor = MHorseColor.getById(entry.getValue().getAsInt());
        stack = "SKULL_ITEM:3 : 1 : nome>&aCor : desc>&7Você pode selecionar qual será\n&7a cor do seu cavalo.\n \n&fAtual: &7"
            + horseColor.getName()
            + "\n \n&eClique para alterar! : skin>" + horseColor.getTextures();
      } else if (entry.getKey().equals("horsetype")) {
        slot = 13;
        MHorseType horseType = MHorseType.getById(entry.getValue().getAsInt());
        stack = "SKULL_ITEM:3 : 1 : nome>&aTipo : desc>&7Você pode selecionar qual será\n&7o tipo do seu cavalo.\n \n&fAtual: &7"
            + horseType.getName()
            + "\n \n&eClique para alterar! : skin>" + horseType.getTextures();
      } else if (entry.getKey().equals("ocelot")) {
        slot = 12;
        MOcelotType ocelotType = MOcelotType.getById(entry.getValue().getAsInt());
        stack = "SKULL_ITEM:3 : 1 : nome>&aRaça : desc>&7Você pode selecionar qual será\n&7a raça do seu gato.\n \n&fAtual: &7"
            + ocelotType.getName()
            + "\n \n&eClique para alterar! : skin>" + ocelotType.getTextures();
      }
      
      ItemStack icon = BukkitUtils.deserializeItemStack(stack);
      this.entries.put(icon, entry);
      this.setItem(slot, icon);
    }
    
    this.setItem(31, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar"));
    
    this.register(Main.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        CUser user = Users.getByName(this.player.getName());
        if (user == null) {
          player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          if (evt.getSlot() == 31) {
            new PetsMenu(user);
          } else {
            PetSettingsEntry settingsEntry = entries.get(evt.getCurrentItem());
            if (settingsEntry != null) {
              if (settingsEntry.getKey().equals("name")) {
                PET_NAME.put(player.getName(), new Object[]{this.petType, settingsEntry});
                
                TextComponent changeName = new TextComponent(" \nDigite no chat o apelido que deseja dar a seu Pet!");
                changeName.setColor(ChatColor.AQUA);
                TextComponent startCancel = new TextComponent("\nCaso mude de ideia clique ");
                startCancel.setColor(ChatColor.GRAY);
                TextComponent clickCancel = new TextComponent("§lAQUI");
                clickCancel.setColor(ChatColor.GOLD);
                clickCancel.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "zcosmeticos:cancelchangename"));
                clickCancel.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Clique para cancelar a mudança de apelido.")));
                TextComponent endCancel = new TextComponent(" para cancelar a mudança de apelido.\n ");
                endCancel.setColor(ChatColor.GRAY);
                
                changeName.addExtra(startCancel);
                changeName.addExtra(clickCancel);
                changeName.addExtra(endCancel);
                
                player.closeInventory();
                player.spigot().sendMessage(changeName);
                return;
              } else if (settingsEntry.getKey().equals("baby")) {
                settingsEntry.getValue().set(!settingsEntry.getValue().getAsBoolean());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetAgeable) user.getSelectedPet().getPetController().getEntity()).setBaby(settingsEntry.getValue().getAsBoolean());
                }
              } else if (settingsEntry.getKey().equals("mushroom")) {
                settingsEntry.getValue().set(!settingsEntry.getValue().getAsBoolean());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  user.getSelectedPet().respawn();
                }
              } else if (settingsEntry.getKey().equals("wither")) {
                settingsEntry.getValue().set(!settingsEntry.getValue().getAsBoolean());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetSkeleton) user.getSelectedPet().getPetController().getEntity()).setWither(settingsEntry.getValue().getAsBoolean());
                }
              } else if (settingsEntry.getKey().equals("sheepcolor")) {
                settingsEntry.getValue().set(MWoolColor.getByWoolData(settingsEntry.getValue().getAsInt()).getNextColor().getWoolData());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  PetSheep petSheep = (PetSheep) user.getSelectedPet().getPetController().getEntity();
                  petSheep.setColor(
                      MWoolColor.getByWoolData(user.getPetSettings(this.petType).stream().filter(pse -> pse.getKey().equals("sheepcolor")).findFirst().get().getValue().getAsInt()));
                }
              } else if (settingsEntry.getKey().equals("rainbow")) {
                settingsEntry.getValue().set(!settingsEntry.getValue().getAsBoolean());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  PetSheep petSheep = (PetSheep) user.getSelectedPet().getPetController().getEntity();
                  petSheep.setRainbow(settingsEntry.getValue().getAsBoolean());
                  petSheep.setColor(
                      MWoolColor.getByWoolData(user.getPetSettings(this.petType).stream().filter(pse -> pse.getKey().equals("sheepcolor")).findFirst().get().getValue().getAsInt()));
                }
              } else if (settingsEntry.getKey().equals("dyecolor")) {
                settingsEntry.getValue().set(MDyeColor.getByDyeData(settingsEntry.getValue().getAsInt()).getNextColor().getDyeData());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetWolf) user.getSelectedPet().getPetController().getEntity()).setColor(MDyeColor.getByDyeData(settingsEntry.getValue().getAsInt()));
                }
              } else if (settingsEntry.getKey().equals("rabbit")) {
                settingsEntry.getValue().set(MRabbitType.getById(settingsEntry.getValue().getAsInt()).getNextType().getId());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetRabbit) user.getSelectedPet().getPetController().getEntity()).setType(MRabbitType.getById(settingsEntry.getValue().getAsInt()));
                }
              } else if (settingsEntry.getKey().equals("profession")) {
                settingsEntry.getValue().set(MVillagerProfession.getById(settingsEntry.getValue().getAsInt()).getNextProfession().getId());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetVillager) user.getSelectedPet().getPetController().getEntity()).setProfession(MVillagerProfession.getById(settingsEntry.getValue().getAsInt()));
                }
              } else if (settingsEntry.getKey().equals("cave")) {
                settingsEntry.getValue().set(!settingsEntry.getValue().getAsBoolean());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  user.getSelectedPet().respawn();
                }
              } else if (settingsEntry.getKey().equals("slimesize")) {
                settingsEntry.getValue().set(MSlimeSize.getBySize(settingsEntry.getValue().getAsInt()).getNextSize().getSize());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetSlime) user.getSelectedPet().getPetController().getEntity()).setSize(MSlimeSize.getBySize(settingsEntry.getValue().getAsInt()));
                }
              } else if (settingsEntry.getKey().equals("slimemagma")) {
                settingsEntry.getValue().set(!settingsEntry.getValue().getAsBoolean());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  user.getSelectedPet().respawn();
                }
              } else if (settingsEntry.getKey().equals("charged")) {
                settingsEntry.getValue().set(!settingsEntry.getValue().getAsBoolean());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetCreeper) user.getSelectedPet().getPetController().getEntity()).setPowered(settingsEntry.getValue().getAsBoolean());
                }
              } else if (settingsEntry.getKey().equals("villager")) {
                settingsEntry.getValue().set(!settingsEntry.getValue().getAsBoolean());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetZombie) user.getSelectedPet().getPetController().getEntity()).setVillager(settingsEntry.getValue().getAsBoolean());
                }
              } else if (settingsEntry.getKey().equals("horsecolor")) {
                settingsEntry.getValue().set(MHorseColor.getById(settingsEntry.getValue().getAsInt()).getNextColor().getId());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetHorse) user.getSelectedPet().getPetController().getEntity()).setColor(MHorseColor.getById(settingsEntry.getValue().getAsInt()));
                }
              } else if (settingsEntry.getKey().equals("horsetype")) {
                settingsEntry.getValue().set(MHorseType.getById(settingsEntry.getValue().getAsInt()).getNextType().getId());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetHorse) user.getSelectedPet().getPetController().getEntity()).setType(MHorseType.getById(settingsEntry.getValue().getAsInt()));
                }
              } else if (settingsEntry.getKey().equals("ocelot")) {
                settingsEntry.getValue().set(MOcelotType.getById(settingsEntry.getValue().getAsInt()).getNextType().getId());
                if (user.getSelectedPet() != null && user.getSelectedPet().getPetType().equals(this.petType)) {
                  ((PetOcelot) user.getSelectedPet().getPetController().getEntity()).setType(MOcelotType.getById(settingsEntry.getValue().getAsInt()));
                }
              }
              
              new PetSettingsMenu(user, this.petType);
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.petType = null;
    this.entries.clear();
    this.entries = null;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}
