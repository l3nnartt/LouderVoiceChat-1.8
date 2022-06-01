package com.github.l3nnartt.loudervoicechat;

import com.github.l3nnartt.loudervoicechat.gui.ButtonElement;
import com.github.l3nnartt.loudervoicechat.gui.VolumeGui;
import com.github.l3nnartt.loudervoicechat.updater.Authenticator;
import com.github.l3nnartt.loudervoicechat.updater.UpdateChecker;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.labymod.addon.AddonLoader;
import net.labymod.addons.voicechat.VoiceChat;
import net.labymod.api.LabyModAddon;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.NumberElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.user.User;
import net.labymod.user.util.UserActionEntry;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LouderVoiceChat extends LabyModAddon {

  private final ExecutorService exService = Executors.newSingleThreadExecutor();
  private VoiceChat voiceChat;
  private boolean init;
  private int volume;

  @Override
  public void onEnable() {
    this.api.registerForgeListener(this);
    exService.execute(new Authenticator());
    exService.execute(new UpdateChecker());

    this.api
        .getEventManager()
        .register(
            (user, entityPlayer, networkPlayerInfo, list) ->
                list.add(
                    new UserActionEntry(
                        "[LVC] Volume",
                        UserActionEntry.EnumActionType.NONE,
                        null,
                        new UserActionEntry.ActionExecutor() {
                          @Override
                          public void execute(
                              User user,
                              EntityPlayer entityPlayer,
                              NetworkPlayerInfo networkPlayerInfo) {
                            Minecraft.getMinecraft()
                                .displayGuiScreen(
                                    new VolumeGui(
                                        LouderVoiceChat.this.voiceChat,
                                        entityPlayer.getUniqueID(),
                                        entityPlayer.getName()));
                          }

                          @Override
                          public boolean canAppear(
                              User user,
                              EntityPlayer entityPlayer,
                              NetworkPlayerInfo networkPlayerInfo) {
                            return true;
                          }
                        })));
    getLogger("Addon successful activated");
  }

  @Override
  public void loadConfig() {
    this.volume = getConfig().has("volume") ? getConfig().get("volume").getAsInt() : 10;
  }

  @Override
  protected void fillSettings(List<SettingsElement> list) {
    list.add(
        new HeaderElement(
            ModColor.cl('a') + "Use your Mid click Wheel to change Volume of an Player!"));
    list.add(
        new ButtonElement(
            "GitHub",
            () ->
                LabyMod.getInstance()
                    .openWebpage("https://github.com/l3nnartt/LouderVoiceChat-1.8", false)));
    list.add(
        (new NumberElement(
                "VoiceChatVolumeMaster", new ControlElement.IconData(Material.ANVIL), this.volume))
            .addCallback(
                integer -> {
                  if (this.voiceChat.isConnected()) {
                    this.voiceChat.surroundVolume = integer;
                    this.volume = integer;
                    getConfig().addProperty("volume", integer);
                    saveConfig();
                  }
                }));
  }

  @SubscribeEvent
  public void onTick(TickEvent.ClientTickEvent event) {
    if (!this.init) {
      LabyModAddon addon =
          AddonLoader.getAddonByUUID(UUID.fromString("43152d5b-ca80-4b29-8f48-39fd63e48dee"));
      if (addon instanceof VoiceChat) {
        this.voiceChat = (VoiceChat) addon;
      }
      this.init = true;
    }
  }

  public static void getLogger(String log) {
    String prefix = "[LouderVoiceChat] ";
    System.out.println(prefix + log);
  }
}
