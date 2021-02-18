package org.moda.actor.cookdemo

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}

trait Command

abstract class DomainEvent[T](eventName: String, data: T) extends Command

final case class Message(data: String, signature: String)
final case class AkkaActorEvnevt(eventName: String, data: Message) extends
  DomainEvent[Message](eventName = eventName, data = data)

case object GracefulShutdown extends Command


class ModaCookActor {

  def apply(): Behavior[Command] =
    Behaviors.setup[Command](context => {
      println(context.self)
      Behaviors.receiveMessage((command: Command) => {
//        command.getClass match {
////          case DomainEvent =>
//          case _ =>
//
//        }
//        println(s"message-data => ${data.data.data}, " +
//          s"message-signature => ${data.data.signature}")
          Behaviors.same
      })
    })
}

object ModaCookActor {
  def apply(): Behavior[Command] = new ModaCookActor()()
}

object Main{
  def main(args: Array[String]): Unit = {
    val receiver: Behavior[Command] = ModaCookActor()
    val system: ActorSystem[Command] = ActorSystem(receiver, "main-actor")
    val messageContent = "我爱北京天安门，天安门上太阳升。"
    system ! AkkaActorEvnevt("test-event", Message(messageContent, "slkdjalkfjalsjf"))

    Thread.sleep(1000l)

    system ! GracefulShutdown
  }
}






































