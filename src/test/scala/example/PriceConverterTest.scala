package example

import org.scalatest._
import PriceConverter._
import com.twitter.util.{Await, Future}

class PriceConverterTest extends FlatSpec with Matchers with TestData {

  "Price converter" should "correctly convert from buy/sell to spread format" in {

    val spreadInfo = Await.result(convertPriceToSpread(buyPriceInfo, sellPriceInfo))

    stripWhitespace(spreadInfo) shouldEqual stripWhitespace(expectedSpreadInfo)

  }
}
