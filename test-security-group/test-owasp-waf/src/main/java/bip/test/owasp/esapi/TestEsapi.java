package bip.test.owasp.esapi;

/**
 * Created by ramezani on 12/5/2019.
 */

import org.owasp.esapi.ESAPI;
public class TestEsapi {
    public static void main(String[] args)
    {
        System.out.println("ESAPI.accessController found: "
                + ESAPI.accessController());
    }
}