package cloud.techstar.cryptokotlin.models

/**
 * Author: Turtuvshin Byambaa.
 * Project: Safety Inst
 * URL: https://www.github.com/tortuvshin
 */
class Coins {
    var id:String?=null
    var name:String?=null
    var symbol:String?=null
    var price_usd:String?=null
    var percent_change_1h:String?=null
    var percent_change_24h:String?=null
    var percent_change_7d:String?=null

    constructor()
    constructor(id: String, name: String, symbol: String, price_usd: String, percent_change_1h: String, percent_change_24h: String, percent_change_7d: String) {
        this.id = id
        this.name = name
        this.symbol = symbol
        this.price_usd = price_usd
        this.percent_change_1h = percent_change_1h
        this.percent_change_24h = percent_change_24h
        this.percent_change_7d = percent_change_7d
    }


}