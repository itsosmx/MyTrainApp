package mytrain.bluestars.me.data

import java.io.Serializable

data class FirebaseCitySpinnerData(
    var id: String = "",
    var name: String = ""
): Serializable {
    override fun toString(): String {
        return name
    }
    fun applyDefaultCities(): ArrayList<FirebaseCitySpinnerData> {
        val array = java.util.ArrayList<FirebaseCitySpinnerData>()
        array.apply {
            add(FirebaseCitySpinnerData( "بنى سويف","benisuef"))
            add(FirebaseCitySpinnerData( "القاهره","cairo"))
            add(FirebaseCitySpinnerData( "الجيزه","giza"))
            add(FirebaseCitySpinnerData( "اسيوط","asyut"))
            add(FirebaseCitySpinnerData( "الأسكندرية","alexandria"))
            add(FirebaseCitySpinnerData( "الإسماعيلية","ismailia"))
            add(FirebaseCitySpinnerData( "الفيوم","fayoum"))
            add(FirebaseCitySpinnerData( "الأقصر","luxor"))
            add(FirebaseCitySpinnerData( "الزقازيق","zagazig"))
            add(FirebaseCitySpinnerData( "السويس","suez"))
            add(FirebaseCitySpinnerData( "أسوان","aswan"))
            add(FirebaseCitySpinnerData( "المحله الكبرى","mahalla_al_kubra"))
            add(FirebaseCitySpinnerData( "المنصورة","mansoura"))
            add(FirebaseCitySpinnerData( "المنيا","minya"))
            add(FirebaseCitySpinnerData( "بنها","banha"))
            add(FirebaseCitySpinnerData( "بورسعيد","portsaid"))
            add(FirebaseCitySpinnerData( "دمنهور","damanhour"))
            add(FirebaseCitySpinnerData( "دمياط","domiette"))
            add(FirebaseCitySpinnerData( "سوهاج","sohage"))
            add(FirebaseCitySpinnerData( "سيدى جابر","sidigaber"))
            add(FirebaseCitySpinnerData( "شبرا الخيمه","shubraelkheima"))
            add(FirebaseCitySpinnerData( "طنطا","tanta"))
            add(FirebaseCitySpinnerData( "قنا","qena"))
            add(FirebaseCitySpinnerData( "كفر الشيخ","kafralsheikh"))
            add(FirebaseCitySpinnerData( "مرسى مطروح","marsamatrouh"))
            add(FirebaseCitySpinnerData( "ايتاى البارود","itaielbaroud"))
        }
        return array
    }
}