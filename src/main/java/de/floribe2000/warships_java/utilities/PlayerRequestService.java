package de.floribe2000.warships_java.utilities;

import de.floribe2000.warships_java.direct.account.Players;
import de.floribe2000.warships_java.direct.account.PlayersPersonalDataFull;
import de.floribe2000.warships_java.direct.account.PlayersPersonalDataFullRequest;
import de.floribe2000.warships_java.direct.account.PlayersRequest;
import de.floribe2000.warships_java.direct.api.typeDefinitions.Region;


public class PlayerRequestService extends AbstractRequestService {

    public static PlayersPersonalDataFull requestPlayersPersonalData(String playerName, Region region) {
        Players players = PlayersRequest.Companion.createRequest().region(region).searchText(playerName).fetch();
        if (!players.getStatus().get()) {
            throw new IllegalStateException("The request failed!");
        }
        if (players.getData().size() < 1) {
            throw new IllegalStateException("Empty api response for this request. No data found.");
        }
        long id = players.getData().get(0).getAccountId();
        PlayersPersonalDataFullRequest request = PlayersPersonalDataFullRequest.createRequest().region(region).addAccountId(id);
        PlayersPersonalDataFull playerData = request.fetch();
        if (!playerData.getStatus().get()) {
            throw new IllegalStateException("Invalid response status!");
        }
        return playerData;
    }
}
