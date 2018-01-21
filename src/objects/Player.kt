package objects

import io.qwerty.neo.framework.Handler
import io.qwerty.neo.framework.ObjectID
import io.qwerty.neo.objects.GameObject
import java.awt.Color
import java.awt.Graphics
import java.awt.Shape
import java.util.*

class Player(x : Float, y : Float) : GameObject(x, y, ObjectID.player) {

    // This value shouldn't need changed after initial declaration. Unless we're going to space, we'll be okay.
    private val MAX_VELOCITY = 8f
    private var onGround = false
    private var goingLeft = false
    private var goingRight = false
    private var doubleJump = false

    // Set local variables to class variables, and specify size.
    init {
        this.x = x
        this.y = y
        width = 50f
        height = 50f
    }

    override fun tick(objects: LinkedList<GameObject>?) {
        // Reset onGround, this'll be checked in super.
        onGround = false
        super.tick(objects)

        // EASE RIGHT
        if(velX > 0 && !goingRight)
            velX = Math.max(velX - 0.2f, 0f)
        // EASE LEFT
        else if(velX < 0 && !goingLeft)
            velX = Math.min(velX + 0.2f, 0f)

        // EASE INTO GRAVITY
        velY = if(!onGround) Math.min(if(velY == 0f) 1f else{ if(velY > 0) velY*1.2f else{velY+0.5f} }, MAX_VELOCITY)
        else 0f

        // ACCELERATE LEFT
        if(goingLeft && velX<=0)
            velX = Math.max(Math.min(-1f, velX*1.1f), -MAX_VELOCITY)
        // ACCELERATE RIGHT
        if(goingRight && velX>=0)
            velX = Math.min(Math.max(1f, velX*1.1f), MAX_VELOCITY)
    }

    override fun getBounds(): Shape {
        return java.awt.Rectangle(x.toInt(), y.toInt(), width.toInt(), height.toInt())
    }

    override fun onCollision(p0: GameObject?, p1: ObjectID?) {
        if(p1 == ObjectID.a) {
            // Check if collides with the ground by comparing the bottom 10px of the bounds to the object.
            if(p0!!.y > y+height-10) {
                if(velY>=0) {
                    y = p0.y - height + 1
                    onGround = true
                    doubleJump = false
                }
            } else if(p0.y + p0.height < y+10) {
                // Same thing here, but we have to reset the velocity to make it thump into the roof.
                y = p0.y + p0.height
                velY = 0f
            } else if(p0.x + p0.width < x+10) {
                x = p0.x + p0.width
            } else if(p0.x > x-10) {
                x = p0.x - width
            }
        }
    }

    override fun render(g : Graphics?) {
        // Draw player.
        g?.color = Color.WHITE
        g?.fillRect(x.toInt(), y.toInt(), 50, 50)
    }

    /*
     This set of functions could be vastly simplified, but this is a lot more readable.
      */

    fun left() {
        goingLeft = true
    }

    fun right() {
        goingRight = true
    }

    fun stopLeft() {
        goingLeft = false
    }

    fun stopRight() {
        goingRight = false
    }

    fun jump() {
        if(onGround) {
            onGround = false
            // Move the player up to avoid collision dragging the player back down.
            y-=3f
            velY = -10f
        } else if(!doubleJump) {
            doubleJump = true
            onGround = false
            y-=3f
            velY = -10f
        }
    }

}