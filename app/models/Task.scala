package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Task(id: Long, label: String, endDate: String)

object Task {
  val task = {
    get[Long]("id") ~
    get[String]("label") ~
    get[String]("endDate") map {
      case id~label~endDate => Task(id, label, endDate)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  def create(label: String, endDate: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label,endDate) values ({label},{endDate})").on(
        'label -> label,
        'endDate -> endDate
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}