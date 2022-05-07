# Foxes

### In case it wasn't obvious, foxes _are_ players!

This simple mod adds more types of foxes to Minecraft.

![foxes](images/foxes.png)

The mod is available for Fabric, Forge support is discontinued. **Make sure you download the correct version!**

#### Fabric requirements (for version 1.3)
- Requires Fabric Loader 0.13 or higher
- Should work on any full Minecraft release from 1.18.2 up to 1.19 until further notice (1.19 excluded)
- Fabric API is not required, but recommended anyway

#### Fabric requirements (for version 1.2)
- Requires Fabric Loader 0.12.12 or higher
- Works on Minecraft release 1.18.1 (mod may load on 1.18.2+ but will crash, use version 1.3)
- Fabric API is not required, but recommended anyway

#### Forge requirements (for version 1.2)
- Requires Minecraft Forge 39.0.0 or higher
- Works on Minecraft release 1.18.1 (mod may load on 1.18.2+ but will crash, use version 1.3 with Fabric instead, Forge support is discontinued)

## Fox types

This mod adds 8 new natural types, making a total of 10 natural types. In addition, it adds two skins for foxes with
certain name tags.

Foxes have a certain chance to have a specific type when spawned in a certain biome (this can be modified in a data
pack, see below).

### Red fox (vanilla)

![red fox](images/red.png)

The most common type of fox, spawning in all taiga variants except snowy ones.

- Type ID: `red`
- Summon: `/summon fox ~ ~ ~ {Type: red}`

### Arctic fox (vanilla)

![arctic fox](images/arctic.png)

The most common type of fox in snowy biomes, highly dominating all other fox types there. Only spawns in snowy biomes.

- Type ID: `snow`
- Summon: `/summon fox ~ ~ ~ {Type: snow}`

### Black fox

![black fox](images/black.png)

A relatively common type of fox that spawns in all taiga variants.

- Type ID: `black`
- Summon: `/summon fox ~ ~ ~ {Type: black}`

### Silver fox

![silver fox](images/silver.png)

A relatively common type of fox that spawns in all normal taiga variants. Has a rare chance to spawn in snowy taiga as well.

- Type ID: `silver`
- Summon: `/summon fox ~ ~ ~ {Type: silver}`

### Platinum fox

![platinum fox](images/platinum.png)

A rare type of fox that spawns in any taiga, yet more commonly in snowy biomes.

- Type ID: `platinum`
- Summon: `/summon fox ~ ~ ~ {Type: platinum}`

### Gold platinum fox

![gold platinum fox](images/gold_platinum.png)

A rarer variant of the platinum fox, that spawns in any taiga, yet more commonly in snowy biomes.

- Type ID: `gold_platinum`
- Summon: `/summon fox ~ ~ ~ {Type: gold_platinum}`

### Grey fox

![grey fox](images/grey.png)

A relatively rare variant that spawns only in old growth taiga biomes.

- Type ID: `grey`
- Summon: `/summon fox ~ ~ ~ {Type: grey}`

### Cross fox

![cross fox](images/cross.png)

A common variant that spawns in all not-snowy taiga biomes.

- Type ID: `cross`
- Summon: `/summon fox ~ ~ ~ {Type: cross}`

### Marble fox (since 1.2)

![marble fox](images/marble.png)

A rare variant that spawns in almost any biome but is more common in snowy biomes or old growth tree taiga biomes. It does not spawn in the grove biome.

- Type ID: `marble`
- Summon: `/summon fox ~ ~ ~ {Type: marble}`

### Brown marble fox (since 1.2)

![brown marble fox](images/brown_marble.png)

A alternate variant to the grey marble fox, that spawns in almost any biome but is more common in snowy biomes or old growth tree taiga biomes. It does not spawn in the grove biome.

- Type ID: `brown_marble`
- Summon: `/summon fox ~ ~ ~ {Type: brown_marble}`

### Name tags

Naming a fox "FoxShadew" gives it this heterochromia skin:

![shadew](images/shadew.png)

The blue skin you see in pictures exists in 1.2 and below but has been removed due to personal reasons. Sorry.

## Modifying spawn chances

It is possible to modify the chances of spawning foxes in biomes using a data pack.

Spawn chances are defined in a data pack using custom a `json` format. The files are located in `data/shwfox/shwfoxrates` and are named by the type ID's of foxes.

The JSON format looks like this (weights must be integers):
```json
{
  "biome_id": 300,
  "biome_id_2": 300,
  "<default>": 300,
  "<snow>": 300
}
```

If a biome is not defined for any fox type, it falls back to `<default>` or `<snow>`, based on whether the precipitation type of the biome is snow or not (or just `<default>` before 1.2). If at least one fox type defines a biome specifically, the other fox types are considered to have zero weight and will not spawn in that biome.

If a file is not present in a data pack, it falls back to a file in another data pack, or the default data. It is recommended to specify a file for every type, and specify the same biomes in every file.

Note that the two special textures have no assigned type, and can hence not be defined in a spawn chance file.

**IMPORTANT NOTE: Defining spawn chances does NOT define in which biomes the types spawn _naturally_. It only defines the chance of getting that certain fox type when it is placed in that biome, either by natural spawning or a spawn egg. This is intentional, to modify which biomes have foxes, modify the biome JSON instead.**

Example (`cross.json`):
```json
{
    "taiga": 700,
    
    "old_growth_pine_taiga": 1000,
    "old_growth_spruce_taiga": 1000,
    
    "snowy_taiga": 0,
    "grove": 0,
    
    "<default>": 700,
    "<snow>": 0
}
```

## Other changes

- When the splash on the title screen reads `In case it wasn't obvious, foxes aren't players.`, it is replaced
  with `In case it wasn't obvious, foxes are players.`.
- This mod prevents foxes from freezing in powder snow, since they tend to walk into powder snow carelessly.

In Minecraft 1.17 (1.1 and before):
- This mod fixes [MC-170551](https://bugs.mojang.com/browse/MC-170551). Foxes can now properly spawn on podzol, dirt, or
  coarse dirt, making them much more common in giant tree taiga biomes.
- Spawning a fox using a spawn egg in snowy biomes other than snowy taigas now spawns arctic types (in vanilla it will
  incorrectly spawn red foxes), by default using the same rates as in snowy taigas.

## Using in your development environment

You can download this mod in your development environment via my Maven repository.

### Fabric
```gradle
repositories {
    maven { url "https://maven.shadew.net/" }
}

dependencies {
    modImplementation ("net.shadew:foxes:1.2+fabric") {
        // Exclude Fabric API, presumably you already have this in your environment
        exclude group: "net.fabricmc"
        exclude group: "net.fabricmc.fabric-api"
    }
}
```

### Forge

```gradle
repositories {
    maven { url "https://maven.shadew.net/" }
}

dependencies {
    implementation fg.deobf("net.shadew:foxes:1.2+forge")
}
```
