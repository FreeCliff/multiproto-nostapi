package me.howard.multiprotonostapi.mixin.gui;

import me.howard.multiprotonostapi.gui.ProtocolVersionScreen;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MixinMultiplayerScreen extends Screen
{
    @Inject(method = "init", at = @At("TAIL"))
    private void addCustomButton(CallbackInfo ci)
    {
        ButtonWidget b = (ButtonWidget) buttons.get(0);
        buttons.add(new ButtonWidget(100, b.x, b.y - 24, "Protocol version: " + ProtocolVersionManager.lastVersion().nameRange(true)));
    }

    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    private void onCustomButtonClicked(ButtonWidget b, CallbackInfo ci)
    {
        if (b.id == 100)
        {
            minecraft.setScreen(new ProtocolVersionScreen(this));
            ci.cancel();
        }
    }

    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;save()V")))
    private void setVersionOnConnect(ButtonWidget button, CallbackInfo ci)
    {
        ProtocolVersionManager.setVersion(ProtocolVersionManager.lastVersion());
    }
}
