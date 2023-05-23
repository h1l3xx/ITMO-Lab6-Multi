package multilib.app.city

import java.time.ZonedDateTime

class Human {
    private var age: Int? = null
    private var birthday: ZonedDateTime? = null

    constructor()
    constructor(age: String, birthday: String){
        this.age = age.toInt()
        this.birthday = ZonedDateTime.parse(birthday)
    }


    fun getAge():
            Int? = age

    fun setAge(age: Int){
        this.age = age
    }

    fun getBirthday(): ZonedDateTime?{
        return this.birthday}

    fun setBirthday(birthday: ZonedDateTime){
        this.birthday = birthday
    }

    override fun toString(): String ="Возраст: $age, День рождения: $birthday"
}
