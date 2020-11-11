import org.jsoup.Jsoup
import yahoofinance.Stock
import yahoofinance.YahooFinance

data class Message(val testString : String)

fun getTickerPrice(ticker : String) : Message{
    // make a request to yahoo finance to get price
    val tickerStock = YahooFinance.get(ticker) ?: return Message("Stock $ticker not available")
    val price = tickerStock.getQuote().price

    return Message("Ticker price of $ticker is $price")

}