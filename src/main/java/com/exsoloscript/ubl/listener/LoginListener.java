package com.exsoloscript.ubl.listener;

import com.exsoloscript.ubl.banlist.BanList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.profile.GameProfile;

@Singleton
public class LoginListener {

    private BanList banList;
    private Logger logger;
    private String kickMessage;

    @Inject
    public LoginListener(BanList banList, Logger logger) {
        this.banList = banList;
        this.logger = logger;
    }

    @Listener
    public void onClientLogin(ClientConnectionEvent.Login event) {
        GameProfile profile = event.getProfile();

        if (this.banList.isBanned(profile.getUniqueId())) {
            event.setCancelled(true);
            event.setMessage(this.banList.getBanMessage(profile.getUniqueId()));
            this.logger.info("Kicked the following user for being on the UBL: " + profile);
        }
    }
}
