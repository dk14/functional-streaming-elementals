
  data Pet = Puppy | Pussy | NinjaTurtle
  
  beCute Puppy cuteness = cuteness < 4
  beCute Pussy cuteness = cuteness < 100500
  beCute _     _        = False
