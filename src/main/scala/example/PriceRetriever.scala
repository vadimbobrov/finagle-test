package example

import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.Future

trait PriceRetriever {
  def retrieveBuyPrice(): Future[String]
  def retrieveSellPrice(): Future[String]
}

class CoinbasePriceRetriever extends PriceRetriever {

  val client: Service[http.Request, http.Response] =
    Http
      .client
      .withTls("api.coinbase.com")
      .newService("api.coinbase.com:443")


  override def retrieveBuyPrice(): Future[String] = {
    val requestBuy = http.Request(http.Method.Get, "/v2/prices/BTC-USD/buy")
    client(requestBuy).map(_.contentString)
  }

  override def retrieveSellPrice(): Future[String] = {
    val requestSell = http.Request(http.Method.Get, "/v2/prices/BTC-USD/sell")
    client(requestSell).map(_.contentString)
  }
}
