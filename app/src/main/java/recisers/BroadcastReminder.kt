package recisers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import data.entily.Film
import view.notifications.NotificationConstants
import view.notifications.NotificationHelper

class BroadcastReminder: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.getBundleExtra(NotificationConstants.FILM_BUNDLE_KEY)
        val film: Film = bundle?.get(NotificationConstants.FILM_KEY) as Film

        NotificationHelper.createNotify(context!!, film)
    }
}