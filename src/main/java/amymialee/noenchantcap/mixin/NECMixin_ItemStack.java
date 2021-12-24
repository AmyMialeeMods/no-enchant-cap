package amymialee.noenchantcap.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.item.ItemStack.ENCHANTMENTS_KEY;

@Mixin(ItemStack.class)
public abstract class NECMixin_ItemStack {
    @Shadow public abstract NbtCompound getOrCreateNbt();
    @Shadow private @Nullable NbtCompound nbt;

    //Removes the (byte) cast on level
    @Inject(method = "addEnchantment", at = @At("HEAD"), cancellable = true)
    public void addEnchantment(Enchantment enchantment, int level, CallbackInfo ci) {
        getOrCreateNbt();
        if (!nbt.contains(ENCHANTMENTS_KEY, 9)) {
            nbt.put(ENCHANTMENTS_KEY, new NbtList());
        }
        NbtList nbtList = this.nbt.getList(ENCHANTMENTS_KEY, 10);
        nbtList.add(EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(enchantment), level));
        ci.cancel();
    }
}
