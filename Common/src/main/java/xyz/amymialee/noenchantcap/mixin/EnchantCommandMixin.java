package xyz.amymialee.noenchantcap.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(EnchantCommand.class)
public class EnchantCommandMixin {
    @WrapOperation(method = "register", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/arguments/IntegerArgumentType;integer(I)Lcom/mojang/brigadier/arguments/IntegerArgumentType;"))
    private static IntegerArgumentType noEnchantCap$anyOnAny(final int min, Operation<IntegerArgumentType> original) {
        return original.call(Integer.MIN_VALUE);
    }

    @WrapOperation(method = "enchant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"))
    private static int noEnchantCap$uncappedCommand(Enchantment enchantment, Operation<Integer> original) {
        int max = original.call(enchantment);
        if (max > 1) {
            return Integer.MAX_VALUE;
        }
        return max;
    }

    @WrapOperation(method = "enchant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;isEnchantmentCompatible(Ljava/util/Collection;Lnet/minecraft/world/item/enchantment/Enchantment;)Z"))
    private static boolean noEnchantCap$anyCombination(Collection<Enchantment> enchantments, Enchantment enchantment, Operation<Boolean> original) {
        return true;
    }

    @WrapOperation(method = "enchant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;canEnchant(Lnet/minecraft/world/item/ItemStack;)Z"))
    private static boolean noEnchantCap$canEnchant(Enchantment enchantment, ItemStack stack, Operation<Boolean> original) {
        return true;
    }
}