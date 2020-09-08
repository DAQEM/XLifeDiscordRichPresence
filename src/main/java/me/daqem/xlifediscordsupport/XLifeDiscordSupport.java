package me.daqem.xlifediscordsupport;

import net.minecraft.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.stream.Collectors;

@Mod(XLifeDiscordSupport.MODID)
public class XLifeDiscordSupport {

    public static final String MODID = "xlifediscordsupport";
    private static final Logger LOGGER = LogManager.getLogger();
    protected static boolean isEnabled = true;
    public static final long gameStarted = Instant.now().toEpochMilli();

    public XLifeDiscordSupport() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.register(this);
        });
    }
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    private void clientSetup(final FMLClientSetupEvent event) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Discord.shutdown();
        }));
        if (isEnabled) Discord.initDiscord();
        if (isEnabled) Discord.setPresence();

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("xlifediscordsupport", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
}
