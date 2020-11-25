import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import java.util.*


fun getGraph() {


    println(System.getProperty("user.dir"))
    val image = get64BaseImage("./src/main/resources/poly.png")


    var response = khttp.post(
        "https://api.imgbb.com/1/upload",
        data = mapOf(
            "key" to Config.IMG_TOKEN,
            "image" to image,
            "expiration" to 600
        )
    )

    var urlImage = Json.parseJson(response.jsonObject["data"].toString()).jsonObject["display_url"].toString()
    urlImage = urlImage.substring(1, urlImage.length-1)
}

fun get64BaseImage(file: String): String? {
    val fileContent: ByteArray = Files.readAllBytes(Path.of(file))
    return Base64.getEncoder().encodeToString(fileContent)
}