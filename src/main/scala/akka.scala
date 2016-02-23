import akka.stream._
import scaladsl._
import akka.actor._

object AkkaStreams extends App {

  implicit val as = ActorSystem()
  implicit val ec = as.dispatcher
  val settings = ActorMaterializerSettings(as)
  implicit val mat = ActorMaterializer(settings)

  class SinkActor extends Actor {
    def receive = {
      case x => println(x)
    }
  }

  /** 

  //--------------------NotWorking flow graph-------------------

  val in = Source(1 to 2)
  val out = Sink.actorRef(as.actorOf(Props[SinkActor]), "end")


  val filter1 = Flow[Int].filter(_ == 1)
  val filter2 = Flow[Int].filter(_ == 2)
  val f1,f3,f4,f5 = Flow[Int].map(_ + 0)
  val f2 = Flow[Int].map{x => Thread.sleep(1000); x}.map(_ + 0)

  val send1 = Flow[Int].map("send1: " + _).to(Sink.actorRef(as.actorOf(Props[SinkActor]), "end"))
  val send2 = Flow[Int].map("send2: " + _).to(Sink.actorRef(as.actorOf(Props[SinkActor]), "end"))

  //val send1 = Flow[Int].map{x => println("send1: " + x); x}
  //val send2 = Flow[Int].map{x => println("send2: " + x); x}
   
  val g = RunnableGraph.fromGraph(FlowGraph.create() { implicit builder: FlowGraph.Builder[Unit] =>
    import FlowGraph.Implicits._

    val bcast = builder.add(Broadcast[Int](2))
    val merge = builder.add(Merge[Int](2))
   
    in ~> f1 ~> bcast ~> filter1 ~> f2 ~> send1 ~> f3 ~> send2 ~> merge ~> f3 ~> out
                bcast ~> filter2 ~> f4 ~> send1 ~> f5 ~> send2 ~> merge
    ClosedShape
  })

  */

  //-----------Working with correct concept, without normal graph---------
  import scala.collection._

  case class Exchange(predicate: Boolean = false, left: Int = -1, right: Int = -1, toSend: Map[String, Int] = Map.empty)

  class MegaActor extends Actor {
    def receive = {
    	case Exchange(_, _, _, toSend) => println(toSend)
      case x => println(x)
    }
  }


  val in = Source(1 to 2).map(x => if(x == 1) Exchange(true, left = x) else Exchange(false, right = x))

  val f1 = Flow[Exchange].map(x => x)
  val f3 = Flow[Exchange].map(x => if(x.predicate) x.copy(left = x.left + 0) else x)
  val f2 = Flow[Exchange].map{x => Thread.sleep(1000); x}.map(x =>  if(x.predicate) x.copy(left = x.left + 0) else x)
  val f4, f5 = Flow[Exchange].map(x =>  if(!x.predicate) x.copy(right = x.right + 0) else x)

  val send1 = Flow[Exchange].map(x => if(x.predicate) x.copy(toSend = x.toSend ++ Map("send1" -> x.left)) else x.copy(toSend = x.toSend ++ Map("send1" -> x.right))) 
  val send2 = Flow[Exchange].map(x => if(x.predicate) x.copy(toSend = x.toSend ++ Map("send2" -> x.left)) else x.copy(toSend = x.toSend ++ Map("send2" -> x.right))) 

  val out = Sink.actorRef(as.actorOf(Props[MegaActor]), "end")

  val g = RunnableGraph.fromGraph(FlowGraph.create() { implicit builder: FlowGraph.Builder[Unit] =>
    import FlowGraph.Implicits._
   
    in ~> f1 ~> f2 ~> send1 ~> f3 ~> send2 ~> f4 ~> send1 ~> f5 ~> send2 ~> out
    ClosedShape
  })

}

