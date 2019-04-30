package simpletextoverlay.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import simpletextoverlay.handler.PlayerHandler;
import simpletextoverlay.network.PacketHandler;

public class ServerProxy extends CommonProxy {
    @Override
    public void init(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        PacketHandler.initServer();
    }
}
