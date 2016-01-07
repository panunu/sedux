package com.panuleppaniemi.sedux

class Store(private val reducers: Map[String, Store.Reducer]) {
  private var subscribers: List[(Map[String, Any]) => Unit] = List()
  private var state: Map[String, Any] = Map()

  def getState = state
  def subscribe(f: (Map[String, Any]) => Unit) = subscribers = f :: subscribers
  def dispatch(action: Store.Action): Unit = {
    reducers.foreach((f: (String, Store.Reducer)) => state = state + (f._1 -> f._2(state.get(f._1), action)))
    subscribers.foreach(_(state))
  }
}

object Store {
  type Reducer = (Option[Any], Action) => Any
  trait Action
}

