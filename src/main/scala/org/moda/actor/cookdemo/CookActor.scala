package org.moda.actor.cookdemo

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors


object CookActor {

  def apply(): Behavior[String] = {
    Behaviors.setup { context =>
      Behaviors.receiveMessage { message =>
        printf("hello " + message)
        // 也就是说 下次 在做这个事情的时候 就直接有behavior对象了，不用再去创建，节省内存开销
        Behaviors.same
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val receiver = CookActor()
    val system: ActorSystem[String] =
      ActorSystem(receiver, "hello")
    system ! "yangxuan"
  }
}
