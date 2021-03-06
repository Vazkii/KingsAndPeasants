/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Kings and Peasants Mod.
 *
 * Kings and Peasants is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [15 Jul 2013, 01:38:40 (GMT)]
 */
package vazkii.kap.util.handler;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import vazkii.kap.core.lib.LibBlockIDs;
import vazkii.kap.core.lib.LibBlockNames;
import vazkii.kap.core.lib.LibItemIDs;
import vazkii.kap.core.lib.LibItemNames;

public final class ConfigHandler {

	private static final String OPTION_SCROLLING_TEXT = "scollingText.show";
	private static final String OPTION_DEATH_GOLD_LOSS = "goldLoss.death";
	private static final String OPTION_DEATH_REP_LOSS = "renownLoss.death";
	private static final String OPTION_KINGDOM_RADUIS = "kingdom.radius.tier%s";
	private static final String OPTION_MIN_KINGDOM_DISTANCE = "kingdom.minDistance";

	private static final String COMMENT_SCROLLING_TEXT = "Set to false to remove the scrolling text next to the crosshair when the playe recieves gold or renown.";
	private static final String COMMENT_DEATH_GOLD_LOSS = "The multiplier (between 0.0 and 1.0) of the gold in hand to lose on death.";
	private static final String COMMENT_DEATH_REP_LOSS = "The multiplier (between 0.0 and 1.0) of the reputation of the player to lose on death.";
	private static final String COMMENT_KINGDOM_RADIUS = "The radius of a kingdom of tier %s. Do not set to negative values.";
	private static final String COMMENT_MIN_KINGDOM_DISTANCE = "The minimum distance (in blocks) the thrones of two kingdoms can be from eachother. Do not set to negative values.";

	public static boolean doScrollingText = true;
	public static float deathGoldLoss = 0.5F;
	public static float deathRepLoss = 1F;
	public static int maxKingdomDistance = 300;

	public static int[] kingdomRadius = new int[] {
		32, 48, 64, 80, 100
	};

	private static Configuration config;

	public static void init(File file) {
		config = new Configuration(file);

		config.load();

		doScrollingText = loadGeneralBool(OPTION_SCROLLING_TEXT, doScrollingText, COMMENT_SCROLLING_TEXT);

		LibItemIDs.idItemHeraldry = loadItem(LibItemNames.NAME_ITEM_HERALDRY, LibItemIDs.DEFAULT_ITEM_HERALDRY);
		LibItemIDs.idKingdomScroll = loadItem(LibItemNames.NAME_KINGDOM_SCROLL, LibItemIDs.DEFAULT_KINGDOM_SCROLL);
		LibItemIDs.idVassalScroll = loadItem(LibItemNames.NAME_VASSAL_SCROLL, LibItemIDs.DEFAULT_VASSAL_SCROLL);

		LibBlockIDs.idBlockHeraldry = loadBlock(LibBlockNames.NAME_HERALDRY_BLOCK, LibBlockIDs.DEFAULT_BLOCK_HERALDRY);
		LibBlockIDs.idThrone = loadBlock(LibBlockNames.NAME_THRONE, LibBlockIDs.DEFAULT_THRONE);

		deathGoldLoss = getRangedFloat(OPTION_DEATH_GOLD_LOSS, deathGoldLoss, COMMENT_DEATH_GOLD_LOSS, 0F, 1F);
		deathRepLoss = getRangedFloat(OPTION_DEATH_REP_LOSS, deathRepLoss, COMMENT_DEATH_REP_LOSS, 0F, 1F);
		maxKingdomDistance = getPositiveInteger(OPTION_MIN_KINGDOM_DISTANCE, maxKingdomDistance, COMMENT_MIN_KINGDOM_DISTANCE);

		for(int i = 0; i < kingdomRadius.length; i++)
			kingdomRadius[i] = getPositiveInteger(String.format(OPTION_KINGDOM_RADUIS, i + 1), kingdomRadius[i], String.format(COMMENT_KINGDOM_RADIUS, i + 1));

		config.save();
	}

	private static int loadItem(String label, int defaultID) {
		return config.getItem(label, defaultID).getInt(defaultID);
	}

	private static int loadBlock(String label, int defaultID) {
		return config.getBlock(label, defaultID).getInt(defaultID);
	}

	private static boolean loadGeneralBool(String label, boolean defaultValue, String comment) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, label, defaultValue);
		prop.comment = comment;
		return prop.getBoolean(defaultValue);
	}

	private static float getRangedFloat(String label, float defaultValue, String comment, float min, float max) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, label, defaultValue);
		prop.comment = comment;
		return (float) Math.max(min, Math.min(max, prop.getDouble(defaultValue)));
	}

	private static int getPositiveInteger(String label, int defaultValue, String comment) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, label, defaultValue);
		prop.comment = comment;
		return Math.max(0, prop.getInt(defaultValue));
	}
}
