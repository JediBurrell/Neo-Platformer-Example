package objects

import io.qwerty.neo.framework.ObjectID
import io.qwerty.neo.objects.GameObject
import java.awt.Color
import java.awt.Graphics
import java.awt.Shape

// Everything here is self-explanatory, no comments for you.
class Surface(x : Float, y : Float, width : Float, height : Float) : GameObject(x, y, ObjectID.a) {

    init {
        this.width = width
        this.height = height
    }

    override fun getBounds(): Shape {
        return java.awt.Rectangle(x.toInt(), y.toInt(), width.toInt(), height.toInt())
    }

    override fun onCollision(p0: GameObject?, p1: ObjectID?) {
    }

    override fun render(g : Graphics?) {
        g?.color = Color.WHITE
        g?.fillRect(x.toInt(), y.toInt(), width.toInt(), height.toInt())
    }

}