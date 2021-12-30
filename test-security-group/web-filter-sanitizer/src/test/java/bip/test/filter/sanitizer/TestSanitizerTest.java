package bip.test.filter.sanitizer;

import static org.junit.Assert.*;

public class TestSanitizerTest {

    TestSanitizer t = new TestSanitizer();

    @org.junit.Test
    public void safeHtml_happyCase() {
        String html = "<html><body><div>happy case</div></body></html>";
        String safe = t.safeHtml(html);
        System.out.println(String.format(" html = %s \nsafe = %s ",html,safe));
        // <div>happy case</div>

         html = "<html><body><script>alert(1)</script> <div>happy case</div></body></html>";
         safe = t.safeHtml(html);
        System.out.println(String.format("\n\n html = %s \nsafe = %s ",html,safe));
        //<div>happy case</div>

    }
}