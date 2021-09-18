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

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.shadew.foxes.Foxes;

@Mixin(SpawnPlacements.class)
public abstract class MixinSpawnPlacements {
    @Shadow
    protected static  <T extends Mob> void register(EntityType<T> entity, SpawnPlacements.Type type, Heightmap.Types hm, SpawnPlacements.SpawnPredicate<T> condition) {
        throw new AssertionError();
    }

    private static boolean fixedFoxSpawns = false;

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static <T extends Mob> void modRegister(EntityType<T> entity, SpawnPlacements.Type type, Heightmap.Types hm, SpawnPlacements.SpawnPredicate<T> condition, CallbackInfo info) {
        if (entity == EntityType.FOX && !fixedFoxSpawns) {
            fixedFoxSpawns = true;
            register(EntityType.FOX, type, hm, Foxes::checkFoxSpawnRules);
            info.cancel();
        }
    }
}
