package days.day5

class Crane(private val stacks: Array<ArrayDeque<Char>>) {

    private fun move9000(instruction: Instruction) {
        repeat(instruction.amount) {
            val toMove = stacks[instruction.start - 1].removeLast()
            stacks[instruction.end - 1].addLast(toMove)
        }
    }

    private fun move9001(instruction: Instruction) {
        val index = stacks[instruction.end - 1].size
        repeat(instruction.amount) {
            val toMove = (stacks[instruction.start - 1].removeLast())
            stacks[instruction.end - 1].add(index, toMove)
        }
    }

    fun moveAll(instructions: List<Instruction>, part: Int): String {
        if (part == 1)
            instructions.forEach { move9000(it) }
        else
            instructions.forEach { move9001(it) }
        return stacks.joinToString("") { it.last().toString() }
    }
}

data class Instruction(val amount: Int, val start: Int, val end: Int)