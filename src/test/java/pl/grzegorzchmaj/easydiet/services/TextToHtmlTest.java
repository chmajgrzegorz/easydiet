package pl.grzegorzchmaj.easydiet.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextToHtmlTest {

    private TextToHtml textToHtml;

    @Before
    public void setUp(){
        textToHtml = new TextToHtmlImpl();
    }

    @Test
    public void toHTML() {
        Assert.assertEquals("Test1<br>Test2.", textToHtml.toHTML("Test1\nTest2."));
        Assert.assertEquals("<br>Test1<br>Test2.", textToHtml.toHTML("\nTest1\nTest2."));
        Assert.assertEquals("Test1<br>Test2<br>.", textToHtml.toHTML("Test1\rTest2\n."));
        Assert.assertEquals("Test1<br>Test2.", textToHtml.toHTML("Test1\r\nTest2."));
    }
}