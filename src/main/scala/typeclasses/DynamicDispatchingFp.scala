//dynamic dispatching with pattern matching (FP)

import DynamicDispatchingOop._

object DynamicDispatchingFp extends App {

  sealed trait Pet
  case object Puppy extends Pet
  case object Pussy extends Pet

  def beCute(a: Pet, cutenessLevel: Int) = a match {
    case _: Puppy => cutenessLevel < 4
    case _: Pussy => cutenessLevel < 100500
    case _ => false
  }

}
 