package example

import com.twitter.util.Future

class MockPriceRetriever extends PriceRetriever with TestData {

  override def retrieveBuyPrice(): Future[String] = buyPriceInfo

  override def retrieveSellPrice(): Future[String] = sellPriceInfo
}
