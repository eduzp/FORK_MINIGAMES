package mc.twilight.bedwars.game;

import com.google.gson.JsonObject;
import mc.twilight.core.Core;
import mc.twilight.core.Manager;
import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.cosmetics.types.*;
import mc.twilight.bedwars.hook.container.SelectedContainer;
import mc.twilight.bedwars.cosmetics.CosmeticType;
import mc.twilight.bedwars.game.enums.BedWarsMode;
import mc.twilight.bedwars.game.generators.Generator;
import mc.twilight.bedwars.game.interfaces.LoadCallback;
import mc.twilight.bedwars.game.object.BedWarsBlock;
import mc.twilight.bedwars.game.object.BedWarsConfig;
import mc.twilight.bedwars.game.object.BedWarsTask;
import mc.twilight.bedwars.utils.tagger.TagUtils;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.bukkit.BukkitParty;
import mc.twilight.core.bukkit.BukkitPartyManager;
import mc.twilight.core.game.FakeGame;
import mc.twilight.core.game.Game;
import mc.twilight.core.game.GameState;
import mc.twilight.core.game.GameTeam;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.party.PartyPlayer;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.CubeID;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static mc.twilight.bedwars.hook.BWCoreHook.reloadScoreboard;

public class BedWars implements Game<BedWarsTeam> {
  
  public static final DecimalFormat TRACKING_FORMAT = new DecimalFormat("###.#");
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("GAME");
  public static final List<BedWars> QUEUE = new ArrayList<>();
  private static final SimpleDateFormat SDF = new SimpleDateFormat("mm:ss");
  public static final Map<String, BedWars> GAMES = new HashMap<>();
  
  protected String name;
  protected BedWarsConfig config;
  protected int timer;
  protected GameState state;
  protected BedWarsTask task;
  protected List<UUID> players;
  protected List<UUID> spectators;
  protected List<Generator> generators;
  protected Map<String, Integer> kills;
  protected Map<String, Integer> beds;
  protected Map<String, BedWarsBlock> blocks = new HashMap<>();
  protected List<Block> placedBlocks;
  private List<Map.Entry<String, Integer>> topKills = new ArrayList<>();
  private Map<String, Object[]> streak = new HashMap<>();
  private Map.Entry<Integer, BedWarsEvent> event;
  private Map.Entry<Integer, BedWarsEvent> nextEvent;
  
  public BedWars(String name, LoadCallback callback) {
    this.name = name;
    this.task = new BedWarsTask(this);
    this.config = new BedWarsConfig(this);
    this.config.setupSpawns();
    this.generators = new ArrayList<>();
    this.config.listGenerators().forEach(location -> this.generators.add(new Generator(this, Generator.Type.fromName(location.split(", ")[1]),
        BukkitUtils.deserializeLocation(location.split(", ")[0]))));
    this.players = new ArrayList<>();
    this.kills = new HashMap<>();
    this.placedBlocks = new ArrayList<>();
    this.beds = new HashMap<>();
    this.spectators = new ArrayList<>();
    this.state = GameState.AGUARDANDO;
    this.task.reset();
    
    if (!Language.options$regen$world_reload) {
      KConfig config = Main.getInstance().getConfig("blocos", name);
      if (config.contains("dataBlocks")) {
        for (String blockdata : config.getStringList("dataBlocks")) {
          blocks.put(blockdata.split(" : ")[0],
              new BedWarsBlock(Material.matchMaterial(blockdata.split(" : ")[1].split(", ")[0]), Byte.parseByte(blockdata.split(" : ")[1].split(", ")[1])));
        }
      } else {
        this.state = GameState.ENCERRADO;
        ArenaRollbackerTask.scan(this, config, callback);
      }
    } else if (callback != null) {
      callback.finish();
    }
  }
  
  public static void addToQueue(BedWars game) {
    if (QUEUE.contains(game)) {
      return;
    }
    
    QUEUE.add(game);
  }
  
  public static void setupGames() {
    BedWarsEvent.setupEvents();
    new ArenaRollbackerTask().runTaskTimer(Main.getInstance(), 0, Language.options$regen$world_reload ? 100 : 1);
    
    File ymlFolder = new File("plugins/zBedWars/arenas");
    File mapFolder = new File("plugins/zBedWars/mundos");
    
    if (!ymlFolder.exists() || !mapFolder.exists()) {
      if (!ymlFolder.exists()) {
        ymlFolder.mkdirs();
      }
      if (!mapFolder.exists()) {
        mapFolder.mkdirs();
      }
    }
    
    for (File file : ymlFolder.listFiles()) {
      load(file, null);
    }
    
    LOGGER.info("Foram carregadas " + GAMES.size() + " salas.");
  }
  
