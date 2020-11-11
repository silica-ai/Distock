import org.jsoup.Jsoup

data class Message
    (val testString : String)

fun getTickerPrice(ticker : String) : Message{
    val doc = Jsoup.connect("https://finance.yahoo.com/quote/$ticker?p=$ticker").get()
    print(doc)

    return Message(ticker)
}