package treegochopchop;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class TreeGoChopChopEvents implements Listener {

    public TreeGoChopChopEvents() {}

    /**
     * Handle block break events where the item used to break was
     * an axe and the target block was a log
     * @param e Block break event
     */
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        // Player is in survival
        if (!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;

        // Tree log values
        Block logBlock = e.getBlock();
        Material logType = logBlock.getType();

        // Axe values
        ItemStack axeItem = e.getPlayer().getInventory().getItemInMainHand();
        AtomicBoolean axeSurvived = new AtomicBoolean(true);

        // Only continue if player is using an axe to break logs
        if (!Constants.axes.contains(axeItem.getType()) || !Constants.logs.contains(logType)) return;

        // Tag tree with meta to prevent infinite recursive loop
        logBlock.setMetadata(Constants.METAKEY, new TreeGoChopChopMetaData());

        // Add initial block to list
        HashMap<String, Block> blocks = new HashMap<String, Block>() {{ put(blockAsKey(logBlock), logBlock); }};

        // Recursively tag neighbouring blocks of same type then sort using TreeMap
        HashMap<String, Block> wholeTree = breakNeighbours(logBlock, logType, blocks);

        // Log tree as much as possible and damage axe proportionally
        logTree(axeItem, wholeTree, axeSurvived, e.getPlayer().getGameMode());

        // Give player stack of logs equivalent to number of deleted
        ItemStack stack = new ItemStack(logType, wholeTree.size());
        logBlock.getWorld().dropItemNaturally(logBlock.getLocation(), stack);

        // Cancel default log break event if axe survives
        e.setCancelled(axeSurvived.get());
    }

    private void logTree(ItemStack axe, HashMap<String, Block> tree, AtomicBoolean axeSurvived, GameMode gameMode) {
        // Axe meta data
        ItemMeta axeMeta = axe.getItemMeta();
        Damageable item = (Damageable) axeMeta;

        // Sort tree data by highest blocks (Y axis)
        TreeMap<String, Block> sorted = new TreeMap<>(Collections.reverseOrder());
        sorted.putAll(tree);

        // Standard max durability of axe being used
        int maxDamage = axe.getType().getMaxDurability();

        // Incrementally delete blocks while axe has durability
        sorted.forEach((k,v) -> {
            int currentDamage = item.getDamage();
            if (currentDamage < maxDamage) {
                // Delete log
                v.setType(Material.AIR);
                // Damage axe if not in creative
                if (!gameMode.equals(GameMode.CREATIVE))
                    item.setDamage(currentDamage + 1);
            } else {
                axeSurvived.set(false);
                v.getMetadata(Constants.METAKEY).get(0).invalidate();
            }
        });

        // Save updated meta data
        axe.setItemMeta(axeMeta);
    }

    private HashMap<String, Block> breakNeighbours(Block main, Material type, HashMap<String, Block> blocks) {
        // For neighbours in every position
        for (int xOffset=-1;xOffset<2;xOffset++) {
            for (int yOffset=-1;yOffset<2;yOffset++) {
                for (int zOffset=-1;zOffset<2;zOffset++) {
                    // Ignore self/known block
                    if (xOffset == 0 && yOffset == 0 && zOffset == 0) continue;

                    // Get neighbour block
                    Block neighbour = main.getLocation().getWorld().getBlockAt(
                            main.getLocation().getBlockX() + xOffset,
                            main.getLocation().getBlockY() + yOffset,
                            main.getLocation().getBlockZ() + zOffset);

                    // Break log and recursively check neighbours
                    if (neighbour.getType() == type && !isTagged(neighbour)) {
                        neighbour.setMetadata(Constants.METAKEY, new TreeGoChopChopMetaData());
                        blocks.put(blockAsKey(neighbour), neighbour);
                        blocks.putAll(breakNeighbours(neighbour, type, blocks));
                    }
                }
            }
        }
        return blocks;
    }

    private boolean isTagged(Block b) {
        AtomicBoolean result = new AtomicBoolean(false);
        b.getMetadata(Constants.METAKEY).forEach(e -> { if (e.asBoolean()) result.set(true); });
        return result.get();
    }

    private String blockAsKey(Block b) { return String.format("%s:%s:%s", b.getY(), b.getX(), b.getZ()); }

}