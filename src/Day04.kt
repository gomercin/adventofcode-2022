/*
* searches I needed to make:
* kotlin tuple
* */

fun main() {

    class Assignment(line: String) {
        var first: Pair<Int, Int>
        var second: Pair<Int, Int>

        init {
            val parts = line.split(",")
            val firstAsStr = parts[0].split("-")
            val secondAsStr = parts[1].split("-")
            first = Pair(firstAsStr[0].toInt(), firstAsStr[1].toInt())
            second = Pair(secondAsStr[0].toInt(), secondAsStr[1].toInt())
        }

        fun fullOverlap(): Boolean {
            // hehe, looks like using first and second as var names made things a bit weird
            // and repeating first this many times made me realize it is a weird word on its own
            return (first.first <= second.first && first.second >= second.second) ||
                    (first.first >= second.first && first.second <= second.second)
        }

        fun partialOverlap(): Boolean {
            return fullOverlap() || (first.first >= second.first && first.first <= second.second) ||
                    (first.second >= second.first && first.second <= second.second)
        }
    }

    fun part1(input: List<String>): Int {
        var numFullOverlap = 0

        for (line in input) {
            val assignment = Assignment(line)
            if (assignment.fullOverlap()) {
                numFullOverlap++
            }
        }
        return numFullOverlap
    }

    fun part2(input: List<String>): Int {
        var numPartialOverlap = 0

        for (line in input) {
            val assignment = Assignment(line)
            if (assignment.partialOverlap()) {
                numPartialOverlap++
            }
        }
        return numPartialOverlap
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
