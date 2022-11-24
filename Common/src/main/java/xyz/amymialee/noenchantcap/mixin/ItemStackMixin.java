package xyz.amymialee.noenchantcap.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract CompoundTag getOrCreateTag();

    @Inject(method = "enchant", at = @At("HEAD"), cancellable = true)
    public void noEnchantCap$noByteLimit(Enchantment enchantment, int level, CallbackInfo ci) {
        CompoundTag tag = this.getOrCreateTag();
        if (!tag.contains("Enchantments", 9)) {
            tag.put("Enchantments", new ListTag());
        }
        ListTag nbtList = tag.getList("Enchantments", 10);
        nbtList.add(EnchantmentHelper.storeEnchantment(EnchantmentHelper.getEnchantmentId(enchantment), level));
        ci.cancel();
    }
}