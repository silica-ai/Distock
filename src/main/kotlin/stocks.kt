import org.jsoup.Jsoup
import yahoofinance.Stock
import yahoofinance.YahooFinance

data class Message(val testString : String)

fun getTickerPrice(ticker : String) : Message{
    // make a request to yahoo finance to get price
    val tickerStock = YahooFinance.get(ticker) ?: return Message("Stock $ticker not available")
    val price = tickerStock.getQuote().price ?: return Message("Stock price for $ticker not available")

    return Message("Ticker price of $ticker is $price")

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