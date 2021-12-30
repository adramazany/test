package bip.test.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class XSSFilterJersey1 implements ContainerRequestFilter
{
    private static final Logger LOG = LoggerFactory.getLogger( XSSFilterJersey1.class );

    /**
     * @see ContainerRequestFilter#filter(ContainerRequest)
     */
    //@Override
    public ContainerRequest filter( ContainerRequest request )
    {
        System.out.println("XSSFilterJersey1.filter");

        // Clean the query strings
        cleanParams( request.getQueryParameters() );

        // Clean the headers
        cleanParams( request.getRequestHeaders() );

        // Clean the cookies
        cleanParams( request.getCookieNameValueMap() );

        // Return the cleansed request
        return request;
    }

    /**
     * Apply the XSS filter to the parameters
     * @param parameters
     * //@param type
     */
    private void cleanParams( MultivaluedMap<String, String> parameters )
    {
        LOG.debug( "Checking for XSS Vulnerabilities: {}", parameters );

        for( Map.Entry<String, List<String>> params : parameters.entrySet() )
        {
            String key = params.getKey();
            List<String> values = params.getValue();

            List<String> cleanValues = new ArrayList<String>();
            for( String value : values )
            {
                cleanValues.add( stripXSS( value ) );
            }

            parameters.put( key, cleanValues );
        }

        LOG.debug( "XSS Vulnerabilities removed: {}", parameters );
    }

    /**
     * Strips any potential XSS threats out of the value
     * @param value
     * @return
     */
    public String stripXSS( String value )
    {
        if( value == null )
            return null;

        // Use the ESAPI library to avoid encoded attacks.
        value = ESAPI.encoder().canonicalize( value );

        // Avoid null characters
        value = value.replaceAll("\0", "");

        // Clean out HTML
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.escapeMode( EscapeMode.xhtml );
        outputSettings.prettyPrint( false );
        value = Jsoup.clean( value, "", Whitelist.none(), outputSettings );

        return value;
    }
}