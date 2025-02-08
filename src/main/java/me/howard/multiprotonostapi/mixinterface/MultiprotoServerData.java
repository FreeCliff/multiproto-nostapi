package me.howard.multiprotonostapi.mixinterface;

import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import net.minecraft.nbt.NbtCompound;
import pl.js6pak.mojangfix.client.gui.multiplayer.ServerData;

public interface MultiprotoServerData
{
    static ServerData create(NbtCompound nbt)
    {
        return create(nbt.getString("name"), nbt.getString("ip"), ProtocolVersion.fromString(nbt.getString("version")));
    }

    static ServerData create(String name, String ip, ProtocolVersion protocol)
    {
        ServerData data = new ServerData(name, ip);
        ((MultiprotoServerData) data).multiproto_setVersion(protocol);
        return data;
    }

    ProtocolVersion multiproto_version();
    void multiproto_setVersion(ProtocolVersion version);
}
