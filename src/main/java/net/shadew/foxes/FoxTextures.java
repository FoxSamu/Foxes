package net.shadew.foxes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;

import java.util.HashMap;
import java.util.Map;

public class FoxTextures {
    public static final FoxTextures SHADEW = new FoxTextures("shwfox:shadew_fox", "shwfox:shadew_fox_sleep");
    public static final FoxTextures ZTEREO = new FoxTextures("shwfox:ztereo_fox", "shwfox:ztereo_fox_sleep");

    private static final Map<Fox.Type, FoxTextures> TEXTURES = new HashMap<>();

    public final ResourceLocation awake;
    public final ResourceLocation sleep;

    private FoxTextures(ResourceLocation awake, ResourceLocation sleep) {
        this.awake = new ResourceLocation(awake.getNamespace(), "textures/entity/fox/" + awake.getPath() + ".png");
        this.sleep = new ResourceLocation(sleep.getNamespace(), "textures/entity/fox/" + sleep.getPath() + ".png");
    }

    private FoxTextures(String awake, String sleep) {
        this(new ResourceLocation(awake), new ResourceLocation(sleep));
    }

    public ResourceLocation getTexture(Fox fox) {
        return getTexture(fox.isSleeping());
    }

    public ResourceLocation getTexture(boolean sleeping) {
        return sleeping ? sleep : awake;
    }

    public static void register(Fox.Type type, ResourceLocation awake, ResourceLocation sleep) {
        FoxTextures textures = new FoxTextures(awake, sleep);
        TEXTURES.put(type, textures);
    }

    public static void register(Fox.Type type, String awake, String sleep) {
        register(type, new ResourceLocation(awake), new ResourceLocation(sleep));
    }

    public static FoxTextures getTextures(Fox.Type type) {
        return TEXTURES.get(type);
    }

    public static FoxTextures getTextures(Fox fox) {
        if ("FoxShadew".equals(fox.getName().getContents()))
            return SHADEW;
        if ("ZtereoHYPE".equals(fox.getName().getContents()))
            return ZTEREO;

        return getTextures(fox.getFoxType());
    }
}
