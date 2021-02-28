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
public class AnvilScreenHandlerMixin {
    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    private int redirectGetMaxLevel(Enchantment enchantment) {
        if (enchantment.getMaxLevel() == 1) {
            return 1;
        } else {
            return config.enchantCap;
        }
    }

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 2))
    private int modifyMaxCost(int original) {
        if (config.removeAnvilLimit) {
            return 500000;
        } else {
            return original;
        }
    }
}
