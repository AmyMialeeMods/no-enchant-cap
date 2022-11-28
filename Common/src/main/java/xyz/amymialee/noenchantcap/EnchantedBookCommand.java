package xyz.amymialee.noenchantcap;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Collection;

public class EnchantedBookCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register((Commands.literal("enchantedbook").requires((commandSourceStack) -> commandSourceStack.hasPermission(2)))
                .then(Commands.argument("targets", EntityArgument.players())
                        .then((Commands.argument("enchantment", ItemEnchantmentArgument.enchantment())
                                .executes((commandContext) -> enchant(commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ItemEnchantmentArgument.getEnchantment(commandContext, "enchantment"), 1)))
                                .then(Commands.argument("level", IntegerArgumentType.integer())
                                        .executes((commandContext) -> enchant(commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ItemEnchantmentArgument.getEnchantment(commandContext, "enchantment"), IntegerArgumentType.getInteger(commandContext, "level")))))));
    }

    private static int enchant(CommandSourceStack commandSourceStack, Collection<? extends Player> collection, Enchantment enchantment, int level) {
        for (Player player : collection) {
            ItemStack itemStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
            player.addItem(itemStack);
        }
        if (collection.size() == 1) {
            commandSourceStack.sendSuccess(Component.translatable("commands.noenchantcap.enchantedbook.success.single", enchantment.getFullname(level), collection.iterator().next().getDisplayName()), true);
        } else {
            commandSourceStack.sendSuccess(Component.translatable("commands.noenchantcap.enchantedbook.success.multiple", enchantment.getFullname(level), collection.size()), true);
        }
        return collection.size();
    }
}