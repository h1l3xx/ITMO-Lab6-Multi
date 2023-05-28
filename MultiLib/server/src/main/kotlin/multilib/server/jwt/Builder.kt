package multilib.server.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Builder {
    private val key =
                "Она_сидела_у_окна,"+
                "А_он_вошёл_в_её_вагон"+
                "-Женат,-_подумала_она,"+
                "-Лет_тридцать_пять-_подумал_он." +
                "А_за_окном_цвела_весна," +
                "День_был_прекрасен,_словно_сон" +
                "-Красив,-_подумала_она."+
                "-Как_хороша,-_подумал_он."+
                "Но_жизнь_событьями_бедна,"+
                "Он_встал_и_вышел_на_перрон."+
                "-Как_жаль!-Подумала_она."+
                "-Как_жаль!-Успел_подумать_он."+
                "А_дома,_сжав_бокал_вина,"+
                "Включив_любимый_Вальс-Бостон,"+
                "-Одна.-Подумала_она."+
                "-Один.-Вдали_подумал_он."


    infix fun getToken(body: Body): String {
        val jwt = Jwts
            .builder()
            .setSubject(body.id.toString())
            .setExpiration(body.expDate)
            .signWith(Keys.hmacShaKeyFor(key.toByteArray()))
            .serializeToJsonWith { map ->
                Json.encodeToString(map.map { it.key!! to it.value.toString() }.toMap()).toByteArray()
            }

        body.data.forEach {
            jwt.claim(it.key, it.value)
        }

        return jwt.compact()
    }

    infix fun verify(token: String): Body {
        val parsed = Jwts
            .parserBuilder()
            .setSigningKey(key.toByteArray())
            .deserializeJsonWith { array ->
                Json.decodeFromString<Map<String, String>>(String(array))
            }
            .build()
            .parseClaimsJws(token)

        val subject = parsed.body.subject.toInt()
        val data = parsed.body.map {
            it.key to it.value.toString()
        }.toMap()

        return Body.setBody(subject, data, parsed.body.expiration)
    }
}
