package com.example.tetristest;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Blocks {
    public static final int pieceNum = 4;

    private int blockNum;
    private int[] xPos = new int[4];
    private int[] yPos = new int[4];
    int[] orgxPos = new int[pieceNum];
    int[] orgyPos = new int[pieceNum];

    public int init(int[][] tetrisMap) {
        Random r = new Random();
        blockNum = r.nextInt(5) + 1;
        switch (blockNum) {
            case 1: // t block
                xPos = new int[]{3, 4, 4, 5};
                yPos = new int[]{0, 0, 1, 0};
                break;
            case 2: // s block
                xPos = new int[]{3, 4, 4, 5};
                yPos = new int[]{1, 1, 0, 0};
                break;
            case 3: // i block
                xPos = new int[]{3, 4, 5, 6};
                yPos = new int[]{0, 0, 0, 0};
                break;
            case 4: // o block
                xPos = new int[]{3, 4, 3, 4};
                yPos = new int[]{0, 0, 1, 1};
                break;
            case 5: // l block
                xPos = new int[]{3, 3, 4, 5};
                yPos = new int[]{0, 1, 0, 0};
            default:
                break;
        }

        if (isDuplicateBlocks(tetrisMap, xPos, yPos) == true){
            for (int i = 0; i < pieceNum; i++) {
                tetrisMap[xPos[i]][yPos[i]] = blockNum;
            }
        } else {
            return -1;
        }
        return blockNum;
    }

    private void preMotion(int[][] tetrisMap) {
        System.arraycopy(xPos, 0, orgxPos, 0, pieceNum);
        System.arraycopy(yPos, 0, orgyPos, 0, pieceNum);

        for (int i=0; i<pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = 0;
        }
    }

    private boolean postMotion(int[][] tetrisMap) {
        boolean retVal = true;
        if (isAcceptableMotion(tetrisMap, xPos, yPos) == false) {
            System.arraycopy(orgxPos, 0, xPos, 0, pieceNum);
            System.arraycopy(orgyPos, 0, yPos, 0, pieceNum);
            retVal = false;
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[orgxPos[i]][orgyPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = blockNum;
        }

        return retVal;
    }

    public void rotateBlock(int[][] tetrisMap) {
        preMotion(tetrisMap);

        // Rotate block by matrix calculation
        for (int i = 1; i < pieceNum; i++) {
            xPos[i] = orgxPos[0] - (orgyPos[i] - orgyPos[0]);
            yPos[i] = orgyPos[0] + (orgxPos[i] - orgxPos[0]);
        }

        postMotion(tetrisMap);
    }

    public boolean downBlock(int[][] tetrisMap) {
        boolean retVal = true;
        preMotion(tetrisMap);

        for (int i = 0; i < pieceNum; i++) {
            yPos[i] = yPos[i] + 1;
        }

        retVal = postMotion(tetrisMap);
        return retVal;
    }

    public void leftBlock(int[][] tetrisMap) {
        preMotion(tetrisMap);

        for (int i = 0; i < pieceNum; i++) {
            xPos[i] = xPos[i] - 1;
        }

        postMotion(tetrisMap);
    }

    public void rightBlock(int[][] tetrisMap) {
        preMotion(tetrisMap);

        for (int i = 0; i < pieceNum; i++) {
            xPos[i] = xPos[i] + 1;
        }

        postMotion(tetrisMap);
    }

    public boolean isAcceptableMotion(int[][] tetrisMap, int[] movedxPos, int[] movedyPos) {
        // x postion is unacceptable
        for (int i = 0; i < pieceNum; i++) {
            if (movedxPos[i] < 0 || movedxPos[i] > CanvasView.maxxMap - 1) {
                return false;
            }
        }

        // y position is unacceptable
        for (int j = 0; j < pieceNum; j++) {
            if (movedyPos[j] < 0 || movedyPos[j] > CanvasView.maxyMap - 1) {
                return false;
            }
        }

        // If there is another block after motion
        for (int i=0; i < pieceNum; i++) {
            if (tetrisMap[movedxPos[i]][movedyPos[i]] > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isDuplicateBlocks(int[][] tetrisMap, int[] newxPos, int[] newyPos) {
        for (int i=0; i < pieceNum; i++) {
            if (tetrisMap[newxPos[i]][newyPos[i]] > 0) {
                return false;
            }
        }

        return true;
    }
}