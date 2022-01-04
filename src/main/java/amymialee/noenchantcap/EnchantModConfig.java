package amymialee.noenchantcap;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "no-enchant-cap")
public class EnchantModConfig implements ConfigData {
    public boolean removeAnvilXPLimit = true;
    public boolean allowAllEnchantmentCombinations = false;
    public boolean allowAnyEnchantOnAnyItem = true;
    public boolean removeAnvilLevelLimit = false;
    public boolean fairLevelCost = true;
    public boolean removeRepairCostIncrease = true;
    public int newAnvilLevelLimit = 255;
}