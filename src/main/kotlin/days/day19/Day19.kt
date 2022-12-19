package days.day19

import days.Day
import util.allInts
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class Day19(override val input: String) : Day<Int>(input) {

    private val blueprints = input.lines().map {
        it.allInts().let { costs ->
            Blueprint(
                costs[0],
                listOf(
                    RobotCost(ore = costs[1]),
                    RobotCost(ore = costs[2]),
                    RobotCost(ore = costs[3], clay = costs[4]),
                    RobotCost(ore = costs[5], obsidian = costs[6])
                )
            )
        }
    }

    override fun solve1(): Int {
        return blueprints.sumOf {
            best = 0
            it.id * simulate(it, SimulationState(0, 0, 0, 0, 0, 1, 0, 0, 0))
        }
    }

    override fun solve2(): Int {
        maxMinutes = 32
        return blueprints.take(3)
            .map {
                best = 0
                simulate(it, SimulationState(0, 0, 0, 0, 0, 1, 0, 0, 0))
            }
            .reduce(Int::times)
    }

    private var best = 0
    private var maxMinutes = 24


    private fun simulate(blueprint: Blueprint, state: SimulationState): Int {
        if (state.minute >= maxMinutes) {
            best = max(state.geodes, best)
            return state.geodes
        }

        val roundsLeft = maxMinutes - state.minute
        val canProduce = state.geodes + (0 until roundsLeft).sumOf { state.geodesProd + it }
        if (canProduce < best) {
            return 0
        }

        var max = 0

        //build geode bot
        if (state.obsidianProd > 0) {
            val oresNeeded = max(0, blueprint.costs[3].ore - state.ore)
            val obsidiansNeeded = max(0, blueprint.costs[3].obsidian - state.obsidian)
            val wait = if (oresNeeded == 0 && obsidiansNeeded == 0) {
                1
            } else {
                max(
                    ceil(oresNeeded.toFloat() / state.oreProd.toFloat()).toInt(),
                    ceil(obsidiansNeeded.toFloat() / state.obsidianProd.toFloat()).toInt(),
                ) + 1
            }
            val stateAfterBuilt = state.progress(wait).let {
                it.copy(
                    ore = it.ore - blueprint.costs[3].ore,
                    obsidian = it.obsidian - blueprint.costs[3].obsidian,
                    geodesProd = it.geodesProd + 1
                )
            }

            val result = simulate(blueprint, stateAfterBuilt)
            max = max(result, max)
        }

        //build obsidian bot
        if (state.clayProd > 0) {
            val oresNeeded = max(0, blueprint.costs[2].ore - state.ore)
            val clayNeeded = max(0, blueprint.costs[2].clay - state.clay)

            val wait = if (oresNeeded == 0 && clayNeeded == 0)
                1
            else {
                max(
                    ceil(oresNeeded.toFloat() / state.oreProd.toFloat()).toInt(),
                    ceil(clayNeeded.toFloat() / state.clayProd.toFloat()).toInt()
                ) + 1
            }
            val stateAfterBuilt = state.progress(wait).let {
                it.copy(
                    ore = it.ore - blueprint.costs[2].ore,
                    clay = it.clay - blueprint.costs[2].clay,
                    obsidianProd = it.obsidianProd + 1
                )
            }
            val result = simulate(blueprint, stateAfterBuilt)
            max = max(result, max)
        }

        //build clay bot
        if (state.oreProd > 0) {
            val oresNeeded = max(0, blueprint.costs[1].ore - state.ore)

            val wait = if (oresNeeded == 0)
                1
            else ceil(oresNeeded.toFloat() / state.oreProd.toFloat()).toInt() + 1

            val stateAfterBuilt = state.progress(wait).let {
                it.copy(
                    ore = it.ore - blueprint.costs[1].ore,
                    clayProd = it.clayProd + 1
                )
            }
            val result = simulate(blueprint, stateAfterBuilt)
            max = max(result, max)
        }

        //ore
        if (state.oreProd > 0) {
            val oresNeeded = max(0, blueprint.costs[0].ore - state.ore)

            val wait = if (oresNeeded == 0)
                1
            else ceil(oresNeeded.toFloat() / state.oreProd.toFloat()).toInt() + 1

            val stateAfterBuilt = state.progress(wait).let {
                it.copy(
                    ore = it.ore - blueprint.costs[0].ore,
                    oreProd = it.oreProd + 1
                )
            }
            val result = simulate(blueprint, stateAfterBuilt)
            max = max(result, max)
        }

        return max
    }


    //0 - ORE
    //1 - CLAY
    //2 - OBSIDIAN
    //3 - GEODE

    data class SimulationState(
        val minute: Int,
        val ore: Int,
        val clay: Int,
        val obsidian: Int,
        val geodes: Int,
        val oreProd: Int,
        val clayProd: Int,
        val obsidianProd: Int,
        val geodesProd: Int,
    )

    //returns the next simulation state after specified minutes have passed
    private fun SimulationState.progress(minutes: Int): SimulationState {
        val time = min(maxMinutes - this.minute, minutes)

        return this.copy(
            minute = minute + time,
            ore = ore + oreProd * time,
            clay = clay + clayProd * time,
            obsidian = obsidian + obsidianProd * time,
            geodes = geodes + geodesProd * time
        )
    }

    data class Blueprint(val id: Int, val costs: List<RobotCost>)

    data class RobotCost(val ore: Int = 0, val clay: Int = 0, val obsidian: Int = 0)

}