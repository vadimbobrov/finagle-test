package example

object Runner extends App {

  new Server(new CoinbasePriceRetriever).start()

}
