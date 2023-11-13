package datatable

import play.api.libs.json._

trait JS {
  def toJS: String
}

given Conversion[String, JS] with {
  def apply(x: String): JS = new {
    def toJS: String = s"\"$x\""
  }
}

given Conversion[Option[JS | String], JS] with {
  def apply(x: Option[JS | String]): JS = new {
    def toJS: String = x match
      case Some(s: JS) => s.toJS
      case Some(s: String) => s.toJS
      case None => "null"
  }
}

extension (l: List[JS]) {
  def toJS: String = "["  ++ l.map(_.toJS).mkString(", ") ++ "]"
}

extension (m: Map[JS, JS]) {
  def toJS: String = "{" ++ m.map((k, v) => s"${k.toJS}: ${v.toJS}").mkString(", ") ++ "}"
}

abstract class Renderer extends JS

case class DateRenderer (
  fmt: String = "YYYY-MM-DD"
) extends Renderer {
  def toJS: String = s"DataTable.render.datetime(\"${fmt}\")"
}

case class EllipsisRenderer (
  n: Int,
  middle: Boolean = false,
  escape: Boolean = false
) extends Renderer {
  def toJS = s"DataTable.render.ellipsis($n, $middle, $escape)"
}

case class Column (
  data: String,
  title: Option[String] = None,
  render: Option[Renderer] = None
) extends JS {
  def toJSMap: Map[JS,JS] = Map[JS, JS](
    ("data", data),
    ("title", title),
    ("render", render)
  )
  def toJS: String = toJSMap.toJS
}

object Column {
  def apply(data: String, title: String): Column = this(data, Some(title))
  def apply(data: String, title: String, render: Renderer): Column = this(data, Some(title), Some(render))
}
