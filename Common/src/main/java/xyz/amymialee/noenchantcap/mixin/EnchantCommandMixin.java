package xyz.amymialee.noenchantcap.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantCommand.class)
public class EnchantCommandMixin {
    @WrapOperation(method = "enchant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"))
    private static int noEnchantCap$uncappedCommand(Enchantment enchantment, Operation<Integer> original) {
        int max = original.call(enchantment);
        if (max > 1) {
            return Integer.MAX_VALUE;
        }
        return max;
    }
}