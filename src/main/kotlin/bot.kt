import com.jessecorbett.diskord.dsl.Command
import com.jessecorbett.diskord.dsl.bot
import com.jessecorbett.diskord.dsl.command
import com.jessecorbett.diskord.dsl.commands
import com.jessecorbett.diskord.util.words
import com.jessecorbett.diskord.dsl.*
import org.jsoup.Jsoup
import kotlin.random.Random

const val BOT_TOKEN = "NzcxOTUzMjQzMzAzNTc1NTUz.X5znew.PWeM8zE5qaIU0Ou5mMD7hU0xiAo"

suspend fun main() {
    bot(BOT_TOKEN) {
        commands {
            command("price"){
                val tickerName = this.words[this.words.size - 1]
                val res = getTickerPrice(tickerName)
                reply(res.testString)
            }
            command("news") {
                val tickerName = this.words[this.words.size - 1]
                val newsLinks = getRecentNews(tickerName)
                reply(newsLinks.testString)
            }
            command("insult"){
                val insults = arrayOf("you gee", "lata bic", "gottem", "nani noo", "wtff dad")

                reply(insults[Random.nextInt(0, insults.size)])
            }
        }
    }
}