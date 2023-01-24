package xyz.amymialee.noenchantcap.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CombatRules.class)
public class CombatRulesMixin {
    @WrapOperation(method = "getDamageAfterMagicAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F"))
    private static float noEnchantCap$uncappedCommand(float variable, float min, float max, Operation<Float> original) {
        return original.call(variable, min, 24.95f);
    }
}