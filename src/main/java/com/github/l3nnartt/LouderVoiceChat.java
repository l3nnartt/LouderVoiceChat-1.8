package com.github.l3nnartt;

import com.github.l3nnartt.updater.Authenticator;
import com.github.l3nnartt.updater.UpdateChecker;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LouderVoiceChat extends LabyModAddon {

    static String prefix = "[LouderVoiceChat]";
    private final ExecutorService exService = Executors.newSingleThreadExecutor();

    @Override
    public void onEnable() {
        exService.execute(new Authenticator());
        exService.execute(new UpdateChecker());
        System.out.println(prefix + " Addon erfolgreich aktiviert");
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }
}
