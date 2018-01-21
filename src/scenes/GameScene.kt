package scenes

import objects.Player
import objects.Surface
import io.qwerty.neo.Neo
import io.qwerty.neo.scene.Scene
import java.awt.Color
import java.awt.Graphics
import java.awt.Rectangle
import java.awt.event.KeyEvent

class GameScene(neo: Neo) : Scene(neo) {

    // We want the player to be declared as a variable for later handling.
    var player : Player = Player(100f, 100f)

    init {
        // Add objects to the handler sandbox for rendering and ticking.
        handler.addObject(player)

        // Set up a render view to prevent rendering out-of-frame objects.
        handler.setRenderBox(Rectangle(0, 0, neo.width, neo.height))

        // Set up invisible barriers to prevent the player from running out of the game.
        handler.addObject(Surface(-50f, 0f, 50f, neo.height.toFloat()))
        handler.addObject(Surface(neo.width.toFloat(), 0f, 50f, neo.height.toFloat()))

        // Floors.
        handler.addObject(Surface(0f, neo.height - 50f, neo.width().toFloat(), 50f))
        handler.addObject(Surface(100f, neo.height - 200f, 200f, 50f))
    }

    override fun render(g : Graphics?) {
        // You don't have to set a background, it'll default to gray without overlaying.
        g?.color = Color.BLACK
        g?.fillRect(0, 0, neo.width, neo.height)

        // Render everything. This is always necessary.
        handler.render(g)
    }

    override fun onKeyPressed(e: KeyEvent?): Boolean {
        if(e?.keyCode == KeyEvent.VK_A || e?.keyCode == KeyEvent.VK_LEFT)
            player.left()
        else if(e?.keyCode == KeyEvent.VK_D || e?.keyCode == KeyEvent.VK_RIGHT)
            player.right()

        if(e?.keyCode == KeyEvent.VK_SPACE || e?.keyCode == KeyEvent.VK_CONTROL)
            player.jump()

        // You have to return something, it doesn't matter what you return right now. Stick with super.
        return super.onKeyPressed(e)
    }

    override fun onKeyReleased(e: KeyEvent?): Boolean {
        if(e?.keyCode == KeyEvent.VK_A || e?.keyCode == KeyEvent.VK_LEFT)
            player.stopLeft()
        else if(e?.keyCode == KeyEvent.VK_D || e?.keyCode == KeyEvent.VK_RIGHT)
            player.stopRight()

        // You have to return something here as well, leave it super.
        return super.onKeyReleased(e)
    }

}