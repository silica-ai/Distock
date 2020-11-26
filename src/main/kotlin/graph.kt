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


fun getGraph(ticker: String, period : String, interval : String) : Message {


    //println(System.getProperty("user.dir"))
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