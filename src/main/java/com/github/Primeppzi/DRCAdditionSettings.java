package com.github.Primeppzi;
import carpet.settings.Rule;
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
}
