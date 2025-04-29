package mc.twilight.utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.config.KWriter;
import mc.twilight.core.plugin.config.KWriter.YamlEntry;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.StringUtils;

@SuppressWarnings("rawtypes")
public class Language {

    @KWriter.YamlEntryInfo(annotation = "Ative caso tiver usando o bungeecord para\n remover os Comandos que tem no bungeecord.")
    public static boolean options$bungeecord = false;

    public static String gamemode$changed = "§aSeu modo de jogo foi atualizado para {mode}";
    public static String gamemode$changed_target = "§aO modo de jogo de {player} foi atualizado para {mode}.";

    public static String lobby$spigot$no_playing = "§fComando desconhecido.";

    public static String tell$format$sender = "§8Mensagem para {player}§8: §6{message}";
    public static String tell$format$target = "§8Mensagem de {player}§8: §6{message}";

    public static boolean staffchat$play_sound = true;
    public static String staffchat$sound = "ORB_PICKUP";

    public static boolean staffchat$warning_actionbar = true;
    public static String staffchat$actionbar = "§eHá uma nova mensagem no chat da staff!";
    public static String staffchat$format = "§6[SC] {player}§f: {message}";

    public static boolean warning$play_sound = true;
    public static String warning$sound = "ORB_PICKUP";
    @KWriter.YamlEntryInfo(annotation = "Utilize {player} para ter o nome e a prefix do jogador.")
    public static String warning$format = "§6[AVISO] §f{message}";

    public static String warning$title$header = "§6§lAVISO";
    public static String warning$title$footer = "{message}";

    public static String upgrades$notification = "\n§aYAY! Agora você é {role}§a! Caso seu grupo\n§anão tenha atualizado basta relogar.\n \n";
    public static String upgrades$titles$header = "{player}";
    public static String upgrades$titles$footer = "§fTornou-se {role}";

    public static String lobby$titles$fly$header = "§a§lFLY";
    public static String lobby$titles$fly$enabled$footer = "§fAtivado com sucesso!";
    public static String lobby$titles$fly$disabled$footer = "§fDesativado com sucesso!";

    public static boolean lobby$titles$welcome$enabled = true;
    public static String lobby$titles$welcome$header = "§6§lLOBBY";
    public static String lobby$titles$welcome$footer = "§fSeja bem vindo(a)!";

    public static String roles$default = "§7Membro";
    public static String roles$set = "§aVocê setou o grupo de {player} para {role}§a.";
    public static String roles$notification = "\n§aYAY! Agora você é {role}§a! Caso seu grupo\n§anão tenha atualizado basta relogar.\n \n";
    public static String roles$titles$header = "{player}";
    public static String roles$titles$footer = "§fTornou-se {role}";

    public static List<String> npc$upgrades$hologram = Arrays.asList("§bUpgrades", "§eClique para fazer um", "§eupgrade em seu VIP!");
    public static String npc$upgrades$skin$value = "eyJ0aW1lc3RhbXAiOjE1ODM0NTc4OTkzMTksInByb2ZpbGVJZCI6IjIxMWNhN2E4ZWFkYzQ5ZTVhYjBhZjMzMTBlODY0M2NjIiwicHJvZmlsZU5hbWUiOiJNYXh0ZWVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MWU0NTc3OTgzZjEzZGI2YTRiMWMwNzQ1MGUyNzQ2MTVkMDMyOGUyNmI0MGQ3ZDMyMjA3MjYwOWJmZGQ0YTA4IiwibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn19fX0=";
    public static String npc$upgrades$skin$signature = "SXnMF3f9x90fa+FdP2rLk/V6/zvMNuZ0sC4RQpPHF9JxdVWYRZm/+DhxkfjCHWKXV/4FSTN8LPPsxXd0XlYSElpi5OaT9/LGhITSK6BbeBfaYhLZnoD0cf9jG9nl9av38KipnkNXI+cO3wttB27J7KHznAmfrJd5bxdO/M0aGQYtwpckchYUBG6pDzaxN7tr4bFxDdxGit8Tx+aow/YtYSQn4VilBIy2y/c2a4PzWEpWyZQ94ypF5ZojvhaSPVl88Fbh+StdgfJUWNN3hNWt31P68KT4Jhx+SkT2LTuDj0jcYsiuxHP6AzZXtOtPPARqM0/xd53CUHCK+TEF5mkbJsG/PZYz/JRR1B1STk4D2cgbhunF87V4NLmCBtF5WDQYid11eO0OnROSUbFduCLj0uJ6QhNRRdhSh54oES7vTi0ja3DftTjdFhPovDAXQxCn+ROhTeSxjW5ZvP6MpmJERCSSihv/11VGIrVRfj2lo9MaxRogQE3tnyMNKWm71IRZQf806hwSgHp+5m2mhfnjYeGRZr44j21zqnSKudDHErPyEavLF83ojuMhNqTTO43ri3MVbMGix4TbIOgB2WDwqlcYLezENBIIkRsYO/Y1r5BWCA7DJ5IlpxIr9TCu39ppVmOGReDWA/Znyox5GP6JIM53kQoTOFBM3QWIQcmXll4=";

    public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger())
            .getModule("LANGUAGE");
    private static final KConfig CONFIG = Main.getInstance().getConfig("language");

    public static void setupLanguage() {
        boolean save = false;
        KWriter writer = Main.getInstance().getWriter(CONFIG.getFile(),
                "Gestão de utilidades feito por eduzp\nVersão: " + Main.getInstance()
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
                        writer.set(nativeName, new YamlEntry(
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
                        writer.set(nativeName, new YamlEntry(
                                new Object[]{entryInfo == null ? "" : entryInfo.annotation(), value}));
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
                    () -> LOGGER.info("A config §6language.yml §afoi modificada ou criada."));
        }
    }
}
