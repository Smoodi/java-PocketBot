package de.smoodi.botutils;

import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;
import java.util.HashSet;

public class CommandWhitelist {
    private boolean enabled = true;
    private HashSet<Long> whitelistedChannels = new HashSet<Long>();

    public CommandWhitelist() {

    }

    /**
     * Returns true if the channel is whitelisted, otherwise false.
     * This will be accurate even if the whitelist is disabled.
     * @param channel
     * @return
     */
    public boolean isWhitelisted(MessageChannel channel) {
        return whitelistedChannels.contains(channel.getIdLong());
    }

    /**
     * Whitelists a channel.
     * @param channel
     */
    public void whitelist(MessageChannel channel) {
        whitelistedChannels.add(channel.getIdLong());
    }

    public void whitelist(long id) {
        whitelistedChannels.add(id);
    }

    /**
     * Unwhitelists the channel.
     * @param channel
     */
    public void unwhitelist(MessageChannel channel) {
        whitelistedChannels.remove(channel.getIdLong());
    }

    public void unwhitelist(long id) {
        whitelistedChannels.remove(id);
    }

    /**
     * Returns if the whitelist is enabled.
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the whitelist is marked as enabled.
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}