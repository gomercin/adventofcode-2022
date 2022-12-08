/**
 * Searches I needed to make:
 * kotlin remove first element (I forgot I had to make a mutable list)
 * kotlin repeat string
 */

fun main() {

    abstract class FileSystemNode(private val name: String) {
        var children = mutableMapOf<String, FileSystemNode>()
        var parent: FileSystemNode? = null

        abstract fun getSize(): Int

        fun addChild(child: FileSystemNode) {
            child.parent = this
            children[child.name] = child
        }

        abstract fun printTree(indent: Int)
    }

    class File(private val name: String, private val size: Int) : FileSystemNode(name) {
        override fun getSize(): Int {
            return size
        }

        override fun printTree(indent: Int) {
            println("\t".repeat(indent) + "${name} (${size})")
        }
    }

    class Directory(private val name: String) : FileSystemNode(name) {
        override fun getSize(): Int {
            var totalSize = 0

            for (child in children.values) {
                totalSize += child.getSize()
            }

            return totalSize
        }

        fun getChildDir(childDirName: String): Directory {
            return children[childDirName] as Directory
        }

        override fun printTree(indent: Int) {
            println("\t".repeat(indent) + name)

            for (child in children.values) {
                child.printTree(indent + 1)
            }
        }

        fun part1(): Int {
            // I looked for a custom iterator but turned out to be a bit complex for a solution
            // hence this ugly-ish hack

            var total = 0
            for (child in children.values) {
                if (child is Directory) {
                    val childSize = child.getSize()
                    if (childSize <= 100000) {
                        total += childSize
                    }

                    total += child.part1()
                }
            }

            return total
        }
    }

    fun createFileSystem(input: List<String>): Directory {
        // create the file system by parsing the input and return the root node
        val root = Directory("/")

        val inputWithoutRoot = input.toMutableList()
        inputWithoutRoot.removeAt(0)
        var currentDirectory = root
        for (line in inputWithoutRoot) {
            if (line.isEmpty()) break
            if (line.startsWith("\$")) {
                if (line.startsWith("\$ cd")) {
                    val lineParts = line.split(" ")
                    val targetDirName = lineParts[2]

                    if(targetDirName == "..") {
                        currentDirectory = currentDirectory.parent as Directory
                    } else {
                        currentDirectory = currentDirectory.getChildDir(targetDirName)
                    }
                }
                // we can ignore ls
            } else {
                val lineParts = line.split(" ")
                // we should have two parts here
                if (lineParts[0] == "dir") {
                    currentDirectory.addChild(Directory(lineParts[1]))
                } else {
                    currentDirectory.addChild(File(lineParts[1], lineParts[0].toInt()))
                }
            }
        }

        return root
    }

    fun part1(root: Directory): Int {

        return root.part1()
    }

    fun findMinDirSize(dir: Directory, refVal: Int, spaceNeeded: Int): Int {
        // it would speed up things if we "cache" the size for each item
        // otherwise getSize will be called a looot of times
        val currentDirSize = dir.getSize()

        if (currentDirSize < spaceNeeded) {
            // no need to traverse further here
            return refVal
        }

        var currentMin = refVal
        if (currentDirSize < currentMin) {
            currentMin = currentDirSize
        }

        for (child in dir.children.values) {
            if (child is Directory) {
                val childDirSize = findMinDirSize(child, currentMin, spaceNeeded)
                if (childDirSize < currentMin) {
                    currentMin = childDirSize
                }
            }
        }

        return currentMin
    }

    fun part2(root: Directory): Int {
        val spaceNeeded = 30000000 - 70000000 + root.getSize()
        println(spaceNeeded)
        return findMinDirSize(root, 70000000, spaceNeeded)
    }

    val input = readInput("Day07")
    val root = createFileSystem(input)
    // root.printTree(0)
    println(part1(root))
    println(part2(root))
}
