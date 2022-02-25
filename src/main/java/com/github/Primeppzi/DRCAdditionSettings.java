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
            desc = "Chance of shooting a blue skull for withers",
            category = {DRC,EXPERIMENTAL,CREATIVE},
            options = {"0","0.001","1"},
            validate = Validator.PROBABILITY.class,
            strict = false
    )
    public static double witherskullchance = 0.001;


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
            desc = "Locate players",
            category = {DRC,SURVIVAL}
    )
    public static boolean locateplayer = false;
}
