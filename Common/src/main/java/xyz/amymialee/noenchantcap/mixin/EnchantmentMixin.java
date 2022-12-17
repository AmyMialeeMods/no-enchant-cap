package xyz.amymialee.noenchantcap.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Shadow public abstract boolean isCurse();
    @Shadow public abstract String getDescriptionId();

    @Inject(method = "getFullname", at = @At("HEAD"), cancellable = true)
    public void noEnchantCap$namesOver10(int level, CallbackInfoReturnable<Component> cir) {
        if (level > 10) {
            MutableComponent text = Component.translatable(this.getDescriptionId());
            if (this.isCurse()) {
                text.withStyle(ChatFormatting.RED);
            } else {
                text.withStyle(ChatFormatting.GRAY);
            }
            text.append(" ").append(Component.literal(String.valueOf(level)));
            cir.setReturnValue(text);
        } else if (level <= 0) {
            MutableComponent text = Component.translatable(this.getDescriptionId());
            text.withStyle(ChatFormatting.RED);
            text.append(" ").append(Component.literal(String.valueOf(level)));
            cir.setReturnValue(text);
        }
    }
}