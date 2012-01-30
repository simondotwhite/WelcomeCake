package sekonda.bukkit.WelcomeCake;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
 
public class Main extends JavaPlugin implements Listener {
 
	Logger log = Logger.getLogger("Minecraft");
	
 
	public void onEnable(){
		//Sort Prefix
		PluginDescriptionFile pdf = this.getDescription();
		final String pluginPrefix = "[" + pdf.getName() + "] ";
		//register events
		getServer().getPluginManager().registerEvents(this, this);
		log.info(pluginPrefix + "has been enabled.");
	}
	
	@EventHandler
    public void onLogin(PlayerJoinEvent event) {
		//Sort Prefix
		PluginDescriptionFile pdf = this.getDescription();
		final String pluginPrefix = "[" + pdf.getName() + "] ";
		//Save Player and welcome them
        Player player = event.getPlayer();
        String playerName = player.getName(); 
    
        //Check to see if they don't have cake first.
        PlayerInventory inventory = player.getInventory();         
        ItemStack cake = new ItemStack(Material.CAKE,1); 
        
        //Gets next empty slot
        int empty = inventory.firstEmpty();
        
        //Checks if there is an empty slot and they don't already have cake.
        if(empty != -1 && !inventory.contains(Material.CAKE)) {
           	//Adds cake
        	inventory.addItem(cake);
           	//Sends welcome message
           	player.sendMessage("Welcome " + ChatColor.GOLD + playerName + ChatColor.WHITE  + " to our server. You had a space in your inventory, so we gave you some cake.");
           	//Log to console
           	log.info(pluginPrefix + "added cake to " + playerName);
           	
        }  else {
        	//Sends welcome message
        	player.sendMessage("Welcome " + ChatColor.GOLD + playerName + ChatColor.WHITE  + " to our server.");
        }
    }
	
	public void onDisable(){
		//Sort Prefix
		PluginDescriptionFile pdf = this.getDescription();
		final String pluginPrefix = "[" + pdf.getName() + "] ";
		
		log.info(pluginPrefix + "has been disabled.");
	}
}

