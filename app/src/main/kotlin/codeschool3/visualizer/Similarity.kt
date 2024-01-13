package codeschool3.visualizer

enum class Similarity() : Printable {
    no {
        override fun print(): String {
            return "    -    "
        }
    },
    close {
        override fun print(): String {
            return " [close] "
        }
    },
    yes {
        override fun print(): String {
            return "   YES   "
        }
    }
}

interface Printable {
    fun print(): String
}
