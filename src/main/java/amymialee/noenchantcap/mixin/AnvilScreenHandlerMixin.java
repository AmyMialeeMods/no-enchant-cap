package amymialee.noenchantcap.mixin;

import amymialee.noenchantcap.EnchantModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    public EnchantModConfig config = AutoConfig.getConfigHolder(EnchantModConfig.class).getConfig();

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    private int redirectGetMaxLevel(Enchantment enchantment) {
        if (enchantment.getMaxLevel() == 1) {
            return 1;
        } else {
            return config.enchantCap;
        }
    }
}
