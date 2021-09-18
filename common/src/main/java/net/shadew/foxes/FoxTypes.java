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

import net.minecraft.world.entity.animal.Fox;

public class FoxTypes {
    public static final Fox.Type RED = Fox.Type.RED;
    public static final Fox.Type SNOW = Fox.Type.SNOW;
    public static final Fox.Type BLACK = FoxTypeHook.make("BLACK", "black");
    public static final Fox.Type SILVER = FoxTypeHook.make("SILVER", "silver");
    public static final Fox.Type PLATINUM = FoxTypeHook.make("PLATINUM", "platinum");
    public static final Fox.Type GOLD_PLATINUM = FoxTypeHook.make("GOLD_PLATINUM", "gold_platinum");
    public static final Fox.Type GREY = FoxTypeHook.make("GREY", "grey");
    public static final Fox.Type CROSS = FoxTypeHook.make("CROSS", "cross");
}
