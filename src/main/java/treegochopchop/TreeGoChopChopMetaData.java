package treegochopchop;

import org.bukkit.Bukkit;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

class TreeGoChopChopMetaData implements MetadataValue {

    private boolean taggedForChopping = true;

    @Override
    public Object value() {
        return taggedForChopping;
    }

    @Override
    public int asInt() {
        return taggedForChopping ? 1 : 0;
    }

    @Override
    public float asFloat() {
        return taggedForChopping ? 1 : 0;
    }

    @Override
    public double asDouble() {
        return taggedForChopping ? 1 : 0;
    }

    @Override
    public long asLong() {
        return taggedForChopping ? 1 : 0;
    }

    @Override
    public short asShort() {
        return (short) (taggedForChopping ? 1 : 0);
    }

    @Override
    public byte asByte() {
        return (byte) (taggedForChopping ? 1 : 0);
    }

    @Override
    public boolean asBoolean() {
        return taggedForChopping;
    }

    @Override
    public String asString() {
        return taggedForChopping ? "true" : "false";
    }

    @Override
    public Plugin getOwningPlugin() {
        return Bukkit.getPluginManager().getPlugin(Constants.PLUGIN_NAME);
    }

    @Override
    public void invalidate() {
        this.taggedForChopping = false;
    }
}