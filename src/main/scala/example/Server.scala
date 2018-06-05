package example

import com.twitter.finagle.{Http, ListeningServer, Service, http}
import com.twitter.io.Buf
import com.twitter.util.{Await, Future}
import PriceConverter._


class Server(retriever: PriceRetriever) {

  var server: ListeningServer = _

  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] = {

      convertPriceToSpread(retriever.retrieveBuyPrice(), retriever.retrieveSellPrice()).map { spread =>

        val response = http.Response(http.Status.Ok)
        response.content = Buf.Utf8(spread)
        response.contentType = "application/json"
        response
      }
    }
  }

  def start(): Unit = {
    server = Http.serve(":8080", service)
    Await.ready(server)
  }

  def stop() = {
    server.close()
  }


}
