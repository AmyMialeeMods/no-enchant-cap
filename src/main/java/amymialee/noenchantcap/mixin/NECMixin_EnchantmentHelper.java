package amymialee.noenchantcap.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static amymialee.noenchantcap.NoEnchantCap.config;
import static net.minecraft.enchantment.EnchantmentHelper.*;

@Mixin(EnchantmentHelper.class)
public class NECMixin_EnchantmentHelper {
    private static final String ID_KEY = "id";
    private static final String LEVEL_KEY = "lvl";

    //Convert the level to an int
    @Inject(method = "createNbt", at = @At("HEAD"), cancellable = true)
    private static void createNbt(Identifier id, int lvl, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString(ID_KEY, String.valueOf(id));
        nbtCompound.putInt(LEVEL_KEY, lvl);
        cir.setReturnValue(nbtCompound);
    }

    //Removes the (short) cast on level
    @Inject(method = "writeLevelToNbt", at = @At("HEAD"), cancellable = true)
    private static void writeLevelToNbt(NbtCompound nbt, int lvl, CallbackInfo ci) {
        nbt.putInt(LEVEL_KEY, lvl);
        ci.cancel();
    }

    //Removes the 255 clamp
    @Inject(method = "getLevelFromNbt", at = @At("HEAD"), cancellable = true)
    private static void getLevelFromNbt(NbtCompound nbt, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(nbt.getInt(LEVEL_KEY));
    }

    //Caps Knockback at 5k to prevent crashes.
    @Inject(method = "getKnockback", at = @At("HEAD"), cancellable = true)
    private static void getKnockback(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int level = getEquipmentLevel(Enchantments.KNOCKBACK, entity);
        if (config.capEnchantPotency && level > 5000) {
            cir.setReturnValue(5000);
        }
    }

    //Caps Efficiency at 5k to prevent speed overflow.
    @Inject(method = "getEfficiency", at = @At("HEAD"), cancellable = true)
    private static void getEfficiency(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int level = getEquipmentLevel(Enchantments.EFFICIENCY, entity);
        if (config.capEnchantPotency && level > 5000) {
            cir.setReturnValue(5000);
        }
    }

    //Caps Looting at 5k to prevent insane lag.
    @Inject(method = "getLooting", at = @At("HEAD"), cancellable = true)
    private static void getLooting(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int level = getEquipmentLevel(Enchantments.LOOTING, entity);
        if (config.capEnchantPotency && level > 10000) {
            cir.setReturnValue(10000);
        }
    }

    //Caps Fortune at 10k to prevent insane lag.
    @Inject(method = "getLevel", at = @At("HEAD"), cancellable = true)
    private static void getLevel(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (!stack.isEmpty() && enchantment == Enchantments.FORTUNE) {
            Identifier identifier = getEnchantmentId(enchantment);
            NbtList nbtList = stack.getEnchantments();

            for(int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                Identifier identifier2 = getIdFromNbt(nbtCompound);
                if (identifier2 != null && identifier2.equals(identifier)) {
                    if (EnchantmentHelper.getLevelFromNbt(nbtCompound) > 10000) {
                        cir.setReturnValue(10000);
                    }
                }
            }
        }
    }
}
