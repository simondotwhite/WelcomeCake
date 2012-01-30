package sekonda.bukkit.HaveCake;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
 
public class Main extends JavaPlugin implements Listener {
 
	Logger log = Logger.getLogger("Minecraft");
 
	public void onEnable(){
		log.info("HaveCake has been enabled.");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
    public void onLogin(PlayerLoginEvent event) {
		//Save Player and welcome them
        Player player = event.getPlayer();
        player.sendMessage("Welcome " + player +" to the server, have some cake.");
        
        //Add the cake to Inventory
        PlayerInventory inventory = player.getInventory(); 
        ItemStack cake = new ItemStack(Material.CAKE,1); 
        inventory.addItem(cake);
        
        //Send to console
        log.info("[HaveCake] " + player + " was given cake.");
    }
	
	public void onDisable(){
		log.info("HaveCake has been disabled.");
	}
}

