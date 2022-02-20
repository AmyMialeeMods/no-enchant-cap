package amymialee.noenchantcap.mixin;

import amymialee.noenchantcap.NoEnchantCap;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreen.class)
public class NECMixin_AnvilScreen {
    //Removes the too expensive text.
    @Redirect(method = "drawForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;"))
    public PlayerAbilities getAbilities(ClientPlayerEntity instance) {
        PlayerAbilities fakeAbilities = instance.getAbilities();
        if (NoEnchantCap.getConfig().removeAnvilXPLimit) fakeAbilities.creativeMode = true;
        return fakeAbilities;
    }
}
