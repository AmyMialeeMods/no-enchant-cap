package amymialee.noenchantcap.mixin;

import amymialee.noenchantcap.NoEnchantCap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class NECMixin_AnvilScreenHandler {
    //Allow anvils growing levels above cap.
    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    private int redirectGetMaxLevel(Enchantment enchantment) {
        if (NoEnchantCap.getConfig().removeAnvilLevelLimit) {
            if (enchantment.getMaxLevel() == 1) {
                return 1;
            } else {
                return NoEnchantCap.getConfig().newAnvilLevelLimit;
            }
        }
        return enchantment.getMaxLevel();
    }

    //Allow combining all enchantments (Sharpness and Smite, etc.)
    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canCombine(Lnet/minecraft/enchantment/Enchantment;)Z"))
    private boolean redirectCanCombine(Enchantment enchantment, Enchantment enchantment2) {
        if (NoEnchantCap.getConfig().allowAllEnchantmentCombinations) {
            return true;
        }
        return enchantment.canCombine(enchantment2);
    }

    //Remove experience cost limit.
    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 2))
    private int modifyMaxCost(int original) {
        if (NoEnchantCap.getConfig().removeAnvilXPLimit) {
            return Integer.MAX_VALUE;
        } else {
            return original;
        }
    }

    //Removes repair cost increase.
    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setRepairCost(I)V"))
    private void removeRepairCostIncrease(ItemStack instance, int repairCost) {
        if (!NoEnchantCap.getConfig().removeRepairCostIncrease) {
            String REPAIR_COST_KEY = "RepairCost";
            instance.getOrCreateNbt().putInt(REPAIR_COST_KEY, repairCost);
        }
    }

    //Takes fair numbers of levels.
    @Redirect(method = "onTakeOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"))
    private void fairLevelCost(PlayerEntity instance, int levels) {
        if (!NoEnchantCap.getConfig().fairLevelCost) {
            instance.addExperienceLevels(levels);
        } else {
            instance.addExperience(-getLevelTotal(-levels));
        }
    }
    private static int getLevelTotal(int level) {
        int total = 0;
        for (int i = 1; i <= level; i++) {
            if (i >= 30) {
                total += 112 + (i - 30) * 9;
            } else if (i >= 15) {
                total += 37 + (i - 15) * 5;
            } else {
                total += 7 + i * 2;
            }
        }
        return total;
    }
}
