package ma.ensa.notifications.classes
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.util.Log

class Medicament(
    var nom: String,
    var dosage: String,
    var frequence: String,
    var heurePris: String
) {


    // Attribution d'un ID unique
    var id: Int = ++comp
    companion object {
        private var comp: Int = 0
    }
}