import com.jessecorbett.diskord.api.rest.EmbedImage
import com.jessecorbett.diskord.dsl.CombinedMessageEmbed
import com.jessecorbett.diskord.dsl.image
import com.jessecorbett.diskord.util.Colors
import kotlinx.serialization.json.Json
import java.awt.Image
import java.io.File
import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import yahoofinance.YahooFinance
import kotlin.collections.HashMap


fun getGraph(ticker: String, period : String, interval : String) : Message {

    val check = checkInput(ticker, period, interval)

    if (!check.text.equals("valid"))
        return check

    val userDirectory = System.getProperty("user.dir")
    val path = "$userDirectory/src/plot.py"
    val process = Runtime.getRuntime().exec("python3 $path $ticker $period $interval")
    process.waitFor()

    val imageB = get64BaseImage("$userDirectory/src/images/fig1.jpg")

    var response = khttp.post(
        "https://api.imgbb.com/1/upload",
        data = mapOf(
            "key" to Config.IMG_TOKEN,
            "image" to imageB,
            "expiration" to "600"
        )
    )

    var urlImage = Json.parseJson(response.jsonObject["data"].toString()).jsonObject["display_url"].toString()
    urlImage = urlImage.substring(1, urlImage.length-1)
    val embedding = CombinedMessageEmbed().apply {
        image=EmbedImage(urlImage)
    }

    return Message("", embedding)
}

fun get64BaseImage(file: String): String? {
    val fileContent: ByteArray = Files.readAllBytes(Path.of(file))
    return Base64.getEncoder().encodeToString(fileContent)
}

fun checkInput(ticker: String, period : String, interval : String) : Message {
    var periodMap = setOf("1d", "5d","1mo","3mo","6mo","1y","2y","5y","10y","ytd","max")
    var intervalMap = setOf("1m","2m","5m","15m","30m","60m","90m","1h","1d","5d","1wk","1mo","3mo")
    var check = ""
    var valid = true

    if(!YahooFinance.get(ticker).isValid){
        check += "Stock $ticker not available\n"
        valid = false
    }
    if(!periodMap.contains(period)){
        check += "Invalid period\n"
        valid = false
    }
    if(!intervalMap.contains(interval)){
        check += "Invalid interval\n"
        valid = false
    }
    if(!valid)
        check += "\nFormat should be:\n.graph symbol period interval\n"

    val embedding = CombinedMessageEmbed().apply {
        description = check
        color = Colors.RED
    }

    if(check.isEmpty())
        return Message("valid")

    return Message("", embedding)

}