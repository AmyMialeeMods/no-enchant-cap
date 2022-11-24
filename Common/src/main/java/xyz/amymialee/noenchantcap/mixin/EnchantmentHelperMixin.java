package xyz.amymialee.noenchantcap.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "storeEnchantment", at = @At("HEAD"), cancellable = true)
    private static void noEnchantCap$storeInt(ResourceLocation id, int lvl, CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("id", String.valueOf(id));
        compoundTag.putInt("lvl", lvl);
        cir.setReturnValue(compoundTag);
    }

    @Inject(method = "setEnchantmentLevel", at = @At("HEAD"), cancellable = true)
    private static void noEnchantCap$setInt(CompoundTag nbt, int lvl, CallbackInfo ci) {
        nbt.putInt("lvl", lvl);
        ci.cancel();
    }

    @Inject(method = "getEnchantmentLevel(Lnet/minecraft/nbt/CompoundTag;)I", at = @At("HEAD"), cancellable = true)
    private static void noEnchantCap$clampSkip(CompoundTag nbt, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(nbt.getInt("lvl"));
    }

    @Inject(method = "getKnockbackBonus", at = @At("HEAD"), cancellable = true)
    private static void noEnchantCap$clampKnockback(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, entity);
        if (level > 4800) {
            cir.setReturnValue(4800);
        }
    }

    @Inject(method = "getBlockEfficiency", at = @At("HEAD"), cancellable = true)
    private static void noEnchantCap$clampEfficiency(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, entity);
        if (level > 4800) {
            cir.setReturnValue(4800);
        }
    }

    @Inject(method = "getMobLooting", at = @At("HEAD"), cancellable = true)
    private static void noEnchantCap$clampLooting(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.MOB_LOOTING, entity);
        if (level > 9600) {
            cir.setReturnValue(9600);
        }
    }

    @Inject(method = "getItemEnchantmentLevel", at = @At(value = "RETURN"), cancellable = true)
    private static void noEnchantCap$clampFortune(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (enchantment == Enchantments.BLOCK_FORTUNE) {
            if (cir.getReturnValue() > 9600) {
                cir.setReturnValue(9600);
            }
        }
    }
}