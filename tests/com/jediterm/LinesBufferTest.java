package com.jediterm;

import com.jediterm.terminal.TextStyle;
import com.jediterm.terminal.model.LinesBuffer;
import com.jediterm.util.CharBufferUtil;
import junit.framework.TestCase;

/**
 * @author traff
 */
public class LinesBufferTest extends TestCase {

    public static final String LINE_1 = "Line 1";
    public static final String LINE_2 = "Line 2";
    public static final String LINE_3 = "Line 3";
    public static final String LINE_4 = "Line 4";

    public void testMoveTopLines() {
        TextStyle style = new TextStyle();
        LinesBuffer scroll = new LinesBuffer();
        scroll.addNewLine(style, CharBufferUtil.create(LINE_1));
        scroll.addNewLine(style, CharBufferUtil.create(LINE_2));
        LinesBuffer text = new LinesBuffer();
        text.addNewLine(style, CharBufferUtil.create(LINE_3));
        text.addNewLine(style, CharBufferUtil.create(LINE_4));

        text.moveTopLinesTo(1, scroll);

        assertEquals(1, text.getLineCount());
        assertEquals(3, scroll.getLineCount());

        assertEquals("Line 1\n" +
                "Line 2\n" +
                "Line 3", scroll.getLines());

        assertEquals("Line 4", text.getLines());
    }

    public void testMoveBottomLines() {
        TextStyle style = new TextStyle();
        LinesBuffer scroll = new LinesBuffer();
        scroll.addNewLine(style, CharBufferUtil.create(LINE_1));
        scroll.addNewLine(style, CharBufferUtil.create(LINE_2));
        scroll.addNewLine(style, CharBufferUtil.create(LINE_3));
        LinesBuffer text = new LinesBuffer();
        text.addNewLine(style, CharBufferUtil.create(LINE_4));

        scroll.moveBottomLinesTo(2, text);

        assertEquals(3, text.getLineCount());
        assertEquals(1, scroll.getLineCount());


        assertEquals("Line 1", scroll.getLines());

        assertEquals("Line 2\n" +
                "Line 3\n" +
                "Line 4", text.getLines());
    }


    public void testRemoveFirstLines() {
        TextStyle style = new TextStyle();
        LinesBuffer text = new LinesBuffer();
        text.addNewLine(style, CharBufferUtil.create(LINE_1));
        text.addNewLine(style, CharBufferUtil.create(LINE_2));
        text.addNewLine(style, CharBufferUtil.create(LINE_3));
        text.addNewLine(style, CharBufferUtil.create(LINE_4));

        text.removeTopLines(3);

        assertEquals(1, text.getLineCount());

        assertEquals("Line 4", text.getLines());
    }

    public void testWriteToLineBuffer() {
        LinesBuffer buf = new LinesBuffer();
        buf.writeString(3, 2, "Hi!", TextStyle.EMPTY);

        assertEquals("\n" +
                "\n" +
                "   Hi!", buf.getLines());

        buf.writeString(1, 1, "*****", TextStyle.EMPTY);

        assertEquals("\n" +
                " *****\n" +
                "   Hi!", buf.getLines());

        buf.writeString(3, 1, "+", TextStyle.EMPTY);

        assertEquals("\n" +
                " **+**\n" +
                "   Hi!", buf.getLines());

        buf.writeString(4, 1, "***", TextStyle.EMPTY);

        assertEquals("\n" +
                " **+***\n" +
                "   Hi!", buf.getLines());

        buf.writeString(8, 1, "=", TextStyle.EMPTY);

        assertEquals("\n" +
                " **+*** =\n" +
                "   Hi!", buf.getLines());
    }

}
