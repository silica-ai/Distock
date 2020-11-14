import com.jessecorbett.diskord.api.model.Embed
import com.jessecorbett.diskord.dsl.CombinedMessageEmbed
import com.jessecorbett.diskord.dsl.footer
import com.jessecorbett.diskord.dsl.message
import com.jessecorbett.diskord.util.Colors
import org.jsoup.Jsoup
import twitter4j.Query
import twitter4j.Status
import yahoofinance.Stock
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.lang.StringBuilder
import kotlin.streams.toList


data class Message(val text : String, val embed : CombinedMessageEmbed? = null)


fun getTickerPrice(ticker : String) : Message {
    // make a request to yahoo finance to get price
    val tickerStock = YahooFinance.get(ticker) ?: return Message("Stock $ticker not available")
    val price = tickerStock.getQuote().price ?: return Message("Stock price for $ticker not available")
    var desc = StringBuilder("")
    val quote = tickerStock.getQuote(true)

    desc.append( "${tickerStock.stockExchange}\n\n")

    desc.append("Prev Close:    ${quote.previousClose}")
    desc.append("   Open: ${quote.open}\n")
    desc.append("Day Low/High:  ${quote.dayLow}/${quote.dayHigh}\n")
    desc.append("Volume:        %,d\n".format(quote.volume))
    desc.append("Avg Volume:    %,d\n".format(quote.avgVolume))


    val change = tickerStock.getQuote().getChangeInPercent().toDouble()
    val changeSign = if (change > 0) "+" else ""

    val embedding = CombinedMessageEmbed().apply {
            title = "${tickerStock.name} (${tickerStock.symbol}) is at $price (%$changeSign$change)"
            text = "Ticker price of $ticker is $price (%$change)"
            description = desc.toString()
            color = if (change < 0) Colors.RED else Colors.GREEN
        }

    return Message("", embedding)

}

fun getRecentNews(ticker : String) : Message {

    val doc = Jsoup.connect("https://finance.yahoo.com/quote/$ticker/news?p=$ticker")
        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
        .maxBodySize(0)
        .timeout(50000)
        .get()

    val recentNews = doc.getElementsByClass("Mb(5px)")
    var reply = "IMPORTANT $ticker NEWS: \n"
    var counter = 0

    for (article in recentNews){
        if(counter == 5)
            break
        if(counter != 0){
            val link = "https://finance.yahoo.com" + article.getElementsByTag("a").attr("href")
            reply += link + "\n\n"
        }
        counter += 1
    }

    return Message(reply)
}

fun getStockTweets(ticker: String, tf: TwitterTalk) : List<Message> {
    var twitter = tf.getInstance()
    val query = Query(ticker)
    val result = twitter.search(query)

    var tweets = result.tweets.stream()
        .map {t -> getTwitterUrl(t) }
        .map {Message(it)}
        .limit(7)
        .toList()

    return tweets

}

fun getTwitterUrl(tweet : Status) : String {
    val url = "https://twitter.com/${tweet.user.screenName}/status/${tweet.getId()}"
    return url
}