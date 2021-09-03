package net.shadew.foxes;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public interface FoxTypeHook {
    Fox.Type foxes_new(String _name, String name, ResourceKey<Biome>... biomes);

    List<ResourceKey<Biome>> foxes_getBiomes();

    void foxes_addBiome(ResourceKey<Biome> biome);

    boolean foxes_hasBiome(ResourceKey<Biome> biome);

    static Fox.Type foxes_make(String _name, String name, ResourceKey<Biome>... biomes) {
        return FoxTypeHook.class.cast(Fox.Type.RED).foxes_new(_name, name, biomes);
    }

    @SuppressWarnings("unchecked")
    static Fox.Type foxes_make(String _name, String name) {
        return FoxTypeHook.class.cast(Fox.Type.RED).foxes_new(_name, name);
    }
}
