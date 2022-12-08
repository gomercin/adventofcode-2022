/**
 * Searches I needed to make:
 kotlin 2D array
 kotlin until reverse
 */

fun main() {
    val size = 99
    fun part1(input: Array<Array<Int>>): Int {
        // well, facepalm way would be iterating every element and comparing
        // it to every element in row and column.
        // but I think we can count by going over each row and each column only once.
        // The only thing is something might be visible in only one direction
        // or in all of them, we shouldn't count them twice.
        // we can store the visibles in a set not to count them twice.
        // size of the set will be the result.
        // Key can be 100* row + column
        // hmm wait, we should go in all 4 directions

        val visibles = mutableSetOf<Int>()
        for (rowIndex in 0 until size) {
            var currentMax = -1

            for (colIndex in 0 until size) {
                val tree = input[rowIndex][colIndex]
                if (tree > currentMax) {
                    visibles.add(100 * rowIndex + colIndex)
                    currentMax = tree
                }
            }

            currentMax = -1
            for (colIndex in size - 1 downTo 0) {
                val tree = input[rowIndex][colIndex]
                if (tree > currentMax) {
                    visibles.add(100 * rowIndex + colIndex)
                    currentMax = tree
                }
            }
        }

        for (colIndex in 0 until size) {
            var currentMax = -1

            for (rowIndex in 0 until size) {
                val tree = input[rowIndex][colIndex]
                if (tree > currentMax) {
                    visibles.add(100 * rowIndex + colIndex)
                    currentMax = tree
                }
            }

            currentMax = -1
            for (rowIndex in size - 1 downTo 0) {
                val tree = input[rowIndex][colIndex]
                if (tree > currentMax) {
                    visibles.add(100 * rowIndex + colIndex)
                    currentMax = tree
                }
            }
        }

        return visibles.size
    }

    fun part2(input: Array<Array<Int>>): Int {
        // can't think of a smart way for this one; brute force it is.
        // we can skip the ones on the edge but who cares at this point :)

        var maxScore = 0

        for (rowIndex in 0 until size) {
            for (colIndex in 0 until size) {
                val treeHeight = input[rowIndex][colIndex]

                // now go in each direction until limits or you find a taller or equal tree
                var upCount = 0

                var localRowIndex = rowIndex - 1
                while (localRowIndex >= 0) {
                    upCount++

                    val leftTree = input[localRowIndex][colIndex]
                    if (leftTree >= treeHeight) break
                    localRowIndex--
                }

                var downCount = 0

                localRowIndex = rowIndex + 1
                while (localRowIndex < size) {
                    downCount++

                    val leftTree = input[localRowIndex][colIndex]
                    if (leftTree >= treeHeight) break
                    localRowIndex++
                }

                var leftCount = 0

                var localColIndex = colIndex - 1
                while (localColIndex >= 0) {
                    leftCount++

                    val leftTree = input[rowIndex][localColIndex]
                    if (leftTree >= treeHeight) break
                    localColIndex--
                }

                var rightCount = 0

                localColIndex = colIndex + 1
                while (localColIndex < size) {
                    rightCount++

                    val leftTree = input[rowIndex][localColIndex]
                    if (leftTree >= treeHeight) break
                    localColIndex++
                }

                val localScore = leftCount * rightCount * upCount * downCount
                if (localScore > maxScore) maxScore = localScore
            }
        }
        return maxScore
    }

    val input = readInput("Day08")
    // we could get the dimensions from the input, but I'm too lazy for that :)
    val forest = Array(size) { row -> Array(size) { col -> input[row][col].digitToInt() } }

//    for (row in forest) {
//        for (num in row) {
//            print(num)
//        }
//        println()
//    }
    println(part1(forest))
    println(part2(forest))
}
