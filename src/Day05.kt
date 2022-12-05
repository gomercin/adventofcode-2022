import java.util.*

/*
* searches I needed to make:
* kotlin stack
* kotlin reverse iterate list
* kotlin split by multiple spaces
* kotlin for in range
* kotlin scanf
* kotlin string scanner
* kotlin get numbers from string
* */

fun main() {

    class Supplies {
        // to be initialized with the stack structure, i.e. the first part of the input
        /* e.g.
    [D]
[N] [C]
[Z] [M] [P]
 1   2   3
        * */
        lateinit var stacks: MutableList<ArrayDeque<Char>>

        fun processInput(stackLines: List<String>) {
            val reversed = stackLines.asReversed()
            stacks = mutableListOf<ArrayDeque<Char>>()

            val columnNumbersStr = reversed[0]
            val columnNumbers = columnNumbersStr.trim().split("\\s+".toRegex())
            // if the input was not already sanitized, we could parse this line further
            // to have column IDs for each stack, now we use this list only to get the
            // number of stacks we want

            for (i in columnNumbers.indices) {
                stacks.add(ArrayDeque<Char>())
            }

            val stackCount = stacks.size
            // now we can start from bottom (or top in the reversed array) and push to appropriate
            // stack. We just need to remember the 0-1 index difference

            for (entryIndex in 1 until reversed.size) {
                val line = reversed[entryIndex]
                // Splitting doesn't make much of a sense here
                // as there might be gaps in the lines
                // [R] [N] [S] [T] [P] [P] [W] [Q] [G]
                // 01234567890123456789012345678902345
                // each crate is at index * 4 + 1 location
                // some lines may not be full, so we still need to check if calculated crate_index
                // is smaller than line length
                for (i in 0 until stackCount) {
                    val crateStrIndex = 1 + (4 * i)

                    if (crateStrIndex < line.length) {
                        val crateCode = line[crateStrIndex]

                        if (!crateCode.isWhitespace()) {
                            stacks[i].push(crateCode)
                        }
                    }
                }
            }
        }

        fun move(howMany: Int, fromIndex: Int, toIndex: Int, moveTogether: Boolean=false) {
            if (!moveTogether) {
                for (i in 0 until howMany) {
                    // should we check if input is always valid
                    // for example if it wants to move 3, but there is only two?
                    // println("Moving ${howMany} items from ${fromIndex} to ${toIndex}")
                    stacks[toIndex - 1].push(stacks[fromIndex - 1].pop())
                }
            } else {
                val tmpStack = ArrayDeque<Char>()

                for (i in 0 until howMany) {
                    // should we check if input is always valid
                    // for example if it wants to move 3, but there is only two?
                    // println("Moving ${howMany} items from ${fromIndex} to ${toIndex}")
                    tmpStack.push(stacks[fromIndex - 1].pop())
                }

                while(!tmpStack.isEmpty()) {
                    stacks[toIndex - 1].push(tmpStack.pop())
                }
            }
        }

        fun getTopElements(): String {
            var topElements = ""
            for (stack in stacks) {
                topElements += stack.first
            }

            return topElements
        }
    }

    fun part1(input: List<String>, moveTogether: Boolean): String {
        val initialStateDefinition = mutableListOf<String>()
        val supplies = Supplies()

        var readCommands = false
        for (line in input) {
            if (!readCommands) {
                if (line.isEmpty()) {
                    supplies.processInput(initialStateDefinition)
                    readCommands = true
                }
                else {
                    initialStateDefinition.add(line)
                }
            } else {
                // println("scanner line: ${line}")
                val moveCmdParts = line.split("move ", " from ", " to ")
                // this comes with an empty element at 0 index, I will ignore that for now
                val howMany = moveCmdParts[1].toInt()
                val from = moveCmdParts[2].toInt()
                val to = moveCmdParts[3].toInt()

                supplies.move(howMany, from, to, moveTogether)
            }
        }
        return supplies.getTopElements()
    }

    val input = readInput("Day05")
    println(part1(input, false))
    println(part1(input, true))
}
