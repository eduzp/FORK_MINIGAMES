package mc.twilight.slime;

import mc.twilight.slime.commands.configCMD;
import mc.twilight.slime.listeners.PlayerListeners;
import mc.twilight.slime.runnables.JumpPadsRunnable;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    public static SlimeJumps slimeJumps;

    @Override
    public void onEnable() {
        
        slimeJumps = this;

        System.out.println("zSlimes");
        System.out.println("Ativado");

        getConfig().options().copyDefaults(true);
        saveConfig();
        getConfig();
        saveDefaultConfig();
        
        cargarComandos();
        cargarListeners();

        int xs = Integer.valueOf(getConfig().getString("createds"));
        
        if (xs > 0) {

            getServer().getScheduler().scheduleSyncRepeatingTask(this, new JumpPadsRunnable(), 20L, 20L);
            
        }

    }

    @Override
    public void onDisable(){
        
        slimeJumps = this;
        
        System.out.println("[zSlime] DESATIVADO");
        
    }
    
    public void cargarComandos() {
        
        getCommand("config").setExecutor(new configCMD());
        
        
    }

    public void cargarListeners() {
        
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

    }

    public static SlimeJumps getInstance() {

        return slimeJumps;

    }

}
