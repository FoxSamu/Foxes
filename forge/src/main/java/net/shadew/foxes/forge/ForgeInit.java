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

package net.shadew.foxes.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import net.shadew.foxes.FoxRateLoader;
import net.shadew.foxes.Foxes;

@Mod("shwfox")
public class ForgeInit {
    public ForgeInit() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        Foxes.load(Foxes.Environment.FORGE, FMLEnvironment.dist == Dist.CLIENT);

        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
    }

    private void setup(FMLCommonSetupEvent event) {
        Foxes.foxes().init();
    }

    private void serverStarting(AddReloadListenerEvent event) {
        event.addListener(new FoxRateLoader());
    }
}
