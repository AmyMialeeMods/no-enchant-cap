package xyz.amymialee.noenchantcap.platform;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import xyz.amymialee.noenchantcap.platform.services.IPlatformHelper;

/**
 * Handles Forge exclusive code.
 */
public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public <T extends GameRules.Value<T>> GameRules.Key<T> registerGamerule(String name, GameRules.Type<T> type) {
        return GameRules.register(name, GameRules.Category.PLAYER, type);
    }

    @Override
    public GameRules.Type<GameRules.BooleanValue> createBooleanValue(boolean defaultValue) {
        return GameRules.BooleanValue.create(defaultValue);
    }

    @Override
    public GameRules.Type<GameRules.IntegerValue> createIntegerValue(int defaultValue) {
        return GameRules.IntegerValue.create(defaultValue);
    }
}