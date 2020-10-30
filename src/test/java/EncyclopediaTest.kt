import de.floribe2000.warships_java.direct.api.ApiBuilder
import de.floribe2000.warships_java.direct.api.typeDefinitions.*
import de.floribe2000.warships_java.direct.encyclopedia.ConsumablesRequest
import de.floribe2000.warships_java.direct.encyclopedia.ShipParametersRequest
import de.floribe2000.warships_java.direct.encyclopedia.Warships
import de.floribe2000.warships_java.direct.encyclopedia.WarshipsRequest
import org.junit.Assert
import org.junit.Test
import java.io.FileInputStream
import java.util.*
import java.util.function.Consumer

class EncyclopediaTest {
    private val apiKey: String
    private val instanceName = "TEST"

    @Test
    fun testWarWarshipsRequest() {
        val limit = 100
        val page = 5
        val warships = WarshipsRequest.createRequest().region(Region.EU).limit(limit)
                .pageNo(page).fetch()
        assert(warships.status.get()) { warships }
        assert(warships.meta != null) { warships }
        assert(warships.data != null) { warships }
        assert(warships.data!!.size == warships.meta!!.count
                && warships.data!!.size <= limit) { warships }
    }

    @Test
    fun testShipRequestFilterClassCruisers() {
        ApiBuilder.createInstance(apiKey, instanceName)
        val request = WarshipsRequest.createRequest().region(Region.EU).shipType(ShipType.CRUISER)
        var response: Warships
        var pageNo = 1
        do {
            response = request.pageNo(pageNo).fetch()
            Assert.assertNotNull(response)
            Assert.assertEquals(Status.OK, response.status)
            response.data!!.values.forEach(Consumer { entry: Warships.ShipEntry -> Assert.assertEquals(ShipType.CRUISER, entry.type) })
            pageNo++
        } while (response.meta!!.page_total >= pageNo)
    }

    @Test
    fun testShipRequestFilterClassCruisersAndBattleShips() {
        val request = WarshipsRequest.createRequest().region(Region.EU).shipType(ShipType.CRUISER, ShipType.BATTLESHIP)
        var response: Warships
        var pageNo = 1
        do {
            response = request.pageNo(pageNo).fetch()
            Assert.assertNotNull(response)
            Assert.assertEquals(Status.OK, response.status)
            response.data!!.values.forEach(Consumer { entry: Warships.ShipEntry -> Assert.assertTrue(entry.type == ShipType.CRUISER || entry.type == ShipType.BATTLESHIP) })
            pageNo++
        } while (response.meta!!.page_total >= pageNo)
    }

    @Test
    fun testShipRequestFilterTierX() {
        val request = WarshipsRequest.createRequest().region(Region.EU).tier(Tier.X)
        var response: Warships
        var pageNo = 1
        do {
            response = request.pageNo(pageNo).fetch()
            Assert.assertNotNull(response)
            Assert.assertEquals(Status.OK, response.status)
            response.data!!.values.forEach(Consumer { entry: Warships.ShipEntry -> Assert.assertEquals(Tier.X, entry.tier) })
            pageNo++
        } while (response.meta!!.page_total >= pageNo)
    }

    @Test
    fun testShipRequestFilterTierIAndII() {
        val request = WarshipsRequest.createRequest().region(Region.EU).tier(Tier.I, Tier.II)
        var response: Warships
        var pageNo = 1
        do {
            response = request.pageNo(pageNo).fetch()
            Assert.assertNotNull(response)
            Assert.assertEquals(Status.OK, response.status)
            response.data!!.values.forEach(Consumer { entry: Warships.ShipEntry -> Assert.assertTrue(entry.tier == Tier.I || entry.tier == Tier.II) })
            pageNo++
        } while (response.meta!!.page_total >= pageNo)
    }

    @Test
    fun testShipRequestFilterNationEurope() {
        val request = WarshipsRequest.createRequest().region(Region.EU).nation(Nation.EUROPE)
        var response: Warships
        var pageNo = 1
        do {
            response = request.pageNo(pageNo).fetch()
            Assert.assertNotNull(response)
            Assert.assertEquals(Status.OK, response.status)
            response.data!!.values.forEach(Consumer { entry: Warships.ShipEntry -> Assert.assertEquals(Nation.EUROPE, entry.nation) })
            pageNo++
        } while (response.meta!!.page_total >= pageNo)
    }

    @Test
    fun testShipRequestFilterNationEuropeAndPanAsia() {
        val request = WarshipsRequest.createRequest().region(Region.EU).nation(Nation.EUROPE, Nation.PAN_ASIA)
        var response: Warships
        var pageNo = 1
        do {
            response = request.pageNo(pageNo).fetch()
            Assert.assertNotNull(response)
            Assert.assertEquals(Status.OK, response.status)
            response.data!!.values.forEach(Consumer { entry: Warships.ShipEntry -> Assert.assertTrue(entry.nation == Nation.EUROPE || entry.nation == Nation.PAN_ASIA) })
            pageNo++
        } while (response.meta!!.page_total >= pageNo)
    }

    @Test
    fun testShipRequestFilterTypeResearch() {
        val request = WarshipsRequest.createRequest().region(Region.EU).shipCategory(ShipCategory.RESEARCH)
        var response: Warships
        var pageNo = 1
        do {
            response = request.pageNo(pageNo).fetch()
            Assert.assertNotNull(response)
            Assert.assertEquals(Status.OK, response.status)
            response.data!!.values.forEach(Consumer { entry: Warships.ShipEntry -> Assert.assertTrue(!entry.isPremium && !entry.isSpecial) })
            pageNo++
        } while (response.meta!!.page_total >= pageNo)
    }

    @Test
    fun testShipRequestFilterTypePremiumOrSpecial() {
        val request = WarshipsRequest.createRequest().region(Region.EU).shipCategory(ShipCategory.PREMIUM, ShipCategory.SPECIAL)
        var response: Warships
        var pageNo = 1
        do {
            response = request.pageNo(pageNo).fetch()
            Assert.assertNotNull(response)
            Assert.assertEquals(Status.OK, response.status)
            response.data!!.values.forEach(Consumer { entry: Warships.ShipEntry -> Assert.assertTrue(entry.isPremium || entry.isSpecial) })
            pageNo++
        } while (response.meta!!.page_total >= pageNo)
    }

    @Test
    fun testConsumableRequest() {
        val request = ConsumablesRequest.createRequest().region(Region.EU)
        val response = request.fetch()
        assert(response.status.get()) { response }
    }

    @Test
    fun testConsumableRequestFlags() {
        val request = ConsumablesRequest.createRequest().region(Region.EU).type(ConsumableType.FLAGS)
        val response = request.fetch()
        assert(response.status.get()) { response }
        for (entry in response.data!!.entries) {
            assert(entry.value.type === ConsumableType.FLAGS) { entry.value }
            assert(entry.key == entry.value.consumableId.toString()) { entry }
        }
    }

    @Test
    fun testShipParameters() {
        val shipId = 4179605200L
        val request = ShipParametersRequest.createRequest().region(Region.EU).shipId(shipId)
        val response = request.fetch()
        assert(response.status.get()) { response }
        assert(response.data?.get(shipId.toString()) != null) { response }
    }

    init {
        val properties = Properties()
        properties.load(FileInputStream("Warships.properties"))
        apiKey = properties.getProperty("APIKEY")
    }
}