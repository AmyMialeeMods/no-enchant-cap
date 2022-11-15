package xyz.amymialee.noenchantcap.platform.services;

import net.minecraft.world.level.GameRules;

public interface IPlatformHelper {
    String getPlatformName();
    boolean isModLoaded(String modId);
    boolean isDevelopmentEnvironment();
    <T extends GameRules.Value<T>> GameRules.Key<T> registerGamerule(String name, GameRules.Type<T> type);
    GameRules.Type<GameRules.BooleanValue> createBooleanValue(boolean defaultValue);
}