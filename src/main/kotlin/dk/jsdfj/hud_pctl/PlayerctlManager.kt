package dk.jsdfj.hud_pctl

import dk.jsdfj.hud_pctl.Hud_pctl.Companion.LOGGER
import java.util.concurrent.Executors

object PlayerctlManager {
    @Volatile
    var title = "N/A"
        private set

    @Volatile
    var artist = "N/A"
        private set

    @Volatile
    var album = "N/A"
        private set

    @Volatile
    var playerName = "N/A"
        private set

    @Volatile
    var position = "N/A"
        private set

    @Volatile
    var duration = "N/A"
        private set

    @Volatile
    var positionRaw = -1
        private set

    @Volatile
    var durationRaw = -1
        private set

    @Volatile
    var status = "N/A"
        private set

    @Volatile
    var volumeRaw = -1F
        private set

    @Volatile
    var volume = -1
        private set

    private val service = Executors.newSingleThreadExecutor {
        Thread(it, "PlayerctlManager").apply {
            isDaemon = true
        }
    }

    init {
        service.execute(::listenToStream)
        LOGGER.info("Initialized manager")
    }

    private fun listenToStream() {
        try {
            val process = ProcessBuilder()
                .command(
                    "playerctl",
                    "metadata",
                    "--format",
                    "{{ title }}\t{{ artist }}\t{{ album }}\t{{ status }}\t{{ position }}\t{{ mpris:length }}\t{{ playerName }}\t{{ volume }}",
                    "--follow"
                )
                .redirectErrorStream(true)
                .start()

            process.inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val parts = line.split("\t", limit = 8)

                    title = parts.getOrNull(0).orEmpty().ifBlank { "N/A" }
                    artist = parts.getOrNull(1).orEmpty().ifBlank { "N/A" }
                    album = parts.getOrNull(2).orEmpty().ifBlank { "N/A" }
                    status = parts.getOrNull(3).orEmpty().ifBlank { "N/A" }
                    positionRaw = (parts.getOrNull(4).orEmpty().ifBlank { "nan" }.toIntOrNull()?.div(1000000) ?: -1)
                    durationRaw = (parts.getOrNull(5).orEmpty().ifBlank { "nan" }.toIntOrNull()?.div(1000000) ?: -1)
                    playerName = parts.getOrNull(6).orEmpty().ifBlank { "N/A" }
                    volumeRaw = parts.getOrNull(7).orEmpty().ifBlank { "-1" }.toFloatOrNull() ?: -1F

                    volume = if (volumeRaw == -1F) -1 else (volumeRaw * 100).toInt()
                    position = if (positionRaw == -1) "N/A" else "${positionRaw / 60}:${if(positionRaw % 60 < 10) "0" + positionRaw % 60 else positionRaw % 60}"
                    duration = if (durationRaw == -1) "N/A" else "${durationRaw / 60}:${if(durationRaw % 60 < 10) "0" + durationRaw % 60 else durationRaw % 60}"
                }
            }
        } catch (e: Exception) {
            LOGGER.error("Failed to listen to playerctl updates", e)
        }
    }
}