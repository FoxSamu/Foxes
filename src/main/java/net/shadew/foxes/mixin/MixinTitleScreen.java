package net.shadew.foxes.mixin;

import net.minecraft.client.gui.screens.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
    @Shadow
    @Nullable
    private String splash;

    @Inject(method = "init", at = @At("RETURN"))
    private void modifySplash(CallbackInfo info) {
        if (splash != null && splash.equals("In case it isn't obvious, foxes aren't players."))
            splash = "In case it isn't obvious, foxes are players.";
    }
}
