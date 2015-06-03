package consul.v1.kv

import org.apache.commons.codec.binary.Base64
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import sun.security.krb5.internal.EncAPRepPart

case class KvValue(CreateIndex: Int, ModifyIndex: Int, LockIndex: Int, Key: String, Flags: Int, Value: String, Session: Option[String])
object KvValue{

  val base64valueReads = StringReads.map{ case encodedValue =>
    new String(new Base64().decode(encodedValue))
  }

  implicit val reads = (
    (__ \ "CreateIndex").read[Int] and
    (__ \ "ModifyIndex").read[Int] and
    (__ \ "LockIndex"  ).read[Int] and
    (__ \ "Key"        ).read[String] and
    (__ \ "Flags"      ).read[Int] and
    (__ \ "Value"      ).read(base64valueReads) and
    (__ \ "Session"    ).readNullable[String]
  )(KvValue.apply _)
}