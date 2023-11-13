package models

import java.time.Instant

import play.api.libs.json._
import slick.jdbc.PostgresProfile.api._

case class Person(
  id: Int,
  sex: Option[String],
  birthDate: Option[Instant],
  deathDate: Option[Instant],
  deathDateEstimatedIndicator: Option[Boolean],
  deathIndicator: Option[Boolean]
)

implicit val personWrites: OWrites[Person] = Json.writes[Person]

class PersonTable(tag: Tag) extends Table[Person](tag, "person") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def sex = column[Option[String]]("sex")
  def birthDate = column[Option[Instant]]("birth_date")
  def deathDate = column[Option[Instant]]("death_date")
  def deathDateEstimatedIndicator = column[Option[Boolean]]("death_date_estimated_indicator")
  def deathIndicator = column[Option[Boolean]]("death_indicator")
  def * = (id, sex, birthDate, deathDate, deathDateEstimatedIndicator, deathIndicator).mapTo[Person]
}

val persons = TableQuery[PersonTable]
