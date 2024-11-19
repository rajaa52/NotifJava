package ma.ensa.notifications.classes


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ma.ensa.notifications.MedicationListActivity
import ma.ensa.notifications.R

class MedicationReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val medicamentName = intent.getStringExtra("medicament_name") ?: "Médicament"
        val notificationType = intent.getStringExtra("notification_type") ?: "default"

        showNotification(context, medicamentName, notificationType)
    }

    private fun showNotification(context: Context, medicamentName: String, type: String) {
        // instance de NotificationManager pour gérer les notifications
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "medication_channel"

        // on Crée un canal de notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Rappels de Médicament",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        //on accède à l'application après l'appuie sur la notification
        val intent = Intent(context, MedicationListActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )


        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.soleil)
            .setContentTitle("Notification de Médicament")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        when (type) {
            "reminder" -> {
                notificationBuilder.setContentText("Il est temps de prendre votre médicament: $medicamentName")
            }
            "delete" -> {
                notificationBuilder.setContentText("Le médicament $medicamentName a été supprimé.")
            }
            "add" -> {
                notificationBuilder.setContentText("Le médicament $medicamentName a été ajouté avec succès.")
            }
            else -> {
                notificationBuilder.setContentText("Notification inconnue.")
            }
        }
        //on affiche l'application
        notificationManager.notify(medicamentName.hashCode(), notificationBuilder.build())
    }
}