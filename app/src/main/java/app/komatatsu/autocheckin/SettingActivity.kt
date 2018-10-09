package app.komatatsu.autocheckin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.InputStreamReader

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // 読み込むファイルを指定
        val fileName = "sample_data.json"
        file_name.text = fileName

        load.setOnClickListener {
            loadData(fileName)
        }
    }

    private fun loadData(fileName: String) {
        // ファイルの読み込み
        val stream = resources.assets.open(fileName)
        val roster = Gson().fromJson(InputStreamReader(stream), Roster::class.java)
        // SQLiteへの保存
        DatabaseHelper(this).saveRoster(roster)
        // 確認
        val count = DatabaseHelper(this).countTicket();
        Log.d("sqlite", "ticket table count: $count")
    }
}
