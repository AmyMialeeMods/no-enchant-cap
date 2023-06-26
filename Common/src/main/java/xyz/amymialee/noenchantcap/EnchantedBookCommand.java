package xyz.amymialee.noenchantcap;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Collection;
import java.util.function.Supplier;

public class EnchantedBookCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
        commandDispatcher.register((Commands.literal("enchantedbook").requires((commandSourceStack) -> commandSourceStack.hasPermission(2)))
                .then(Commands.argument("targets", EntityArgument.players())
                        .then((Commands.argument("enchantment", ResourceArgument.resource(commandBuildContext, Registries.ENCHANTMENT))
                                .executes((commandContext) -> enchant(commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ResourceArgument.getEnchantment(commandContext, "enchantment"), 1)))
                                .then(Commands.argument("level", IntegerArgumentType.integer())
                                        .executes((commandContext) -> enchant(commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ResourceArgument.getEnchantment(commandContext, "enchantment"), IntegerArgumentType.getInteger(commandContext, "level")))))));
    }

    private static int enchant(CommandSourceStack commandSourceStack, Collection<? extends Player> collection, Holder<Enchantment> enchantmentHolder, int level) {
        Enchantment enchantment = enchantmentHolder.value();
        for (Player player : collection) {
            ItemStack itemStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
            player.addItem(itemStack);
        }
        if (collection.size() == 1) {
            commandSourceStack.sendSuccess((Supplier<Component>) Component.translatable("commands.noenchantcap.enchantedbook.success.single", enchantment.getFullname(level), collection.iterator().next().getDisplayName()), true);
        } else {
            commandSourceStack.sendSuccess((Supplier<Component>) Component.translatable("commands.noenchantcap.enchantedbook.success.multiple", enchantment.getFullname(level), collection.size()), true);
        }
        return collection.size();
    }
}