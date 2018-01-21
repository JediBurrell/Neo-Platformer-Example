import GameScene
import io.qwerty.neo.Neo
import io.qwerty.neo.Window

class Main : Neo(Window("Neo Platformer Example")) {

    init {
        // Set the canvas.
        window.setContent(this)
        window.setSize(1000, 600)
        // Create the window.
        window.start()
        // Set the scene, otherwise the game will crash and you'll just get a blank screen.
        scene = GameScene(this)
        // Set the content size.
        setSize(1000, 600)

        // Start the game rendering/playing.
        start()
    }

}

fun main(args: Array<String>) {
    Main()
}