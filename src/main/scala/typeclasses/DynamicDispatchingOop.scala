//- dynamic dispatching with virtual methods (OOP)

object DynamicDispatchingOop extends App {

  class Pet {
    def beCute(cutenessLevel: Int): Boolean = false
  }

  class Puppy extends Pet {
    override def beCute(cutenessLevel: Int): Boolean = cutenessLevel < 4
  }

  class Pussy extends Pet {
    override def beCute(cutenessLevel: Int): Boolean = cutenessLevel < 100500
  }
}