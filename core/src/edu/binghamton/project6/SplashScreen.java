package edu.binghamton.project6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class SplashScreen implements Screen {
    private final MyGame app;
    private SpriteBatch batch;
    private Texture splashImg;

    //used for loading assets and changing to menu screen
    private long startTime;
    private AssetManager assetManager;

    public SplashScreen(final MyGame app) {
        super();
        this.app = app;

        startTime = TimeUtils.millis();
        assetManager = new AssetManager();

        batch = new SpriteBatch();
        splashImg = new Texture("splash.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(splashImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        //driver to change to menu screen
        if (assetManager.update() && TimeUtils.timeSinceMillis(startTime) > 3000) {
            app.setScreen(new MenuScreen(app));
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        splashImg.dispose();
        assetManager.dispose();
        batch.dispose();
    }
}