package com.github.l3nnartt.loudervoicechat.gui;

import java.io.IOException;
import java.util.UUID;
import net.labymod.addons.voicechat.VoiceChat;
import net.labymod.addons.voicechat.audio.surround.UserStream;
import net.labymod.main.LabyMod;
import net.labymod.utils.DrawUtils;
import net.labymod.utils.ModColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.renderer.GlStateManager;

public class VolumeGui extends GuiScreen {

  private VoiceChat voiceChat;
  private UUID uuid;
  private String username;
  private GuiSlider slider;
  private int sliderValue;
  
  public VolumeGui(VoiceChat voiceChat, UUID uuid, String username) {
    this.voiceChat = voiceChat;
    this.uuid = uuid;
    this.username = username;
  }
  
  public void initGui() {
    super.initGui();
    this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 2 + 40, 200, 20, "Save"));
    this.buttonList.add(new GuiButton(6969, this.width / 2 - 100, this.height / 2 + 15, 200, 20, "Reset Volume"));
    this.slider = new GuiSlider(new GuiPageButtonList.GuiResponder() {
        @Override
        public void func_175321_a(int i, boolean b) {
        }

        @Override
        public void onTick(int id, float value) {
          VolumeGui.this.setEntryValue(id, value);
        }

        @Override
        public void func_175319_a(int i, String s) {
        }
      },4, this.width / 2 - 75, this.height / 2 - 30, "Volume", 0.0F, 500.0F, this.voiceChat.getVolume(this.uuid), new GuiSlider.FormatHelper() {
      @Override
      public String getText(int id, String string, float value) {
        char color = 'a';
        if (value > 145.0F) {
          color = '4';
        } else if (value > 130.0F) {
          color = 'c';
        }
        return string + ": " + ModColor.cl(color) + (VolumeGui.this.sliderValue = (int) value) + "%";
      }
    });
    this.buttonList.add(this.slider);
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    drawDefaultBackground();
    DrawUtils draw = LabyMod.getInstance().getDrawUtils();
    draw.drawCenteredString("Change Volume of " + this.username, (this.width / 2), (this.height / 2 - 60));
    super.drawScreen(mouseX, mouseY, partialTicks);
    UserStream userStream = (UserStream)this.voiceChat.getSurroundManager().getUserStreams().get(this.uuid);
    if (userStream != null) {
      GlStateManager.pushMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      userStream.renderVisual((this.width / 2 - 100), (this.height / 2 - 80), 200.0D);
      GlStateManager.popMatrix();
    } 
  }


  protected void actionPerformed(GuiButton button) throws IOException {
    super.actionPerformed(button);
    if (button.id == 6969) {
      setEntryValue(4, 100.0F);
      Minecraft.getMinecraft().displayGuiScreen(null);
      //Todo: (Minecraft.getMinecraft()).m = null;
      this.voiceChat.savePlayersVolumes();
    } else if (button.id == 5) {
      Minecraft.getMinecraft().displayGuiScreen(null);
      //Todo: (Minecraft.getMinecraft()).m = null;
      this.voiceChat.savePlayersVolumes();
    } 
  }

  public void setEntryValue(int id, float value) {
    this.voiceChat.getPlayerVolumes().put(this.uuid, Integer.valueOf(this.sliderValue = (int)value));
  }
}