package me.howard.multiprotonostapi.parity;

import me.howard.multiprotonostapi.MultiprotoNoStAPI;
import me.howard.multiprotonostapi.mixin.parity.block.BlockAccessor;
import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class BlockParityHelper
{
    public static void applyParity()
    {
        ((BlockAccessor) Block.COBWEB).setMaterial(ProtocolVersionManager.isBefore(ProtocolVersion.BETA_14) ? Material.WOOL : Material.COBWEB);
        ((BlockAccessor) Block.COBWEB).invokeSetHardness(ProtocolVersionManager.isBefore(ProtocolVersion.BETA_14) ? 0f : 4f);
        ((BlockAccessor) Block.COBWEB).invokeSetOpacity(ProtocolVersionManager.isBefore(ProtocolVersion.BETA_14) ? 0 : 1);
        Block.BLOCKS_OPAQUE[Block.COBWEB.id] = ProtocolVersionManager.isBefore(ProtocolVersion.BETA_14);

        ((BlockAccessor) Block.GLOWSTONE).setMaterial(ProtocolVersionManager.isBefore(ProtocolVersion.BETA_13) ? Material.GLASS : Material.STONE);
        ((BlockAccessor) Block.GLOWSTONE).invokeSetHardness(ProtocolVersionManager.isBefore(ProtocolVersion.BETA_14) ? 0.1f : 0.3f);

        MultiprotoNoStAPI.LOGGER.info("Applied version block parity");
    }
}
