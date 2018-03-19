package cloud.techstar.cryptokotlin.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_coin.view.*

/**
 * Author: Turtuvshin Byambaa.
 * Project: Safety Inst
 * URL: https://www.github.com/tortuvshin
 */

class CoinViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    var voinIcon = itemView.coin_icon
    var coinSymbol = itemView.coin_symbol
    var coinName = itemView.coin_name
    var coinPrice = itemView.price_usd
    var coinOneHourChange = itemView.price_hour
    var coinTwentyHourChange = itemView.price_24hour
    var sevenDayHange = itemView.price_7day
}

class CoinAdapter {

}