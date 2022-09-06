package com.javarush.games.snake;

import java.util.ArrayList;
import java.util.List;

import com.javarush.engine.cell.*;

public class Snake extends GameObject {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;


    public Snake(int x, int y) {
        super(x, y);

        GameObject first = new GameObject(x, y);
        GameObject second = new GameObject(x + 1, y);
        GameObject third = new GameObject(x + 2, y);

        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }

    public void draw(Game game) {

        Color color = isAlive ? Color.BLACK : Color.RED;

        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            game.setCellValueEx(part.x, part.y, Color.NONE, sign, color, 75);
        }
    }

    public void move(Apple apple) {

        GameObject headnew = createNewHead();

        if (headnew.x >= SnakeGame.WIDTH
                || headnew.y >= SnakeGame.HEIGHT
                || headnew.x < 0
                || headnew.y < 0) {
            isAlive = false;
            return;
        }
        if(checkCollision(headnew)){
            isAlive = false;
        }
        else {
            snakeParts.add(0, headnew);

            if (headnew.x == apple.x && headnew.y == apple.y) {
                apple.isAlive = false;
            } else {
                removeTail();
            }
        }

    }


    public GameObject createNewHead() {
        GameObject oldHead = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            return new GameObject(oldHead.x - 1, oldHead.y);
        } else if (direction == Direction.RIGHT) {
            return new GameObject(oldHead.x + 1, oldHead.y);
        } else if (direction == Direction.UP) {
            return new GameObject(oldHead.x, oldHead.y - 1);
        } else {
            return new GameObject(oldHead.x, oldHead.y + 1);
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }


    public void setDirection(Direction direction) {
        if (this.direction == Direction.LEFT) {
            if (snakeParts.get(0).x != snakeParts.get(1).x) {
                if (direction != Direction.RIGHT) {
                    this.direction = direction;
                }
            }
        }
        if (this.direction == Direction.RIGHT) {
            if (snakeParts.get(0).x != snakeParts.get(1).x) {
                if (direction != Direction.LEFT) {
                    this.direction = direction;
                }
            }
        }

        if (this.direction == Direction.UP) {
            if (snakeParts.get(0).y != snakeParts.get(1).y) {
                if (direction != Direction.DOWN) {
                    this.direction = direction;
                }
            }
        }

        if (this.direction == Direction.DOWN) {
            if (snakeParts.get(0).y != snakeParts.get(1).y) {
                if (direction != Direction.UP) {
                    this.direction = direction;
                }
            }
        }
    }

    public boolean checkCollision(GameObject snakeP) {


        for (GameObject partT : snakeParts) {
            if (partT.x == snakeP.x && partT.y == snakeP.y) {
                return true;
            }
        }
        return false;
    }
    public int getLength(){
        return snakeParts.size();
    }

}




