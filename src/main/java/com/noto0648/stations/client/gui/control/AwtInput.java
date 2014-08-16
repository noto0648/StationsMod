package com.noto0648.stations.client.gui.control;

import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.font.TextHitInfo;
import java.awt.im.InputMethodRequests;
import java.text.AttributedCharacterIterator;

/**
 * Created by Noto on 14/08/11.
 */
public class AwtInput extends Component implements InputMethodListener, InputMethodRequests
{
    public AwtInput()
    {
        super();
        addInputMethodListener(this);
        setFocusable(true);
    }

    @Override
    public void inputMethodTextChanged(InputMethodEvent event)
    {
        System.out.println("called,");
    }

    @Override
    public void caretPositionChanged(InputMethodEvent event)
    {
        System.out.println("called,   ssd");
    }

    @Override
    public Rectangle getTextLocation(TextHitInfo offset)
    {
        return null;
    }

    @Override
    public TextHitInfo getLocationOffset(int x, int y)
    {
        return null;
    }

    @Override
    public int getInsertPositionOffset()
    {
        return 0;
    }

    @Override
    public AttributedCharacterIterator getCommittedText(int beginIndex, int endIndex, AttributedCharacterIterator.Attribute[] attributes)
    {
        return null;
    }

    @Override
    public int getCommittedTextLength()
    {
        return 0;
    }

    @Override
    public AttributedCharacterIterator cancelLatestCommittedText(AttributedCharacterIterator.Attribute[] attributes)
    {
        return null;
    }

    @Override
    public AttributedCharacterIterator getSelectedText(AttributedCharacterIterator.Attribute[] attributes)
    {
        return null;
    }
}
