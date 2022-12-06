package xyz.amymialee.noenchantcap.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.noenchantcap.NoEnchantCap;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Shadow public int experienceLevel;
    @Shadow public float experienceProgress;
    @Shadow public int totalExperience;
    @Shadow protected int enchantmentSeed;
    @Shadow public abstract void giveExperiencePoints(int points);

    protected PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "onEnchantmentPerformed", at = @At(value = "HEAD"), cancellable = true)
    private void noEnchantCap$fairishEnchantCost(ItemStack stack, int levels, CallbackInfo ci) {
        if (this.level.getGameRules().getBoolean(NoEnchantCap.FAIR_EXPERIENCE_COST)) {
            int min = Math.min(30, this.experienceLevel);
            this.giveExperiencePoints(-noEnchantCap$getExperienceTotal(min - levels, min));
            if (this.experienceLevel < 0) {
                this.experienceLevel = 0;
                this.experienceProgress = 0.0F;
                this.totalExperience = 0;
            }
            this.enchantmentSeed = this.random.nextInt();
            ci.cancel();
        }
    }

    private static int noEnchantCap$getExperienceTotal(int startingAt, int endingAt) {
        int total = 0;
        for (int i = startingAt; i < endingAt ; i++) {
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