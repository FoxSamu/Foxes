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

import com.google.common.reflect.Reflection;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

public class Foxes {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "shwfox";

    private static Foxes instance;
    private static Environment env;

    private FoxRateLoader rateLoader;

    public Foxes() {
        if (instance != null) {
            throw new RuntimeException(
                "Loaded proxy class for Shadew's Foxes twice! " +
                    "This is critical, please open a bug report on https://github.com/FoxShadew/Foxes/issues " +
                    "and PROVIDE THE CRASH REPORT!"
            );
        }
        instance = this;
    }

    public void init() {
        System.out.println("Hello Foxy world!");
        Reflection.initialize(FoxTypes.class);
    }

    public void serverResourcesInit(List<PreparableReloadListener> listeners) {
        if (rateLoader == null)
            rateLoader = new FoxRateLoader();
        listeners.add(rateLoader);
    }

    public boolean mustInjectResourceManagerNamespace() {
        return true;
    }

    public static boolean checkFoxSpawnRules(EntityType<? extends Animal> entity, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return level.getBlockState(pos.below()).is(BlockTags.DIRT) && level.getRawBrightness(pos, 0) > 8;
    }

    public static Foxes foxes() {
        return instance;
    }

    public static Environment env() {
        return env;
    }

    public static Foxes load(Environment environment, boolean client) {
        if (environment == null) {
            LOGGER.warn("Null environment, using " + Environment.RAW);
            environment = Environment.RAW;
        }

        String proxyClass = client ? environment.client : environment.server;
        LOGGER.info("Loading Shadew's foxes on {} in {} envionment", client ? "CLIENT" : "SERVER", environment);
        LOGGER.info("Proxy class: {}", proxyClass);

        try {
            Class<?> cls = Class.forName(proxyClass);
            cls.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(
                "Failed to load proxy class for Shadew's Foxes! " +
                    "This is critical, please open a bug report on https://github.com/FoxShadew/Foxes/issues " +
                    "and PROVIDE THE CRASH REPORT!" + System.lineSeparator() +
                    String.format("Environment: %s, on %s%n", environment, client ? "CLIENT" : "SERVER") +
                    String.format("Proxy class: %s", proxyClass),
                e
            );
        }

        env = environment;

        return foxes();
    }

    public enum Environment {
        RAW("net.shadew.foxes.Foxes", "net.shadew.foxes.ClientFoxes"),
        FORGE("net.shadew.foxes.forge.ForgeFoxes", "net.shadew.foxes.forge.ForgeClientFoxes"),
        FABRIC_API("net.shadew.foxes.fabric.FabricFoxes", "net.shadew.foxes.fabric.FabricClientFoxes");

        private final String server;
        private final String client;

        Environment(String server, String client) {
            this.server = server;
            this.client = client;
        }
    }
}
