package cloud.techstar.cryptokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import cloud.techstar.cryptokotlin.`interface`.ILoadMore
import cloud.techstar.cryptokotlin.adapters.CoinAdapter
import cloud.techstar.cryptokotlin.models.Coins
import cloud.techstar.cryptokotlin.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), ILoadMore {

    internal var items:MutableList<Coins> = ArrayList()
    internal lateinit var adapter: CoinAdapter
    internal lateinit var client: OkHttpClient
    internal lateinit var request: Request

    override fun onLoadMore() {
        if (items.size <= Utils.MAX_COIN_LOAD)
            loadNext10Coin(items.size)
        else
            Toast.makeText(this@MainActivity, "Data max is"+Utils.MAX_COIN_LOAD, Toast.LENGTH_LONG).show()
    }

    private fun loadFist10Coin(){
        client = OkHttpClient()
        request = Request.Builder()
                .url(String.format("https://api.coinmarketcap.com/v1/ticker/?start=%d&limit=10"))
                .build()

        client.newCall(request)
                .enqueue(object :Callback{
                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.d("ERROR: ", e.toString())
                    }

                    override fun onResponse(call: Call?, response: Response) {
                        val body = response.body()!!.string()
                        val gson = Gson()
                        items = gson.fromJson(body, object:TypeToken<List<Coins>>(){}.type)
                        runOnUiThread{
                            adapter.updateData(items)
                        }
                    }
                })
    }

    private fun loadNext10Coin(index: Int){
        client = OkHttpClient()
        request = Request.Builder()
                .url(String.format("https://api.coinmarketcap.com/v1/ticker/?start=%d&limit=10", index))
                .build()

        swife_to_refresh.isRefreshing = true
        client.newCall(request)
                .enqueue(object :Callback{
                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.d("ERROR: ", e.toString())
                    }

                    override fun onResponse(call: Call?, response: Response) {
                        val body = response.body()!!.string()
                        val gson = Gson()
                        val newItems = gson.fromJson<List<Coins>>(body, object:TypeToken<List<Coins>>(){}.type)
                        runOnUiThread{
                            items.addAll(newItems)
                            adapter.setLoaded()
                            adapter.updateData(items)
                            swife_to_refresh.isRefreshing = false
                        }
                    }
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swife_to_refresh.post { loadFist10Coin() }

        swife_to_refresh.setOnRefreshListener {
            items.clear()
            loadFist10Coin()
            setUpAdapter()
        }
    }

    private fun setUpAdapter(){
        adapter = CoinAdapter(coin_recycler_view, this@MainActivity, items)
        coin_recycler_view.adapter = adapter
        adapter.setLoadMore(this)
    }
}
