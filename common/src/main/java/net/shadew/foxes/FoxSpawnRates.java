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

package net.shadew.foxes;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FoxSpawnRates {

    private static final Map<Fox.Type, FoxSpawnRates> REGISTRY = new HashMap<>();
    private static final Map<ResourceLocation, Cache> CACHE = new HashMap<>();

    private final Map<ResourceLocation, Integer> weightMap = new HashMap<>();
    private int defaultWeight;
    private int snowDefaultWeight;

    private FoxSpawnRates() {
    }

    public void weight(ResourceLocation biome, int weight) {
        if (weight < 0)
            weightMap.remove(biome);
        else
            weightMap.put(biome, weight);
    }

    public void defaultWeight(int weight) {
        defaultWeight = weight;
    }

    public void snowDefaultWeight(int weight) {
        snowDefaultWeight = weight;
    }

    public void biomeWeight(String name, int weight) {
        weight(new ResourceLocation(name), weight);
    }

    public int weight(ResourceLocation biome) {
        return weightMap.getOrDefault(biome, -1);
    }

    public int defaultWeight(boolean snow) {
        return snow ? snowDefaultWeight : defaultWeight;
    }

    public int defaultWeight() {
        return defaultWeight;
    }

    public int snowDefaultWeight() {
        return snowDefaultWeight;
    }

    public int biomeWeight(ResourceKey<Biome> biome) {
        return weight(biome.location());
    }

    public int biomeWeight(String name) {
        return weight(new ResourceLocation(name));
    }

    public void config(Config config) {
        for (Map.Entry<Either<ResourceLocation, Boolean>, Integer> entry : config.weights().entrySet()) {
            Either<ResourceLocation, Boolean> either = entry.getKey();
            int weight = entry.getValue();
            either.right().ifPresent(b -> {
                if (b) defaultWeight(weight);
                else snowDefaultWeight(weight);
            });
            either.left().ifPresent(id -> {
                weight(id, weight);
            });
        }
    }

    public static Fox.Type pick(Random random, ResourceLocation biome, Holder<Biome> biomeInst) {
        Cache cache = CACHE.computeIfAbsent(biome, key -> {
            Map<Fox.Type, Integer> weights = new HashMap<>();

            int totalWeight = 0;

            if (biome != null) {
                for (Map.Entry<Fox.Type, FoxSpawnRates> entry : REGISTRY.entrySet()) {
                    Fox.Type type = entry.getKey();
                    int weight = entry.getValue().weight(biome);
                    if (weight > 0) {
                        totalWeight += weight;
                        weights.put(type, weight);
                    }
                }
            }

            if (weights.isEmpty() || totalWeight <= 0) {
                for (Map.Entry<Fox.Type, FoxSpawnRates> entry : REGISTRY.entrySet()) {
                    Fox.Type type = entry.getKey();
                    int weight = entry.getValue().defaultWeight(biomeInst.value().getPrecipitation() == Biome.Precipitation.SNOW);
                    if (weight > 0) {
                        totalWeight += weight;
                        weights.put(type, weight);
                    }
                }
            }

            return new Cache(weights, totalWeight);
        });

        if (cache.isEmpty()) {
            return Fox.Type.byBiome(biomeInst);
        }

        int rand = random.nextInt(cache.total());

        for (Map.Entry<Fox.Type, Integer> entry : cache.weights().entrySet()) {
            rand -= entry.getValue();
            if (rand <= 0)
                return entry.getKey();
        }

        return Fox.Type.byBiome(biomeInst);
    }

    public static FoxSpawnRates forType(Fox.Type type) {
        return REGISTRY.computeIfAbsent(type, k -> new FoxSpawnRates());
    }

    public static void reset() {
        CACHE.clear();
        REGISTRY.values().forEach(rates -> rates.weightMap.clear());
    }

    public static record Config(Map<Either<ResourceLocation, Boolean>, Integer> weights) {
        public static final Codec<Either<ResourceLocation, Boolean>> BIOME_CODEC = Codec.either(
            ResourceLocation.CODEC,
            Codec.STRING.flatXmap(
                k -> k.equals("<default>")
                     ? DataResult.success(Boolean.TRUE)
                     : k.equals("<snow>")
                       ? DataResult.success(Boolean.FALSE)
                       : DataResult.error("Invalid biome"),
                k -> k ? DataResult.success("<default>") : DataResult.success("<snow>")
            )
        );

        public static final Codec<Config> CODEC = Codec.unboundedMap(BIOME_CODEC, Codec.INT)
                                                       .xmap(Config::new, Config::weights);
    }

    private static record Cache(Map<Fox.Type, Integer> weights, int total) {
        boolean isEmpty() {
            return weights.isEmpty() || total <= 0;
        }
    }
}
