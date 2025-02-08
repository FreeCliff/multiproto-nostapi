package me.howard.multiprotonostapi.mixin.parity.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Block.class)
public interface BlockAccessor
{
    @Accessor @Mutable void setMaterial(Material material);

    @Invoker Block invokeSetHardness(float hardness);
    @Invoker Block invokeSetOpacity(int i);
}
