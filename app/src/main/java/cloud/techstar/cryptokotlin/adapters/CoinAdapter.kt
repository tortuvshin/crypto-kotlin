package cloud.techstar.cryptokotlin.adapters

import android.accounts.AccountAuthenticatorActivity
import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import cloud.techstar.cryptokotlin.R
import cloud.techstar.cryptokotlin.`interface`.ILoadMore
import cloud.techstar.cryptokotlin.models.Coins
import cloud.techstar.cryptokotlin.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin.view.*

class CoinViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    var voinIcon = itemView.coin_icon
    var coinSymbol = itemView.coin_symbol
    var coinName = itemView.coin_name
    var coinPrice = itemView.price_usd
    var coinOneHourChange = itemView.price_hour
    var coinTwentyHourChange = itemView.price_24hour
    var sevenDayHange = itemView.price_7day
}

class CoinAdapter(recyclerView: RecyclerView, internal var activity: Activity, var items:List<Coins>): RecyclerView.Adapter<CoinViewHolder>() {

    internal var loadMore:ILoadMore?=null
    var isLoading:Boolean?=null
    var visibleThreshold=5
    var lastVisibleItem:Int=0
    var totalItemCount:Int=0

    init {
        val linearLayout = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayout.itemCount
                lastVisibleItem = linearLayout.findLastVisibleItemPosition()
                if (isLoading!! && totalItemCount <= lastVisibleItem+visibleThreshold){
                    if (loadMore != null)
                        loadMore!!.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    fun setLoadMore(loadMore: ILoadMore){
        this.loadMore = loadMore
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_coin, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder?, position: Int) {
        val coinModel = items.get(position)
        val item = holder as CoinViewHolder

        item.coinName.text = coinModel.name
        item.coinSymbol.text = coinModel.symbol
        item.coinPrice.text = coinModel.price_usd
        item.coinOneHourChange.text = coinModel.percent_change_1h
        item.coinTwentyHourChange.text = coinModel.percent_change_24h
        item.sevenDayHange.text = coinModel.percent_change_7d

        Picasso.with(activity.baseContext)
                .load(StringBuilder(Utils.imageUrl)
                .append(coinModel.symbol!!.toLowerCase())
                .append(".png")
                .toString())

        item.coinOneHourChange.setTextColor(if (coinModel.percent_change_1h!!.contains("-"))
            Color.parseColor("#FF0000")
        else
            Color.parseColor("#32CD32")
        )
        item.coinTwentyHourChange.setTextColor(if (coinModel.percent_change_24h!!.contains("-"))
            Color.parseColor("#FF0000")
        else
            Color.parseColor("#32CD32")
        )
        item.sevenDayHange.setTextColor(if (coinModel.percent_change_7d!!.contains("-"))
            Color.parseColor("#FF0000")
        else
            Color.parseColor("#32CD32")
        )
    }

    fun setLoaded(){
        isLoading = false
    }

    fun updateData(coinModels: List<Coins>){
        this.items = coinModels
        notifyDataSetChanged()
    }
}
