package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

/**
 * Created by Noto on 14/08/16.
 */
public class ControlTextBox extends Control
{
    private String text = "";
    private int cursorCounter;

    private int cursorPosition;

    private int selectStart = -1;
    private int selectEnd = -1;

    public ControlTextBox(IGui gui)
    {
        super(gui);
    }

    public ControlTextBox(IGui gui, int x, int y, int w, int h)
    {
        super(gui);
        locationX = x;
        locationY = y;
        width = w;
        height = h;
        text = "Oreshura";
    }

    @Override
    public void update()
    {
        if(isFocus)
        {
            cursorCounter++;
            if(cursorCounter == 20) cursorCounter = 0;
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        drawRect(locationX, locationY, locationX + width, locationY + height, 0xFF000000);
        drawRect(locationX, locationY, locationX + width, locationY + 1, 0xFFC0C0C0);
        drawRect(locationX, locationY, locationX + 1, locationY + height, 0xFFC0C0C0);
        drawRect(locationX, locationY + height - 1, locationX + width, locationY + height, 0xFFC0C0C0);
        drawRect(locationX + width - 1, locationY, locationX + width, locationY + height, 0xFFC0C0C0);

        if(selectStart != -1 || selectEnd != -1)
        {
            int txtWidth1 = getFont().getStringWidth(text.substring(0, selectStart));
            int txtWidth = getFont().getStringWidth(text.substring(selectStart, selectEnd));
            drawRect(locationX + 5 + txtWidth1, locationY + 5, locationX + 5 + txtWidth1 + txtWidth, locationY + 16, 0xFF0094FF);
        }

        getFont().drawStringWithShadow(text, locationX + 5, locationY + 6, 0xE0E0E0);
        if(isFocus && cursorCounter < 10)
        {
            int txtWidth = getFont().getStringWidth(text.substring(0, cursorPosition));
            drawRect(locationX + 5 + txtWidth, locationY + 5, locationX + 6 + txtWidth, locationY + 16, 0xFFE0E0E0);
        }
    }

    @Override
    public void keyTyped(char par1, int par2)
    {
        if(isFocus)
        {
            if(par1 == '\b')
            {
                if(selectStart != -1 && selectEnd != -1)
                {
                    if(selectStart == 0 && selectEnd == text.length())
                    {
                        text = "";
                    }
                    else
                    {
                        text = text.substring(0, selectStart) + text.substring(selectEnd, text.length());
                    }
                    cursorPosition = selectStart;
                    selectEnd = selectStart = -1;
                }
                else if(text.length() == 1 || text.length() == 0)
                {
                    text = "";
                    cursorPosition--;
                }
                else if(cursorPosition == text.length())
                {
                    text = (text.substring(0, text.length() - 1));
                    cursorPosition--;
                }
                else if(cursorPosition == 0)
                {

                }
                else
                {
                    text = (text.substring(0, cursorPosition - 1) + text.substring(cursorPosition, text.length()));
                    cursorPosition--;
                }

                cursorPositionCheck();
            }
            else if(par2 == Keyboard.KEY_DELETE)
            {
                if(cursorPosition == text.length())
                {

                }
                else
                {
                    text = (text.substring(0, cursorPosition) + text.substring(cursorPosition + 1, text.length()));
                }
                cursorPositionCheck();
            }
            else if(par2 == Keyboard.KEY_RIGHT || par2 == Keyboard.KEY_LEFT || par2 == Keyboard.KEY_UP || par2 == Keyboard.KEY_DOWN || par2 == Keyboard.KEY_HOME || par2 == Keyboard.KEY_END)
            {
                if(GuiScreen.isShiftKeyDown())
                {
                    if(par2 == Keyboard.KEY_RIGHT)
                    {
                        if(selectStart == -1)
                            selectStart = cursorPosition;

                        if(selectEnd == -1)
                            selectEnd = Math.min(cursorPosition + 1, text.length());
                        else
                            selectEnd = Math.min(cursorPosition + 1, text.length());
                        cursorPosition++;
                    }
                    else if(par2 == Keyboard.KEY_LEFT)
                    {
                        if(selectEnd == -1)
                            selectEnd = cursorPosition;

                        if(selectStart == -1)
                            selectStart = Math.max(cursorPosition - 1, 0);
                        else
                            selectStart = Math.max(cursorPosition - 1, 0);
                        cursorPosition--;
                    }
                }
                else
                {
                    if(par2 == Keyboard.KEY_RIGHT)
                    {
                        cursorPosition++;
                        selectEnd = selectStart = -1;
                    }
                    if(par2 == Keyboard.KEY_LEFT)
                    {
                        cursorPosition--;
                        selectEnd = selectStart = -1;
                    }

                    if(par2 == Keyboard.KEY_HOME)
                    {
                        cursorPosition = 0;
                        selectEnd = selectStart = -1;
                    }
                    if(par2 == Keyboard.KEY_END)
                    {
                        cursorPosition = text.length();
                        selectEnd = selectStart = -1;
                    }
                }
                cursorPositionCheck();
            }
            else if(par2 == Keyboard.KEY_LSHIFT || par2 == Keyboard.KEY_LSHIFT)
            {

            }
            else if(!ChatAllowedCharacters.isAllowedCharacter(par1) || (par2 >= 59 && par2 <= 68) || par2 == 88 || par2 == 87 || par2 == 210)
            {

            }
            else
            {
                if(cursorPosition == text.length())
                {
                    StringBuilder sb = new StringBuilder(text);
                    sb.append(par1);
                    text = sb.toString();
                }
                else
                {
                    text = text.substring(0, cursorPosition) + String.valueOf(par1) + text.substring(cursorPosition, text.length());
                }
                cursorPosition++;
                cursorPositionCheck();
            }
        }
    }

    private void cursorPositionCheck()
    {
        cursorPosition = Math.max(0, cursorPosition);
        cursorPosition = Math.min(text.length(), cursorPosition);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {

    }

    public void setText(String par1)
    {
        text = par1;
    }

    public String getText()
    {
        return text;
    }

}
