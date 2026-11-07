# NilFix

Nilmod aiming to fix some bugs in old Minecraft versions and mods.

Every fix can be disabled/enabled individually in the config.

## Current fixes

Not every Minecraft version needs the same fixes. Some fixes may not be available on some Minecraft versions.

### Enabled by default

These patches are enabled by default, but can still be turned off in the config, if wanted:
- [General] Remove/skip broken update checkers
- [General] Remove/skip debug logs from the console
- [Vanilla] Fix "The End?" achievement not triggering
- [Vanilla] Fix many rendering bugs on some Intel graphics driver versions (credits to [TheMasterCaver](https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1294926-themastercavers-world?comment=294))
- [Vanilla] Apply Lights Out and Antifreeze patches to fix lighting bugs (credits to [last_username](https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1282658-1-2-5-lights-out-antifreeze-two-mods-to-fix))
- [Vanilla] Fix statistics/achievements load and reset bugs (credits to [Naruto1310](https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1282170-1-5-keepmystats))
- [Forge/ML] Add warnings if two entities share the same ID
- [Forge] Tidy up unresolved ore recipes (important when NEI is also installed as these lead to crashes)
- [Better Dungeons] Add annoying warning when the mod is not properly installed
- [Buildcraft 3] Fix oil dupe using refinery
- [Enchanting Plus] Fix log spam when using the enchanting table
- [Equivalent Exchange 2] Fix built-in Buildcraft addon for Buildcraft 3
- [Factorization] Fix item rendering on barrels
- [Infernal Mobs] Fix boss bar flickering
- [Infernal Mobs] Fix Vengeance modifier hurting the mob itself
- [Inventory Tweaks] Fix shortcuts instantly triggering upon opening GUIs
- [Iron Chest] Fix race condition when trying to upgrade a double chest
- [MAtmos] Use mirror for expansion database
- [More Creeps and Weirdos] Fix health bars being randomised
- [More Creeps and Weirdos] Fix achievements related to Cavemen
- [NEI] Fix pruning item stacks to only a single remaining item when clicking "Show Usages" on them
- [NEI] Made search field better (<kbd>Ctrl</kbd>+<kbd>A</kbd>, arrow keys etc. are now working!)
- [Single Player Commands/Player API] Fix <kbd>Tab</kbd> autocompletion in singleplayer chats
- [RedPower 2] Fix crashes in certain Mystcraft dimensions due to faulty Marble generation
- [Rei's Minimap] Fix sporadic internal crash
- [Tropicraft] Change hardcoded turtle ID to avoid chunk resets with Mo' Creatures

### Disabled by default

You can enable these patches in the config:
- [Vanilla] Fix infinite oceans in a similar way to Vanilla 1.7.2
- [Vanilla] Add/improve item merging mechanics in a similar way to Vanilla 1.4.6
- [Vanilla/FastLeafDecay] Leaves decay faster (credits to [Lumien231](https://www.curseforge.com/minecraft/mc-mods/random-things) and [olafskiii](https://www.curseforge.com/minecraft/mc-mods/fast-leaf-decay))
- [Vanilla/InfiniBows] Bows enchanted with Infinity do not need arrows in your inventory (credits to [Myrathi](https://forum.feed-the-beast.com/threads/myrathis-mod-compendium.18505/))

## FAQ

**Q**: What the hell is a Nilmod? How do I install this? AAAAAAAAAAA??!?!?!!  
**A**: First of all: you can install NilLoader alongside any other loader (including Forge, ModLoader etc.)! A Nilmod is a mod that is loaded by [NilLoader](https://git.sleeping.town/exa/NilLoader). You should [install NilLoader](https://git.sleeping.town/exa/NilLoader#using-nilloader) first and then put NilFix in either the dedicated `nilmods` folder (recommended!) or in the usual `mods` folder of your Minecraft instance.

**Q**: I have found a bug in Vanilla or some mod. Can you please fix it?  
**A**: Well, I will try my best! Please report it over at the [mod's issues](https://github.com/ThexXTURBOXx/NilFix/issues).

**Q**: I have found a bug in NilFix itself. Can you please fix it?  
**A**: That's unfortunate! Please report it over at the [mod's issues](https://github.com/ThexXTURBOXx/NilFix/issues) and I will fix it, of course.

**Q**: I don't have some of these mods installed. Will this mod still work?  
**A**: Yes, NilLoader only applies my fixes if the corresponding mods are present.

**Q**: I want to disable certain fixes. Is this possible?  
**A**: Yes! In your Minecraft instance folder, go to the `config` directory and open the `Nil[...]Fix.cfg` file. There, you can disable unwanted patches.

**Q**: Is there a server version of this?  
**A**: For MC 1.3 and above: I think (and hope) that NilFix should work on servers as-is. Let me know, if that isn't the case!  
For MC 1.2.5 and below: not yet. The server uses very different mappings and I guess (even though, I have not tested this so far) that it is simply incompatible for now.

**Q**: Are there any plans for b1.7.3/1.5.2/[any other version]?  
**A**: If enough people are interested in this: yes, of course!
