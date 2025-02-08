package me.howard.multiprotonostapi.mixin.mojangfix;

import me.howard.multiprotonostapi.gui.ProtocolVersionScreen;
import me.howard.multiprotonostapi.mixinterface.MultiprotoServerData;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.js6pak.mojangfix.client.gui.CallbackButtonWidget;
import pl.js6pak.mojangfix.client.gui.multiplayer.EditServerScreen;
import pl.js6pak.mojangfix.client.gui.multiplayer.ServerData;

@Mixin(EditServerScreen.class)
public class MixinEditServerScreen extends Screen
{
    @Shadow(remap = false) @Final private ServerData server;

    @Redirect(method = "lambda$init$0", at = @At(value = "NEW", target = "Lpl/js6pak/mojangfix/client/gui/multiplayer/ServerData;", remap = false), remap = false)
    private ServerData redirectMultiprotoServerData(String name, String ip)
    {
        return MultiprotoServerData.create(name, ip, ProtocolVersionScreen.lastServerVersion());
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addCustomButton(CallbackInfo ci)
    {
        this.buttons.add(new CallbackButtonWidget(width / 2 - 100, height / 4 + 72 + 12, "Protocol version: " + (server != null ?
                ((MultiprotoServerData) server).multiproto_version().nameRange(true) :
                ProtocolVersionScreen.lastServerVersion().nameRange(true)),
                button -> minecraft.setScreen(new ProtocolVersionScreen(this, server))));
    }
}
