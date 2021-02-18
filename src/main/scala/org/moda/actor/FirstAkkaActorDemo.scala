package org.moda.actor

import akka.actor.typed.scaladsl.{ActorContext, Behaviors, LoggerOps}
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

object HelloWorld {
  // 接受的数据结构
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  // 回复的数据结构
  final case class Greeted(whom: String, from: ActorRef[Greet])

  def apply(): Behavior[Greet] = Behaviors.receive { (context: ActorContext[Greet], message: Greet) =>
    // 打印 接受到的 消息
    println(s"Hello ${message.whom}!")
    // message.replyTo 获得 回复者是谁
    message.replyTo ! Greeted(message.whom, context.self)
    // 类似与actor的单例
    Behaviors.same
  }
}

object HelloWorldBot {

  def apply(max: Int): Behavior[HelloWorld.Greeted] = {
    bot(0, max)
  }

  private def bot(greetingCounter: Int, max: Int): Behavior[HelloWorld.Greeted] =
    Behaviors.receive { (context, message) =>
      val n = greetingCounter + 1
      println(s"Greeting $n for ${message.whom}")
      if (n == max) {
        Behaviors.stopped
      } else {
        message.from ! HelloWorld.Greet(message.whom, context.self)
        bot(n, max)
      }
    }
}

object FirstAkkaActorDemo {

  final case class SayHello(name: String)

  def apply(): Behavior[SayHello] =
    Behaviors.setup { context =>
      // 初始化一个greeter
      // greeter发出消息，HelloWorld()为接受者。
      val greeter = context.spawn(HelloWorld(), "greeter")

      Behaviors.receiveMessage { message =>
        val replyTo = context.spawn(HelloWorldBot(max = 3), message.name)
        greeter ! HelloWorld.Greet(message.name, replyTo)
        Behaviors.same
      }
    }

  def main(args: Array[String]): Unit = {
    val receiver = FirstAkkaActorDemo()
    val system: ActorSystem[FirstAkkaActorDemo.SayHello] = ActorSystem(receiver, "moda")

    system ! FirstAkkaActorDemo.SayHello("World")
//    system ! FirstAkkaActorDemo.SayHello("Akka")
  }
}

// This is run by ScalaFiddle
//FirstAkkaActorDemo.main(Array.empty)

/**
 * ActorSystem 和 context.spawn 都是定义发送到哪一个actor 以及 自己的名称 两个参数
 */

