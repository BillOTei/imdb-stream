import akka.Done
import akka.actor.ActorSystem
import com.imdb.models.Title
import com.imdb.services.DataService

import scala.concurrent._
import scala.util.{Failure, Success}

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("Imdb")

  val source                                = DataService.streamFile("title.basics.tsv")
  val done: Future[Done]                    = source.runForeach(i => {
    println(Title.fromMap(i) match {
      case Success(value) =>
      case Failure(exception) => {
        println(exception.getMessage)
      }
    })
  })
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  done.onComplete(_ => system.terminate())

}
