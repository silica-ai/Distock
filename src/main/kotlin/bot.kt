import com.jessecorbett.diskord.dsl.Command
import com.jessecorbett.diskord.dsl.bot
import com.jessecorbett.diskord.dsl.command
import com.jessecorbett.diskord.dsl.commands
import com.jessecorbett.diskord.util.words
import com.jessecorbett.diskord.dsl.*
import org.jsoup.Jsoup
import org.jsoup.helper.StringUtil
import kotlin.random.Random


suspend fun main() {
    val tf = TwitterTalk()
    bot(Config.BOT_TOKEN) {
        commands {
            command("price") {
                val tickerName = this.words[this.words.size - 1]
                val res = getTickerPrice(tickerName)
                reply(res.text)
            }
            command("news") {
                val tickerName = this.words[this.words.size - 1]
                val newsLinks = getRecentNews(tickerName)
                reply(newsLinks.text)
            }
            command("tweets") {
                val tweets = getStockTweets(this.words[this.words.size - 1], tf).map { m -> m.text }.toList()
                val response = StringUtil.join(tweets, "\n")
                reply(response)
            }
            command("insult"){
                val insults = arrayOf("you gee", "lata bic", "gottem", "nani noo", "wtff dad")

                reply(insults[Random.nextInt(0, insults.size)])
            }
        }
    }
}