package xyz.amymialee.noenchantcap;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(NoEnchantCap.MOD_ID)
public class NoEnchantCapForge {
    public NoEnchantCapForge() {
        NoEnchantCap.init();
    }

    @SubscribeEvent
    public void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        EnchantedBookCommand.register(dispatcher);
    }
}