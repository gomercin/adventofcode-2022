/*
* searches I needed to make:
* kotlin list intersection
* kotlin list split
* kotlin string to list of chars
* kotlin ch to ascii
* */


fun main() {

    class Rucksack {
        var compartment1: List<Char>
        var compartment2: List<Char>
        var allContents: List<Char>

        constructor(contents: String) {
            allContents = contents.toList()
            val compartments = contents.chunked(contents.length / 2)
            compartment1 = compartments[0].toList()
            compartment2 = compartments[1].toList()
        }

        fun getCommonElement(): Char {
            return compartment1.intersect(compartment2).toList()[0]
        }
    }

    fun getPriority(ch: Char): Int {
        return if (ch.isLowerCase()) {
            ch.code - 'a'.code + 1
        } else {
            // this might be unneeded, I don't remember if "A" was just after "z"
            // if that's the case, no lowercase check was necessary and we could use the above block
            ch.code - 'A'.code + 27
        }
    }

    fun part1(input: List<String>): Int {
        var totalPriority = 0
        for (line in input) {
            val rucksack = Rucksack(line)
            val commonElement = rucksack.getCommonElement()
            // println("common element is: ${commonElement}")
            totalPriority += getPriority(commonElement)
        }
        return totalPriority
    }

    fun part2(input: List<String>): Int {
        var totalPriority = 0
        var commonElements = listOf<Char>()

        var counter = 0
        for (line in input) {
            val rucksack = Rucksack(line)
            if (counter == 0) {
                commonElements = rucksack.allContents
            } else {
                commonElements = commonElements.intersect(rucksack.allContents).toList()
            }

            if (counter == 2) {
                counter = -1 //to increment to 0 after if, blergh, ugly
                // here we should have a single element
                totalPriority += getPriority(commonElements[0])
            }
            counter++
        }
        return totalPriority
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
