package com.example.tetristest;

import java.util.Random;

public class Blocks {
    public static final int pieceNum = 4;
    public static final int blockGenerateError = -1;
    public static final int tBlock = 1;
    public static final int sBlock = 2;
    public static final int iBlock = 3;
    public static final int oBlock = 4;
    public static final int lBlock = 5;
    public static final int jBlock = 6;
    public static final int zBlock = 7;

    private int blockNum;
    private int[] xPos = new int[4];
    private int[] yPos = new int[4];
    private int[] orgxPos = new int[pieceNum];
    private int[] orgyPos = new int[pieceNum];

    public static boolean isBlock(int value) {
        if (value == tBlock || value == sBlock || value == iBlock || value == oBlock || value == lBlock || value == jBlock || value == zBlock) {
            return true;
        }
        return false;
    }

    public int init(int[][] tetrisMap) {
        Random r = new Random();
        blockNum = r.nextInt(7) + 1;
        switch (blockNum) {
            case tBlock: // t block
                xPos = new int[]{3, 4, 4, 5};
                yPos = new int[]{0, 0, 1, 0};
                break;
            case sBlock: // s block
                xPos = new int[]{3, 4, 4, 5};
                yPos = new int[]{1, 1, 0, 0};
                break;
            case iBlock: // i block
                xPos = new int[]{3, 4, 5, 6};
                yPos = new int[]{0, 0, 0, 0};
                break;
            case oBlock: // o block
                xPos = new int[]{3, 4, 3, 4};
                yPos = new int[]{0, 0, 1, 1};
                break;
            case lBlock: // l block
                xPos = new int[]{3, 3, 4, 5};
                yPos = new int[]{0, 1, 0, 0};
                break;
            case jBlock: // j block
                xPos = new int[]{3, 3, 4, 5};
                yPos = new int[]{0, 1, 1, 1};
                break;
            case zBlock: // z block
                xPos = new int[]{3, 4, 4, 5};
                yPos = new int[]{0, 0, 1, 1};
                break;
            default:
                break;
        }

        if (isThereNoBlocks(tetrisMap, xPos, yPos) == true){
            for (int i = 0; i < pieceNum; i++) {
                tetrisMap[xPos[i]][yPos[i]] = blockNum;
            }
        } else {
            return blockGenerateError;
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

    public boolean isThereNoBlocks(int[][] tetrisMap, int[] newxPos, int[] newyPos) {
        for (int i=0; i < pieceNum; i++) {
            if (tetrisMap[newxPos[i]][newyPos[i]] > 0) {
                return false;
            }
        }

        return true;
    }
}