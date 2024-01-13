package codeschool3

import codeschool3.visualizer.Similarity
import java.util.Locale

/**
 * @author Drew Bailey (byteboundary.com)
 */
interface WordleRunner {
    fun playGame(words: WordleWords) {
        val target = words.validSolutions.shuffled().first().lowercase()

        var guessNumber = 1

        while (guessNumber <= 6) {
            println("Guess the word! Guess #$guessNumber:")
            val guess = readlnOrNull()?.trim()?.lowercase()

            if (isInvalid(words, guess)) {
                println("Invalid guess! Must be a 'real' 5 letter word.")
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

    private fun isInvalid(words: WordleWords, guess: String?): Boolean {
        return guess == null || !words.validGuesses.contains(guess)
    }

    private fun handleGuessAndCheckIfCorrect(target: String, guess: String): Boolean {
        val similarity = getSimilarity(target, guess)
        prettyPrintResult(guess, similarity)

        return guess == target
    }

    fun playAgain(): Boolean {
        println("Would you like to play again? Enter [y] for yes, or [n] for no.")
        return readlnOrNull()?.trim()?.lowercase(Locale.getDefault())?.startsWith("y") == true
    }

    fun getSimilarity(target: String, guess: String): List<Similarity> {
        val result = mutableListOf(Similarity.no, Similarity.no, Similarity.no, Similarity.no, Similarity.no)
        val unguessedLetters = target.toCharArray().toMutableList()
        for (i in 0..4) {
            if (target[i] == guess[i]) {
                result[i] = Similarity.yes
                unguessedLetters.removeAt(unguessedLetters.indexOf(target[i]))
            }
        }

        for (i in 0..4) {
            if (target[i] != guess[i] && unguessedLetters.contains(guess[i])) {
                result[i] = Similarity.close
                unguessedLetters.removeAt(unguessedLetters.indexOf(guess[i]))
            }
        }

        return result

    }

    fun prettyPrintResult(guess: String, result: List<Similarity>) {
        val guessChars = guess.uppercase().toCharArray()

        val borderLine = "-".repeat(51)
        val dividerLine = "|" + "-".repeat(49) + "|"
        val letterLine = "|" + guessChars.joinToString("|") {
            "    $it    "
        } + "|"
        val feedbackLine = "|" + result.joinToString("|") {
            it.print()
        } + "|"

        println(borderLine)
        println(letterLine)
        println(dividerLine)
        println(feedbackLine)
        println(borderLine)
    }
}

class KatherineWordleRunner() : WordleRunner
