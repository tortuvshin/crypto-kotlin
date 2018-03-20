package cloud.techstar.cryptokotlin

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import java.util.logging.Logger

class SplashActivity : AppCompatActivity() {

    private var mHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler = Handler(Looper.getMainLooper())

        mHandler!!.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 500)
    }
}
