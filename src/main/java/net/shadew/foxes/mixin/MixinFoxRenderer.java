package net.shadew.foxes.mixin;

import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.shadew.foxes.FoxTextures;
import net.shadew.foxes.ShwFoxes;

@Mixin(FoxRenderer.class)
public class MixinFoxRenderer {
    @Inject(method = "getTextureLocation", at = @At("HEAD"), cancellable = true)
    private void modifyTextureLocation(Fox fox, CallbackInfoReturnable<ResourceLocation> info) {
        FoxTextures textures = FoxTextures.getTextures(fox);
        if (textures != null)
            info.setReturnValue(textures.getTexture(fox));
    }
}
