package net.redesky.skins;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import net.redesky.skins.plugin.utils.OlymConfig;
import net.redesky.skins.plugin.utils.OlymLogger;
import net.redesky.skins.plugin.utils.OlymWriter;
import net.redesky.skins.plugin.utils.StringUtils;

public class Language {
   @OlymWriter.YamlEntryInfo
   public static String options$default_skin$value = "eyJ0aW1lc3RhbXAiOjE1MTY4MzUxMTMxOTcsInByb2ZpbGVJZCI6IjIxMWNhN2E4ZWFkYzQ5ZTVhYjBhZjMzMTBlODY0M2NjIiwicHJvZmlsZU5hbWUiOiJGaXJld2FsbDEwMiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODYzZGU5OWRmMzYyODU4YzdjNWUxNWY3NzBiMDk3MjNlMWQ4MmZiNzM1ZWFlMzE5YzViYmMzNTdkYjEifX19";
   @OlymWriter.YamlEntryInfo
   public static String options$default_skin$signature = "iTHsgJ/0Xh9cc600MPmzcUeZ3zfa1jD/6yyZpZ5nx5nD2LB0XHe0s6FcaGnMfOIv3a8HFt4/qQxRELViaFQK35/QNG6T7jrkmkEqb4lOZNy9DCVpWzv+9fqwfd6GeJXYPILYquA/dMniMAaCgDptM1g4Rv2pMbJO8GekCaJV8l0yEclQS7CDqCSnAkiDILTYDQJRew0xvhxzvt8HvUeOiPdxH5nivbxN/XP1KQLrdn42DmrfvpBCOfITAJGHeWMMha3aPs/5bphRwSMVgYeMWtTdADc9IpmBfC/QzG9FY4rq9TCMbeCTKKqyc12Ei1VT0B4jIgrL99VA7r8bR7XjXSjyUyegX4lpmCTmv8v8FKgj0yazTzYqsQqpAvgfOFBAMBYTGCMJowc6JgTvbTDChNK0wJP0IaY/m/H6D3h8CTMszJRnzmCWpwvCYtgEJVTyTIjBCcL0zDTjxFaN13u1v0d3///QSSN9qteMZSlB3LTXT3sleWvtMZWerJs2H/zy6YWn9scpck2txMtq7lc5bCHifkqbYF27ghGsT6R9eKVcAM88HF56l4daCQ+ChsPIdRMq5MVIU6u8grlQwYztIBva3ANoRbTr6MNpjmU1vRWqzfeBYNbFGU9kK4K5s9Klbk8+3Rih/WbUSrRpSh89vgkXSiWuU/LGkoDDzc8inm0=";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$no_perm = " \n&eDesculpe você não possui permissão para alterar sua skin\n/-/&bCompre VIPs nossa loja para desbloquear esta função! : ttp>&7Clique aqui para ir até nossa loja! : url>https://server.com.br/loja/-/ \n";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$help = " \n§eAjuda - Skins\n \n§3/skin atualizar §f- §7Retomar a skin original da sua conta.\n§3/skin [jogador] §f- §7Utilizar a skin de outro jogador.\n ";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$cooldown = "§cVocê deve aguardar {time} para alterar sua skin novamente.";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$change = "§aAlterando sua skin para a de {nick}...";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$length = "§cNome de jogadores podem conter apenas de 2 a 16 caracteres.";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$limit = "§cVocê já está no limite máximo de skins.";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$exception = "§cFalha ao encontrar skin do jogador {nick}!";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$changed = "§aSua skin foi alterada com sucesso, relogue para visualisar.";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$update = "§aAtualizando sua skin....";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$updated = "§aSua skin foi atualizada com sucesso, relogue para visualisar.";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$removed = "§aSua skin foi removida com sucesso.";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$event = " \n§aDigite o nick da skin que você deseja no chat\n§7Ou clique /-/§7§lAQUI : ttp>§7Clique para cancelar! : exe>event.cancel_skin/-/ §7para cancelar essa operação\n ";
   @OlymWriter.YamlEntryInfo
   public static String messages$command$skin$event_cancelled = "§cMudança de skin cancelada.";
   @OlymWriter.YamlEntryInfo(
      annotation = "O menu não pode ser alterado no server bungee!"
   )
   public static String menu$skin$title = "Skins";
   @OlymWriter.YamlEntryInfo(
      annotation = "É recomendado que seja '6' para que não ocorra bugs"
   )
   public static int menu$skin$rows = 6;
   @OlymWriter.YamlEntryInfo
   public static int menu$skin$items$empty$slot = 22;
   @OlymWriter.YamlEntryInfo
   public static String menu$skin$items$empty$icon = "WEB : 1 : nome>§aVazio! : desc>§7Você não possui skins salvas no momento!";
   @OlymWriter.YamlEntryInfo
   public static int menu$skin$items$setskin$slot = 48;
   @OlymWriter.YamlEntryInfo
   public static String menu$skin$items$setskin$icon = "BOOK_AND_QUILL : 1 : nome>§aEscolher : desc>§7Você pode escolher uma nova skin\n§7para ser utilizada em sua conta.\n \n§fComando: §7/skin [jogador]\n \n§eClique para escolher uma skin.";
   @OlymWriter.YamlEntryInfo
   public static int menu$skin$items$update$slot = 49;
   @OlymWriter.YamlEntryInfo
   public static String menu$skin$items$update$icon = "ITEM_FRAME : 1 : nome>§aAtualizar : desc>&7Altere a sua skin para a mais recente\n&7utilizada em sua conta original!\n \n&8Caso você utilize minecraft pirata\\n&8sua skin será padronizada!\n \n&fComando:&7 /skin atualizar\n \n&eClique para atualizar sua skin!";
   @OlymWriter.YamlEntryInfo
   public static int menu$skin$items$help$slot = 50;
   @OlymWriter.YamlEntryInfo
   public static String menu$skin$items$help$icon = "SKULL_ITEM:3 : 1 : nome>§aAjuda : desc>§7As ações disponíveis neste menu também\n§7podem ser realizadas por comando.\n \n§fComando: §7/skin ajuda\n \n§eClique para listar os comandos. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19";
   @OlymWriter.YamlEntryInfo(
      annotation = "Descrição das skins"
   )
   public static String menu$skin$items$description = "§fUsada pela última vez em: §7{tempo}\n \n§eClique shift + clique direito para deletar!\n§aClique para utilizar essa skin!";
   @OlymWriter.YamlEntryInfo
   public static String menu$skin$items$description_using = "§fUsada pela última vez em: §7{tempo}\n \n§6Skin selecionada!";
   private static final OlymConfig CONFIG = OlymConfig.getConfig("language");
   public static final OlymLogger LOGGER;

