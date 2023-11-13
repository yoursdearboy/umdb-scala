package models

import play.api.libs.json._
import slick.jdbc.PostgresProfile.api._

case class Name(
  id: Int,
  use: Option[String],
  family: Option[String],
  `given`: Option[String],
  middle: Option[String],
  patronymic: Option[String],
  prefix: Option[String],
  suffix: Option[String]
)

implicit val nameWrites: OWrites[Name] = Json.writes[Name]

class NameTable(tag: Tag) extends Table[Name](tag, "name") {
  def id         = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def personId   = column[Int]("person_id")
  def person = foreignKey("name_person_id_fkey", personId, persons)(
    _.id,
    onUpdate = ForeignKeyAction.Cascade,
    onDelete = ForeignKeyAction.Cascade)
  def use        = column[Option[String]]("use")
  def family     = column[Option[String]]("family")
  def `given`    = column[Option[String]]("given")
  def middle     = column[Option[String]]("middle")
  def patronymic = column[Option[String]]("patronymic")
  def prefix     = column[Option[String]]("prefix")
  def suffix     = column[Option[String]]("suffix")
  def * = (id, use, family, `given`, middle, patronymic, prefix, suffix).mapTo[Name]
}

val names = TableQuery[NameTable]

val nameNumber = SimpleLiteral[Int]("ROW_NUMBER() OVER(PARTITION BY person_id ORDER BY id DESC)")
var namesNumbered = names.map(n => (n, nameNumber)).subquery
val primaryNames = namesNumbered.filter(_._2 === 1).map(_._1)
