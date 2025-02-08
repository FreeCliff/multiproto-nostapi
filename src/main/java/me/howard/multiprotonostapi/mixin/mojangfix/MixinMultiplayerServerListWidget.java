package me.howard.multiprotonostapi.mixin.mojangfix;

import me.howard.multiprotonostapi.mixinterface.MultiprotoServerData;
import net.minecraft.client.render.Tessellator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import pl.js6pak.mojangfix.client.gui.multiplayer.MultiplayerScreen;
import pl.js6pak.mojangfix.client.gui.multiplayer.MultiplayerServerListWidget;
import pl.js6pak.mojangfix.client.gui.multiplayer.ServerData;

@Mixin(MultiplayerServerListWidget.class)
public class MixinMultiplayerServerListWidget
{
    @Shadow(remap = false) @Final private MultiplayerScreen parent;

    @Inject(method = "renderEntry", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addVersionText(int index, int x, int y, int l, Tessellator arg, CallbackInfo ci, ServerData server)
    {
        this.parent.drawTextWithShadow(this.parent.getFontRenderer(), ((MultiprotoServerData) server).multiproto_version().nameRange(true), x + 2, y + 23, 8421504);
    }
}