  public static void load(File yamlFile, LoadCallback callback) {
    String arenaName = yamlFile.getName().split("\\.")[0];
    
    try {
      File backup = new File("plugins/zBedWars/mundos", arenaName);
      if (!backup.exists() || !backup.isDirectory()) {
        throw new IllegalArgumentException("Backup do mapa nao encontrado para a arena \"" + yamlFile.getName() + "\"");
      }
      
      BedWars game = new BedWars(arenaName, callback);
      game.getWorld().getEntities().forEach(Entity::remove);
      GAMES.put(arenaName, game);
    } catch (Exception ex) {
      LOGGER.log(Level.WARNING, "load(\"" + yamlFile.getName() + "\"): ", ex);
    }
  }
  
  public static BedWars getByWorldName(String worldName) {
    return GAMES.get(worldName);
  }
  
  public static int getWaiting(BedWarsMode mode) {
    int waiting = 0;
    List<BedWars> games = listByMode(mode);
    for (BedWars game : games) {
      if (game.getState() != GameState.EMJOGO) {
        waiting += game.getOnline();
      }
    }
    
    return waiting;
  }
  
  public static int getPlaying(BedWarsMode mode) {
    int playing = 0;
    List<BedWars> games = listByMode(mode);
    for (BedWars game : games) {
      if (game.getState() == GameState.EMJOGO) {
        playing += game.getOnline();
      }
    }
    
    return playing;
  }
  
  public static BedWars findRandom(BedWarsMode mode) {
    List<BedWars> games = GAMES.values().stream().filter(game -> game.getMode().equals(mode) && game.getState().canJoin() && game.getOnline() < game.getMaxPlayers())
        .sorted((g1, g2) -> Integer.compare(g2.getOnline(), g1.getOnline())).collect(Collectors.toList());
    BedWars game = games.stream().findFirst().orElse(null);
    if (game != null && game.getOnline() == 0) {
      game = games.get(ThreadLocalRandom.current().nextInt(games.size()));
    }
    
    return game;
  }
  
  public static Map<String, List<BedWars>> getAsMap(BedWarsMode mode) {
    Map<String, List<BedWars>> result = new HashMap<>();
    GAMES.values().stream().filter(
        game -> game.getMode().equals(mode) && game.getState().canJoin() && game.getOnline() < game
            .getMaxPlayers()).forEach(game -> {
      List<BedWars> list = result.computeIfAbsent(game.getMapName(), k -> new ArrayList<>());
      
      if (game.getState().canJoin() && game.getOnline() < game.getMaxPlayers()) {
        list.add(game);
      }
    });
    
    return result;
  }
  
  public static List<BedWars> listByMode(BedWarsMode mode) {
    return GAMES.values().stream().filter(bw -> bw.getMode().equals(mode))
        .collect(Collectors.toList());
  }
  
  public boolean isPlacedBlock(Block block) {
    return !placedBlocks.contains(block);
  }
  
  public void addPlacedBlock(Block block) {
    placedBlocks.add(block);
  }
  
  public void destroyBed(BedWarsTeam team, Profile breaker) {
    team.breakBed();
    
    if (breaker == null) {
      return;
    }
    
    breaker.addStats("zCoreBedWars", this.getMode().getStats() + "bedsdestroyeds");
    breaker.addCoinsWM("zCoreBedWars", Language.options$coins$beds);
    
    this.beds.put(breaker.getName(), (this.beds.get(breaker.getName()) == null ? 0 : this.beds.get(breaker.getName())) + 1);
    
    breaker.addStats("zCoreBedWars", "monthlybeds");
    
    BreakEffect dc = breaker.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.BREAK_EFFECT, BreakEffect.class);
    if (dc != null) {
      this.listPlayers(true).forEach(a -> dc.showIn(a, team.getBedLocation()));
    }
    
    team.listPlayers().forEach(t -> Profile.getProfile(t.getName()).addStats("zCoreBedWars", this.getMode().getStats() + "bedslosteds"));
    
