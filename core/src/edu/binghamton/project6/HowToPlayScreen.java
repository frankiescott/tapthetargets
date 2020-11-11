package edu.binghamton.project6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HowToPlayScreen implements Screen {
    private final MyGame app;
    private SpriteBatch batch;
    private Texture splashImg;

    private Stage stage;
    private BitmapFont titleFont, textFont;
    private TextButton back;

    String howtoplay = "Tap on the targets as they appear!\n\n" +
            "Destroy target\n50 pts\n\n" +
            "Destroy two or more targets with one tap\n125 pts\n\n" +
            "Receive bonus points for streaks! Missed taps will cost you points!";

    public HowToPlayScreen(final MyGame app) {
        super();
        this.app = app;
        batch = new SpriteBatch();
        splashImg = new Texture("bg.png");
        titleFont = new BitmapFont();
        textFont = new BitmapFont();
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        //back button
        configureButton();

        //initialize fonts for the screen
        FreeTypeFontGenerator title = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BRUSHSCI.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter titleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParameter.size = 148;
        titleFont = title.generateFont(titleParameter);

        FreeTypeFontGenerator text = new FreeTypeFontGenerator(Gdx.files.internal("fonts/consola.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter textParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        textParameter.size = 50;
        textFont = text.generateFont(textParameter);
    }

    public void configureButton() {
        back = new TextButton("Back", app.skin,"small");
        back.getLabel().setFontScale(3.0f);
        back.setSize(app.col_width*4,app.row_height*1.5F);
        back.setPosition(app.col_width*4,app.row_height);
        back.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                app.setScreen(new MenuScreen(app));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(back);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(splashImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        titleFont.draw(batch, "How to Play",app.col_width*4,Gdx.graphics.getHeight()-50);
        textFont.draw(batch, howtoplay,0,Gdx.graphics.getHeight()-app.row_height*2.5F, Gdx.graphics.getWidth(), 1, true);
        batch.end();

        stage.act();
        stage.draw();
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
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        batch.dispose();
        titleFont.dispose();
        splashImg.dispose();
        textFont.dispose();
        stage.dispose();
    }
}
