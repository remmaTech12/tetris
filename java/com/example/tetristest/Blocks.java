package com.example.tetristest;

import java.util.Arrays;
import java.util.List;
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
    private static List<Integer> blockArray = Arrays.asList(tBlock, sBlock, iBlock, oBlock, lBlock, jBlock, zBlock);

    public static boolean isBlock(int value) {
        if (blockArray.contains(value)) {
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

        if (canPlaceBlock(tetrisMap, xPos, yPos) == true){
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

        // Delete previous blocks from map.
        for (int i=0; i<pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = 0;
        }
    }

    private boolean postMotion(int[][] tetrisMap) {
        boolean isAcceptMotion = isAcceptableMotion(tetrisMap, xPos, yPos);
        if (isAcceptMotion == false) {
            System.arraycopy(orgxPos, 0, xPos, 0, pieceNum);
            System.arraycopy(orgyPos, 0, yPos, 0, pieceNum);
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[orgxPos[i]][orgyPos[i]] = 0;
        }

        for (int i = 0; i < pieceNum; i++) {
            tetrisMap[xPos[i]][yPos[i]] = blockNum;
        }

        return isAcceptMotion;
    }

    public void rotateBlock(int[][] tetrisMap) {
        preMotion(tetrisMap);

        for (int i = 1; i < pieceNum; i++) {
            xPos[i] = orgxPos[0] - (orgyPos[i] - orgyPos[0]);
            yPos[i] = orgyPos[0] + (orgxPos[i] - orgxPos[0]);
        }

        postMotion(tetrisMap);
    }

    public boolean downBlock(int[][] tetrisMap) {
        preMotion(tetrisMap);

        for (int i = 0; i < pieceNum; i++) {
            yPos[i] = yPos[i] + 1;
        }

        return postMotion(tetrisMap);
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

        for (int i = 0; i < pieceNum; i++) {
            // x postion is unacceptable
            if (movedxPos[i] < 0 || movedxPos[i] > CanvasView.maxxMap - 1) {
                return false;
            }

            // y position is unacceptable
            if (movedyPos[i] < 0 || movedyPos[i] > CanvasView.maxyMap - 1) {
                return false;
            }

            // If there is another block after motion
            if (tetrisMap[movedxPos[i]][movedyPos[i]] > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean canPlaceBlock(int[][] tetrisMap, int[] newxPos, int[] newyPos) {
        for (int i=0; i < pieceNum; i++) {
            if (tetrisMap[newxPos[i]][newyPos[i]] > 0) {
                return false;
            }
        }

        return true;
    }
}