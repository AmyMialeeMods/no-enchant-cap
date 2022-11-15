package xyz.amymialee.noenchantcap.platform;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.GameRules;
import xyz.amymialee.noenchantcap.EnchantedBookCommand;
import xyz.amymialee.noenchantcap.NoEnchantCapFabric;
import xyz.amymialee.noenchantcap.platform.services.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T extends GameRules.Value<T>> GameRules.Key<T> registerGamerule(String name, GameRules.Type<T> type) {
        return GameRuleRegistry.register(name, NoEnchantCapFabric.ENCHANT_CAP_GAMERULES, type);
    }

    @Override
    public GameRules.Type<GameRules.BooleanValue> createBooleanValue(boolean defaultValue) {
        return GameRuleFactory.createBooleanRule(defaultValue);
    }
}