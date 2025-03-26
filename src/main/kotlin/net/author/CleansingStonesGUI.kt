package net.author

import net.botwithus.rs3.imgui.ImGui
import net.botwithus.rs3.script.ScriptConsole
import net.botwithus.rs3.script.ScriptGraphicsContext

class CleansingStonesGUI(
    private val script: CleansingStones,
    console: ScriptConsole
) : ScriptGraphicsContext (console) {

    override fun drawSettings() {   
        super.drawSettings()
        ImGui.Begin("Cleansing Stones", 0)
        ImGui.SetWindowSize(250f, -1f)
        ImGui.Text("This is my bot")
        if (ImGui.Button("Start")) {
            script.botState = CleansingStones.BotState.SKILLING;
        }
        ImGui.SameLine()
        if (ImGui.Button("Stop")) {
            script.botState = CleansingStones.BotState.IDLE
        }
        ImGui.End()
    }

    override fun drawOverlay() {
        super.drawOverlay()
    }

}