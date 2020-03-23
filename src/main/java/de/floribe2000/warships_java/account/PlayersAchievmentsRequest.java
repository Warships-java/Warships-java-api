package de.floribe2000.warships_java.account;

import de.floribe2000.warships_java.api.ApiBuilder;
import de.floribe2000.warships_java.api.Region;
import de.floribe2000.warships_java.api.RequestAction;
import lombok.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayersAchievmentsRequest implements RequestAction<PlayersAchievments>, AccountRequest {

    /**
     * The server region for this request
     */
    @NonNull
    private Region region;

    /**
     * The account id of the player
     */
    @NonNull
    private List<String> accountId;

    PlayersAchievmentsRequest(Region region, String accountId) {
        this.region = region;
        this.accountId = new ArrayList<>();
        this.accountId.add(accountId);
    }

    @Override
    public PlayersAchievments fetch() {
        String path = "/wows/account/achievements/";
        StringBuilder ids = new StringBuilder();
        String prefix = "";
        for (String str : accountId) {
            ids.append(prefix).append(str);
            prefix = ",";
        }
        String url = region.getBaseURL() + path + ApiBuilder.getApiKeyAsParam() + "&account_id=" + ids.toString();
        PlayersAchievments result;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            result = GSON.fromJson(reader, PlayersAchievments.class);
        } catch (Exception e) {
            //TODO exception handling
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * A list of all available achievments. Entries are named after their technical IDs, their ingame names can be retrieved by using {@link #getTitle()}.
     */
    @AllArgsConstructor
    public enum AchievmentElement {
        FOOLSDAY_TROOPER("Not Overly Assaultive"),
        COLLECTION_HAPPY_BIRTHDAY2018_COMPLETED("3 Years of World of Warships"),
        BD2_NY2016("Ho-Ho-Ho!"),
        ONE_SOLDIER_IN_THE_FIELD("Solo Warrior"),
        CAMPAIGN_NEWYEAR2018ELITE_COMPLETED("New Year Raid"),
        CLAN_SEASON_1_LEAGUE_3("Gale"),
        SEA_LEGEND("Bane of the Oceans"),
        CLAN_SEASON_1_LEAGUE_1("Typhoon"),
        CLAN_SEASON_1_LEAGUE_4("Squall"),
        COLLECTION_BRITISHARC_COMPLETED("Royal Navy Destroyers"),
        SCIENCE_OF_WINNING_TACTICIAN("Naval Warfare. Tactics"),
        DOUBLE_KILL("Double Strike"),
        COLLECTION_DUNKIRK_COMPLETED("Dunkirk"),
        MAIN_CALIBER("High Caliber"),
        BD2_RANKS("Like a Pro"),
        NY17_WIN_ALL("Hoarder 2016"),
        COLLECTION_YAMAMOTO_COMPLETED("Yamamoto Isoroku"),
        COLLECTION_AMERICANARC_COMPLETED("American Cruisers"),
        BD2016_RUN_FOREST("Run! Admiral! Run!"),
        NY17_AIMING("Aiming? Too Much Effort"),
        CAMPAIGN_NEWYEAR2019PEF_COMPLETED("Mighty Prinz"),
        AVASHARKS("Sharks"),
        FOOLSDAY_POEKHALI("Poekhali!"),
        COLLECTION_HAPPYNEWYEAR2019_COMPLETED("Belle Époque"),
        LIQUIDATOR("Liquidator"),
        CLAN_SEASON_1_LEAGUE_2("Storm"),
        WITHERING("Witherer"),
        BD2016_FIRESHOW("Fire Show"),
        FIREPROOF("Fireproof"),
        BD2_CONTAINERS("You've Got a Parcel Waiting for You!"),
        EV1APR19_ATTDEF1("Guardian of the Galaxy"),
        NY17_SAFECRACKER("Tin Can"),
        SUPPORT("Confederate"),
        MERCENARY("Workhorse"),
        MESSENGER("In the Thick of It"),
        PVE_HON_PR_SAVE_3("Protector"),
        PVE_HON_PR_SAVE_1("Shield"),
        SCIENCE_OF_WINNING_ARSONIST("Naval Warfare. Arson"),
        CAMPAIGN_BISMARCK_COMPLETED("The Hunt for Bismarck"),
        TWITCH_PRIME("Twitch Prime"),
        NY17_DRESS_THE_TREE("Feeling Good"),
        WORKAHOLIC_L("Senior Supply Officer"),
        HEADBUTT("Die-Hard"),
        BD2_CAMPAIGNS("Will Now Have Stories"),
        BD2_CREW("Skipper on Deck!"),
        ATBA_CALIBER("Close Quarters Expert"),
        MERCENARY_L("Supply Officer"),
        BD2_ARP("Look Who We Have Here!"),
        FILLALBUM_BRIT_CVARC_COMPLETED("Naval Aviation"),
        NEVER_ENOUGH_MONEY("Business Magnate"),
        BD2016_FESTIV_SOUP("Festive Soup"),
        ARSONIST("Arsonist"),
        WARRIOR("Kraken Unleashed!"),
        PVE_HON_PR_DONE_ALL_1("Important Missions"),
        DETONATED("Detonation"),
        CHIEF_ENGINEER("Chief Naval Architect"),
        PVE_HON_FRAG_CLASS("Natural Selection"),
        NO_DAY_WITHOUT_ADVENTURE_L("Smooth Supply"),
        FILLALBUM_FRENCHDD_COMPLETED("Legion of Honor"),
        SCIENCE_OF_WINNING_LUCKY("Naval Warfare. Lucky Shot"),
        CAMPAIGN_SB_COMPLETED("Honorable Service"),
        STREAM("Stream Drop!"),
        COLLECTION_HSF2018_COMPLETED("High School Fleet"),
        EV_POST_APOCALYPSE2019_WINNER("True Survivalist"),
        COLLECTION_OVECHKIN_COMPLETED("The Great Eight"),
        COLLECTION_HAPPYNEWYEAR2018_COMPLETED("Battle of the North Cape"),
        PVE_HON_FRAG_OBJ("Shark Among Shrimps"),
        UNSINKABLE("Unsinkable"),
        ENGINEER("Naval Constructor"),
        CAMPAIGN_HALSEY_COMPLETED("Hit Hard! Hit Fast! Hit Often!"),
        CAMPAIGN_NEWYEAR2018BASIC_COMPLETED("Battle of the North Cape"),
        AVACOMMON("Go Navy!"),
        JUNIOR_PLANNER("Junior Naval Designer"),
        BD2_CAMO("Paint It Gold!"),
        PVE_HERO_WIN_ONE("Will to Win"),
        INSTANT_KILL("Devastating Strike"),
        VETERAN("Veteran"),
        PVE_HON_FRAG_WAY("Crash Tester"),
        COLLECTION_VIVELAFRANCE_COMPLETED("Vive la France"),
        PVE_HERO_WIN_SUR("Tactical Expertise"),
        NO_PRICE_FOR_HEROISM("Legend of the Seas"),
        BD2_PVE("Eat It, Toaster!"),
        DREADNOUGHT("Dreadnought"),
        HALLOWEEN_2017("Exorcist"),
        HALLOWEEN_2016("Ghostbuster"),
        HALLOWEEN_2018("Terror of the Deep"),
        BD2016_PARTY_CHECK_IN("Queue Jumper"),
        PVE_HON_PR_SAVE_2("Guardian"),
        PVE_HERO_DAM_ENEM("Assistant"),
        FIGHTER("Warrior"),
        NY17_500_LEAGUES("An Epic Journey"),
        MILLIONAIR("Moneybags"),
        CAMPAIGN_YAMAMOTO_COMPLETED("Yamamoto Isoroku"),
        EV1APR19_TORPEDO1("Torpedoproof"),
        PVE_HON_DONE_CLASS("Universal Seaman"),
        PVE_HON_PR_DONE_1("Weather Beaten"),
        EV1APR19_TORPEDO5("Master Blaster"),
        BD2_BISMARCK("Before Zee Germans Get There!"),
        MESSENGER_L("Junior Supply Officer"),
        BATTLE_HERO("Battle Hero"),
        BD2016_WRONG_SOW("A Shot in the Dark"),
        PVE_HON_WIN_ALL_DONE("Sea Star"),
        CAMPAIGN1_COMPLETED("Science of Victory"),
        BD2_CREDITS("Not a Pyramid Scheme!"),
        SCIENCE_OF_WINNING_HARD_EDGED("Naval Warfare. Ramming"),
        FILLALBUM_AZURLANE_COMPLETED("Azur Lane"),
        RETRIBUTION("It's Just a Flesh Wound!"),
        BD2_HW2016("Foes Way Past Their Prime"),
        CAMPAIGN_NY17B_COMPLETED("The Hunt for Graf Spee"),
        TWITCH_WG("Twitch Welcome"),
        BD2016_PARTY_ANIMAL("Life and Soul of the Party"),
        SCIENCE_OF_WINNING_TO_THE_BOTTOM("Naval Warfare. Flooding"),
        GREATEEIGHT("Four-Goal Haul"),
        COLLECTION_BISMARCK_COMPLETED("The Hunt for Bismarck"),
        FILLALBUM_SOVIETBB_COMPLETED("In the service of the Motherland"),
        NY17_BREAK_THE_BANK("Break the Bank"),
        ALL_THREE_HALLOWEEN_COMPLETE("Warrior of the Light"),
        CAPITAL("Initial Capital"),
        SCIENCE_OF_WINNING_BOMBARDIER("Naval Warfare. Weaponry Basics"),
        FILLALBUM_ITCA_COMPLETED("Resolute and Rapid"),
        PVE_DUNKERQUE_OPERATION_DYNAMO("Operation Dynamo"),
        FIRST_BLOOD("First Blood"),
        COLLECTION_WOWSBIRTHDAY_COMPLETED("2 Years of World of Warships"),
        AIRDEFENSEEXPERT("AA Defense Expert"),
        NY17_WIN_AT_LEAST_ONE("Good Start"),
        CAMPAIGN_NY17B_COMPLETED_EXCELLENT("The Hunt for Graf Spee with Honors"),
        AMAUTEUR("Amateur");

        /**
         * The ingame title of this element
         */
        @Getter
        private String title;

        /**
         * Allows to retrieve an element based on its title
         *
         * @param title the ingame title of the achievment
         * @return the AchievmentElement with a matching title or null if no match is found
         */
        public static AchievmentElement retrieveElement(String title) {
            for (AchievmentElement element : values()) {
                if (element.title.equals(title)) {
                    return element;
                }
            }
            return null;
        }
    }
}
