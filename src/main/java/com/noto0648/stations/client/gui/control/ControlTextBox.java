package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Noto on 14/08/16.
 */
public class ControlTextBox extends Control
{
    private String text = "";
    private int cursorCounter;

    private int scroll;
    private int cursorPosition;

    private int selectStart = -1;
    private int selectEnd = -1;

    protected ControlButton buttonEdit;
    private ControlContextMenu menu;

    private Frame inputWindow;
    private String inputLimit = "";

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
        buttonEdit = (new ControlButton(getGui(), locationX + width - height, locationY, height, height, "E")
        {
            @Override
            public void onButtonClick(int button)
            {
                if(button == 0)
                {
                    playClickSound();
                    showEditWindow();
                }
            }
        });
        menu = (new ControlContextMenu(getGui(), new String[]{"Cut", "Copy", "Paste", "Select All"})
        {
            @Override
            public void dataClick(int id)
            {
                if(id == 1)
                {
                    if(getSelectText() != null)
                    {
                        GuiScreen.setClipboardString(getSelectText());
                    }
                }
                else if(id == 2)
                {
                    setText(GuiScreen.getClipboardString());
                }
                else if(id == 0)
                {
                    if(getSelectText() != null)
                    {
                        GuiScreen.setClipboardString(getSelectText());
                        setText("");
                    }
                }
                else if(id == 3)
                {
                    selectStart = 0;
                    selectEnd = text.length();
                    cursorPositionCheck();
                }
            }
        });
    }

    @Override
    public void initGui()
    {
        buttonEdit.initGui();
        menu.initGui();
        buttonEdit.setLocation(locationX + width - height, locationY);
        buttonEdit.setSize(height, height);
    }

    @Override
    public void update()
    {
        if(isFocus && isEnable)
        {
            cursorCounter++;
            if(cursorCounter == 20) cursorCounter = 0;
        }
        else
        {
            closeWindow();
        }

        if(isEnable)
        {
            buttonEdit.update();
            menu.update();
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        if(!isEnable)
            return;

        final Minecraft client = Minecraft.getMinecraft();
        final ScaledResolution res = new ScaledResolution(client);
        final double scaleW = (double)client.displayWidth / res.getScaledWidth_double();
        final double scaleH = (double)client.displayHeight / res.getScaledHeight_double();

        drawRect(locationX, locationY, locationX + width, locationY + height, 0xFF000000);
        drawRect(locationX, locationY, locationX + width, locationY + 1, 0xFFC0C0C0);
        drawRect(locationX, locationY, locationX + 1, locationY + height, 0xFFC0C0C0);
        drawRect(locationX, locationY + height - 1, locationX + width, locationY + height, 0xFFC0C0C0);
        drawRect(locationX + width - 1, locationY, locationX + width, locationY + height, 0xFFC0C0C0);

        final int cursorRight = getFont().getStringWidth(text.substring(0, cursorPosition));

        if(cursorRight - scroll > width - height - 5)
        {
            scroll = cursorRight - (width - height - 7);
        }
        else if(cursorRight - scroll < 0)
        {
            scroll = Math.max(0, cursorRight - 2);
        }

        if(cursorPosition == 0)
        {
            scroll = 0;
        }

        if(selectStart != -1 || selectEnd != -1)
        {
            final int txtWidth1 = getFont().getStringWidth(text.substring(0, selectStart));
            final int txtWidth = getFont().getStringWidth(text.substring(selectStart, selectEnd));
            drawRect(locationX + 5 + txtWidth1 - scroll, locationY + 5, locationX + 5 + txtWidth1 + txtWidth - scroll, locationY + 16, 0xFF0094FF);
        }

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int)(locationX * scaleW), (int)((double)client.displayHeight - (locationY + height) * scaleH), (int)((width - height) * scaleW), (int)(height * scaleH));
        getFont().drawStringWithShadow(text, locationX + 5 - scroll, locationY + 6, 0xE0E0E0);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        if(isFocus && cursorCounter < 10)
        {
            drawRect(locationX + 5 + cursorRight - scroll, locationY + 5, locationX + 6 + cursorRight - scroll, locationY + 16, 0xFFE0E0E0);
        }

        buttonEdit.draw(mouseX, mouseY);
        menu.draw(mouseX, mouseY);
    }

    @Override
    public void drawTopLayer(int mouseX, int mouseY)
    {
        menu.drawTopLayer(mouseX, mouseY);
    }

    @Override
    public void keyTyped(char par1, int par2)
    {
        if(isFocus && isEnable)
        {
            if(par1 == '\b')
            {
                if(selectStart != -1 && selectEnd != -1)
                {
                    if(selectStart == 0 && selectEnd == text.length())
                    {
                        text = "";
                        textChanged();
                    }
                    else
                    {
                        text = text.substring(0, selectStart) + text.substring(selectEnd, text.length());
                        textChanged();
                    }
                    cursorPosition = selectStart;
                    selectEnd = selectStart = -1;
                }
                else if(text.length() == 1 || text.length() == 0)
                {
                    text = "";
                    cursorPosition--;
                    textChanged();
                }
                else if(cursorPosition == text.length())
                {
                    text = (text.substring(0, text.length() - 1));
                    cursorPosition--;
                    textChanged();
                }
                else if(cursorPosition == 0)
                {

                }
                else
                {
                    text = (text.substring(0, cursorPosition - 1) + text.substring(cursorPosition, text.length()));
                    cursorPosition--;
                    textChanged();
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
                    textChanged();
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
            if(GuiScreen.isCtrlKeyDown())
            {
                if(par1 == 'C')
                {
                    if(getSelectText() != null)
                    {
                        GuiScreen.setClipboardString(getSelectText());
                    }
                }
                else if(par1 == 'V')
                {
                    setText(GuiScreen.getClipboardString());
                }
                else if(par1 == 'X')
                {
                    if(getSelectText() != null)
                    {
                        GuiScreen.setClipboardString(getSelectText());
                        setText("");
                    }
                }
                else if(par1 == 'A')
                {
                    selectStart = 0;
                    selectEnd = text.length();
                    cursorPositionCheck();
                }
                return;
            }
            else if(!ChatAllowedCharacters.isAllowedCharacter(par1) || (par2 >= 59 && par2 <= 68) || par2 == 88 || par2 == 87 || par2 == 210)
            {
                return;
            }
            else
            {
                if(inputLimit != null && inputLimit.length() != 0)
                {
                    boolean canInput = false;
                    for(int i = 0; i < inputLimit.length(); i++)
                    {
                        if(par1 == inputLimit.charAt(i))
                        {
                            canInput = true;
                            break;
                        }
                    }
                    if(!canInput) return;
                }
                if(cursorPosition == text.length())
                {
                    StringBuilder sb = new StringBuilder(text);
                    sb.append(par1);
                    text = sb.toString();
                    textChanged();
                }
                else if(selectStart != -1 && selectEnd != -1)
                {
                    String first = text.substring(0, selectStart);
                    String end = text.substring(selectEnd);
                    text = first + String.valueOf(par1) + end;
                    cursorPosition = selectStart;
                    selectStart = selectEnd = -1;
                    textChanged();
                }
                else
                {
                    text = text.substring(0, cursorPosition) + String.valueOf(par1) + text.substring(cursorPosition, text.length());
                    textChanged();
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
        if(isEnable)
        {
            menu.mouseClicked(mouseX, mouseY, button);
        }
        if(onTheMouse(mouseX, mouseY) && isEnable)
        {
            buttonEdit.mouseClicked(mouseX, mouseY, button);
            if(buttonEdit.onTheMouse(mouseX, mouseY))
                return;

            if(button == 1)
            {
                menu.show(mouseX, mouseY, controlId);
            }


            if(button == 0)
            {
                int mx = mouseX - locationX - 5;
                int wSize = 0;

                int strWidth = getFont().getStringWidth(text);
                if(mx >= strWidth)
                {
                    cursorPosition = text.length();
                    return;
                }

                for(int i = 0; i < text.length(); i++)
                {
                    int cWidth = getFont().getCharWidth(text.charAt(i));
                    if(mx <= wSize + cWidth)
                    {
                        if(mx <= wSize + cWidth /2)
                        {
                            cursorPosition = Math.max(0, i);
                        }
                        else
                        {
                            cursorPosition = Math.min(text.length(), i + 1);
                        }
                        return;
                    }
                    wSize += cWidth;
                }
            }
        }
    }

    public void showEditWindow()
    {
        if(inputWindow == null)
        {
            inputWindow = (new Frame("Minecraft Text Box Control"));
            inputWindow.setLayout(new BorderLayout());
            inputWindow.setResizable(false);
            JTextField tf = new JTextField(getText());

            tf.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Component c = inputWindow.getComponent(0);
                    if(c instanceof JTextField)
                    {
                        String str = ((JTextField)c).getText();
                        setText(str);
                        closeWindow();
                    }
                }
            });
            tf.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
            inputWindow.add(tf, BorderLayout.CENTER);
        }
        inputWindow.setSize(Display.getWidth() , 70);
        inputWindow.setLocation(Display.getX(), Display.getY() + Display.getHeight());
        inputWindow.setVisible(true);
        inputWindow.toFront();
        ((JTextField)inputWindow.getComponent(0)).selectAll();
        inputWindow.getComponent(0).requestFocusInWindow();
    }

    public void closeWindow()
    {
        if(inputWindow != null)
        {
            inputWindow.setVisible(false);
            inputWindow.dispose();
            inputWindow = null;
        }
    }

    @Override
    public void onGuiClosed()
    {
        closeWindow();
    }


    public void setText(String par1)
    {
        if(par1 == null) par1 = "";

        text = par1;

        if(inputLimit != null && inputLimit.length() != 0)
        {
            String result = "";
            for(int j = 0; j < text.length(); j++)
            {
                char textChar = text.charAt(j);
                boolean addFlag = false;
                for(int i = 0; i < inputLimit.length(); i++)
                {
                    if(textChar == inputLimit.charAt(i))
                    {
                        addFlag = true;
                        break;
                    }
                }
                if(addFlag)
                {
                    result += String.valueOf(textChar);
                }
            }
            text = result;
        }
        cursorPosition = 0;
        textChanged();
    }

    public String getText()
    {
        return text;
    }

    public String getSelectText()
    {
        if(text.length() == 0)
            return null;

        if(selectStart != -1 && selectEnd != -1)
        {
            return text.substring(selectStart, selectEnd);
        }
        return text;
    }

    public void setInputLimit(String par1)
    {
        inputLimit = par1;
    }

    /**
     * Text change event
     */
    public void textChanged() {}
}
