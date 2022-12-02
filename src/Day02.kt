import java.util.*

/*
* searches:
* kotlin enum
* kotlin switch
* kotlin dictionary
* kotlin enum ordinal
* kotlin init array
* kotlin init list
* */
enum class Move {
    ROCK, PAPER, SCISSOR
}

enum class Winner {
    OPPONENT, DRAW, PLAYER
}

fun main() {
    val opponentMoveMap = mapOf("A" to Move.ROCK, "B" to Move.PAPER, "C" to Move.SCISSOR)

    // this mapping is a bit tricky to explain; as I assumed they will later ask for a different
    // mapping then the one given in part 1, I just mapped the x-y-z to array indices
    // and the array will have a permutation of "Move"s
    // let's see if "premature optimization is the root of all evil" :)
    // Part 2 update: I was wrong indeed :)
    val playerMoveMap = mapOf("X" to 0, "Y" to 1, "Z" to 2)
    val moveScoreMap = mapOf(Move.ROCK to 1, Move.PAPER to 2, Move.SCISSOR to 3)
    val gameScoreMap = mapOf(Winner.OPPONENT to 0, Winner.DRAW to 3, Winner.PLAYER to 6)

    fun getResult(opponentMove: Move, playerMove: Move): Winner {
        if (opponentMove == playerMove) {
            return Winner.DRAW
        }

        return when(opponentMove) {
            Move.ROCK -> if (playerMove == Move.PAPER) Winner.PLAYER else Winner.OPPONENT
            Move.PAPER -> if (playerMove == Move.SCISSOR) Winner.PLAYER else Winner.OPPONENT
            Move.SCISSOR -> if (playerMove == Move.ROCK) Winner.PLAYER else Winner.OPPONENT
        }
    }

    fun getScore(opponentMove:Move, playerMove:Move): Int {
        val result = getResult(opponentMove, playerMove)

        return gameScoreMap[result]!! + moveScoreMap[playerMove]!!
    }

    fun getTotalScore(input: List<String>, playerMoveList: List<Move>): Int {
        var totalScore = 0

        for (line in input) {
            val moves = line.split(" ")
            totalScore += getScore(opponentMoveMap[moves[0]]!!, playerMoveList[playerMoveMap[moves[1]]!!])
        }

        return totalScore
    }

    fun part1(input: List<String>): Int {
        // heh, second part will probably say something like
        // your assumption about X,Y,Z was wrong, what is the best combination for highest result
        // so let's get ready for that by making it simple to have variations of player move mapping
        // later, we can create a list of these lists and loop over them

        val playerMoveList = listOf(Move.ROCK, Move.PAPER, Move.SCISSOR)

        return getTotalScore(input, playerMoveList)
    }

    // from https://medium.com/@jcamilorada/recursive-permutations-calculation-algorithm-in-kotlin-86233a0a2ee1
    fun permutationsRecursive(input: List<Move>, index: Int, answers: MutableList<List<Move>>) {
        if (index == input.lastIndex) answers.add(input.toList())
        for (i in index .. input.lastIndex) {
            Collections.swap(input, index, i)
            permutationsRecursive(input, index + 1, answers)
            Collections.swap(input, i, index)
        }
    }

    fun permutations(input: List<Move>): List<List<Move>> {
        val solutions = mutableListOf<List<Move>>()
        permutationsRecursive(input, 0, solutions)
        return solutions
    }

    fun part2(input: List<String>): Int {
        val playerMoveList = listOf(Move.ROCK, Move.PAPER, Move.SCISSOR)

        val allPermutations = permutations(playerMoveList)
        var maxScore = 0
        for (perm in allPermutations) {
            val currentScore = getTotalScore(input, perm)

            if (currentScore > maxScore) {
                maxScore = currentScore
            }
        }
        return maxScore
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
