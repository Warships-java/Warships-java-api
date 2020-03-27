import de.floribe2000.warships_java.account.PlayerStatisticsByDateRequest;
import de.floribe2000.warships_java.account.PlayersAchievmentsRequest;
import de.floribe2000.warships_java.account.PlayersPersonalDataFullRequest;
import de.floribe2000.warships_java.account.PlayersRequest;
import de.floribe2000.warships_java.api.ApiBuilder;
import de.floribe2000.warships_java.api.Region;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PlayersTest {

    private String apiKey = "";

    @Test
    public void testPlayersRequest() {
        //TODO
        ApiBuilder.createInstance(apiKey, true);
        PlayersRequest request = PlayersRequest.createRequest().region(Region.EU).searchText("floribe");
        System.out.println(request.fetch());
        //System.out.println(AccountRequestBuilder.playersRequest(Region.EU, "floribe").fetch());

    }

    @Test
    public void testPlayerPersonalDataRequest() {
        //TODO
        ApiBuilder.createInstance(apiKey, true);
        System.out.println(PlayersPersonalDataFullRequest.createRequest().region(Region.EU).addAccountId(537376379)
                .addExtraField(PlayersPersonalDataFullRequest.ExtraField.PVE)
                .addExtraField(PlayersPersonalDataFullRequest.ExtraField.RANK_SOLO).fetch());
    }

    @Test
    public void testPlayersAchievments() {
        ApiBuilder.createInstance(apiKey, true);
        System.out.println(PlayersAchievmentsRequest.createRequest().region(Region.EU).accountId(537376379).fetch());
        System.out.println(PlayersAchievmentsRequest.AchievmentElement.retrieveElement("Solo Warrior"));
    }

    @Test
    public void testPlayersStatisticsByDate() {
        ApiBuilder.createInstance(apiKey, true);
        System.out.println(PlayerStatisticsByDateRequest.createRequest().region(Region.EU).accountId(537376379).addDate("20200318").fetch());
        System.out.println(PlayerStatisticsByDateRequest.createRequest().region(Region.EU).accountId(537376379).addDate("20200228").addDate("20200118")
                .extra(PlayerStatisticsByDateRequest.ExtraField.PVE).fetch());
    }

    @Test
    public void testRateLimiter() {
        int accountId = 537376379;
        ApiBuilder.createInstance(apiKey);
        PlayersPersonalDataFullRequest request = PlayersPersonalDataFullRequest.createRequest().region(Region.EU).addAccountId(accountId);
        ExecutorService service = Executors.newFixedThreadPool(40);
        for (int i = 0; i < 30; i++) {
            service.execute(() -> System.out.println(request.fetch()));
        }
        service.shutdown();
        try {
            service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
