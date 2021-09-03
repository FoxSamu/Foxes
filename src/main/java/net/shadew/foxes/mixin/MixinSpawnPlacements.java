package net.shadew.foxes.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

import net.shadew.foxes.ShwFoxes;

@Mixin(SpawnPlacements.class)
public abstract class MixinSpawnPlacements {
    @Shadow
    protected static  <T extends Mob> void register(EntityType<T> entity, SpawnPlacements.Type type, Heightmap.Types hm, SpawnPlacements.SpawnPredicate<T> condition) {
        throw new AssertionError();
    }

    private static boolean fixedFoxSpawns = false;

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static <T extends Mob> void modRegister(EntityType<T> entity, SpawnPlacements.Type type, Heightmap.Types hm, SpawnPlacements.SpawnPredicate<T> condition, CallbackInfo info) {
        if (entity == EntityType.FOX && !fixedFoxSpawns) {
            fixedFoxSpawns = true;
            register(EntityType.FOX, type, hm, ShwFoxes::checkFoxSpawnRules);
            info.cancel();
        }
    }
}