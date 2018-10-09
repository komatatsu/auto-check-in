package app.komatatsu.autocheckin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import com.google.zxing.integration.android.IntentIntegrator

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val PARAM_TICKET = "paramTicket"
        @JvmStatic
        fun getNewIntent(context: Context, ticket: Ticket): Intent {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra(PARAM_TICKET, ticket)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        // get data
        val ticket = intent.getParcelableExtra<Ticket>(PARAM_TICKET)
        if (ticket == null) {
            showError()
            return
        }

        // check
        val ticketInRoster = DatabaseHelper(this).search(ticket.hash)
        if (ticketInRoster == null) {
            showError()
        } else {
            showSuccess(ticketInRoster)
        }

        // initialize
        scan.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }
    }


    private fun showSuccess(ticket: Ticket) {
        number.visibility = View.VISIBLE
        footer.visibility = View.VISIBLE
        header.text = ticket.name + getString(R.string.label_result_header)
        number.text = ticket.number.toString()
    }

    private fun showError() {
        number.visibility = View.GONE
        footer.visibility = View.GONE
        header.text = getString(R.string.ticket_not_found)
    }
}
