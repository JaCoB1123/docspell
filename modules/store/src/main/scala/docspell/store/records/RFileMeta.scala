package docspell.store.records

import docspell.common._
import docspell.store.impl.Implicits._
import docspell.store.impl._
import docspell.store.syntax.MimeTypes._

import bitpeace.FileMeta
import bitpeace.Mimetype
import doobie._
import doobie.implicits._

object RFileMeta {

  val table = fr"filemeta"

  object Columns {
    val id        = Column("id")
    val timestamp = Column("timestamp")
    val mimetype  = Column("mimetype")
    val length    = Column("length")
    val checksum  = Column("checksum")
    val chunks    = Column("chunks")
    val chunksize = Column("chunksize")

    val all = List(id, timestamp, mimetype, length, checksum, chunks, chunksize)

  }

  def findById(fid: Ident): ConnectionIO[Option[FileMeta]] = {
    import bitpeace.sql._

    selectSimple(Columns.all, table, Columns.id.is(fid)).query[FileMeta].option
  }

  def findMime(fid: Ident): ConnectionIO[Option[MimeType]] = {
    import bitpeace.sql._

    selectSimple(Seq(Columns.mimetype), table, Columns.id.is(fid))
      .query[Mimetype]
      .option
      .map(_.map(_.toLocal))
  }
}
