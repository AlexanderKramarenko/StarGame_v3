package ru.alexander_kramarenko.math;

import com.badlogic.gdx.math.Vector2;

// Прямоугольник

public class Rect {

//    public final Vector2 pos = new Vector2(); // позиция по центру
    public final Vector2 centerPosition = new Vector2(); // позиция по центру
    protected float halfWidth; // половина ширины
    protected float halfHeight; // половина высоты

    public Rect(){

    }

    public Rect(Rect from){
        this(from.centerPosition.x, from.centerPosition.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public Rect(float x, float y, float halfWidth, float halfHeight){
        centerPosition.set(x,y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getLeft() { return centerPosition.x - halfWidth; }

    public float getTop() { return centerPosition.y + halfHeight; }

    public float getRight() { return centerPosition.x + halfWidth; }

    public float getBottom() { return centerPosition.y - halfHeight; }

    public float getHalfWidth() { return halfWidth; }

    public float getHalfHeight() { return halfHeight; }

    public float getWidth() { return halfWidth * 2f; }

    public float getHeight() { return halfHeight * 2f;}

    public void set(Rect from){
        centerPosition.set(from.centerPosition);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;
    }

    public void setLeft(float left) { centerPosition.x = left + halfWidth; }

    public void setTop(float top) { centerPosition.y = top - halfHeight; }

    public void setRight(float right) { centerPosition.x = right - halfWidth; }

    public void setBottom(float bottom) { centerPosition.y = bottom + halfHeight; }

    public void setWidth(float width) { this.halfWidth = width / 2f; }

    public void setHeight(float height) { this.halfHeight = height / 2f; }

    public void setSize(float width, float height){
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
    }

    public boolean isMe(Vector2 mouseClickTarget){
        return mouseClickTarget.x >= getLeft() && mouseClickTarget.x <= getRight() && mouseClickTarget.y >= getBottom() && mouseClickTarget.y <= getTop();
    }

    public boolean isOutside(Rect other){
        return getLeft() > other.getRight() || getRight() < other.getLeft() || getBottom() > other.getTop() || getTop() < other.getBottom();
    }

    @Override
    public String toString() {
        return "Rectangle: pos" + centerPosition + " size(" + getWidth() + ", " + getHeight() + ")";
    }
}
