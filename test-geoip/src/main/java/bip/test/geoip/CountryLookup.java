package bip.test.geoip;

import com.maxmind.geoip.LookupService;

import java.io.File;
import java.io.IOException;

/**
 * Created by ramezani on 8/2/2018.
 */
public class CountryLookup {
    public static void main(String[] args) throws IOException {
        String dbfile =  "C:/Users/ramezani/git/test/test-geoip/src/main/resources/GeoIP.dat";

        System.out.println("dbfile="+new File(dbfile).getAbsolutePath());
        // You should only call LookupService once, especially if you use
        // GEOIP_MEMORY_CACHE mode, since the LookupService constructor takes up
        // resources to load the GeoIP.dat file into memory
        //LookupService cl = new LookupService(dbfile,LookupService.GEOIP_STANDARD);
        LookupService cl = new LookupService(dbfile,LookupService.GEOIP_MEMORY_CACHE);

        System.out.println(cl.getCountry("151.38.39.114").getCode());
        System.out.println(cl.getCountry("151.38.39.114").getName());
        System.out.println(cl.getCountry("12.25.205.51").getName());
        System.out.println(cl.getCountry("64.81.104.131").getName());
        System.out.println(cl.getCountry("200.21.225.82").getName());
        System.out.println(cl.getDatabaseInfo());

        cl.close();
    }
}
