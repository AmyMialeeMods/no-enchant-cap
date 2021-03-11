package amymialee.noenchantcap.mixin;

import amymialee.noenchantcap.NoEnchantCap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Shadow @Final public EnchantmentTarget type;

    @Shadow public abstract String getTranslationKey();

    @Inject(method = "isAcceptableItem", at = @At(value = "HEAD"), cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (NoEnchantCap.config.allowAnyEnchantOnAnyItem) {
            cir.setReturnValue(true);
        } else {
            if (this.getTranslationKey().equals("enchantment.minecraft.looting") && stack.getItem() instanceof AxeItem) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(this.type.isAcceptableItem(stack.getItem()));
            }
        }
    }
}
