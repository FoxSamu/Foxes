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

package net.shadew.foxes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class ShwFoxes implements ModInitializer {
    public static final Fox.Type RED = Fox.Type.RED;
    public static final Fox.Type SNOW = Fox.Type.SNOW;
    public static final Fox.Type BLACK = FoxTypeHook.foxes_make("BLACK", "black");
    public static final Fox.Type SILVER = FoxTypeHook.foxes_make("SILVER", "silver");
    public static final Fox.Type PLATINUM = FoxTypeHook.foxes_make("PLATINUM", "platinum");
    public static final Fox.Type GOLD_PLATINUM = FoxTypeHook.foxes_make("GOLD_PLATINUM", "gold_platinum");
    public static final Fox.Type GREY = FoxTypeHook.foxes_make("GREY", "grey");
    public static final Fox.Type CROSS = FoxTypeHook.foxes_make("CROSS", "cross");

    @Override
    public void onInitialize() {
        System.out.println("Hello Foxy world!");

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FoxRateLoader());
    }

    public static boolean checkFoxSpawnRules(EntityType<? extends Animal> entity, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return level.getBlockState(pos.below()).is(BlockTags.DIRT) && level.getRawBrightness(pos, 0) > 8;
    }
}
