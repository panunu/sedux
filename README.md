<img src="https://raw.githubusercontent.com/panunu/sedux/master/cover.png" style="float: right"/>

# Sedux

A naive implementation of Redux (JavaScript library) in Scala.

Just for fun. WIP.

## Usage

Example in ScalaJS.

```scala
import com.panuleppaniemi.sedux.Store
import com.panuleppaniemi.sedux.Store._

case class Sell(amount: Int) extends Action
case class Train() extends Action

def resource: Reducer = (state: Option[Any], action: Action) => {
  val resource = state.getOrElse(0).asInstanceOf[Int]

  action match {
    case t: Train => resource + 1
    case s: Sell => resource - s.amount
    case _ => resource
  }
}

def bank: Reducer = (state: Option[Any], action: Action) => {
  val bank = state.getOrElse(0.0).asInstanceOf[Double]

  action match {
    case s: Sell => bank + (s.amount / 10.0)
    case _ => bank
  }
}

val store = new Store(Map("resource" -> resource, "bank" -> bank))
store.subscribe((state: Map[String, Any]) => {
  $("#resources").html(state("resource").toString)
  $("#bank").html(state("bank").toString)
})

val $ = jQuery
$("#transfer-competence").click((e: Event) => store.dispatch(Train()))
$("#sell").click((e: Event) => store.dispatch(Sell(store.getState("resource").asInstanceOf[Int])))
```
