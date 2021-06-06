package ru.alexander_kramarenko.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    protected final List<T> aciveObjects = new ArrayList<>();
    protected final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()){
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() -1);
        }
        aciveObjects.add(object);
        System.out.println(this.getClass().getName() + " active/free : " + aciveObjects.size() + "/" + freeObjects.size());
        return object;
    }

    public void updateActiveSprites(float delta){
        for (Sprite sprite : aciveObjects){
            if (!sprite.isDestroyed()){
                sprite.update(delta);
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch){
        for (Sprite sprite : aciveObjects){
            if (!sprite.isDestroyed()){
                sprite.draw(batch);
            }
        }
    }

    public void freeAllDestroyed(){
        for (int i=0; i<aciveObjects.size();i++){
            T sprite = aciveObjects.get(i);
            if (sprite.isDestroyed()){
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    public void dispose(){
        aciveObjects.clear();
        freeObjects.clear();
    }

    public List<T> getAciveObjects() {
        return aciveObjects;
    }

    private void free (T object){

        if (aciveObjects.remove(object)){
            freeObjects.add(object);
        }
    }


}
