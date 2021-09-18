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

package net.shadew.foxes.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import net.shadew.foxes.Foxes;

public class FabricInit implements PreLaunchEntrypoint, ModInitializer {
    @Override
    public void onInitialize() {
        Foxes.foxes().init();
    }

    @Override
    public void onPreLaunch() {
        EnvType env = FabricLoader.getInstance().getEnvironmentType();
        Foxes.Environment environment = Foxes.Environment.RAW;

        if (FabricLoader.getInstance().isModLoaded("fabric")) {
            environment = Foxes.Environment.FABRIC_API;
        }

        Foxes.load(environment, env == EnvType.CLIENT);
    }
}
