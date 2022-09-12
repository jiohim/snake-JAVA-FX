package pl.jiohim.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    private Snake snake;
    private Apple apple;

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private int turnDelay;
    private  int score;
    private boolean isGameStopped;
    public static final String GAME_OVER = "GAME OVER";
    public static final String YOU_WIN = "YOU WIN";

    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        turnDelay = 300;
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        score=0;
        setScore(score);
        drawScene();
    }

    


    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int time) {
        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
            score+=5;
            setScore(score);
            turnDelay-=10;
            setTurnTimer(turnDelay);
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {


        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        }
        if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        }
        if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        }
        if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        }

        if (key == Key.SPACE && isGameStopped) {
           createGame();

        }

    }


    private void createNewApple() {
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }while(snake.checkCollision(apple));
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKORANGE, GAME_OVER, Color.BLACK, 75);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKORANGE, YOU_WIN, Color.BLACK, 75);

    }


}
