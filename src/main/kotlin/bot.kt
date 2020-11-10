import com.jessecorbett.diskord.dsl.bot
import com.jessecorbett.diskord.dsl.command
import com.jessecorbett.diskord.dsl.commands

const val BOT_TOKEN = "NzcxOTUzMjQzMzAzNTc1NTUz.X5znew.PWeM8zE5qaIU0Ou5mMD7hU0xiAo"

suspend fun main() {
    bot(BOT_TOKEN) {
        commands {
            command("ping") {
                reply("pong")
                delete()
            }
        }
    }
}