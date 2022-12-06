package xyz.amymialee.noenchantcap.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.noenchantcap.NoEnchantCap;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i, inventory, containerLevelAccess);
    }

    @WrapOperation(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"))
    private int noEnchantCap$uncappedAnvil(Enchantment enchantment, Operation<Integer> original) {
        int max = original.call(enchantment);
        if (max == 1) {
            return 1;
        }
        if (this.player.level.getGameRules().getBoolean(NoEnchantCap.UNCAPPED_ANVILS)) {
            return Integer.MAX_VALUE;
        } else {
            ItemStack itemStack1 = this.inputSlots.getItem(0);
            ItemStack itemStack2 = this.inputSlots.getItem(1);
            int a = EnchantmentHelper.getEnchantments(itemStack1).getOrDefault(enchantment, 0);
            int b = EnchantmentHelper.getEnchantments(itemStack2).getOrDefault(enchantment, 0);
            return Math.max(max, Math.max(a, b));
        }
    }

    @WrapOperation(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;isCompatibleWith(Lnet/minecraft/world/item/enchantment/Enchantment;)Z"))
    private boolean noEnchantCap$moreCombinations(Enchantment enchantment1, Enchantment enchantment2, Operation<Boolean> original) {
        if (this.player.level.getGameRules().getBoolean(NoEnchantCap.MIX_ANY_ENCHANTS)) {
            return true;
        }
        return original.call(enchantment1, enchantment2);
    }

    @WrapOperation(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAbilities()Lnet/minecraft/world/entity/player/Abilities;", ordinal = 0))
    private Abilities noEnchantCap$enchantAnyItem(Player player, Operation<Abilities> original) {
        if (this.player.level.getGameRules().getBoolean(NoEnchantCap.ENCHANT_ANY_ITEM)) {
            Abilities abilities = new Abilities();
            abilities.instabuild = true;
            return abilities;
        }
        return original.call(player);
    }

    @ModifyExpressionValue(method = "createResult", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;instabuild:Z", ordinal = 1))
    private boolean noEnchantCap$allowAnyCost(boolean original) {
        return true;
    }

    @WrapWithCondition(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setRepairCost(I)V"))
    private boolean noEnchantCap$staticRepairCost(ItemStack instance, int repairCost) {
        return !this.player.level.getGameRules().getBoolean(NoEnchantCap.STATIC_REPAIR_COST);
    }


    @WrapWithCondition(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;giveExperienceLevels(I)V"))
    private boolean noEnchantCap$fairAnvilCost(Player instance, int levels) {
        if (this.player.level.getGameRules().getBoolean(NoEnchantCap.FAIR_EXPERIENCE_COST)) {
            this.player.giveExperiencePoints(-noEnchantCap$getExperienceTotal(-levels));
            return false;
        }
        return true;
    }

    private static int noEnchantCap$getExperienceTotal(int level) {
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