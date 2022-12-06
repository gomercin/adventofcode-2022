import java.util.*

/**
 * Searches I needed to make:
kotlin queue with fixed size
kotlin iterate with index and val
 */

fun main() {


    class CircularQueue<E>(private val capacity: Int) : LinkedList<E>() {
        override fun add(element: E): Boolean {
            if (size >= capacity) removeFirst()
            return super.add(element)
        }

        fun isStartOfPacket(): Boolean {
            return distinctBy { it }.size == capacity
        }
    }

    fun part1(input: List<String>): Int {
        val queue = CircularQueue<Char>(4)

        input[0].trim().forEachIndexed { index, ch ->
            queue.add(ch)

            if (queue.isStartOfPacket()) {
                return index + 1
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val queue = CircularQueue<Char>(14)

        input[0].trim().forEachIndexed { index, ch ->
            queue.add(ch)

            if (queue.isStartOfPacket()) {
                return index + 1
            }
        }

        return 0
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
