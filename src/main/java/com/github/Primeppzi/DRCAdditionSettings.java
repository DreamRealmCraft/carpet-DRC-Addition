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
    public static final String CARPET_MOD = "carpet_mod";


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
}
