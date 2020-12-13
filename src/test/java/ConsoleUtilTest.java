import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ConsoleUtilTest {
    ConsoleUtil cu = new ConsoleUtil();
    @Test
    public void readIntInput() {
        String input = "5";
        Integer expected = Integer.parseInt(input);
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Integer actual = cu.readIntInput();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void readStrInput() {
        String expected = "Hello, World!";
        InputStream in = new ByteArrayInputStream(expected.getBytes());
        System.setIn(in);

        String actual = cu.readStrInput();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void print() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expected = "Hello, World!\n";

        cu.print("Hello, World!");

        Assert.assertEquals(expected,outContent.toString());
    }
}