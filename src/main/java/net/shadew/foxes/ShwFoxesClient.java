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
