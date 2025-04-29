package mc.twilight.bedwars;

import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.config.KWriter;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("rawtypes")
public class Language {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger())
      .getModule("LANGUAGE");
  private static final KConfig CONFIG = Main.getInstance().getConfig("language");
  
  public static int options$coins$wins = 50;
  public static int options$coins$beds = 25;
  public static int options$coins$kills = 5;
  public static int options$start$waiting = 45;
  public static int options$start$full = 10;
  
  @KWriter.YamlEntryInfo(annotation = "Se você não definir o evento FIM a partida não terá fim por tempo.\nEventos disponíveis:\nFIM:tempo_em_segundos\nBEDDESTROY:tempo_em_segundos\nEMERALD:tempo_em_segundos\nDIAMOND:tempo_em_segundos")
  public static List<String> options$events$all$timings = Arrays
      .asList("DIAMOND:280", "EMERALD:480", "DIAMOND:680", "EMERALD:880", "BEDDESTROY:1240", "FIM:16840");
  @KWriter.YamlEntryInfo(annotation = "Quantos blocos serão regenerados por tick no BlockRegen")
  public static int options$regen$block_regen$per_tick = 20000;
  @KWriter.YamlEntryInfo(annotation = "Existem dois tipos de Regeneração de Arena: WorldReload e BlockRegen.\nÉ recomendável que teste os dois e veja qual se sai melhor.")
  public static boolean options$regen$world_reload = true;
  
  public static String options$events$diamond = "Diamante {tier}";
  public static String options$events$emerald = "Esmeralda {tier}";
  public static String options$events$beddestroy = "Destruição de Camas";
  public static String options$events$end = "Fim de Jogo";
  
  public static int options$generator$diamond$countdown_tier_1 = 30;
  public static int options$generator$diamond$countdown_tier_2 = 23;
  public static int options$generator$diamond$countdown_tier_3 = 15;
  public static int options$generator$emerald$countdown_tier_1 = 65;
  public static int options$generator$emerald$countdown_tier_2 = 50;
  public static int options$generator$emerald$countdown_tier_3 = 35;
  public static double options$team_generator$emerald$countdown = 7.0;
  public static double options$team_generator$gold$countdown = 3.0;
  public static double options$team_generator$iron$countdown = 1.0;
  
  public static long scoreboards$scroller$every_tick = 4;
  public static List<String> scoreboards$scroller$titles = Arrays
      .asList(
              "§a§lBED WARS",
              "§f§l§6§lB§a§lED WARS",
              "§f§lB§6§lE§a§lD WARS",
              "§f§lBE§6§lD §a§lWARS",
              "§f§lBED §6§lW§a§lARS",
              "§f§lBED W§6§lA§a§lRS",
              "§f§lBED WA§6§lR§a§lS",
              "§f§lBED WAR§6§lS",
              "§f§lBED WARS",
              "§f§lBED WAR§6§lS",
              "§f§lBED WA§6§lR§a§lS",
              "§f§lBED W§6§lA§a§lRS",
              "§f§lBED §6§lW§a§lARS",
              "§f§lBE§6§lD §a§lWARS",
              "§f§lB§6§lE§a§lD WARS",
              "§f§l§6§lB§a§lED WARS",
              "§a§lBED WARS",
              "§c§lB§a§lED WAR§c§lS",
              "§c§lBE§a§lD WA§c§lRS",
              "§c§lBED §a§lW§c§lARS",
              "§c§lBED W§c§lARS",
              "§c§lBED WARS",
              "§c§lBED WARS",
              "§c§lBED §a§lW§c§lARS",
              "§c§lBE§a§lD WA§c§lRS",
              "§c§lB§a§lED WAR§c§lS",
              "§a§lBED WARS"
      );
  public static String scoreboards$time$waiting = "Aguardando...";
  public static String scoreboards$time$starting = "Iniciando em §a{time}s";
  public static List<String> scoreboards$lobby = Arrays
      .asList("", " §eGeral", "  Vitorias: §a%zCore_BedWars_wins%", "  Abates Finais: §a%zCore_BedWars_finalkills%",
          "  Abates Gerais: §a%zCore_BedWars_kills%", "  Partidas: §a%zCore_BedWars_games%", "",
          " Coins: §6%zCore_BedWars_coins%", " Tokens: §b%zCore_cash%", "", " §7twilightmc.com.br", " ");
  public static List<String> scoreboards$waiting =
      Arrays.asList("", "  Mapa: §a{map}", "  Jogadores: §a{players}/{max_players}",
          "", "  {time}", "", "  §7twilightmc.com.br", "");
  public static List<String> scoreboards$ingame = Arrays
      .asList("", " Próximo Evento:", " §a{next_event}", "", " {red}", " {pink}", " {aqua}",
          " {blue}", " {white}", " {orange}", " {purple}", " {green}", "", " §7twilightmc.com.br", " ");
  
  public static String chat$delay = "§cAguarde mais {time}s para falar novamente.";
  public static String chat$color$default = "§7";
  public static String chat$color$custom = "§f";
  public static String chat$format$ingame$team = "§7[TIME] {player}{color}: {message}";
  public static String chat$format$ingame$global = "§6[GLOBAL] {team} {player}{color}: {message}";
  public static String chat$format$lobby = "{player}{color}: {message}";
  public static String chat$format$spectator = "§8[Espectador] {player}{color}: {message}";
  
  public static String lobby$achievement = " \n§aVocê completou o desafio §f{name}\n ";
  public static String lobby$broadcast = "{player} §dpulou para o servidor!";
  public static boolean lobby$tab$enabled = true;
  public static String lobby$tab$header = " \n§b§lTWILIGHT\n  §ftwilightmc.com.br\n ";
  public static String lobby$tab$footer =
          " \n \n§aForúm: §ftwilightmc.com/forum\n§aDiscord: §fdc.gg/twilightsv\n \n                                          §bAdquira VIP acessando nosso discord                                          \n ";
  
  public static long lobby$leaderboard$minutes = 30;
  public static String lobby$leaderboard$empty = "§7Ninguém";
  
  public static List<String> lobby$leaderboard$wins$hologram = Arrays
      .asList("{monthly_color}Mensal {total_color}Total", "§6§lClique para alternar!", "§a10. {name_10} §7- §a{stats_10}", "§a9. {name_9} §7- §a{stats_9}",
          "§a8. {name_8} §7- §a{stats_8}", "§a7. {name_7} §7- §a{stats_7}",
          "§a6. {name_6} §7- §a{stats_6}",
          "§a5. {name_5} §7- §a{stats_5}", "§a4. {name_4} §7- §a{stats_4}",
          "§a3. {name_3} §7- §a{stats_3}", "§a2. {name_2} §7- §a{stats_2}",
          "§a1. {name_1} §7- §a{stats_1}", "",
          "§7Ranking de Vitórias", "§f§lTodos os Modos");
  public static List<String> lobby$leaderboard$kills$hologram = Arrays
      .asList("{monthly_color}Mensal {total_color}Total", "§6§lClique para alternar!", "§a10. {name_10} §7- §a{stats_10}", "§a9. {name_9} §7- §a{stats_9}",
          "§a8. {name_8} §7- §a{stats_8}", "§a7. {name_7} §7- §a{stats_7}",
          "§a6. {name_6} §7- §a{stats_6}",
          "§a5. {name_5} §7- §a{stats_5}", "§a4. {name_4} §7- §a{stats_4}",
          "§a3. {name_3} §7- §a{stats_3}", "§a2. {name_2} §7- §a{stats_2}",
          "§a1. {name_1} §7- §a{stats_1}", "",
          "§7Ranking de Abates Finais", "§f§lTodos os Modos");
  public static List<String> lobby$leaderboard$beds$hologram = Arrays
      .asList("{monthly_color}Mensal {total_color}Total", "§6§lClique para alternar!", "§a10. {name_10} §7- §a{stats_10}", "§a9. {name_9} §7- §a{stats_9}",
          "§a8. {name_8} §7- §a{stats_8}", "§a7. {name_7} §7- §a{stats_7}",
          "§a6. {name_6} §7- §a{stats_6}",
          "§a5. {name_5} §7- §a{stats_5}", "§a4. {name_4} §7- §a{stats_4}",
          "§a3. {name_3} §7- §a{stats_3}", "§a2. {name_2} §7- §a{stats_2}",
          "§a1. {name_1} §7- §a{stats_1}", "",
          "§7Ranking de Camas Destruídas", "§f§lTodos os Modos");
  
  public static String lobby$npc$play$connect = "§aConectando...";
  public static String lobby$npc$play$menu$info$item = "PAPER : 1 : nome>§aInformações : desc>{desc}";
  public static String lobby$npc$play$menu$info$desc_limit =
      "§fLimite Diário: §7{limit}\n \n§7Jogadores que possuem o grupo §dMystic §7ou\n§7superior, podem escolher o mapa sem\n§7limite algum.\n \n&7twilightmc.com.br";
  public static String lobby$npc$play$menu$info$desc_not_limit = "§7Você não possui limite diário de seleções.";
  
  public static String lobby$npc$deliveries$deliveries = "§c{deliveries} Entrega{s}";
  public static List<String> lobby$npc$deliveries$hologram = Arrays
      .asList("{deliveries}", "§bEntregador", "§e§lCLIQUE DIREITO");
  public static List<String> lobby$npc$stats$hologram = Arrays
      .asList("§6Estatísticas", "Total de Eliminações: §7%zCore_BedWars_kills%",
          "Total de Vitórias: §7%zCore_BedWars_wins%", "§e§lCLIQUE DIREITO");
  public static List<String> lobby$npc$play$dupla$hologram = Arrays
      .asList("§bDuplas", "§a{players} Jogadores");
  public static List<String> lobby$npc$play$solo$hologram = Arrays
      .asList("§bSolo", "§a{players} Jogadores");
  public static List<String> lobby$npc$play$quarteto$hologram = Arrays
      .asList("§bQuartetos", "§a{players} Jogadores");
  public static String lobby$npc$deliveries$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1ODM0NTc4OTkzMTksInByb2ZpbGVJZCI6IjIxMWNhN2E4ZWFkYzQ5ZTVhYjBhZjMzMTBlODY0M2NjIiwicHJvZmlsZU5hbWUiOiJNYXh0ZWVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MWU0NTc3OTgzZjEzZGI2YTRiMWMwNzQ1MGUyNzQ2MTVkMDMyOGUyNmI0MGQ3ZDMyMjA3MjYwOWJmZGQ0YTA4IiwibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn19fX0=";
  public static String lobby$npc$deliveries$skin$signature =
      "SXnMF3f9x90fa+FdP2rLk/V6/zvMNuZ0sC4RQpPHF9JxdVWYRZm/+DhxkfjCHWKXV/4FSTN8LPPsxXd0XlYSElpi5OaT9/LGhITSK6BbeBfaYhLZnoD0cf9jG9nl9av38KipnkNXI+cO3wttB27J7KHznAmfrJd5bxdO/M0aGQYtwpckchYUBG6pDzaxN7tr4bFxDdxGit8Tx+aow/YtYSQn4VilBIy2y/c2a4PzWEpWyZQ94ypF5ZojvhaSPVl88Fbh+StdgfJUWNN3hNWt31P68KT4Jhx+SkT2LTuDj0jcYsiuxHP6AzZXtOtPPARqM0/xd53CUHCK+TEF5mkbJsG/PZYz/JRR1B1STk4D2cgbhunF87V4NLmCBtF5WDQYid11eO0OnROSUbFduCLj0uJ6QhNRRdhSh54oES7vTi0ja3DftTjdFhPovDAXQxCn+ROhTeSxjW5ZvP6MpmJERCSSihv/11VGIrVRfj2lo9MaxRogQE3tnyMNKWm71IRZQf806hwSgHp+5m2mhfnjYeGRZr44j21zqnSKudDHErPyEavLF83ojuMhNqTTO43ri3MVbMGix4TbIOgB2WDwqlcYLezENBIIkRsYO/Y1r5BWCA7DJ5IlpxIr9TCu39ppVmOGReDWA/Znyox5GP6JIM53kQoTOFBM3QWIQcmXll4=";
  public static String lobby$npc$play$dupla$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1MjM1Njk3MjI0OTgsInByb2ZpbGVJZCI6IjdiM2QxNGQ2YzExZDRjODA5NTc1ZjI5ODczNGE0ZDFiIiwicHJvZmlsZU5hbWUiOiJUYWxvbkRldiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQwMTdhYmQ5ZjExZTlkZTM4ODBkNGM0OTAxODUzNTdiOGY4ZmY1NGM3MzA2Mzg2ZTgyYWQ1NjdhNTMwMzMifX19";
  public static String lobby$npc$play$dupla$skin$signature =
      "i7k5tYkZ0CJ1hnGrGELLVXjIi0hfVVtg+c4a/iXP4wOwvAPj6tQtExFWgGaZYnYhN6ldcjJKUw13a/TRwHi4er4OceOlxBgqSvc0zzT7U4iZsEUuCwv7r9t6a+3MELqSQe3/bbX6WP6pDA9TRSVWaCTGpBtZfAYyrszk+VTowMjKrDB7r/kzrhE+h2rSozVcv4fUMGOd4m8xbTPlcvBatZ9OcHfZEpuoTpECUq3tWH3GIJi+Uxz3rTVl5rKJdKLOeUVXLpiLSgQ0jybMy705WlB0NWFbWFkY0mEQU7yca6keopEsGaQ+36yEtcE4hKYhibqW2sFhne/wIZh5arwyXVv/04twL/dpdiBwg4nqGEO60i+tQoF9RVWeCmIwJizEn3+WO6H2QogfCy+W1vNO65/HoHlhVbC6Y6nkUUQ8r0jtqz/sBQVAEBhFDjOQcdFucyjnO4LXrZPajdzJtBhkottBZDQZQlbFoZxC47WpQ+sktc51SWT2f3BzMowRKg08R8xpZxMTf+bB5OldilMuDPggXF/wVQU4+N9OFo1qYNxRPtM/7DCP8dtS7pwfhJkRhnQOfBVu7/mkNX1EM3mlMRzhEiUmqXfhL3SSyzTzqdTB76JgrRF92zuW+ouUlnXHe4hWiaWvRQ1XHB4fc+HOQ6/1RMYb4NItJFte1tjcQQs=";
  public static String lobby$npc$play$solo$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1MjM1Njk3MjI0OTgsInByb2ZpbGVJZCI6IjdiM2QxNGQ2YzExZDRjODA5NTc1ZjI5ODczNGE0ZDFiIiwicHJvZmlsZU5hbWUiOiJUYWxvbkRldiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQwMTdhYmQ5ZjExZTlkZTM4ODBkNGM0OTAxODUzNTdiOGY4ZmY1NGM3MzA2Mzg2ZTgyYWQ1NjdhNTMwMzMifX19";
  public static String lobby$npc$play$solo$skin$signature =
      "i7k5tYkZ0CJ1hnGrGELLVXjIi0hfVVtg+c4a/iXP4wOwvAPj6tQtExFWgGaZYnYhN6ldcjJKUw13a/TRwHi4er4OceOlxBgqSvc0zzT7U4iZsEUuCwv7r9t6a+3MELqSQe3/bbX6WP6pDA9TRSVWaCTGpBtZfAYyrszk+VTowMjKrDB7r/kzrhE+h2rSozVcv4fUMGOd4m8xbTPlcvBatZ9OcHfZEpuoTpECUq3tWH3GIJi+Uxz3rTVl5rKJdKLOeUVXLpiLSgQ0jybMy705WlB0NWFbWFkY0mEQU7yca6keopEsGaQ+36yEtcE4hKYhibqW2sFhne/wIZh5arwyXVv/04twL/dpdiBwg4nqGEO60i+tQoF9RVWeCmIwJizEn3+WO6H2QogfCy+W1vNO65/HoHlhVbC6Y6nkUUQ8r0jtqz/sBQVAEBhFDjOQcdFucyjnO4LXrZPajdzJtBhkottBZDQZQlbFoZxC47WpQ+sktc51SWT2f3BzMowRKg08R8xpZxMTf+bB5OldilMuDPggXF/wVQU4+N9OFo1qYNxRPtM/7DCP8dtS7pwfhJkRhnQOfBVu7/mkNX1EM3mlMRzhEiUmqXfhL3SSyzTzqdTB76JgrRF92zuW+ouUlnXHe4hWiaWvRQ1XHB4fc+HOQ6/1RMYb4NItJFte1tjcQQs=";
  public static String lobby$npc$play$quarteto$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1MjM1Njk3MjI0OTgsInByb2ZpbGVJZCI6IjdiM2QxNGQ2YzExZDRjODA5NTc1ZjI5ODczNGE0ZDFiIiwicHJvZmlsZU5hbWUiOiJUYWxvbkRldiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQwMTdhYmQ5ZjExZTlkZTM4ODBkNGM0OTAxODUzNTdiOGY4ZmY1NGM3MzA2Mzg2ZTgyYWQ1NjdhNTMwMzMifX19";
  public static String lobby$npc$play$quarteto$skin$signature =
      "i7k5tYkZ0CJ1hnGrGELLVXjIi0hfVVtg+c4a/iXP4wOwvAPj6tQtExFWgGaZYnYhN6ldcjJKUw13a/TRwHi4er4OceOlxBgqSvc0zzT7U4iZsEUuCwv7r9t6a+3MELqSQe3/bbX6WP6pDA9TRSVWaCTGpBtZfAYyrszk+VTowMjKrDB7r/kzrhE+h2rSozVcv4fUMGOd4m8xbTPlcvBatZ9OcHfZEpuoTpECUq3tWH3GIJi+Uxz3rTVl5rKJdKLOeUVXLpiLSgQ0jybMy705WlB0NWFbWFkY0mEQU7yca6keopEsGaQ+36yEtcE4hKYhibqW2sFhne/wIZh5arwyXVv/04twL/dpdiBwg4nqGEO60i+tQoF9RVWeCmIwJizEn3+WO6H2QogfCy+W1vNO65/HoHlhVbC6Y6nkUUQ8r0jtqz/sBQVAEBhFDjOQcdFucyjnO4LXrZPajdzJtBhkottBZDQZQlbFoZxC47WpQ+sktc51SWT2f3BzMowRKg08R8xpZxMTf+bB5OldilMuDPggXF/wVQU4+N9OFo1qYNxRPtM/7DCP8dtS7pwfhJkRhnQOfBVu7/mkNX1EM3mlMRzhEiUmqXfhL3SSyzTzqdTB76JgrRF92zuW+ouUlnXHe4hWiaWvRQ1XHB4fc+HOQ6/1RMYb4NItJFte1tjcQQs=";
 
  public static String cosmetics$color$locked = "§a";
  public static String cosmetics$color$canbuy = "§e";
  public static String cosmetics$color$unlocked = "§a";
  public static String cosmetics$color$selected = "§6";
  
  public static String cosmetics$icon$perm_desc$common = "§cVocê não possui permissão.";
  public static String cosmetics$icon$perm_desc$role = "§7Exclusivo para {role} §7ou superior.";
  public static String cosmetics$icon$buy_desc$enough = "§cVocê não possui saldo suficiente.";
  public static String cosmetics$icon$buy_desc$click_to_buy = "§eClique para comprar!";
  public static String cosmetics$icon$has_desc$select = "§eClique para selecionar!";
  public static String cosmetics$icon$has_desc$selected = "§eClique para deselecionar!";
  
  public static String cosmetics$kill_effect$icon$perm_desc$start = "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$kill_effect$icon$buy_desc$start =
      "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$kill_effect$icon$has_desc$start = "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  
  public static String cosmetics$shopkeeperskin$icon$perm_desc$start =
      "§7Altera a skin da Loja para {name}\n&7durante a partida.\n  \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$shopkeeperskin$icon$buy_desc$start =
      "§7Altera a skin da Loja para {name}\n&7durante a partida.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$shopkeeperskin$icon$has_desc$start =
      "§7Altera a skin da Loja para {name}\n&7durante a partida.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
 
  public static String cosmetics$break_effect$icon$perm_desc$start =
      "§7Quando você quebrar uma cama\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$break_effect$icon$buy_desc$start =
      "§7Quando você quebrar uma cama\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$break_effect$icon$has_desc$start =
      "§7Quando você quebrar uma cama\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  
  public static String cosmetics$deathcry$icon$perm_desc$start =
      "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§6Clique direito para escutar!\n  \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$deathcry$icon$buy_desc$start =
      "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n§6Clique direito para escutar!\n \n{buy_desc_status}";
  public static String cosmetics$deathcry$icon$has_desc$start =
      "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§6Clique direito para escutar!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  
  public static String cosmetics$projectile_effect$icon$perm_desc$start =
      "§7Quando você jogar um projétil\n§7sairá partículas de {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$projectile_effect$icon$buy_desc$start =
      "§7Quando você jogar um projétil\n§7sairá partículas de {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$projectile_effect$icon$has_desc$start =
      "§7Quando você jogar um projétil\n§7sairá partículas de {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
 
  public static String cosmetics$fall_effect$icon$perm_desc$start =
      "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$fall_effect$icon$buy_desc$start =
      "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$fall_effect$icon$has_desc$start =
      "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  
  public static String cosmetics$teleport_effect$icon$perm_desc$start =
      "§7Quando você se teleportar\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$teleport_effect$icon$buy_desc$start =
      "§7Quando você se teleportar\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$teleport_effect$icon$has_desc$start =
      "§7Quando você se teleportar\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  
  public static String cosmetics$wood_types$icon$perm_desc$start =
      "§7Costumizar a madeira do \n§7Vendedor para {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$wood_types$icon$buy_desc$start =
      "§7Costumizar a madeira do \n§7Vendedor para {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$wood_types$icon$has_desc$start =
      "§7Costumizar a madeira do \n§7Vendedor para {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  
  public static String cosmetics$death_message$icon$perm_desc$start =
      "\n§6Clique direito para ver!\n \n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$death_message$icon$buy_desc$start =
      "\n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$death_message$icon$has_desc$start =
      "\n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
 
  public static String cosmetics$cage$icon$perm_desc$start =
      "§7Altere sua jaula para {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$cage$icon$buy_desc$start =
      "§7Altere sua jaula para {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins\n \n{buy_desc_status}";
  public static String cosmetics$cage$icon$has_desc$start =
      "§7Altere sua jaula para {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";

  public static String cosmetics$balloon$icon$perm_desc$start = "§7Altera o balão decorativo da ilha\n§7para {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$balloon$icon$buy_desc$start =
      "§7Altera o balão decorativo da ilha\n§7para {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$balloon$icon$has_desc$start = "§7Altera o balão decorativo da ilha\n§7para {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
 
  public static String cosmetics$win_animation$icon$perm_desc$start = "\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$win_animation$icon$buy_desc$start = "\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$win_animation$icon$has_desc$start = "\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
 
  public static List<String> ingame$generators$hologram = Arrays
      .asList("§eNível §c{tier}", "{type}", "§eGera em §c{time} §esegundo{s}");
  public static List<String> ingame$npc$shop$item$hologram = Arrays
      .asList("§e§lLOJA DE", "§e§lITENS", "§b§lCLIQUE DIREITO");
  public static List<String> ingame$npc$shop$upgrade$hologram = Arrays
      .asList("§e§lLOJA DE", "§e§lMELHORIAS", "§b§lCLIQUE DIREITO");
  
  public static String ingame$broadcast$join = "{player} §eentrou na partida! §a({players}/{max_players})";
  public static String ingame$broadcast$leave = "{player} §csaiu da partida! §a({players}/{max_players})";
  public static String ingame$broadcast$starting = "§aO jogo começa em §f{time} §asegundo{s}.";
  public static String ingame$broadcast$suicide = "{name} §amorreu sozinho.";
  public static String ingame$broadcast$killed = "{name} §afoi abatido por {killer}";
  public static String ingame$broadcast$generator_upgrade$diamond = "§eOs geradores de §b§lDiamante §eforam aprimorados para o nivel §b§l{tier}§e!";
  public static String ingame$broadcast$generator_upgrade$emerald = "§eOs geradores de §2§lEsmeralda §eforam aprimorados para o nivel §b§l{tier}§e!";
  public static String ingame$broadcast$team_eliminated = "\n §f§lTIME ELIMINADO > §cO time {team} §cfoi eliminado!\n ";
  public static String ingame$broadcast$bed_destroyself = "\n §f§lCAMA DESTRUIDA > §7A sua cama foi destruída por {name}\n ";
  public static String ingame$broadcast$bed_destroy = "\n §f§lCAMA DESTRUIDA > §7A cama do time {team}§7 foi destruída por {name}\n ";
  public static String ingame$broadcast$double_kill = "§a. §e§lDOUBLE KILL";
  public static String ingame$broadcast$triple_kill = "§a. §b§lTRIPLE KILL";
  public static String ingame$broadcast$quadra_kill = "§a. §6§lQUADRA KILL";
  public static String ingame$broadcast$monster_kill = "§a. §c§lMONSTER KILL";
  public static String ingame$broadcast$end = " \n§aO tempo acabou, não houve ganhadores.\n ";
  public static String ingame$broadcast$win$solo = " \n{name} §avenceu a partida!\n ";
  public static String ingame$broadcast$win$dupla = " \n{name} §avenceram a partida!\n ";
  public static String ingame$titles$death$header = "§c§lVOCE MORREU";
  public static String ingame$titles$death$footer = "§7Você agora é um espectador";
  public static String ingame$titles$win$header = "§a§lVITÓRIA";
  public static String ingame$titles$win$footer = "§7Seu time venceu!";
  public static String ingame$titles$beddestroy_self$header = "§c§lCAMA DESTRUIDA";
  public static String ingame$titles$beddestroy_self$footer = "§7Você não renascerá mais.";
  public static String ingame$titles$lose$header = "§c§lFIM DE JOGO";
  public static String ingame$titles$lose$footer = "§7Você não foi vitorioso dessa vez";
  public static String ingame$messages$bow$hit = "{name} §aestá com §c{hp} §ade HP.";
  public static String ingame$messages$coins$base = " \n  §a{coins} coins ganhos nesta partida:\n {coins_win}{coins_beds}{coins_kills}\n ";
  public static String ingame$messages$coins$win = "\n       §a+{coins} §fpor vencer o jogo";
  public static String ingame$messages$coins$beds = "\n       §a+{coins} §fpor destruir §c{beds} §fcama{s}";
  public static String ingame$messages$coins$kills = "\n       §a+{coins} §fpor realizar §c{kills} §fabate{s}";
  
  public static void setupLanguage() {
    boolean save = false;
    KWriter writer = Main.getInstance().getWriter(CONFIG.getFile(),
        "Gestão de BedWars feito por eduzp\nVersão: " + Main.getInstance()
            .getDescription().getVersion());
    for (Field field : Language.class.getDeclaredFields()) {
      if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
        String nativeName = field.getName().replace("$", ".").replace("_", "-");
        
        try {
          Object value;
          KWriter.YamlEntryInfo entryInfo = field.getAnnotation(KWriter.YamlEntryInfo.class);
          
          if (CONFIG.contains(nativeName)) {
            value = CONFIG.get(nativeName);
            if (value instanceof String) {
              value = StringUtils.formatColors((String) value).replace("\\n", "\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.formatColors((String) v).replace("\\n", "\n"));
                } else {
                  list.add(v);
                }
              }
              
              value = list;
            }
            
            field.set(null, value);
            writer.set(nativeName, new KWriter.YamlEntry(
                new Object[]{entryInfo == null ? "" : entryInfo.annotation(),
                    CONFIG.get(nativeName)}));
          } else {
            value = field.get(null);
            if (value instanceof String) {
              value = StringUtils.deformatColors((String) value).replace("\n", "\\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.deformatColors((String) v).replace("\n", "\\n"));
                } else {
                  list.add(v);
                }
              }
              
              value = list;
            }
            
            save = true;
            writer.set(nativeName, new KWriter.YamlEntry(new Object[]{entryInfo == null ? "" : entryInfo.annotation(), value}));
          }
        } catch (ReflectiveOperationException e) {
          LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
        }
      }
    }
    
    if (save) {
      writer.write();
      CONFIG.reload();
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
          () -> LOGGER.info("§bO idioma do plugin foi modificado."));
    }
  }
}
