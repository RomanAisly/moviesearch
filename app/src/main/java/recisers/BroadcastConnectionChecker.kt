package recisers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.moviesearch.R

class BroadcastConnectionChecker: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return
        when (intent.action) {
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(context,
                context?.getString(R.string.toast_battery_is_low),
                Toast.LENGTH_SHORT).show()

            Intent.ACTION_POWER_CONNECTED -> Toast.makeText(context,
                context?.getString(R.string.toast_charging_connected),
                Toast.LENGTH_SHORT).show()

            Intent.ACTION_POWER_DISCONNECTED -> Toast.makeText(context,
                context?.getString(R.string.toast_charging_disconnected),
                Toast.LENGTH_SHORT).show()
        }
    }
}