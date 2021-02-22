package org.moda.actor.code

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object PrintMyActorRefActor {
  def apply(): Behavior[String] = Behaviors.setup(context => new PrintMyActorRefActor(context))
}

class PrintMyActorRefActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "print" =>
        // 创建第二个actor
        val actorRef2 = context.spawn(Behaviors.empty[String], "second-actor")
        println(s"second-actor: ${actorRef2}")
        this
    }
}

class Main(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "start" =>
        // 创建第一个actor
        val actorRef1 = context.spawn(PrintMyActorRefActor(), "first-actor")
        println(s"first-actor: $actorRef1")
        actorRef1 ! "print"
        this
    }
}

object Main {
  def apply(): Behavior[String] = Behaviors.setup(context => new Main(context))
}

object ActorHierarchyExperiments extends App {
  val testSystem = ActorSystem(Main(), "testSystem")
  testSystem ! "start"
}
