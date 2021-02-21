package amymialee.noenchantcap;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class NoEnchantCap implements ModInitializer {
    EnchantModConfig config;
    @Override
    public void onInitialize() {
        AutoConfig.register(EnchantModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(EnchantModConfig.class).getConfig();
    }
}
