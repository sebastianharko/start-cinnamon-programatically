import java.lang.management.ManagementFactory

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.sun.tools.attach.VirtualMachine

import scala.util.control.NonFatal


class SomeActor extends Actor with ActorLogging {
   
  def receive = {
    case "Ping" =>
      log.info("received ping")
      sender ! "Pong"
  }

}


object Main extends App {

  val nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName()
  val p = nameOfRunningVM.indexOf('@')
  val pid = nameOfRunningVM.substring(0, p)

  val jarFilePath: String = "/Users/avicii/.ivy2/local/com.lightbend.cinnamon/cinnamon-agent/2.6.0-SNAPSHOT/jars/cinnamon-agent.jar"

  try {
    val vm = VirtualMachine.attach(pid)
    vm.loadAgent(jarFilePath)
    vm.detach()
  } catch {
    case NonFatal(e) => println("couldn't load agent")
  }


  implicit val system = ActorSystem("minimal")

  import system.dispatcher

  import scala.concurrent.duration._

  val actor = system.actorOf(Props(classOf[SomeActor]))

  system.scheduler.schedule(0 seconds, 1 seconds, actor, "Ping")

}
