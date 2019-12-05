package games.indigo.presents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            ItemStack clicked = event.getItem();
            if (clicked != null) {
                Presents plugin = Presents.getInstance();
                String giftOwner = plugin.getPresentSender(clicked);
                // is a present
                if (giftOwner != null) {
                    event.setCancelled(true);
                    Player player = event.getPlayer();
                    if (player.getName().equalsIgnoreCase(giftOwner)) {
                        player.sendMessage(plugin.getPrefix() + "You may not open presents that are from you!");
                    } else {
                        clicked.setAmount(clicked.getAmount() - 1);
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cc open Present " + player.getName());
                    }
                }
            }
        }
    }
}
