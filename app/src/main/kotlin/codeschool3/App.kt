/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package codeschool3

class App

enum class Similarity {
    no,
    close,
    yes
}

fun getSimilarity(target: String, guess: String): List<Similarity> {
    var result = mutableListOf(Similarity.no, Similarity.no, Similarity.no, Similarity.no, Similarity.no)
    val unguessedLetters = target.toCharArray().toMutableList()
    for (i in 0..4) {
        if (target[i] == guess[i]) {
            result[i] = Similarity.yes
            unguessedLetters.removeAt(unguessedLetters.indexOf(target[i]))
        }
    }

    for (i in 0..4) {
        if (unguessedLetters.contains(guess[i])) {
            result[i] = Similarity.close
            unguessedLetters.removeAt(unguessedLetters.indexOf(guess[i]))
        }
    }

    return result
}

fun getFeedback(sim: Similarity): String {
    return when (sim) {
        Similarity.no -> "    -    "
        Similarity.yes -> "   YES   "
        Similarity.close -> " [close] "
    }
}

fun prettyPrintResult(guess: String, result: List<Similarity>) {
    val guessChars = guess.toCharArray()

    val borderLine = "-".repeat(51)
    val dividerLine = "|" + "-".repeat(49) + "|"
    val letterLine = "|" + guessChars.map { "    $it    " }.joinToString("|") + "|"
    val feedbackLine = "|" + result.map { getFeedback(it) }.joinToString("|") + "|"

    println(borderLine)
    println(letterLine)
    println(dividerLine)
    println(feedbackLine)
    println(borderLine)
}

fun isInvalid(guess: String?): Boolean {
    return guess == null || guess.length != 5
}

fun handleGuessAndCheckIfCorrect(target: String, guess: String): Boolean {
    val similarity = getSimilarity(target, guess)
    prettyPrintResult(guess, similarity)

    return guess == target
}

fun playGame() {
    val target = listOf("hello", "crane", "toils", "yikes", "drewb").shuffled().first().uppercase()

    var guessNumber = 1

    while (guessNumber <= 6) {
        println("Guess the word! Guess #$guessNumber:")
        var guess = readLine()?.trim()?.toUpperCase()

        if (isInvalid(guess)) {
            println("Invalid guess! Must be 5 letters.")
            continue
        }

        if (handleGuessAndCheckIfCorrect(target, guess!!)) {
            break;
        }

        guessNumber++
    }

    if (guessNumber < 7) {
        println("YOU DID IT! The word was: $target")
    } else {
        println("Oh no! You didn't get guess it this time. The word was: $target")
    }
}

fun playAgain(): Boolean {
    println("Would you like to play again? Enter [y] for yes, or [n] for no.")
    return readLine()?.trim()?.toLowerCase()?.startsWith("y") == true
}

fun main() {
    playGame()

    while (playAgain()) {
        playGame()
    }
}