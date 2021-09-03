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

import net.fabricmc.api.ClientModInitializer;

public class ShwFoxesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FoxTextures.register(ShwFoxes.BLACK, "shwfox:black_fox", "shwfox:black_fox_sleep");
        FoxTextures.register(ShwFoxes.SILVER, "shwfox:silver_fox", "shwfox:silver_fox_sleep");
        FoxTextures.register(ShwFoxes.PLATINUM, "shwfox:platinum_fox", "shwfox:platinum_fox_sleep");
        FoxTextures.register(ShwFoxes.GOLD_PLATINUM, "shwfox:gold_platinum_fox", "shwfox:gold_platinum_fox_sleep");
        FoxTextures.register(ShwFoxes.GREY, "shwfox:grey_fox", "shwfox:grey_fox_sleep");
        FoxTextures.register(ShwFoxes.CROSS, "shwfox:cross_fox", "shwfox:cross_fox_sleep");
    }
}
