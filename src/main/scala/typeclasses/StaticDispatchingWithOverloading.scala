// static dispatching with overloading

object DynamicDispatchingOverloading extends App {

  sealed trait Pet
  sealed trait SmallPet extends Pet
  case object Puppy extends SmallPet
  case object Pussy extends SmallPet

  object CuteOps {
  	def beCute(a: Puppy.type, cutenessLevel: Int) = cutenessLevel < 4
  	def beCute(a: Pussy.type, cutenessLevel: Int) = cutenessLevel < 100500
  }

}
