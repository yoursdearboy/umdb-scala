package controllers

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

import play.api._
import play.api.mvc._
import play.api.db.slick._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import slick.jdbc._
import slick.jdbc.PostgresProfile.api._

import datatable._
import models._

private case class Data(
  person: Person,
  name: Option[Name]
)

implicit val dataWrites: OWrites[Data] = Json.writes[Data]

val dataColumns =
  Column("person.id", "Person ID") ::
  Column("person.birthDate", "Birth date", DateRenderer()) ::
  Column("name.family", Some("Family name")) ::
  Nil

@Singleton
class PersonController @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider,
  val controllerComponents: ControllerComponents
)(
  implicit executionContext: ExecutionContext
) extends BaseController
  with HasDatabaseConfigProvider[JdbcProfile]
{
  def index() = Action { implicit request: Request[AnyContent] =>
    val columns = toJS(dataColumns)
    Ok(views.html.person.index(columns))
  }

  def data() = Action.async { request =>
    val query = persons joinLeft primaryNames on (_.id === _.personId)
    val result= db.run(query.result)
    val data = result.map(rows => rows.map(Data.apply))
    val json = data.map(Json.toJson).map(Ok.apply)
    json
  }
}
