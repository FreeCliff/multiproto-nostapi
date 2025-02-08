package me.howard.multiprotonostapi.mixin.mojangfix;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import pl.js6pak.mojangfix.client.gui.CallbackButtonWidget;
import pl.js6pak.mojangfix.client.gui.multiplayer.DirectConnectScreen;

@Mixin(DirectConnectScreen.class)
public interface DirectConnectScreenAccessor
{
    @Accessor(remap = false) TextFieldWidget getAddressField();
    @Accessor(remap = false) CallbackButtonWidget getConnectButton();
}
