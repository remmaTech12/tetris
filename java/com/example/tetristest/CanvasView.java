package com.example.tetristest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class CanvasView extends View {
    public static final int STATIONAL = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ROTATE = 4;

    public static final int maxxMap = 10;
    public static final int maxyMap = 10;

    private int motion;
    private int score = 0;
    private boolean gameOverFlag = false;
    private int[][] tetrisMap = new int[maxxMap][maxyMap];
    private Blocks movingBlock = new Blocks();

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        movingBlock.init(tetrisMap);
    }

    public int getScore () {
        return score;
    }

    public boolean getGameOverFlag () {
        return gameOverFlag;
    }

    public void showCanvas(int motion){
        invalidate();
        this.motion = motion;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(175, 180, 180, 180));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(50, 50, 1050, 1050, paint);

        // Set background color as white
        canvas.drawColor(Color.argb(125, 255, 255, 255));

        switch (motion) {
            case ROTATE:
                movingBlock.rotateBlock(tetrisMap);
                break;
            case DOWN:
                if (movingBlock.downBlock(tetrisMap) == false) {
                    checkTetrisMap();
                    Blocks newBlock = new Blocks();
                    if (newBlock.init(tetrisMap) != Blocks.blockGenerateError) {
                        movingBlock = newBlock;
                    } else {
                        gameOverFlag = true;
                    }
                }
                break;
            case LEFT:
                movingBlock.leftBlock(tetrisMap);
                break;
            case RIGHT:
                movingBlock.rightBlock(tetrisMap);
                break;
            default:
                break;
        }

        printTetrisMap(canvas);
    }

    private void printTetrisMap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        for (int i=0; i<maxxMap; i++) {
            for (int j=0; j<maxyMap; j++) {
                switch (tetrisMap[i][j]) {
                    case Blocks.tBlock:
                        paint.setColor(Color.argb(250, 0, 0, 255));
                        break;
                    case Blocks.sBlock:
                        paint.setColor(Color.argb(250, 0, 255, 255));
                        break;
                    case Blocks.iBlock:
                        paint.setColor(Color.argb(250, 255, 0, 0));
                        break;
                    case Blocks.oBlock:
                        paint.setColor(Color.argb(250, 0, 255, 0));
                        break;
                    case Blocks.lBlock:
                        paint.setColor(Color.argb(250, 255, 0, 255));
                        break;
                    default:

                }
                if (Blocks.isBlock(tetrisMap[i][j]) == true) {
                    canvas.drawCircle(100 * i + 100, 100 * j + 100, 50, paint);
                }
            }
        }
    }

    private void checkTetrisMap() {
        // Check if there is deletable lines.
        for (int j=0; j<maxyMap; j++) {
            for (int i=0; i<maxxMap; i++) {
                if (tetrisMap[i][j] == 0) {
                    break;
                } else if (i==maxxMap-1 && tetrisMap[i][j] != 0) {
                    cleanBlocks(j);
                }
            }
        }
    }

    private void cleanBlocks(int row) {
        // Delete blocks aligned in one line.
        for (int i=0; i<maxxMap; i++) {
            tetrisMap[i][row] = 0;
        }

        // Replace rows above the deleted row.
        for (int j=row; j>0; j--) {
            for (int i=0; i<maxxMap; i++) {
                int tmp = 0;
                tmp = tetrisMap[i][j - 1];
                tetrisMap[i][j] = tmp;
            }
        }
        for (int i=0; i<maxxMap; i++) {
            tetrisMap[i][0] = 0;
        }
        score += 100;
    }
}