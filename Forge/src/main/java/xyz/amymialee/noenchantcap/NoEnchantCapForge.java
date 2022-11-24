package xyz.amymialee.noenchantcap;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(NoEnchantCap.MOD_ID)
public class NoEnchantCapForge {
    public NoEnchantCapForge() {
        NoEnchantCap.init();
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommandsEvent);
    }

    @SubscribeEvent
    public void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        EnchantedBookCommand.register(dispatcher);
    }
}