package example

import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.{Await, Future}

import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest._

class ServerTest extends FlatSpec with Matchers with TestData {

  "Service" should "correctly respond to request" in {

    val server = new Server(new MockPriceRetriever)
    scala.concurrent.Future { server.start() }

    val client: Service[http.Request, http.Response] = Http.newService("localhost:8080")
    val request = http.Request(http.Method.Get, "/")
    request.host = "localhost"

    Await.result(client(request).onSuccess { rep: http.Response =>
      stripWhitespace(rep.contentString) shouldEqual stripWhitespace(expectedSpreadInfo)
    })

    Await.ready(server.stop())
  }

}
