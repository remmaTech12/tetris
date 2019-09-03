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

    public int init() {
        Random r = new Random();
        blockNum = r.nextInt(5) + 1;
        switch (blockNum) {
            case 1: // t block
                xPos = new int[]{5, 5, 6, 5};
                yPos = new int[]{0, 1, 1, 2};
                break;
            case 2: // s block
                xPos = new int[]{5, 5, 6, 6};
                yPos = new int[]{0, 1, 1, 2};
                break;
            case 3: // i block
                xPos = new int[]{5, 5, 5, 5};
                yPos = new int[]{0, 1, 2, 3};
                break;
            case 4: // o block
                xPos = new int[]{5, 5, 6, 6};
                yPos = new int[]{0, 1, 0, 1};
                break;
            case 5: // l block
                xPos = new int[]{5, 5, 5, 6};
                yPos = new int[]{0, 1, 2, 2};
            default:
                break;
        }

        return blockNum;
    }

    public int getxPos(int i) {
        if (i < 0 || i > pieceNum + 1) {
            Log.d("debug", "An undefined piece is selected.");
            return 0;
        }
        return xPos[i];
    }

    public int getyPos(int i) {
        if (i < 0 || i > pieceNum + 1) {
            Log.d("debug", "An undefined piece is selected.");
            return 0;
        }
        return yPos[i];
    }

    public boolean isThereMyBlockBelow(int i) {
        for (int j = 0; j < pieceNum; j++) {
            if (i != j && xPos[j] == xPos[i] && yPos[j] == yPos[i] + 1) {
                Log.d("debug", "thats true");
                return true;
            }
        }

        return false;
    }

    public int getLowestBlock() {
        int element = 0;

        for (int i = 0; i < pieceNum; i++) {
            if (yPos[element] < yPos[i]) {
                element = i;
            }
        }

        return element;
    }

    public void rotateBlock(int[][] tetrisMap) {
        int[] orgxPos = new int[pieceNum];
        int[] orgyPos = new int[pieceNum];
        System.arraycopy(xPos, 0, orgxPos, 0, pieceNum);
        System.arraycopy(yPos, 0, orgyPos, 0, pieceNum);

        for (int i=0; i<pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = 0;
        }

        // Rotate block by matrix calculation
        for (int i = 1; i < pieceNum; i++) {
            xPos[i] = orgxPos[0] - (orgyPos[i] - orgyPos[0]);
            yPos[i] = orgyPos[0] + (orgxPos[i] - orgxPos[0]);
        }

        if (isAcceptableMotion(tetrisMap, xPos, yPos) == false) {
            System.arraycopy(orgxPos, 0, xPos, 0, pieceNum);
            System.arraycopy(orgyPos, 0, yPos, 0, pieceNum);
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[orgxPos[i]][orgyPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = blockNum;
        }
    }

    public void downBlock(int[][] tetrisMap) {
        int[] orgxPos = new int[pieceNum];
        int[] orgyPos = new int[pieceNum];
        System.arraycopy(xPos, 0, orgxPos, 0, pieceNum);
        System.arraycopy(yPos, 0, orgyPos, 0, pieceNum);

        for (int i=0; i<pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            yPos[i] = yPos[i] + 1;
        }

        if (isAcceptableMotion(tetrisMap, xPos, yPos) == false) {
            System.arraycopy(orgxPos, 0, xPos, 0, pieceNum);
            System.arraycopy(orgyPos, 0, yPos, 0, pieceNum);
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[orgxPos[i]][orgyPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = blockNum;
        }
    }

    public void leftBlock(int[][] tetrisMap) {
        int[] orgxPos = new int[pieceNum];
        int[] orgyPos = new int[pieceNum];
        System.arraycopy(xPos, 0, orgxPos, 0, pieceNum);
        System.arraycopy(yPos, 0, orgyPos, 0, pieceNum);

        for (int i=0; i<pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            xPos[i] = xPos[i] - 1;
        }

        if (isAcceptableMotion(tetrisMap, xPos, yPos) == false) {
            System.arraycopy(orgxPos, 0, xPos, 0, pieceNum);
            System.arraycopy(orgyPos, 0, yPos, 0, pieceNum);
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[orgxPos[i]][orgyPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = blockNum;
        }
    }

    public void rightBlock(int[][] tetrisMap) {
        int[] orgxPos = new int[pieceNum];
        int[] orgyPos = new int[pieceNum];
        System.arraycopy(xPos, 0, orgxPos, 0, pieceNum);
        System.arraycopy(yPos, 0, orgyPos, 0, pieceNum);

        for (int i=0; i<pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            xPos[i] = xPos[i] + 1;
        }

        if (isAcceptableMotion(tetrisMap, xPos, yPos) == false) {
            System.arraycopy(orgxPos, 0, xPos, 0, pieceNum);
            System.arraycopy(orgyPos, 0, yPos, 0, pieceNum);
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[orgxPos[i]][orgyPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = blockNum;
        }
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
}