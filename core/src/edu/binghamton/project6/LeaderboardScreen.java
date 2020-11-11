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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LeaderboardScreen implements Screen {
    private final MyGame app;
    private SpriteBatch batch;
    private Texture splashImg;
    private Stage stage;
    private TextButton back;
    private BitmapFont titleFont;

    private Table container;

    public LeaderboardScreen(final MyGame app) {
        super();
        this.app = app;

        batch = new SpriteBatch();
        splashImg = new Texture("bg.png");

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        configureButton();
        configureList();

        FreeTypeFontGenerator title = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BRUSHSCI.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter titleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParameter.size = 148;
        titleFont = title.generateFont(titleParameter);
    }

    public void configureList() {
        container = new Table();
        //turn on and off for guidelines for positioning help
        //container.setDebug(true);
        stage.addActor(container);
        container.setFillParent(true);
        container.padBottom(app.row_height/2);
        Table table = new Table();
        final ScrollPane scroll = new ScrollPane(table, app.skin);
        scroll.setScrollingDisabled(true,false);

        FreeTypeFontGenerator font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/consola.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;
        BitmapFont entryFont = font.generateFont(fontParameter);

        //top 10 scores are stored by their position on the leaderboard ex: #5 spot is accessed with the key "5"
        for (int i = 1; i <= 10; ++i) {
            table.row();
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = entryFont;
            Label entry = new Label(i + ". " + app.prefs.getString(Integer.toString(i)), style);
            entry.setAlignment(Align.center);
            entry.setWrap(true);
            table.add(entry).width(Gdx.graphics.getWidth());
        }
        container.add(scroll).height(app.row_height*6.5F).expandX().fillX();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(splashImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        titleFont.draw(batch, "Leaderboard",app.col_width*4,Gdx.graphics.getHeight()-50);
        batch.end();

        stage.act();
        stage.draw();
    }

    public void configureButton() {
        back = new TextButton("Back", app.skin, "small");
        back.getLabel().setFontScale(3.0f);
        back.setSize(app.col_width * 4, app.row_height * 1.5F);
        back.setPosition(app.col_width * 4, app.row_height);
        back.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                app.setScreen(new MenuScreen(app));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(back);
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
        splashImg.dispose();
        titleFont.dispose();
        batch.dispose();
        stage.dispose();
    }
}