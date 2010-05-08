/**
 * Copyright (C) 2009-2010 Scalable Solutions AB <http://scalablesolutions.se>
 */

package sample.remote

import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.actor.RemoteActor
import se.scalablesolutions.akka.remote.RemoteNode
import se.scalablesolutions.akka.util.Logging

class RemoteHelloWorldActor extends RemoteActor("localhost", 9999) {
  start
  def receive = {
    case "Hello" => 
      log.info("Received 'Hello'")
      reply("World")
  }
}

object ClientManagedRemoteActorServer extends Logging {

  def run = {
    RemoteNode.start("localhost", 9999)
    log.info("Remote node started")
  }

  def main(args: Array[String]) = run
}

object ClientManagedRemoteActorClient extends Logging {
  
  def run = {
    val actor = actorOf[RemoteHelloWorldActor]
    log.info("Remote actor created, moved to the server")
    log.info("Sending 'Hello' to remote actor")
    val result = actor !! "Hello"
    log.info("Result from Remote Actor: '%s'", result.get)
  }

  def main(args: Array[String]) = run
}

