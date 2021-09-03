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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.animal.Fox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FoxRateLoader implements SimpleResourceReloadListener<Map<Fox.Type, FoxSpawnRates.Config>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation ID = new ResourceLocation("shwfox:rate_loader");

    public FoxRateLoader() {
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    public CompletableFuture<FoxSpawnRates.Config> loadOne(Fox.Type type, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        ResourceLocation resId = new ResourceLocation("shwfox:shwfoxrates/" + type.getName() + ".json");
        return new CompletableFuture<FoxSpawnRates.Config>().completeAsync(
            () -> {
                try {
                    Resource res = manager.getResource(resId);
                    try (InputStreamReader stream = new InputStreamReader(res.getInputStream())) {
                        JsonElement json = new JsonParser().parse(stream);

                        DataResult<FoxSpawnRates.Config> result = FoxSpawnRates.Config.CODEC.parse(JsonOps.INSTANCE, json);

                        if (result.result().isPresent()) {
                            LOGGER.debug("Loaded rates for type '" + type + "'");
                            return result.result().get();
                        } else if (result.error().isPresent()){
                            DataResult.PartialResult<FoxSpawnRates.Config> partial = result.error().get();
                            LOGGER.error("Failed to load fox spawn rates for type '" + type + "': " + partial.message());
                        }
                    }
                } catch (Throwable exc) {
                    LOGGER.error("Failed to load fox spawn rates for type '" + type.getName() + "'", exc);
                }
                return new FoxSpawnRates.Config(Collections.emptyMap());
            },
            executor
        );
    }

    @Override
    public CompletableFuture<Map<Fox.Type, FoxSpawnRates.Config>> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        CompletableFuture<Map<Fox.Type, FoxSpawnRates.Config>> allFuture = CompletableFuture.completedFuture(Collections.synchronizedMap(new HashMap<>()));

        for (Fox.Type type : Fox.Type.values()) {
            CompletableFuture<FoxSpawnRates.Config> future = loadOne(type, manager, profiler, executor);
            allFuture = allFuture.thenCombine(future, (map, config) -> {
                map.put(type, config);
                return map;
            });
        }
        return allFuture;
    }

    @Override
    public CompletableFuture<Void> apply(Map<Fox.Type, FoxSpawnRates.Config> data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        FoxSpawnRates.reset();
        for (Map.Entry<Fox.Type, FoxSpawnRates.Config> entry : data.entrySet()) {
            FoxSpawnRates.forType(entry.getKey()).config(entry.getValue());
        }
        return CompletableFuture.completedFuture(null);
    }
}
