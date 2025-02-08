package me.howard.multiprotonostapi.mixin.mojangfix;

import me.howard.multiprotonostapi.gui.ProtocolVersionScreen;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.js6pak.mojangfix.client.gui.CallbackButtonWidget;
import pl.js6pak.mojangfix.client.gui.multiplayer.DirectConnectScreen;

@Mixin(DirectConnectScreen.class)
public class MixinDirectConnectScreen extends Screen
{
    @Inject(method = "lambda$init$0", at = @At(value = "INVOKE", target = "Lpl/js6pak/mojangfix/client/gui/multiplayer/DirectConnectScreen;connect(Lnet/minecraft/client/Minecraft;Ljava/lang/String;)V"), remap = false)
    private void setVersionOnConnect(CallbackButtonWidget button, CallbackInfo ci)
    {
        ProtocolVersionManager.setVersion(ProtocolVersionManager.lastVersion());
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addCustomButton(CallbackInfo ci)
    {
        ProtocolVersionManager.lastVersion();
        this.buttons.add(new CallbackButtonWidget(width / 2 - 100, height / 4 + 72 + 12, "Protocol version: " + ProtocolVersionManager.lastVersion().nameRange(true), button -> minecraft.setScreen(new ProtocolVersionScreen(this))));
    }
}
