/*
 * Copyright 2022 Shadew
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.shadew.foxes.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.shadew.foxes.FoxSpawnRates;

@Mixin(Fox.class)
public class MixinFox {
    @Redirect(
        method = "finalizeSpawn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/animal/Fox$Type;" +
                         "byBiome(Lnet/minecraft/core/Holder;)" +
                         "Lnet/minecraft/world/entity/animal/Fox$Type;"
        )
    )
    private Fox.Type foxByType(Holder<Biome> biome, ServerLevelAccessor level) {
        Fox fox = Fox.class.cast(this);
        ResourceLocation id = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(biome.value());
        return FoxSpawnRates.pick(fox.getRandom(), id, biome);
    }
}
