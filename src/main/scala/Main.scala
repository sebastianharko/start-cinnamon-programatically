import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.event.LoggingReceive


class SomeActor extends Actor with ActorLogging {
   
  def receive = LoggingReceive {
    case "Ping" =>
      sender ! "Pong"
  }

}


object Main extends App {

  implicit val system = ActorSystem("minimal")

  import system.dispatcher

  import scala.concurrent.duration._

  val actor = system.actorOf(Props(classOf[SomeActor]))

  system.scheduler.schedule(0 seconds, 1 seconds, actor, "Ping")

}
