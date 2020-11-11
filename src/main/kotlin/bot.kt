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
            command("ping") {

                reply("pong")
            }
            command("news"){
                val getCommand = this.words
                val tickerName = this.words[-1]
                print(tickerName)
                val res = getTickerPrice("APPL")
                reply("GORD")
            }
            command("insult"){
                val insults = arrayOf("you gee", "lata bic", "gottem", "nani noo", "wtff dad")

                reply(insults[Random.nextInt(0, insults.size)])
            }
        }
    }
}