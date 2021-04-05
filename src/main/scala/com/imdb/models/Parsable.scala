package com.imdb.models

trait Parsable[T] {
  def apply(a: List[String]): T
}