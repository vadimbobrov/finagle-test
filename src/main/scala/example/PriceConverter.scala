package example

import com.twitter.util.Future
import spray.json.DefaultJsonProtocol
import spray.json._

case class CoinbaseResponse(data: Data)
case class Data(base: String, currency: String, amount: String)

object CoinbaseResponseJsonProtocol extends DefaultJsonProtocol {
  implicit val dataFormat = jsonFormat3(Data)
  implicit val resFormat = jsonFormat1(CoinbaseResponse)
}

case class LookoutResponse(buyPriceUSD: String, sellPriceUSD: String, spreadUSD: String)

object LookoutResponseJsonProtocol extends DefaultJsonProtocol {
  implicit val lookoutFormat = jsonFormat3(LookoutResponse)
}

object PriceConverter {

  import CoinbaseResponseJsonProtocol._
  import LookoutResponseJsonProtocol._

  /**
    * convert buy and sell price quotes in CoinBase format into spread info in JSON
    * @param buyPriceInfo buy price info in JSON
    * @param sellPriceInfo sell price info in JSON
    * @return spread info in JSON
    */
  def convertPriceToSpread(buyPriceInfo: Future[String], sellPriceInfo: Future[String]): Future[String] = {

    val resultBuy = buyPriceInfo.map(_.parseJson.convertTo[CoinbaseResponse].data)
    val resultSell = sellPriceInfo.map(_.parseJson.convertTo[CoinbaseResponse].data)

    val spreadInfo = for {
      buy <- resultBuy
      sell <- resultSell
    } yield
      LookoutResponse(buy.amount, sell.amount, (BigDecimal(buy.amount) - BigDecimal(sell.amount)).toString)

    spreadInfo.map(_.toJson.prettyPrint)
  }
}
