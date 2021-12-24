package amymialee.noenchantcap.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
}
