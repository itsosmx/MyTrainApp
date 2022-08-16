package mytrain.bluestars.me.data

import java.io.Serializable

data class CitySpinnerData(
    var id: String = "",
    var name: String = ""
): Serializable {
    override fun toString(): String {
        return name
    }
    fun applyDefaultCities(): ArrayList<CitySpinnerData> {
        val array = java.util.ArrayList<CitySpinnerData>()
        array.apply {
            add(CitySpinnerData("benisuef", "بنى سويف"))
            add(CitySpinnerData("cairo", "القاهره"))
            add(CitySpinnerData("giza", "الجيزه"))
            add(CitySpinnerData("asyut", "اسيوط"))
            add(CitySpinnerData("alexandria", "الأسكندرية"))
            add(CitySpinnerData("ismailia", "الإسماعيلية"))
            add(CitySpinnerData("fayoum", "الفيوم"))
            add(CitySpinnerData("luxor", "الأقصر"))
            add(CitySpinnerData("zagazig", "الزقازيق"))
            add(CitySpinnerData("suez", "السويس"))
            add(CitySpinnerData("aswan", "أسوان"))
            add(CitySpinnerData("mahalla_al_kubra", "المحله الكبرى"))
            add(CitySpinnerData("mansoura", "المنصورة"))
            add(CitySpinnerData("minya", "المنيا"))
            add(CitySpinnerData("banha", "بنها"))
            add(CitySpinnerData("portsaid", "بورسعيد"))
            add(CitySpinnerData("damanhour", "دمنهور"))
            add(CitySpinnerData("domiette", "دمياط"))
            add(CitySpinnerData("sohage", "سوهاج"))
            add(CitySpinnerData("sidigaber", "سيدى جابر"))
            add(CitySpinnerData("shubraelkheima", "شبرا الخيمه"))
            add(CitySpinnerData("tanta", "طنطا"))
            add(CitySpinnerData("qena", "قنا"))
            add(CitySpinnerData("kafralsheikh", "كفر الشيخ"))
            add(CitySpinnerData("marsamatrouh", "مرسى مطروح"))
            add(CitySpinnerData("itaielbaroud", "ايتاى البارود"))
        }
        return array
    }
}