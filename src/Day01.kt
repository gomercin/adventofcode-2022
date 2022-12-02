/**
 * Searches I needed to make:
kotlin for loop
kotlin check string empty
kotlin string to integer
kotlin array
kotlin list
kotlin mutable list
kotlin sort
kotlin sort reverse
kotlin split list
kotlin sum list
 */

fun main() {
    fun part1(input: List<String>): Int {
        var currentMax = 0
        var currentSum = 0

        for (line in input) {
            if (line.isEmpty()) {
                if (currentSum > currentMax) {
                    currentMax = currentSum
                }
                currentSum = 0
            } else {
                currentSum += line.toInt()
            }
        }

        return currentMax
    }

    fun part2(input: List<String>): Int {
        // input is small enough to handle with sorting
        // would also work for the first part
        val calorieList = mutableListOf<Int>()
        var currentSum = 0
        for (line in input) {
            if (line.isEmpty()) {
                calorieList.add(currentSum)
                currentSum = 0
            } else {
                currentSum += line.toInt()
            }
        }

        calorieList.sortDescending()
        return calorieList.subList(0, 3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
