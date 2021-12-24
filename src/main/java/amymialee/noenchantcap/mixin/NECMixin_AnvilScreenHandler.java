package amymialee.noenchantcap.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import static amymialee.noenchantcap.NoEnchantCap.config;

@Mixin(AnvilScreenHandler.class)
public class NECMixin_AnvilScreenHandler {
    //Allow anvils growing levels above cap.
    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    private int redirectGetMaxLevel(Enchantment enchantment) {
        if (config.removeAnvilLevelLimit) {
            if (enchantment.getMaxLevel() == 1) {
                return 1;
            } else {
                return config.newAnvilLevelLimit;
            }
        }
        return enchantment.getMaxLevel();
    }

    //Allow combining all enchantments (Sharpness and Smite, etc.)
    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canCombine(Lnet/minecraft/enchantment/Enchantment;)Z"))
    private boolean redirectCanCombine(Enchantment enchantment, Enchantment enchantment2) {
        if (config.allowAllEnchantmentCombinations) {
            return true;
        }
        return enchantment.canCombine(enchantment2);
    }

    //Remove experience cost limit.
    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 2))
    private int modifyMaxCost(int original) {
        if (config.removeAnvilXPLimit) {
            return Integer.MAX_VALUE;
        } else {
            return original;
        }
    }
}
