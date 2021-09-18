/*
 * Copyright 2021 Shadew
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

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

import net.shadew.foxes.FoxSpawnRates;
import net.shadew.foxes.FoxTypeHook;

@Mixin(Fox.Type.class)
@SuppressWarnings("unused")
public abstract class MixinFoxType implements FoxTypeHook {
    @SafeVarargs
    @Invoker("<init>")
    private static Fox.Type make(String _name, int _ordinal, int id, String name, ResourceKey<Biome>... biomes) {
        throw new AssertionError();
    }

    @Unique
    private static final Random BIOME_RANDOM = new Random();

    @Shadow
    @Final
    @Mutable
    private static Fox.Type[] $VALUES;

    @Shadow
    @Final
    @Mutable
    private static Fox.Type[] BY_ID;

    @Shadow
    @Final
    @Mutable
    private static Map<String, Fox.Type> BY_NAME;

    @Shadow
    @Final
    @Mutable
    private List<ResourceKey<Biome>> biomes;

    @Unique
    private static void expandById(int newId) {
        if (BY_ID.length <= newId) {
            Fox.Type[] byId = new Fox.Type[newId + 1];
            System.arraycopy(BY_ID, 0, byId, 0, BY_ID.length);
            BY_ID = byId;
        }
    }

    private static void ensureByNameIsMutable() {
        if (!(BY_NAME instanceof HashMap)) {
            BY_NAME = new HashMap<>(BY_NAME);
        }
    }

    @Override
    public Fox.Type foxes_new(String _name, String name, ResourceKey<Biome>... biomes) {
        List<Fox.Type> variants = new ArrayList<>(Arrays.asList($VALUES));
        Fox.Type last = variants.get(variants.size() - 1);

        Fox.Type newType = make(_name, last.ordinal() + 1, last.getId() + 1, name, biomes);
        variants.add(newType);

        FoxTypeHook typeHook = FoxTypeHook.class.cast(newType);

        expandById(newType.getId());
        BY_ID[newType.getId()] = newType;

        ensureByNameIsMutable();
        BY_NAME.put(newType.getName(), newType);

        $VALUES = variants.toArray(Fox.Type[]::new);
        return newType;
    }

    @Inject(method = "byBiome", at = @At("HEAD"), cancellable = true)
    private static void modifyByBiome(Optional<ResourceKey<Biome>> optional, CallbackInfoReturnable<Fox.Type> info) {
        info.setReturnValue(FoxSpawnRates.pick(BIOME_RANDOM, optional.orElse(null)));
    }

    @Override
    public List<ResourceKey<Biome>> foxes_getBiomes() {
        return biomes;
    }

    @Override
    public void foxes_addBiome(ResourceKey<Biome> biome) {
        if (!(biomes instanceof ArrayList))
            biomes = new ArrayList<>(biomes);

        if (!biomes.contains(biome))
            biomes.add(biome);
    }

    @Override
    public boolean foxes_hasBiome(ResourceKey<Biome> biome) {
        return biomes.contains(biome);
    }
}
