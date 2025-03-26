package net.author

import net.botwithus.api.game.hud.inventories.Backpack
import net.botwithus.api.game.hud.inventories.Bank
import net.botwithus.internal.scripts.ScriptDefinition
import net.botwithus.rs3.game.Client
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery
import net.botwithus.rs3.game.scene.entities.characters.player.Player
import net.botwithus.rs3.game.scene.entities.`object`.SceneObject
import net.botwithus.rs3.script.Execution
import net.botwithus.rs3.script.LoopingScript
import net.botwithus.rs3.script.config.ScriptConfig
import java.util.Random

class CleansingStones(
    name: String,
    scriptConfig: ScriptConfig,
    scriptDefinition: ScriptDefinition
) : LoopingScript (name, scriptConfig, scriptDefinition) {

    val random: Random = Random()
    var botState: BotState = BotState.SKILLING

    enum class BotState {
        SKILLING,
        IDLE
    }

    override fun initialize(): Boolean {
        super.initialize()
        this.sgc = CleansingStonesGUI(this, console)
        println("My script loaded!")
        return true;
    }

    override fun onLoop() {
        val player = Client.getLocalPlayer();
        if (Client.getGameState() != Client.GameState.LOGGED_IN || player == null || botState == BotState.IDLE) {
            Execution.delay(random.nextLong(2500,5500))
            return
        }
        when (botState) {
            BotState.SKILLING -> {
                Execution.delay(handleSkilling(player))
                return
            }

            else -> {
                println("Unexpected bot state, report to author!")
            }
        }
        Execution.delay(random.nextLong(2000,4000))
        return
    }
    private fun handleSkilling(player: Player): Long {
        if(player.animationId != -1){
            return random.nextLong(1200,2000)
        }
        val sceneObject: SceneObject? = SceneObjectQuery.newQuery().name("Corrupted Seren Stone").option("Cleanse").results().first()
        if(sceneObject != null) {
            sceneObject.interact("Cleanse")
            Execution.delayUntil(5000, { player.animationId != -1 })
        }
        return random.nextLong(1000,3000)
    }
}