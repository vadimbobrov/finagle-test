package example

import com.twitter.util.Future

trait TestData {

  def stripWhitespace(s: String) = s
    .replaceAll(" ","")
    .replaceAll("\n", "")


  val buyPriceInfo = Future.value(
    """
      |{
      |  "data": {
      |    "base": "BTC",
      |    "amount": "1020.25",
      |    "currency": "USD"
      |  }
      |}
    """.stripMargin)

  val sellPriceInfo = Future.value(
    """
      |{
      |  "data": {
      |    "base": "BTC",
      |    "amount": "1001.20",
      |    "currency": "USD"
      |  }
      |}
    """.stripMargin)


  val expectedSpreadInfo =
    """
      |{
      |    "buyPriceUSD": "1020.25",
      |    "sellPriceUSD": "1001.20",
      |    "spreadUSD": "19.05"
      |}
    """.stripMargin
}
