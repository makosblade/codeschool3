package codeschool3

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlin.system.exitProcess


/**
 * @author Drew Bailey (byteboundary.com)
 */
data class WordleWords(
    val validSolutions: List<String>,
    private val validAttemptWords: List<String>,
    val validGuesses: List<String> = buildList {
        addAll(validSolutions)
        addAll(validAttemptWords)
    }
)

val MAPPER = ObjectMapper().registerKotlinModule()
fun loadFromJson(): WordleWords {
    val jsonInputStream = WordleWords::class.java.getResourceAsStream("/words.json")
    if (jsonInputStream == null){
        println("sad :((")
        exitProcess(-1)
    }
    return MAPPER.readValue(jsonInputStream)
}