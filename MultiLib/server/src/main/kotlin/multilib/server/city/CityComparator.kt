package multilib.server.city


class CityComparator : Comparator<City?> {
    override fun compare(o1: City?, o2: City?): Int {
        if(o1!!.getMetersAboveSeaLevel() === o2!!.getMetersAboveSeaLevel()){
            return 0
        }
        return if(o1!!.getMetersAboveSeaLevel()!! > o2!!.getMetersAboveSeaLevel()!!){
            1
        }else{
            -1
        }
    }
}