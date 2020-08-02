import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Constants {

    // Global constants
    static final String PLUGIN_NAME = "TreeGoChopChop";
    private static final String PREFIX = String.format("%s[%s%s%s]%s ",
            ChatColor.WHITE, ChatColor.BLUE, PLUGIN_NAME, ChatColor.WHITE, ChatColor.RESET);

    // Plugin status messages
    static final String STATUS_ACTIVATED = PREFIX + ChatColor.GREEN + "Activated";
    static final String STATUS_DEACTIVATED = PREFIX + ChatColor.RED + "Deactivated";

    // Meta Data Key
    static final String METAKEY = "treeGoChopChop";

    // Lists
    static final List<Material> axes = Arrays.asList(Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE,
            Material.NETHERITE_AXE, Material.STONE_AXE, Material.WOODEN_AXE);
    static final List<Material> logs = Arrays.asList(
            Material.ACACIA_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.OAK_LOG,
            Material.STRIPPED_OAK_LOG,
            Material.SPRUCE_LOG,
            Material.STRIPPED_SPRUCE_LOG
    );

}
