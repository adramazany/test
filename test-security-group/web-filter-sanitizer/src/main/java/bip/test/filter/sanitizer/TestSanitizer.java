package bip.test.filter.sanitizer;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class TestSanitizer {

    public String safeHtml(String html){
//        PolicyFactory policyFactory = new HtmlPolicyBuilder().toFactory();
//        PolicyFactory policyFactory = Sanitizers.BLOCKS;
//        PolicyFactory policyFactory = Sanitizers.FORMATTING;
//        PolicyFactory policyFactory = Sanitizers.IMAGES;
        PolicyFactory policyFactory = Sanitizers.LINKS;
        return policyFactory.sanitize(html);
    }

}
