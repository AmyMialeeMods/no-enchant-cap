package xyz.amymialee.noenchantcap;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Initializes the common initializer and command event.
 */
@Mod(NoEnchantCap.MOD_ID)
public class NoEnchantCapForge {
    /**
     * Constructs the Forge init.
     */
    public NoEnchantCapForge() {
        NoEnchantCap.init();
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommandsEvent);
    }

    /**
     * Registers the Enchanted Book Command.
     * @param event Register Commands Event.
     */
    @SubscribeEvent
    public void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        CommandBuildContext buildContext = event.getBuildContext();
        EnchantedBookCommand.register(dispatcher, buildContext);
    }
}