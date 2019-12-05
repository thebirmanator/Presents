package games.indigo.presents;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GivePresentCmd implements CommandExecutor {

    private String[] helpMsg = {"help"};
    public String givepresent = "givepresent";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("indigo.command.givepresents")) {
            if (args.length < 1) {
                sender.sendMessage(helpMsg);
            } else {
                Presents plugin = Presents.getInstance();
                String receiverName = args[0];
                Player receiver = Bukkit.getPlayer(receiverName);
                int amount = 1;
                if (args.length > 1) {
                    amount = NumberUtils.toInt(args[1], 1);
                }
                if (receiver != null) {
                    ItemStack present = plugin.getPresentItem(receiver);
                    present.setAmount(amount);
                    HashMap<Integer, ItemStack> failed = receiver.getInventory().addItem(present);
                    if (!failed.isEmpty()) {
                        for (ItemStack fail : failed.values()) {
                            receiver.getWorld().dropItem(receiver.getLocation(), fail);
                        }
                    }
                    sender.sendMessage(plugin.getPrefix() + "Given " + receiver.getName() + " a present!");
                } else {
                    sender.sendMessage(plugin.getPrefix() + receiverName + ChatColor.RED + " is not online!");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Sorry, you may not use this command.");
        }
        return true;
    }
}
