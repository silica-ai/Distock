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
            command("stock") {
                val tickerName = this.words[this.words.size - 1]
                val res = getTickerPrice(tickerName)
                if(res.embed == null) {
                    reply(res.text)
                } else{
                    reply(res.text, res.embed?.embed())
                }
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
            command("graph") {
                reply("Testing1")
                getGraph()
                reply("Testing2")
            }
            command("insult"){
                val insults = arrayOf("lata bic", "gottem", "nani noo", "wtff dad")

                reply(insults[Random.nextInt(0, insults.size)])
            }
        }
    }
}