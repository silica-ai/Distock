import org.jsoup.Jsoup

data class Message
    (val testString : String)

fun getTickerPrice(ticker : String) : Message{
    // make a request to yahoo finance to get price
    val doc = Jsoup.connect("https://finance.yahoo.com/quote/$ticker?p=$ticker").timeout(10000).get()
    val tickerPrice = doc.getElementsByClass("Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)").text()
    if(ticker.isBlank()) {
        return Message("The ticker: $ticker does not exist.")
    }
    return Message("Ticker price of $ticker is $tickerPrice")
}