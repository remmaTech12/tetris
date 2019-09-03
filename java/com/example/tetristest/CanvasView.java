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
    //private Paint paint;
    private int[][] tetrisMap = new int[maxxMap][maxyMap];
    private Blocks movingBlock = new Blocks();
    private boolean switchNextBlock = false;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //paint = new Paint();
        movingBlock.init();
    }

    public void showCanvas(int motion){
        invalidate();
        this.motion = motion;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(100, 150, 150, 0));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(50, 50, 1050, 1050, paint);
        // Set background color as black
        canvas.drawColor(Color.argb(125, 255, 255, 255));

        checkTetrisMap();

        switch (motion) {
            case ROTATE:
                movingBlock.rotateBlock(tetrisMap);
                break;
            case DOWN:
                if (movingBlock.downBlock(tetrisMap) == false && isTopRowVacant() == true) {
                    Blocks newBlock = new Blocks();
                    newBlock.init();
                    movingBlock = newBlock;
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
                    case 1:
                        paint.setColor(Color.argb(150, 0, 0, 255));
                        canvas.drawCircle(100 * i + 100, 100 * j + 100, 50, paint);
                        break;
                    case 2:
                        paint.setColor(Color.argb(150, 0, 255, 0));
                        canvas.drawCircle(100 * i + 100, 100 * j + 100, 50, paint);
                        break;
                    case 3:
                        paint.setColor(Color.argb(150, 255, 0, 0));
                        canvas.drawCircle(100 * i + 100, 100 * j + 100, 50, paint);
                        break;
                    case 4:
                        paint.setColor(Color.argb(150, 0, 255, 255));
                        canvas.drawCircle(100 * i + 100, 100 * j + 100, 50, paint);
                        break;
                    case 5:
                        paint.setColor(Color.argb(150, 255, 0, 255));
                        canvas.drawCircle(100 * i + 100, 100 * j + 100, 50, paint);
                        break;
                    default:

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

    private boolean isTopRowVacant() {
        // Check if there is deletable lines.
        for (int i = 0; i < maxxMap; i++) {
            if (tetrisMap[i][0] > 0) {
                return false;
            }
        }
        return true;
    }

    private void cleanBlocks(int row) {
        // Delete a row whose blocks are aligned in one line.
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
    }
}