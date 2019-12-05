package games.indigo.presents;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Presents extends JavaPlugin {

    private ItemStack item;
    private String presentName = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Present";
    private String prefix = ChatColor.GREEN + "" + ChatColor.BOLD + "HOLIDAY" + ChatColor.DARK_GRAY + " ⎜ " + ChatColor.GRAY;

    private static Presents instance;

    private GivePresentCmd givePresentCmd = new GivePresentCmd();

    public void onEnable() {
        instance = this;
        getCommand(givePresentCmd.givepresent).setExecutor(givePresentCmd);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Presents enabled!");
    }

    public ItemStack getPresentItem(Player player) {
        if (item == null) {
            ItemStack item = new ItemStack(Material.DRIED_KELP_BLOCK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(presentName);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "This gift will give");
            lore.add(ChatColor.GRAY + "a random premium reward.");
            lore.add(" ");
            lore.add(ChatColor.GRAY + "Happy Holidays, from");
            meta.setLore(lore);
            item.setItemMeta(meta);
            this.item = item;
        }

        ItemStack clone = item.clone();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(ChatColor.AQUA + player.getName());
        meta.setLore(lore);
        clone.setItemMeta(meta);
        return clone;
    }

    public String getPresentSender(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getDisplayName().equals(presentName)) {
                String formattedName = meta.getLore().get(4);
                return ChatColor.stripColor(formattedName);
            }
        }
        return null;
    }

    public String getPrefix() {
        return prefix;
    }

    public static Presents getInstance() {
        return instance;
    }
}
