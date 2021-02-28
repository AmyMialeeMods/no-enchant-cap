package amymialee.noenchantcap.mixin;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static amymialee.noenchantcap.NoEnchantCap.config;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {
    @ModifyConstant(method = "drawForeground", constant = @Constant(intValue = 40, ordinal = 0))
    private int modifyMaxCost(int original) {
        if (config.removeAnvilLimit) {
            return 500000;
        } else {
            return original;
        }
    }
}