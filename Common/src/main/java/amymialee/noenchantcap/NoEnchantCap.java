package amymialee.noenchantcap;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class NoEnchantCap implements ModInitializer {
    public static String MODID = "noenchantcap";
    public static ConfigHolder<EnchantModConfig> configHolder;

    @Override
    public void onInitialize() {
        AutoConfig.register(EnchantModConfig.class, Toml4jConfigSerializer::new);
        configHolder = AutoConfig.getConfigHolder(EnchantModConfig.class);
    }

    public static EnchantModConfig getConfig() {
        return configHolder.getConfig();
    }
}
