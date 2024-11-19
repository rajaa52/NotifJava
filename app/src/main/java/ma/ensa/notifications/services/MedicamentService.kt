package ma.ensa.notifications.services

import ma.ensa.notifications.classes.Medicament
import ma.ensa.notifications.dao.IDao

class MedicamentService private constructor() : IDao<Medicament> {
    private val medicaments: MutableList<Medicament> = mutableListOf()

    companion object {
        private var instance: MedicamentService? = null
        fun getInstance(): MedicamentService {
            if (instance == null) {
                instance = MedicamentService()
            }
            return instance!!
        }
    }

    override fun create(o: Medicament): Boolean {
        return medicaments.add(o)
    }

    override fun update(o: Medicament): Boolean {
        for (m in medicaments) {
            if (m.id == o.id) {
                m.nom = o.nom
                m.dosage = o.dosage
                m.frequence = o.frequence
                m.heurePris = o.heurePris
                return true
            }
        }
        return false
    }

    override fun delete(o: Medicament): Boolean {
        return medicaments.remove(o)
    }

    override fun findById(id: Int): Medicament? {
        return medicaments.find { it.id == id }
    }

    override fun findAll(): List<Medicament> {
        return medicaments
    }
}