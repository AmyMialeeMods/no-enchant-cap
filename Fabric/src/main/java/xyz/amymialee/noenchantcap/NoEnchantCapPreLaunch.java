package xyz.amymialee.noenchantcap;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

/**
 * PreLaunch to Initialize Mixin Extras which is used heavily in the mod.
 */
public class NoEnchantCapPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        MixinExtrasBootstrap.init();
    }
}