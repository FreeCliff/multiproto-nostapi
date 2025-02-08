package me.howard.multiprotonostapi.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class MultiprotoMixinPlugin implements IMixinConfigPlugin
{
    public static boolean shouldApplyMojangFixServerListIntegration()
    {
        return FabricLoader.getInstance().isModLoaded("mojangfix");
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
    {
        return switch (mixinClassName)
        {
            case "me.howard.multiprotonostapi.mixin.mojangfix.DirectConnectScreenAccessor",
                 "me.howard.multiprotonostapi.mixin.mojangfix.EditServerScreenAccessor",
                 "me.howard.multiprotonostapi.mixin.mojangfix.MixinDirectConnectScreen",
                 "me.howard.multiprotonostapi.mixin.mojangfix.MixinEditServerScreen",
                 "me.howard.multiprotonostapi.mixin.mojangfix.MixinMultiplayerScreen",
                 "me.howard.multiprotonostapi.mixin.mojangfix.MixinMultiplayerServerListWidget",
                 "me.howard.multiprotonostapi.mixin.mojangfix.MixinServerData" ->
                shouldApplyMojangFixServerListIntegration();
            default -> true;
        };
    }

    @Override
    public void onLoad(String mixinPackage)
    {
    }

    @Override
    public String getRefMapperConfig()
    {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets)
    {
    }

    @Override
    public List<String> getMixins()
    {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
    {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
    {
    }
}
