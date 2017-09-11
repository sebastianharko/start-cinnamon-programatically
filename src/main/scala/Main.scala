import java.lang.management.ManagementFactory

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.sun.tools.attach.VirtualMachine

import scala.util.control.NonFatal


class SomeActor extends Actor with ActorLogging {

  import scala.concurrent.duration._
  import context.dispatcher

  def receive = {
    case "Ping" =>
      log.info("received ping")
      context.system.scheduler.schedule(0 seconds,
        scala.util.Random.nextInt(1200) milliseconds, self, "Ping")
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

  val actor = system.actorOf(Props(classOf[SomeActor]))

  actor ! "Ping"

}
