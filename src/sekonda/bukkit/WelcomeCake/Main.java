package sekonda.bukkit.WelcomeCake;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
 
public class Main extends JavaPlugin implements Listener {
	
	Logger log = Logger.getLogger("Minecraft");
 
	public void onEnable(){
		
		//Declare public variables
		FileConfiguration config = this.getConfig();
		new File(getDataFolder() + "config.yml");
		try {
			if(!config.contains("extendedLog")) {
				config.set("extendedLog", false);
			}
			if(!config.contains("welcome.enabled")) {
				config.set("welcome.enabled", false);
			}
			if(!config.contains("welcome.message")) {
				config.set("welcome.message", "");
			}
			if(!config.contains("chance.enabled")) {
				config.set("chance.enabled", false);
			}
			if(!config.contains("chance.rate")) {
				config.set("chance.rate", 5);
			}
			saveConfig();
		} catch(Exception e1){
			e1.printStackTrace();
		}
		
		final boolean extendedLog = config.getBoolean("extendedLog");
		final boolean enableWelcome = config.getBoolean("welcome.enabled");
		final String welcomeMessage = config.getString("welcome.message");
		final boolean enableChance = config.getBoolean("chance.enabled");		
		final int chanceRate = config.getInt("chance.rate");
		
		if(extendedLog == true) {
			logger("Extended Logs are enabled.", "normal");
		} else {
			logger("Extended Logs are disabled.", "normal");
		}
		
		//Send to console (extended)
		if(enableWelcome == true) {
			logger("Welcome message are enabled.","extended");
			if(welcomeMessage != "") {
				logger("Users will be sent \""+welcomeMessage+"\" when they login.","extended");
			}
		} else {
			logger("Welcome messages are disabled.","extended");
		}
		if(enableChance == true) {
			logger("Chance for getting cake is enabled.","extended");
			logger("The chance of getting cake is 1/"+chanceRate+".","extended");
		} else if(enableChance == false) {
			logger("Chance for getting cake is disabled.","extended");
		}
		
		//register events
		getServer().getPluginManager().registerEvents(this, this);
	
		//send to console
		logger("Has been enabled.","normal");
	}
	
	@EventHandler
    public void onLogin(PlayerJoinEvent event) {
		//Save Player and welcome them
        Player player = event.getPlayer();
        String playerName = player.getName();
        
        //Get Config and save settings
        FileConfiguration config = this.getConfig();
		final boolean enableWelcome = config.getBoolean("welcome.enabled");
		final String welcomeMessage = config.getString("welcome.message");
		final boolean enableChance = config.getBoolean("chance.enabled");		
		final int chanceRate = config.getInt("chance.rate");
		
        //Check to see if they don't have cake first.
        PlayerInventory inventory = player.getInventory();         
        ItemStack cake = new ItemStack(Material.CAKE,1); 
        
        if(enableWelcome == true && welcomeMessage != "") {
        	player.sendMessage(welcomeMessage);
        	logger("Sent "+playerName+" the welcome message: "+welcomeMessage,"extended");
        }
        
        //Gets next empty slot
        int empty = inventory.firstEmpty();
        
        //Checks if there is an empty slot and they don't already have cake.
        if(empty != -1 && !inventory.contains(Material.CAKE)) {
        	
        	if(enableChance  == true) { 
        		int number = 1 + (int) ( Math.random()*(chanceRate -1)+1);
        		
        		logger(playerName+ " rolled "+number,"extended");
        	
        		if(chanceRate == number) {
        			//Adds cake
        			inventory.addItem(cake);
        			player.sendMessage("I seen that you had a free slot in your inventory, so I gave you cake.");
        			logger("Added cake to " + playerName,"extended");
        		} 
        	} else if(enableChance == false) {
        		inventory.addItem(cake);
        		player.sendMessage("I seen that you had a free slot in your inventory, so I gave you cake.");
    			logger("Added cake to " + playerName,"extended");
        	}
        }
    }
	
	public void onDisable(){
		//Reload and Save config before shutdown
		reloadConfig();
		saveConfig();
		
		//send to console
		logger("has been disabled.","normal");
	}
	
	//Logger Method
	public void logger(String msg, String type) {
		
		//Pull config for extendedLogs
		FileConfiguration config = this.getConfig();
		final boolean extendedLog = config.getBoolean("extendedLog");
		
		//Create Constant Prefix
		PluginDescriptionFile pdf = this.getDescription();
		final String pluginPrefix = "[" + pdf.getName() + "] ";
		
		//Checks for extendedLogging
		if(type == "extended" && extendedLog == true) {
			this.log.info(pluginPrefix + msg); 
		} else if (type == "normal"){
			this.log.info(pluginPrefix + msg);
		}
	}
}

