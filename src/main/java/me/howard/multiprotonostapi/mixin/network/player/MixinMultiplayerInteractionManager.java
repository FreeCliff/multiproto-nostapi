package me.howard.multiprotonostapi.mixin.network.player;

import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.MultiplayerInteractionManager;
import net.minecraft.block.Block;
import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiplayerInteractionManager.class)
public abstract class MixinMultiplayerInteractionManager extends InteractionManager
{
    @Shadow private float field_2611;
    @Shadow private int field_2614;
    @Shadow private boolean field_2615;
    @Shadow private ClientNetworkHandler networkHandler;
    @Shadow public abstract boolean method_1716(int i, int j, int k, int l);

    @Shadow private float field_2612;

    @Shadow private float field_2613;

    @Shadow private int field_2608;

    @Shadow private int field_2609;

    @Shadow private int field_2610;

    @Shadow public abstract void method_1707(int i, int j, int k, int l);

    public MixinMultiplayerInteractionManager(Minecraft minecraft)
    {
        super(minecraft);
    }

    @ModifyVariable(method = "clickSlot", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private boolean disableShiftClick(boolean value)
    {
        return value;
    }

    @Inject(method = "method_1716", at = @At("HEAD"))
    private void sendBlockMined(int i, int j, int k, int l, CallbackInfoReturnable<Boolean> cir)
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_9))
            networkHandler.sendPacket(new PlayerActionC2SPacket(3, i, j, k, l));
    }

    @Inject(method = "method_1707", at = @At("HEAD"), cancellable = true)
    private void startMining(int i, int j, int k, int l, CallbackInfo ci)
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_9))
        {
            field_2615 = true;
            networkHandler.sendPacket(new PlayerActionC2SPacket(0, i, j, k, l));
            int id = minecraft.world.getBlockId(i, j, k);
            if (id > 0 && field_2611 == 0.0f)
            {
                Block.BLOCKS[id].onBlockBreakStart(minecraft.world, i, j, k, minecraft.player);
            }
            if (id > 0 && Block.BLOCKS[id].getHardness(this.minecraft.player) >= 1.0f)
            {
                this.method_1716(i, j, k, l);
            }
            ci.cancel();
        }
    }

    @Inject(method = "method_1705", at = @At("HEAD"))
    private void stopMining(CallbackInfo ci)
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_9) && field_2615)
        {
            networkHandler.sendPacket(new PlayerActionC2SPacket(2, 0, 0, 0, 0));
            field_2614 = 0;
        }
    }

    @Inject(method = "method_1721", at = @At("HEAD"))
    private void sendMining(int i, int j, int k, int l, CallbackInfo ci)
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_9))
        {
            field_2615 = true;
            networkHandler.sendPacket(new PlayerActionC2SPacket(1, i, j, k, l));
        }
    }

    @Redirect(method = "method_1721", at = @At(value = "FIELD", target = "Lnet/minecraft/MultiplayerInteractionManager;field_2615:Z", opcode = Opcodes.PUTFIELD), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockId(III)I")))
    private void redirectPutField_2615(MultiplayerInteractionManager instance, boolean value)
    {
        if (!ProtocolVersionManager.isBefore(ProtocolVersion.BETA_9)) field_2615 = value;
    }

    @Redirect(method = "method_1721", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V"), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/MultiplayerInteractionManager;field_2615:Z", opcode = Opcodes.PUTFIELD, ordinal = 1)))
    private void redirectSendStopMiningPacket(ClientNetworkHandler handler, Packet packet)
    {
        if (!ProtocolVersionManager.isBefore(ProtocolVersion.BETA_9)) handler.sendPacket(packet);
    }

    @Redirect(method = "method_1721", at = @At(value = "INVOKE", target = "Lnet/minecraft/MultiplayerInteractionManager;method_1707(IIII)V"))
    private void redirectStartMiningInSendMining(MultiplayerInteractionManager instance, int i, int j, int k, int l)
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_9))
        {
            field_2611 = 0.0f;
            field_2612 = 0.0f;
            field_2613 = 0.0f;
            field_2608 = i;
            field_2609 = j;
            field_2610 = k;
            return;
        }
        method_1707(i, j, k, l);
    }
}
