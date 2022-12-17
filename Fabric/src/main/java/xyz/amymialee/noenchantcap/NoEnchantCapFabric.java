package xyz.amymialee.noenchantcap;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.minecraft.network.chat.Component;

/**
 * Initializes the custom gamerule category and the common initializer.
 */
public class NoEnchantCapFabric implements ModInitializer {
    /**
     * Custom Gamerules category for the mod on fabric. Uses players category on forge due to lack of custom gamerule categories.
     */
    public static final CustomGameRuleCategory ENCHANT_CAP_GAMERULES = new CustomGameRuleCategory(NoEnchantCap.id("no_enchant_cap"), Component.translatable("gamerule.category."+ NoEnchantCap.MOD_ID));

    @Override
    public void onInitialize() {
        NoEnchantCap.init();
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) -> EnchantedBookCommand.register(commandDispatcher, commandBuildContext));
    }
}