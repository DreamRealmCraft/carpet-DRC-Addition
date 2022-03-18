package com.github.Primeppzi;
import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.*;


/**
 * Here is your example Settings class you can plug to use carpetmod /carpet settings command
 */
public class DRCAdditionSettings
{
    public static final String DRC="DRC";


    @Rule(
            desc ="Set the spawning restriction of glow squid in 1.18 the same as 1.17",
            extra = {"The spawning of glow squid will be under y=30",
                    "with no light and with blocks contains Tag'base_stone_overwolrd' 5 blocks under it."
            },
            category = {DRC,EXPERIMENTAL}
    )
    public static boolean glowSquidRestriction = false;

    @Rule(
            desc = "Forcing the experience orb to merge together instead of being restricted by rules",
            extra = {"If false, only the experience orb with same amount of experience will merge",
                    "when there are 40+ number of experience orb.",
                    "If true, the experience orb will merge together when in touch",
                    "Player can also absorb the merged experience orb at once"
            },
            category = {DRC,EXPERIMENTAL}
    )
    public static boolean forceExpMerge = false;
    @Rule(
            desc="Glow after eating glow berries",
            extra={"Add glow effect to players after eating glow berries"},
            category = {DRC,FEATURE}
    )
    public static boolean glowGlowBerries = false;

    @Rule(
            desc = "Chance of shooting a blue skull for withers",
            category = {DRC,EXPERIMENTAL,CREATIVE},
            options = {"0","0.001","1"},
            validate = Validator.PROBABILITY.class,
            strict = false
    )
    public static double witherskullchance = 0.001;

    @Rule(
            desc="Show weather in command",
            extra={"Show the weather in text form in the command line,available when cheating is disabled"},
            category = {DRC,SURVIVAL,COMMAND}
    )
    public static boolean tellweather = false;
    @Rule(
            desc = "disable ice forming",
            category = {DRC,CREATIVE}
    )
    public static boolean disableiceforming = false;
    @Rule(
            desc = "disable snow forming",
            category = {DRC,CREATIVE}
    )
    public static  boolean disablesnowforming = false;
    @Rule(
            desc = "Modify the explosion power of TNT",
            category = {DRC,EXPERIMENTAL,CREATIVE},
            options = {"0","4.0"},
            validate = Validator.NONNEGATIVE_NUMBER.class,
            strict = false
    )
    public static double tntExplosionPower = 4.0F;
    @Rule(
            desc = "Modify the fuse time of TNT",
            category = {DRC,EXPERIMENTAL,CREATIVE},
            options = {"0","80"},
            validate = Validator.NONNEGATIVE_NUMBER.class,
            strict = false
    )
    public static double tntFuseTime = 80;
    @Rule(
            desc = "disable villager jockeys",
            category = {DRC,EXPERIMENTAL,SURVIVAL}
    )
    public static boolean disableVillagerJockey = false;
    @Rule(
            desc = "Highlight Entities",
            category = {DRC,EXPERIMENTAL,CREATIVE}
    )
    public static boolean highlightEntity = false;
    @Rule(
            desc = "Locate players",
            category = {DRC,SURVIVAL}
    )
    public static boolean locateplayer = false;
    @Rule(
            desc = "Use the worldspawn rule in 1.17-",
            category = {DRC,SURVIVAL}
    )
    public static boolean oldworldspawn = false;
    @Rule(
            desc = "Can feed axolotol with tropic fish item",
            category = {DRC,EXPERIMENTAL}
    )
    public static boolean easierAxolotlFeed = false;
    @Rule(
            desc = "Chance of breeding a blue axolotl",
            category = {DRC,EXPERIMENTAL,CREATIVE},
            options = {"0","0.000833","1"},
            validate = Validator.PROBABILITY.class,
            strict = false
    )
    public static double blueAxolotlChance = 0.000833;
}
