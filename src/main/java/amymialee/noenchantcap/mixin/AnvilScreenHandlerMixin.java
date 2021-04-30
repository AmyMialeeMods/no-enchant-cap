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
            if (config.useGlobalEnchantCap) {
                return config.globalEnchantCap;
            } else {
                switch (enchantment.getTranslationKey()) {
                    case "enchantment.minecraft.sharpness":
                        return config.sharpnessCap;
                    case "enchantment.minecraft.smite":
                        return config.smiteCap;
                    case "enchantment.minecraft.bane_of_arthropods":
                        return config.baneOfArthropodsCap;
                    case "enchantment.minecraft.knockback":
                        return config.knockbackCap;
                    case "enchantment.minecraft.fire_aspect":
                        return config.fireAspectCap;
                    case "enchantment.minecraft.sweeping":
                        return config.sweepingCap;
                    case "enchantment.minecraft.protection":
                        return config.protectionCap;
                    case "enchantment.minecraft.fire_protection":
                        return config.protectionFireCap;
                    case "enchantment.minecraft.feather_falling":
                        return config.featherFallingCap;
                    case "enchantment.minecraft.blast_protection":
                        return config.protectionBlastCap;
                    case "enchantment.minecraft.projectile_protection":
                        return config.protectionProjectileCap;
                    case "enchantment.minecraft.respiration":
                        return config.respirationCap;
                    case "enchantment.minecraft.depth_strider":
                        return config.depthStriderCap;
                    case "enchantment.minecraft.frost_walker":
                        return config.frostWalkerCap;
                    case "enchantment.minecraft.soul_speed":
                        return config.soulSpeedCap;
                    case "enchantment.minecraft.efficiency":
                        return config.efficiencyCap;
                    case "enchantment.minecraft.unbreaking":
                        return config.unbreakingCap;
                    case "enchantment.minecraft.looting":
                        return config.lootingCap;
                    case "enchantment.minecraft.fortune":
                        return config.fortuneCap;
                    case "enchantment.minecraft.luck_of_the_sea":
                        return config.luckOfTheSeaCap;
                    case "enchantment.minecraft.lure":
                        return config.lureCap;
                    case "enchantment.minecraft.power":
                        return config.powerCap;
                    case "enchantment.minecraft.punch":
                        return config.punchCap;
                    case "enchantment.minecraft.thorns":
                        return config.thornsCap;
                    case "enchantment.minecraft.loyalty":
                        return config.loyaltyCap;
                    case "enchantment.minecraft.impaling":
                        return config.impalingCap;
                    case "enchantment.minecraft.quick_charge":
                        return config.quickChargeCap;
                    case "enchantment.minecraft.piercing":
                        return config.piercingCap;
                    default:
                        return config.globalEnchantCap;
                }
            }
        }
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/enchantment/Enchantment;canCombine(Lnet/minecraft/enchantment/Enchantment;)Z"))
    private boolean redirectCanCombine(Enchantment enchantment, Enchantment enchantment2) {
        if (config.allowAllEnchantmentCombinations) {
            if (enchantment.getTranslationKey().equals("enchantment.minecraft.silk_touch") &&
                    enchantment2.getTranslationKey().equals("enchantment.minecraft.fortune") ||
                    enchantment.getTranslationKey().equals("enchantment.minecraft.riptide") &&
                    enchantment2.getTranslationKey().equals("enchantment.minecraft.loyalty") ||
                    enchantment.getTranslationKey().equals("enchantment.minecraft.fortune") &&
                    enchantment2.getTranslationKey().equals("enchantment.minecraft.silk_touch") ||
                    enchantment.getTranslationKey().equals("enchantment.minecraft.loyalty") &&
                            enchantment2.getTranslationKey().equals("enchantment.minecraft.riptide")
            ) {
                return false;
            }
            return true;
        }
        return enchantment.canCombine(enchantment2);
    }

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 2))
    private int modifyMaxCost(int original) {
        if (config.removeAnvilLimit) {
            return 50000;
        } else {
            return original;
        }
    }
}
