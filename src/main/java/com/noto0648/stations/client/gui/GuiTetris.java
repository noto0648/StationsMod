package com.noto0648.stations.client.gui;

import org.lwjgl.input.Keyboard;

import java.util.Random;

/**
 * Created by Noto on 14/08/23.
 */
public class GuiTetris extends GuiScreenBase
{
    private final int BLOCK_SIZE = 8;
    private final int BOARD_WIDTH = 12;
    private final int BOARD_HEIGHT = 22;
    private final Random rnd = new Random();

    private int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];

    private static int[] colors = new int[]{0xFF0A0A0A, 0xFFC0C0C0, 0xFFFF0000, 0xFFFFFF00, 0xFFFF00FF, 0xFF00FF00, 0xFF0000FF, 0xFFFF8000, 0xFF00FFFF};

    private static int[][][] blocks = new int[][][]{{{-1, 0}, {1, 0}, {2, 0}}, {{-1, -1}, {-1, 0}, {0, -1}}, {{-1, 0}, {0, -1}, {1, -1}}, {{-1, -1}, {0, -1}, {1, 0}}, {{-1, 0}, {1, 0}, {1, 1}}, {{-1, 0}, {-1, 1}, {1, 0}}, {{-1, 0}, {0, -1}, {1, 0}} };
    private static int[] blockMaxRotate = new int[]{2, 1, 2, 2, 4, 4, 4};

    private int currentBlockId;
    private int currentBlockX, currentBlockY;
    private int currentBlockRotate;
    private int downerCounter;

    private int gameScene = 1;

    public GuiTetris()
    {
        for(int i = 0; i < BOARD_WIDTH; i++)
            board[BOARD_HEIGHT - 1][i] = 1;
        for(int i = 0; i < BOARD_HEIGHT; i++)
        {
            board[i][0] = 1;
            board[i][BOARD_WIDTH - 1] = 1;
        }

        newBlock();
    }

    @Override
    protected void paint(int mouseX, int mouseY)
    {
        int l = (width - BLOCK_SIZE * BOARD_WIDTH) / 2;
        int k = (height - BLOCK_SIZE * BOARD_HEIGHT) / 2;

        for(int y = 0; y < BOARD_HEIGHT; y++)
        {
            for(int x = 0; x < BOARD_WIDTH; x++)
            {
                drawRect(x * BLOCK_SIZE + l, y * BLOCK_SIZE + k, x * BLOCK_SIZE + l + BLOCK_SIZE - 1, y * BLOCK_SIZE + k + BLOCK_SIZE - 1, colors[board[y][x]]);
            }
        }

        if(gameScene == 2)
        {
            getFontRenderer().drawStringWithShadow("GAME OVER", width / 2 - (getFontRenderer().getStringWidth("GAME OVER") / 2), height / 2 - 8, 0xFFFFFF);
        }
    }

    @Override
    protected void resize()
    {

    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        if(gameScene != 1)
            return;

        downerCounter++;
        if(downerCounter == 10)
        {
            downerCounter = 0;
            downBlock();
        }
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        super.keyTyped(p_73869_1_, p_73869_2_);

        if(gameScene == 1)
        {
            if(p_73869_2_ == Keyboard.KEY_UP)
            {
                putBlock(currentBlockX, currentBlockY, currentBlockId, -2, currentBlockRotate);
                for(int i = 0; i < 4;i++)
                {
                    currentBlockRotate ++;
                    currentBlockRotate %= blockMaxRotate[currentBlockId];
                    if(putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate, false))
                    {
                        putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate);
                        break;
                    }
                }
            }
            if(p_73869_2_ == Keyboard.KEY_DOWN)
            {
                downBlock();
            }
            if(p_73869_2_ == Keyboard.KEY_RIGHT)
            {
                putBlock(currentBlockX, currentBlockY, currentBlockId, -2, currentBlockRotate);
                currentBlockX++;
                if(putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate, false))
                {
                    putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate);

                }
                else
                {
                    currentBlockX--;
                    putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate);
                }
            }
            if(p_73869_2_ == Keyboard.KEY_LEFT)
            {
                putBlock(currentBlockX, currentBlockY, currentBlockId, -2, currentBlockRotate);
                currentBlockX--;
                if(putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate, false))
                {
                    putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate);

                }
                else
                {
                    currentBlockX++;
                    putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate);
                }
            }
        }
    }

    private void gameOver()
    {
        gameScene = 2;
        for(int y = 0; y < BOARD_HEIGHT; y++)
        {
            for(int x = 0; x < BOARD_WIDTH; x++)
            {
                if(board[y][x] != 0)
                    board[y][x] = 1;
            }
        }

    }

    private boolean downBlock()
    {
        putBlock(currentBlockX, currentBlockY, currentBlockId, -2, currentBlockRotate);
        currentBlockY++;
        if(putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate, false))
        {
            putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate);
            return true;
        }
        else
        {
            currentBlockY--;
            putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate);


            for(int y = BOARD_HEIGHT - 2; y > 0; y--)
            {
                boolean all = true;

                for(int x = 1; x < BOARD_WIDTH - 1; x++)
                {
                    if(board[y][x] == 0)
                    {
                        all = false;
                        break;
                    }
                }

                if(all)
                {
                    for(int _y = y; _y > 1; _y--)
                    {
                        for(int i = 1; i < BOARD_WIDTH - 1; i++)
                            board[_y][i] = board[_y - 1][i];
                    }
                    for(int i = 1; i < BOARD_WIDTH - 1; i++)
                        board[1][i] = 0;
                    y++;
                }
            }

            if(!newBlock())
            {
                gameOver();
                return false;
            }
            return false;
        }
    }

    private boolean newBlock()
    {
        currentBlockId = rnd.nextInt(blocks.length);
        currentBlockX = 5;
        currentBlockY = 2;
        currentBlockRotate = 0;

        if(putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate, false))
        {
            putBlock(currentBlockX, currentBlockY, currentBlockId, currentBlockRotate);
            return true;
        }

        return false;
    }

    private boolean putBlock(int x, int y, int blockId, int putBlock, int rotate)
    {
        return putBlock(x, y, blockId, putBlock, rotate, true);
    }

    private boolean putBlock(int x, int y, int blockId, int putBlock, int rotate, boolean put)
    {
        if(putBlock != -2 && board[y][x] != 0) return false;

        if(put)
            board[y][x] = putBlock + 2;

        for(int i = 0; i < blocks[blockId].length; i++)
        {
            int _x = blocks[blockId][i][0];
            int _y = blocks[blockId][i][1];

            for(int j = 0; j < rotate; j++)
            {
                int x2 = _x;
                _x = -_y;
                _y = x2;
            }

            if(putBlock != -2 && board[_y + y][_x + x] != 0) return false;



            if(put)
                board[_y + y][_x + x] = putBlock + 2;
        }

        return true;
    }

    private boolean putBlock(int x, int y, int blockId, int rotate)
    {
        return putBlock(x, y, blockId, blockId, rotate, true);
    }

    private boolean putBlock(int x, int y, int blockId, int rotate, boolean put)
    {
        return putBlock(x, y, blockId, blockId, rotate, put);
    }
}
