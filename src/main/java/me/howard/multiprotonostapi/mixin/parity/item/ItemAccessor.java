package me.howard.multiprotonostapi.mixin.parity.item;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Item.class)
public interface ItemAccessor
{
    @Invoker Item invokeSetMaxDamage(int maxDamage);
}