   public static void setupSettings() {
      boolean save = false;
      OlymWriter writer = new OlymWriter(CONFIG.getFile(), "RedeSkySkins - Criado por RedeSky\nVersão da configuração: " + Core.getVersion());
      Field[] var2 = Language.class.getDeclaredFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field field = var2[var4];
         if (field.isAnnotationPresent(OlymWriter.YamlEntryInfo.class)) {
            OlymWriter.YamlEntryInfo entryInfo = (OlymWriter.YamlEntryInfo)field.getAnnotation(OlymWriter.YamlEntryInfo.class);
            String nativeName = field.getName().replace("$", ".").replace("_", "-");

            try {
               Object value = null;
               List l;
               ArrayList list;
               Iterator var11;
               Object v;
               if (CONFIG.contains(nativeName)) {
                  value = CONFIG.get(nativeName);
                  if (value instanceof String) {
                     value = StringUtils.formatColors((String)value).replace("\\n", "\n");
                  } else if (value instanceof List) {
                     l = (List)value;
                     list = new ArrayList(l.size());
                     var11 = l.iterator();

                     while(var11.hasNext()) {
                        v = var11.next();
                        if (v instanceof String) {
                           list.add(StringUtils.formatColors((String)v).replace("\\n", "\n"));
                        } else {
                           list.add(v);
                        }
                     }

                     l = null;
                     value = list;
                  }

                  field.set((Object)null, value);
                  writer.set(nativeName, new OlymWriter.YamlEntry(new Object[]{entryInfo.annotation(), CONFIG.get(nativeName)}));
               } else {
                  value = field.get((Object)null);
                  if (value instanceof String) {
                     value = StringUtils.deformatColors((String)value).replace("\n", "\\n");
                  } else if (value instanceof List) {
                     l = (List)value;
                     list = new ArrayList(l.size());
                     var11 = l.iterator();

                     while(var11.hasNext()) {
                        v = var11.next();
                        if (v instanceof String) {
                           list.add(StringUtils.deformatColors((String)v).replace("\n", "\\n"));
                        } else {
                           list.add(v);
                        }
                     }

                     l = null;
                     value = list;
                  }

                  save = true;
                  writer.set(nativeName, new OlymWriter.YamlEntry(new Object[]{entryInfo.annotation(), value}));
               }
            } catch (ReflectiveOperationException var13) {
               LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", var13);
            }
         }
      }

      if (save) {
         writer.write();
         CONFIG.reload();
         Core.LOGGER.info("A config §6language.yml §afoi modificada ou criada.");
      }

   }

   static {
      LOGGER = Core.LOGGER.getModule("LANGUAGE");
   }
}
