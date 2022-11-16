package xyz.amymialee.noenchantcap;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;
import xyz.amymialee.noenchantcap.platform.Services;

public class NoEnchantCap {
    public static final String MOD_ID = "noenchantcap";

    public static final GameRules.Key<GameRules.BooleanValue> MIX_ANY_ENCHANTS = Services.PLATFORM.registerGamerule(MOD_ID + ":mix_any_enchants", Services.PLATFORM.createBooleanValue(false));
    public static final GameRules.Key<GameRules.BooleanValue> ENCHANT_ANY_ITEM = Services.PLATFORM.registerGamerule(MOD_ID + ":enchant_any_item", Services.PLATFORM.createBooleanValue(false));
    public static final GameRules.Key<GameRules.BooleanValue> UNCAPPED_ANVILS = Services.PLATFORM.registerGamerule(MOD_ID + ":uncapped_anvils", Services.PLATFORM.createBooleanValue(false));
    public static final GameRules.Key<GameRules.BooleanValue> STATIC_REPAIR_COST = Services.PLATFORM.registerGamerule(MOD_ID + ":static_repair_cost", Services.PLATFORM.createBooleanValue(true));
    public static final GameRules.Key<GameRules.BooleanValue> FAIR_EXPERIENCE_COST = Services.PLATFORM.registerGamerule(MOD_ID + ":fair_experience_cost", Services.PLATFORM.createBooleanValue(true));

    public static void init() {}

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MOD_ID, id);
    }
}