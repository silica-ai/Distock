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
                if(this.words.size == 1)
                    reply("Wrong format: use .help")

                val tickerName = this.words[this.words.size - 1]
                val res = getTickerPrice(tickerName)
                if(res.embed == null) {
                    reply(res.text)
                } else{
                    reply(res.text, res.embed?.embed())
                }
            }
            command("news") {
                if(this.words.size == 1)
                    reply("Wrong format: use .help")
                else {
                    val tickerName = this.words[this.words.size - 1]
                    val newsLinks = getRecentNews(tickerName)
                    reply(newsLinks.text)
                }
            }
            command("tweets") {
                if(this.words.size == 1)
                    reply("Wrong format: use .help")
                else {
                    val tweets = getStockTweets(this.words[this.words.size - 1], tf).map { m -> m.text }.toList()
                    val response = StringUtil.join(tweets, "\n")
                    reply(response)
                }
            }
            command("graph") {
                if(this.words.size < 4)
                    reply("Wrong format: use .help")

                val interval = this.words[this.words.size - 1]
                val period = this.words[this.words.size - 2]
                val tickerName = this.words[this.words.size - 3]

                val chart = getGraph(tickerName, period, interval)
                reply(chart.text, chart.embed?.embed())

            }
            command("help"){

            }
            command("insult"){
                val insults = arrayOf("lata bic", "gottem", "nani noo", "wtff dad")

                reply(insults[Random.nextInt(0, insults.size)])
            }
        }
    }
}