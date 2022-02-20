package amymialee.noenchantcap.mixin;

import amymialee.noenchantcap.NoEnchantCap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class NECMixin_Enchantment {
    @Shadow public abstract boolean isCursed();
    @Shadow public abstract String getTranslationKey();

    //Makes enchantments over level 10 use integer levels
    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    public void getName(int level, CallbackInfoReturnable<Text> cir) {
        if (level > 10) {
            TranslatableText mutableText = new TranslatableText(getTranslationKey());
            if (isCursed()) {
                mutableText.formatted(Formatting.RED);
            } else {
                mutableText.formatted(Formatting.GRAY);
            }
            mutableText.append(" ").append(new LiteralText(Integer.toString(level)));
            cir.setReturnValue(mutableText);
        }
        if (level < 0) {
            TranslatableText mutableText = new TranslatableText(getTranslationKey());
            mutableText.formatted(Formatting.RED);
            mutableText.append(" ").append(new LiteralText(Integer.toString(level)));
            cir.setReturnValue(mutableText);
        }
    }

    //Put any enchant on any item
    @Inject(method = "isAcceptableItem", at = @At(value = "HEAD"), cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (NoEnchantCap.getConfig().allowAnyEnchantOnAnyItem) {
            cir.setReturnValue(true);
        }
    }
}
