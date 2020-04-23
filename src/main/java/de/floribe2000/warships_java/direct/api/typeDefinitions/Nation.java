package de.floribe2000.warships_java.direct.api.typeDefinitions;

import com.google.gson.annotations.SerializedName;

/**
 * An enum containing all available nations of the game.
 */
public enum Nation {
	@SerializedName("usa") USA,
	@SerializedName("japan") JAPAN,
	@SerializedName("germany") GERMANY,
	@SerializedName("ussr") USSR,
	@SerializedName("uk") UK,
	@SerializedName("france") FRANCE,
	@SerializedName("italy") ITALY,
	@SerializedName("pan_asia") PAN_ASIA,
	@SerializedName("europe") EUROPE,
	@SerializedName("commonwealth") COMMONWEALTH,
	@SerializedName("pan_america") PAN_AMERICA;
}
