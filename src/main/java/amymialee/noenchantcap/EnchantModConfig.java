package amymialee.noenchantcap;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "noenchantcap")
public
class EnchantModConfig implements ConfigData {
    public boolean removeAnvilLimit = true;
    public boolean allowAllEnchantmentCombinations = false;

    public boolean useGlobalEnchantCap = true;
    public int globalEnchantCap = 10;

    public int protectionCap = 4;
    public int protectionFireCap = 4;
    public int protectionBlastCap = 4;
    public int protectionProjectileCap = 4;

    public int respirationCap = 3;
    public int thornsCap = 3;
    public int frostWalkerCap = 2;
    public int soulSpeedCap = 3;
    public int depthStriderCap = 3;
    public int featherFallingCap = 4;

    public int smiteCap = 5;
    public int sharpnessCap = 5;
    public int baneOfArthropodsCap = 5;
    public int knockbackCap = 2;
    public int fireAspectCap = 2;
    public int lootingCap = 3;
    public int sweepingCap = 3;

    public int efficiencyCap = 5;
    public int fortuneCap = 3;

    public int powerCap = 5;
    public int punchCap = 2;

    public int lureCap = 3;
    public int luckOfTheSeaCap = 3;

    public int impalingCap = 5;
    public int loyaltyCap = 3;

    public int quickChargeCap = 3;
    public int piercingCap = 4;

    public int unbreakingCap = 3;
}
