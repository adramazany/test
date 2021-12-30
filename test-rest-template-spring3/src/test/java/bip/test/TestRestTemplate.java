package bip.test;

import org.junit.Test;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class TestRestTemplate {

    @Test
    public void testRestTemplate(){
        System.out.println("TestRestTemplate.testRestTemplate");
        SimpleClientHttpRequestFactory requestFactory=new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        RestTemplate restTemplate=new RestTemplate(requestFactory);

//        restTemplate.postForEntity()
//        restTemplate.postForObject()
    }
}