    for (Player player : this.listPlayers(true)) {
      String message = team.hasMember(player) ? Language.ingame$broadcast$bed_destroyself.replace("{name}", getTeam(breaker.getPlayer()).getColored(breaker.getPlayer())) : Language.ingame$broadcast$bed_destroy.replace("{team}", team.getName()).replace("{name}", getTeam(breaker.getPlayer()).getColored(breaker.getPlayer()));
      
      player.sendMessage(message);
      EnumSound.ENDERDRAGON_GROWL.play(player, 1.0F, 1.0F);
      if (team.hasMember(player)) {
        NMS.sendTitle(player, Language.ingame$titles$beddestroy_self$header, Language.ingame$titles$beddestroy_self$footer);
      }
    }
  }
  
  @Override
  public void broadcastMessage(String message) {
    this.broadcastMessage(message, true);
  }
  
  @Override
  public void broadcastMessage(String message, boolean spectators) {
    this.listPlayers().forEach(player -> player.sendMessage(message));
  }
  
  public void addSpawn(JsonObject spawn) {
    this.listTeams().add(new BedWarsTeam(this, spawn.toString(), this.getMode().getSize()));
    this.config.listSpawns().add(spawn.toString());
    this.config.getConfig().set("spawns", this.config.listSpawns());
  }
  
  public void addGenerator(Location location, Generator.Type type) {
    String serialized = BukkitUtils.serializeLocation(location) + ", " + type.name();
    this.generators.add(new Generator(this, type, location));
    this.config.listGenerators().add(serialized);
    this.config.getConfig().set("generators", this.config.listGenerators());
  }
  
  private void joinParty(Profile profile, boolean ignoreLeader) {
    Player player = profile.getPlayer();
    if (player == null || !this.state.canJoin() || this.players.size() >= this.getMaxPlayers()) {
      return;
    }
    
    if (profile.getGame() != null && profile.getGame().equals(this)) {
      return;
    }
    
    BedWarsTeam team = null;
    boolean fullSize = false;
    
    BukkitParty party = BukkitPartyManager.getMemberParty(player.getName());
    if (party != null) {
      if (!ignoreLeader) {
        if (!party.isLeader(player.getName())) {
          player.sendMessage("§cApenas o líder da Party pode buscar por partidas.");
          return;
        }
        
        if (party.onlineCount() + players.size() > getMaxPlayers()) {
          return;
        }
        
        fullSize = true;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
            () -> party.listMembers().stream().filter(PartyPlayer::isOnline).map(pp -> Profile.getProfile(pp.getName()))
                .filter(pp -> pp != null && pp.getGame(FakeGame.class) == null).forEach(pp -> joinParty(pp, true)), 5);
      } else {
        team =
            listTeams().stream().filter(st -> st.canJoin() && (party.listMembers().stream().anyMatch(pp -> pp.isOnline() && st.hasMember((Player) Manager.getPlayer(pp.getName())))))
                .findAny().orElse(null);
      }
    }
    
    team = team == null ? getAvailableTeam(fullSize ? this.getMode().getSize() : 1) : team;
    if (team == null) {
      return;
    }
    
    team.addMember(player);
    if (profile.getGame() != null) {
      profile.getGame().leave(profile, profile.getGame());
    }
    
    this.players.add(player.getUniqueId());
    profile.setGame(this);
    
    if (team.listPlayers().size() == 1) {
      team.buildCage(profile.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.CAGE, Cage.class));
    }
    
    player.teleport(team.getLocation());
    reloadScoreboard(profile);
    
    profile.setHotbar(Hotbar.getHotbarById("waiting"));
    profile.refresh();
    for (Player players : Bukkit.getOnlinePlayers()) {
      if (!players.getWorld().equals(player.getWorld())) {
        player.hidePlayer(players);
        players.hidePlayer(player);
        continue;
      }
      
      if (isSpectator(players)) {
        player.hidePlayer(players);
      } else {
        player.showPlayer(players);
      }
      players.showPlayer(player);
    }
    
    this.broadcastMessage(Language.ingame$broadcast$join.replace("{player}", Role.getColored(player.getName())).replace("{players}", String.valueOf(this.getOnline()))
        .replace("{max_players}", String.valueOf(this.getMaxPlayers())));
    if (this.getOnline() == this.getMaxPlayers() && this.timer > Language.options$start$full) {
      this.timer = Language.options$start$full;
    }
  }
  
  @Override
  public void join(Profile profile) {
    this.joinParty(profile, false);
  }
  
  @Override
  public void leave(Profile profile, Game<?> game) {
    Player player = profile.getPlayer();
    if (player == null || profile.getGame() != this) {
      return;
    }
    
    BedWarsTeam team = this.getTeam(player);
    
    boolean alive = this.players.contains(player.getUniqueId());
    this.players.remove(player.getUniqueId());
    this.spectators.remove(player.getUniqueId());
    
    if (game != null) {
      if (alive && this.state == GameState.EMJOGO) {
        List<Profile> hitters = profile.getLastHitters();
        Profile killer = hitters.size() > 0 ? hitters.get(0) : null;
        killLeave(profile, killer);
        for (Profile hitter : hitters) {
          if (!hitter.equals(killer) && hitter.playingGame() && hitter.getGame().equals(this) && !this.isSpectator(hitter.getPlayer())) {
            hitter.addStats("zCoreBedWars", this.getMode().getStats() + "assists");
            
            // Mensal
            hitter.addStats("zCoreBedWars", "monthlyassists");
          }
        }
        hitters.clear();
      }
      
      if (team != null) {
        team.removeMember(player);
        if (this.state == GameState.AGUARDANDO && !team.isAlive()) {
          team.breakCage();
        }
      }
      if (Profile.isOnline(player.getName())) {
        profile.setGame(null);
        TagUtils.setTag(player);
      }
      if (this.state == GameState.AGUARDANDO) {
        this.broadcastMessage(Language.ingame$broadcast$leave.replace("{player}", Role.getColored(player.getName())).replace("{players}", String.valueOf(this.getOnline()))
            .replace("{max_players}", String.valueOf(this.getMaxPlayers())));
      }
      this.check();
      return;
    }
    
    if (alive && this.state == GameState.EMJOGO) {
      List<Profile> hitters = profile.getLastHitters();
      Profile killer = hitters.size() > 0 ? hitters.get(0) : null;
      killLeave(profile, killer);
      for (Profile hitter : hitters) {
        if (!hitter.equals(killer) && hitter.playingGame() && hitter.getGame().equals(this) && !this.isSpectator(hitter.getPlayer())) {
          hitter.addStats("zCoreBedWars", this.getMode().getStats() + "assists");
          
          // Mensal
          hitter.addStats("zCoreBedWars", "monthlyassists");
        }
      }
      hitters.clear();
    }
    
    if (team != null) {
      team.removeMember(player);
      if (this.state == GameState.AGUARDANDO && !team.isAlive()) {
        team.breakCage();
      }
    }
    profile.setGame(null);
    TagUtils.setTag(player);
    reloadScoreboard(profile);
    profile.setHotbar(Hotbar.getHotbarById("lobby"));
    profile.refresh();
    if (this.state == GameState.AGUARDANDO) {
      this.broadcastMessage(Language.ingame$broadcast$leave.replace("{player}", Role.getColored(player.getName())).replace("{players}", String.valueOf(this.getOnline()))
          .replace("{max_players}", String.valueOf(this.getMaxPlayers())));
    }
    this.check();
  }
  
  @Override
  public void kill(Profile profile, Profile killer) {
    Player player = profile.getPlayer();
    BedWarsTeam team = getTeam(player);
    
    boolean alive = this.players.contains(player.getUniqueId());
    if (!alive || team == null) {
      profile.refresh();
      return;
    }
    
    Player pk = killer != null ? killer.getPlayer() : null;
    
    if (player.equals(pk)) {
      pk = null;
    }
    
    Location returns = team.getLocation();
    if (pk != null) {
      PlayerUtils.giveResources(player, pk);
    }
    
    // Com cama.
    if (!team.bed()) {
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
        player.teleport(returns);
        spectators.add(player.getUniqueId());
        for (Player players : listPlayers(true)) {
          if (isSpectator(players)) {
            player.showPlayer(players);
          } else {
            players.hidePlayer(player);
          }
        }
        profile.setHotbar(null);
        profile.refresh();
        player.setGameMode(GameMode.ADVENTURE);
        player.spigot().setCollidesWithEntities(false);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        this.updateTags();
        
        NMS.sendTitle(player, "§c§lVOCÊ MORREU!", "§7Renascendo em 5s", 20, 60, 20);
        
        new BukkitRunnable() {
          int count = 5;
          
          @Override
          public void run() {
            if (count == 0) {
              this.cancel();
              if (!profile.isOnline() || profile.getGame(BedWars.class) != BedWars.this) {
                return;
              }
              
              NMS.sendTitle(player, "", "");
              spectators.remove(player.getUniqueId());
              profile.refresh();
              player.setGameMode(GameMode.SURVIVAL);
              player.setNoDamageTicks(80);
              team.equip(player);
              team.refresh(player);
              Location islandReturns = returns.clone();
              islandReturns.setY(returns.getWorld().getHighestBlockYAt(returns));
              player.teleport(islandReturns);
              for (Player players : listPlayers(true)) {
                if (isSpectator(players)) {
                  player.hidePlayer(players);
                } else {
                  players.showPlayer(player);
                }
              }
              
              updateTags();
              return;
            }
            if (profile.isOnline()) {
              NMS.sendTitle(player, "§c§lVOCÊ MORREU!",
                  "§7Renascendo em 5s".replace("5", StringUtils.formatNumber(count == 0 ? 1 : count)), 0, 20, 0);
            }
            count--;
          }
        }.runTaskTimer(Main.getInstance(), 0, 20);
      }, 3);
      
      if (killer == null) {
        this.broadcastMessage(Language.ingame$broadcast$suicide.replace("{name}", team.getColored(player)));
      } else {
        BedWarsTeam killerTeam = this.getTeam(pk);
        if (player.getLastDamageCause() == null || player.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.VOID) {
          KillEffect ke = killer.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.KILL_EFFECT, KillEffect.class);
          if (ke != null) {
            ke.execute(player.getLocation());
          }
          
        }
        
        String suffix = this.addKills(pk);
        EnumSound.ORB_PICKUP.play(pk, 1.0F, 1.0F);
        killer.addCoinsWM("zCoreBedWars", Language.options$coins$kills);
        killer.addStats("zCoreBedWars", this.getMode().getStats() + "kills");
        
        DeathMessage dm = killer.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.DEATH_MESSAGE, DeathMessage.class);
        if (dm != null) {
          this.broadcastMessage(dm.getRandomMessage().replace("{name}", team.getColored(player)).replace("{killer}", killerTeam.getColored(pk)) + suffix);
        } else {
          this.broadcastMessage(Language.ingame$broadcast$killed.replace("{name}", team.getColored(player)).replace("{killer}", killerTeam.getColored(pk)) + suffix);
        }
      }
      
      profile.addStats("zCoreBedWars", this.getMode().getStats() + "deaths");
      
      // Monthly
      profile.addStats("zCoreBedWars", "monthlydeaths");
      DeathCry dc = profile.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.DEATH_CRY, DeathCry.class);
      if (dc != null) {
        dc.getSound().play(player.getWorld(), player.getLocation(), dc.getVolume(), dc.getSpeed());
      }
      
      return;
    }
    
    if (killer == null) {
      this.broadcastMessage(Language.ingame$broadcast$suicide.replace("{name}", team.getColored(player)));
    } else {
      BedWarsTeam killerTeam = this.getTeam(pk);
      
      if (player.getLastDamageCause() == null || player.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.VOID) {
        KillEffect ke = killer.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.KILL_EFFECT, KillEffect.class);
        if (ke != null) {
          ke.execute(player.getLocation());
        }
      }
      
      killer.addStats("zCoreBedWars", this.getMode().getStats() + "finalkills");
      killer.addCoinsWM("zCoreBedWars", Language.options$coins$kills);
      killer.addStats("zCoreBedWars", "monthlykills");
      
      String suffix = this.addKills(pk);
      
      DeathMessage dm = killer.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.DEATH_MESSAGE, DeathMessage.class);
      if (dm != null) {
        this.broadcastMessage(dm.getRandomMessage().replace("{name}", team.getColored(player)).replace("{killer}", killerTeam.getColored(pk)) + suffix + "§f§l. ABATE FINAL");
      } else {
        this.broadcastMessage(Language.ingame$broadcast$killed.replace("{name}", team.getColored(player)).replace("{killer}", killerTeam.getColored(pk)) + suffix + "§f§l. ABATE FINAL");
      }
    }
    
    profile.addStats("zCoreBedWars", this.getMode().getStats() + "finaldeaths");
    
    profile.addStats("zCoreBedWars", "monthlydeaths");
    
    team.removeMember(player);
    players.remove(player.getUniqueId());
    spectators.add(player.getUniqueId());
    for (Player players : listPlayers(true)) {
      if (isSpectator(players)) {
        player.showPlayer(players);
      } else {
        players.hidePlayer(player);
      }
    }
    
    final Profile finalKiller = killer;
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
      if (player.isOnline()) {
        profile.setHotbar(Hotbar.getHotbarById("spectator"));
        profile.refresh();
        player.getEnderChest().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.spigot().setCollidesWithEntities(false);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        if (finalKiller != null) {
          player.setVelocity(player.getLocation().getDirection().multiply(-1.6));
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
          if (player.isOnline()) {
            int coinsKill = (int) profile.calculateWM(this.getKills(player) * Language.options$coins$kills);
            int coinsBeds = (int) profile.calculateWM(this.beds.get(player.getName()) == null ? 0 : this.beds.get(player.getName()) * Language.options$coins$beds);
            
            if (coinsKill > 0) {
              player.sendMessage(Language.ingame$messages$coins$base.replace("{coins}", StringUtils.formatNumber(coinsKill + coinsBeds)).replace("{coins_beds}", coinsBeds > 0 ? Language.ingame$messages$coins$beds.replace("{coins}", StringUtils.formatNumber(coinsBeds)).replace("{s}", (this.beds.get(player.getName()) == null ? 0 : this.beds.get(player.getName())) > 1 ? "s" : "").replace("{beds}", StringUtils.formatNumber(this.beds.get(player.getName()) == null ? 0 : this.beds.get(player.getName()))).replace("{coins}", StringUtils.formatNumber(coinsBeds)) : "").replace("{coins_win}", "").replace("{coins_kills}",
                  Language.ingame$messages$coins$kills.replace("{coins}", StringUtils.formatNumber(coinsKill)).replace("{kills}", StringUtils.formatNumber(this.getKills(player)))
                      .replace("{s}", this.getKills(player) > 1 ? "s" : "")));
            }
            
            NMS.sendTitle(player, Language.ingame$titles$death$header, Language.ingame$titles$death$footer, 0, 60, 0);
          }
        }, 27);
      }
    }, 3);
    
    DeathCry dc = profile.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.DEATH_CRY, DeathCry.class);
    if (dc != null) {
      dc.getSound().play(player.getWorld(), player.getLocation(), dc.getVolume(), dc.getSpeed());
    }
    
    this.updateTags();
    this.check();
  }
  
  public void spectate(Player player, Player target) {
    if (this.getState() == GameState.AGUARDANDO) {
      player.sendMessage("§cA partida ainda não começou.");
      return;
    }
    
    Profile profile = Profile.getProfile(player.getName());
    if (profile.playingGame()) {
      if (profile.getGame().equals(this)) {
        return;
      }
      
      profile.getGame().leave(profile, this);
    }
    
    profile.setGame(this);
    spectators.add(player.getUniqueId());
    
    player.teleport(target.getLocation());
    reloadScoreboard(profile);
    for (Player players : Bukkit.getOnlinePlayers()) {
      if (!players.getWorld().equals(player.getWorld())) {
        player.hidePlayer(players);
        players.hidePlayer(player);
        continue;
      }
      
      if (isSpectator(players)) {
        players.showPlayer(player);
      } else {
        players.hidePlayer(player);
      }
      player.showPlayer(players);
    }
    
    profile.setHotbar(Hotbar.getHotbarById("spectator"));
    profile.refresh();
    player.setGameMode(GameMode.ADVENTURE);
    player.spigot().setCollidesWithEntities(false);
    player.setAllowFlight(true);
    player.setFlying(true);
    this.updateTags();
  }
  
  private void check() {
    if (this.state != GameState.EMJOGO) {
      return;
    }
    
    List<BedWarsTeam> teams = this.listTeams().stream().filter(GameTeam::isAlive).collect(Collectors.toList());
    if (teams.size() <= 1) {
      this.stop(teams.isEmpty() ? null : teams.get(0));
    }
    
    teams.clear();
  }
  
  @Override
  public void killLeave(Profile profile, Profile killer) {
    Player player = profile.getPlayer();
    Player pk = killer != null ? killer.getPlayer() : null;
    
    if (player.equals(pk)) {
      pk = null;
    }
    BedWarsTeam team = getTeam(player);
    
    if (pk == null) {
      this.broadcastMessage(Language.ingame$broadcast$suicide.replace("{name}", team.getColored(player)));
    } else {
      BedWarsTeam killerTeam = getTeam(pk);
      
      if (player.getLastDamageCause() == null || player.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.VOID) {
        KillEffect ke = killer.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.KILL_EFFECT, KillEffect.class);
        if (ke != null) {
          ke.execute(player.getLocation());
        }
      }
      
      PlayerUtils.giveResources(player, pk);
      
      String suffix = this.addKills(pk);
      DeathMessage dm = killer.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.DEATH_MESSAGE, DeathMessage.class);
      if (dm != null) {
        this.broadcastMessage(dm.getRandomMessage().replace("{name}", team.getColored(player)).replace("{killer}", killerTeam.getColored(pk)) + suffix);
      } else {
        this.broadcastMessage(Language.ingame$broadcast$killed.replace("{name}", team.getColored(player)).replace("{killer}", killerTeam.getColored(pk)) + suffix);
      }
      
      killer.addStats("zCoreBedWars", this.getMode().getStats() + "finalkills");
      killer.addCoinsWM("zCoreBedWars", Language.options$coins$kills);
      killer.addStats("zCoreBedWars", "monthlykills");
    }
    
    profile.addStats("zCoreBedWars", this.getMode().getStats() + "finaldeaths");
    profile.addStats("zCoreBedWars", "monthlydeaths");
    
    player.getEnderChest().clear();
    
    DeathCry dc = profile.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.DEATH_CRY, DeathCry.class);
    if (dc != null) {
      dc.getSound().play(player.getWorld(), player.getLocation(), dc.getVolume(), dc.getSpeed());
    }
    
    this.updateTags();
    this.check();
  }

  @Override
  public void start() {
    this.state = GameState.EMJOGO;
    this.task.swap(null);
  
    this.listGenerators().forEach(Generator::enable);
    this.listTeams().forEach(BedWarsTeam::spawn);
    
    this.listPlayers(false).forEach(player -> {
      Profile profile = Profile.getProfile(player.getName());
      player.setNoDamageTicks(80);
      player.getEnderChest().clear();
      player.setGameMode(GameMode.SURVIVAL);
      profile.addStats("zCoreBedWars", this.getMode().getStats() + "games");
      profile.addStats("zCoreBedWars", "monthlygames");
    });
    
    this.updateTags();
    this.check();
  }
  
  @Override
  public void stop(BedWarsTeam winners) {
    this.state = GameState.ENCERRADO;
    
    StringBuilder name = new StringBuilder();
    List<Player> players = winners != null ? winners.listPlayers() : null;
    if (players != null) {
      for (Player player : players) {
        if (!name.toString().isEmpty()) {
          name.append(" §ae ").append(winners.getColored(player));
        } else {
          name = new StringBuilder(winners.getColored(player));
        }
      }
      
      players.clear();
    }
    if (name.toString().isEmpty()) {
      this.broadcastMessage(Language.ingame$broadcast$end);
    } else {
      this.broadcastMessage((this.getMode() == BedWarsMode.SOLO ? Language.ingame$broadcast$win$solo : Language.ingame$broadcast$win$dupla)
          .replace("{name}", name.toString()));
    }
    for (Player player : this.listPlayers(false)) {
      Profile profile = Profile.getProfile(player.getName());
      profile.update();
      BedWarsTeam team = this.getTeam(player);
      if (team != null) {
        int coinsWin = (int) (team.equals(winners) ? profile.calculateWM(Language.options$coins$wins) : 0);
        int coinsKill = (int) profile.calculateWM(this.getKills(player) * Language.options$coins$kills);
        int coinsBeds = (int) profile.calculateWM(this.beds.get(player.getName()) == null ? 0 : this.beds.get(player.getName()) * Language.options$coins$beds);
        
        int totalCoins = coinsWin + coinsKill + coinsBeds;
        if (totalCoins > 0) {
          Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> player.sendMessage(
              Language.ingame$messages$coins$base.replace("{coins}", StringUtils.formatNumber(totalCoins))
                  .replace("{coins_win}", coinsWin > 0 ? Language.ingame$messages$coins$win.replace("{coins}", StringUtils.formatNumber(coinsWin)) : "").replace("{coins_beds}", coinsBeds > 0 ? Language.ingame$messages$coins$beds.replace("{coins}", StringUtils.formatNumber(coinsBeds)).replace("{s}", (this.beds.get(player.getName()) == null ? 0 : this.beds.get(player.getName())) > 1 ? "s" : "").replace("{beds}", StringUtils.formatNumber(this.beds.get(player.getName()) == null ? 0 : this.beds.get(player.getName()))) : "")
                  .replace("{coins_kills}",
                      coinsKill > 0 ?
                          Language.ingame$messages$coins$kills.replace("{coins}", StringUtils.formatNumber(coinsKill)).replace("{kills}", StringUtils.formatNumber(this.getKills(player)))
                              .replace("{s}", this.getKills(player) > 1 ? "s" : "") :
                          "")), 30);
        }
      }
      
      if (winners != null && winners.hasMember(player)) {
        profile.addCoinsWM("zCoreBedWars", Language.options$coins$wins);
        profile.addStats("zCoreBedWars", this.getMode().getStats() + "wins");
        
        // Mensal
        profile.addStats("zCoreBedWars", "monthlywins");
        
        NMS.sendTitle(player, Language.ingame$titles$win$header, Language.ingame$titles$win$footer, 10, 80, 10);
      } else {
        NMS.sendTitle(player, Language.ingame$titles$lose$header, Language.ingame$titles$lose$footer, 10, 80, 10);
      }
      
      this.spectators.add(player.getUniqueId());
      profile.setHotbar(Hotbar.getHotbarById("spectator"));
      profile.refresh();
      player.getEnderChest().clear();
      player.setGameMode(GameMode.ADVENTURE);
      player.setAllowFlight(true);
      player.setFlying(true);
    }
    
    this.updateTags();
    this.task.swap(winners);
  }
  
  public void destroy() {
    this.name = null;
    this.config.destroy();
    this.config = null;
    this.timer = 0;
    this.state = null;
    this.task.cancel();
    this.task = null;
    this.players.clear();
    this.players = null;
    this.spectators.clear();
    this.spectators = null;
    this.kills.clear();
    this.kills = null;
    this.beds.clear();
    this.beds = null;
    this.topKills.clear();
    this.topKills = null;
    this.placedBlocks.clear();
    this.placedBlocks = null;
    this.generators.clear();
  }
  
  @Override
  public void reset() {
    this.event = null;
    this.nextEvent = null;
    this.topKills.clear();
    this.beds.clear();
    this.kills.clear();
    this.streak.clear();
    this.placedBlocks.clear();
    this.players.clear();
    this.spectators.clear();
    this.listGenerators().forEach(Generator::reset);
    this.task.cancel();
    this.listTeams().forEach(BedWarsTeam::reset);
    addToQueue(this);
  }
  
  @Override
  public String getGameName() {
    return this.name;
  }
  
  public int getTimer() {
    return this.timer;
  }
  
  public void setTimer(int timer) {
    this.timer = timer;
  }
  
  public BedWarsConfig getConfig() {
    return this.config;
  }
  
  public World getWorld() {
    return this.config.getWorld();
  }
  
  public BedWarsTask getTask() {
    return this.task;
  }

  public CubeID getCubeId() {
    return this.config.getCubeId();
  }
  
  public String getMapName() {
    return this.config.getMapName();
  }
  
  public BedWarsMode getMode() {
    return this.config.getMode();
  }
  
  @Override
  public GameState getState() {
    return this.state;
  }
  
  public void setState(GameState state) {
    this.state = state;
  }
  
  public int getKills(Player player) {
    return this.kills.get(player.getName()) != null ? kills.get(player.getName()) : 0;
  }
  
  public int getBeds(Player player) {
    return this.beds.get(player.getName()) != null ? beds.get(player.getName()) : 0;
  }
  
  public String addKills(Player player) {
    this.kills.put(player.getName(), this.getKills(player) + 1);
    Object[] lastKill = this.streak.get(player.getName());
    if (lastKill != null) {
      if ((long) lastKill[0] + 6000 > System.currentTimeMillis()) {
        long streak = (long) lastKill[1];
        this.streak.get(player.getName())[0] = System.currentTimeMillis();
        this.streak.get(player.getName())[1] = streak + 1L;
        return streak == 2 ?
            Language.ingame$broadcast$double_kill :
            streak == 3 ? Language.ingame$broadcast$triple_kill : streak == 4 ? Language.ingame$broadcast$quadra_kill : Language.ingame$broadcast$monster_kill;
      }
    }
    
    lastKill = new Object[2];
    lastKill[0] = System.currentTimeMillis();
    lastKill[1] = 2L;
    this.streak.put(player.getName(), lastKill);
    return "";
  }
  
  @Override
  public boolean isSpectator(Player player) {
    return this.spectators.contains(player.getUniqueId());
  }
  
  public void updateTags() {
    for (Player player : this.listPlayers()) {
      Scoreboard scoreboard = player.getScoreboard();
      
      for (Player players : this.listPlayers()) {
        BedWarsTeam gt;
        
        if (this.isSpectator(players)) {
          Team team = scoreboard.getEntryTeam(players.getName());
          if (team != null && !team.getName().equals("spectators")) {
            if (team.getSize() == 1) {
              team.unregister();
            } else {
              team.removeEntry(players.getName());
            }
            team = null;
          }
          
          if (team == null) {
            team = scoreboard.getTeam("spectators");
            if (team == null) {
              team = scoreboard.registerNewTeam("spectators");
              team.setPrefix("§8");
              team.setCanSeeFriendlyInvisibles(true);
            }
            
            if (!team.hasEntry(players.getName())) {
              team.addEntry(players.getName());
            }
          }
        } else if ((gt = this.getTeam(players)) != null) {
          Team team = scoreboard.getTeam(gt.getName());
          if (team == null) {
            team = scoreboard.registerNewTeam(gt.getName());
            String[] colors =
                {"§c", "§d", "§b", "§9", "§f", "§6", "§5", "§2"};
            team.setPrefix(colors[gt.getIndex()]);
          }
          
          if (!team.hasEntry(players.getName())) {
            team.addEntry(players.getName());
          }
          
          NameTagVisibility visibility = players.hasPotionEffect(PotionEffectType.INVISIBILITY) ? NameTagVisibility.HIDE_FOR_OTHER_TEAMS : NameTagVisibility.ALWAYS;
          if (!team.getNameTagVisibility().equals(visibility)) {
            team.setNameTagVisibility(visibility);
          }
        }
      }
    }
  }
  
  @Override
  public int getOnline() {
    return this.players.size();
  }
  
  @Override
  public int getMaxPlayers() {
    return this.listTeams().size() * this.getMode().getSize();
  }
  
  public void resetBlock(Block block) {
    BedWarsBlock sb = this.blocks.get(BukkitUtils.serializeLocation(block.getLocation()));
    
    if (sb != null) {
      block.setType(sb.getMaterial());
      BlockState state = block.getState();
      state.getData().setData(sb.getData());
      if (state instanceof Chest) {
        ((Chest) state).getInventory().clear();
      }
      state.update(true);
    } else {
      block.setType(Material.AIR);
    }
  }
  
  public void generateEvent() {
    this.event =
        this.listEvents().entrySet().stream().filter(e -> this.getTimer() < e.getKey()).min(Comparator.comparingInt(Map.Entry::getKey))
            .orElse(null);
    this.nextEvent = this.listEvents().entrySet().stream().filter(e -> this.getTimer() < e.getKey()).min(Comparator.comparingInt(Map.Entry::getKey)).orElse(null);
  }

  public String getEvent() {
    if (this.event == null) {
      return "Fim";
    }
    
    Generator diamond = this.listGenerators().stream().filter(collect -> collect.getType().equals(Generator.Type.DIAMOND)).
        findAny().orElse(null);
    Generator emerald = this.listGenerators().stream().filter(collect -> collect.getType().equals(Generator.Type.EMERALD))
        .findAny().orElse(null);
    
    return this.event.getValue().getName().replace("{tier}", this.event.getValue().getName().equals(Language.options$events$diamond) ?
        StringUtils.repeat("I", diamond == null ? 1 : (diamond.getTier() + 1)) : StringUtils.repeat("I", emerald == null ? 1 : (emerald.getTier() + 1))
    ) + " " + SDF.format((this.event.getKey() - this.getTimer()) * 1000);
  }

  public Map.Entry<Integer, BedWarsEvent> getNextEvent() {
    return this.nextEvent;
  }
  
  public int getTimeUntilEvent() {
    return (this.event == null ? this.getTimer() : this.event.getKey()) - this.getTimer();
  }
  
  public Map<Integer, BedWarsEvent> listEvents() {
    return BedWarsEvent.SOLO;
  }
  
  public Map<String, BedWarsBlock> getBlocks() {
    return this.blocks;
  }
  
  public BedWarsTeam getAvailableTeam(int teamSize) {
    return this.listTeams().stream().filter(team -> team.canJoin(teamSize)).findAny().orElse(null);
  }
  
  @Override
  public BedWarsTeam getTeam(Player player) {
    return this.listTeams().stream().filter(team -> team.hasMember(player)).findAny().orElse(null);
  }
  
  public List<Generator> listGenerators() {
    return this.generators;
  }
  
  @Override
  public List<BedWarsTeam> listTeams() {
    return this.config.listTeams();
  }
  
  @Override
  public List<Player> listPlayers() {
    return this.listPlayers(true);
  }
  
  @Override
  public List<Player> listPlayers(boolean spectators) {
    List<Player> players = new ArrayList<>(spectators ? this.spectators.size() + this.players.size() : this.players.size());
    this.players.forEach(id -> players.add(Bukkit.getPlayer(id)));
    if (spectators) {
      this.spectators.stream().filter(id -> !this.players.contains(id)).forEach(id -> players.add(Bukkit.getPlayer(id)));
    }
    
    return players.stream().filter(Objects::nonNull).collect(Collectors.toList());
  }
  
}