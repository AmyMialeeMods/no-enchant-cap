package amymialee.noenchantcap.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.command.EnchantCommand;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;

@Mixin(EnchantCommand.class)
public class EnchantCommandMixin {
    /*
    @Shadow @Final private static DynamicCommandExceptionType FAILED_ENTITY_EXCEPTION;
    @Shadow @Final private static DynamicCommandExceptionType FAILED_ITEMLESS_EXCEPTION;
    @Shadow @Final private static SimpleCommandExceptionType FAILED_EXCEPTION;

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/EnchantCommand;execute(Lnet/minecraft/server/command/ServerCommandSource;Ljava/util/Collection;Lnet/minecraft/enchantment/Enchantment;I)I"))
    private static int executeRedirect(ServerCommandSource source, Collection<? extends Entity> targets,
                                       Enchantment enchantment, int level) throws CommandSyntaxException {
        int i = 0;
        Iterator var5 = targets.iterator();
        while(true) {
            while(true) {
                while(true) {
                    while(var5.hasNext()) {
                        Entity entity = (Entity)var5.next();
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity)entity;
                            ItemStack itemStack = livingEntity.getMainHandStack();
                            if (!itemStack.isEmpty()) {
                                itemStack.addEnchantment(enchantment, level);
                                ++i;
                            } else if (targets.size() == 1) {
                                throw FAILED_ITEMLESS_EXCEPTION.create(livingEntity.getName().getString());
                            }
                        } else if (targets.size() == 1) {
                            throw FAILED_ENTITY_EXCEPTION.create(entity.getName().getString());
                        }
                    }
                    if (i == 0) {
                        throw FAILED_EXCEPTION.create();
                    }
                    if (targets.size() == 1) {
                        source.sendFeedback(new TranslatableText("commands.enchant.success.single", new Object[]{enchantment.getName(level), ((Entity)targets.iterator().next()).getDisplayName()}), true);
                    } else {
                        source.sendFeedback(new TranslatableText("commands.enchant.success.multiple", new Object[]{enchantment.getName(level), targets.size()}), true);
                    }
                    return i;
                }
            }
        }
    }

     */

    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    private static int redirectGetMaxLevel(Enchantment enchantment) {
        return 32766;
    }

    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z"))
    private static boolean redirectIsAcceptableItem(Enchantment enchantment, ItemStack stack) {
        return true;
    }

    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;isCompatible(Ljava/util/Collection;Lnet/minecraft/enchantment/Enchantment;)Z"))
    private static boolean redirectIsCompatible(Collection<Enchantment> existing, Enchantment candidate) {
        return true;
    }

    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;addEnchantment(Lnet/minecraft/enchantment/Enchantment;I)V"))
    private static void redirectAddEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {
        itemStack.getOrCreateTag();
        try {
            assert itemStack.getTag() != null;
            if (!itemStack.getTag().contains("Enchantments", 9)) {
                itemStack.getTag().put("Enchantments", new ListTag());
            }
        } catch (Exception ignored) {}

        ListTag listTag = itemStack.getOrCreateTag().getList("Enchantments", 10);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(enchantment)));
        compoundTag.putInt("lvl", level);
        listTag.add(compoundTag);
    }
}
