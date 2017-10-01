/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.text.html.parser.DTDConstants;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:GenericResource [generic]<br>
 * USAGE:
 * <pre>
 *        NewJerseyClient client = new NewJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jay
 */
public class NewJerseyClient {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = 
            "http://35.163.102.13:8080/bsdsProject-13750734962408646325.0-SNAPSHOT/webresources";
    //private static final String BASE_URI = "http://localhost:8080/bsdsProject/webresources";

    public NewJerseyClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("generic");
    }

    public String getHtml() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
    }

    public void putHtml(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.TEXT_HTML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.TEXT_HTML));
    }

    public void close() {
        client.close();
    }
    
    static int successResp;
    
    public static void main(String[] args) {
        //NewJerseyClient client = new NewJerseyClient();
        final List<Integer> latencies = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        System.out.println("Client starting ... Time : " + startTime);

        ExecutorService executor = Executors.newFixedThreadPool(100);
        for(int i = 0; i< 100; i++){
            executor.execute(new Runnable(){
                public void run(){
                    NewJerseyClient client = new NewJerseyClient();
                    
                    for(int i = 0; i< 100 ;i++){
                        long start = System.currentTimeMillis();
                        String s = client.webTarget.request(MediaType.TEXT_HTML).get(String.class);
                        Response response1 = client.webTarget.request(MediaType.TEXT_HTML).
                                    get();
                        response1.close();
                        successResp++;
                        long end = System.currentTimeMillis();
                        latencies.add((int)(end-start));
                    }
                    for(int i = 0; i< 100 ;i++){
                        long start = System.currentTimeMillis();
                        Response response2 = client.webTarget.request(MediaType.TEXT_HTML).
                                    post(Entity.entity("alive", MediaType.TEXT_HTML));
                        //String re = response.readEntity(String.class);//readEntity(Integer.class);
                        response2.close();
                        successResp++;
                        long end = System.currentTimeMillis();
                        latencies.add((int) (end-start));
                    }
                    client.close();
                }
            });
        }
        executor.shutdown();
        System.out.println("All threads running ...");
        
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
        }
        
        long finishtime = System.currentTimeMillis();
        System.out.println("All threads complete ... Time: " + finishtime);
        System.out.println("Total Number of requests sent: 20000");
        System.out.println("Total Number of Successful responses:" + successResp);
        float walltime = (finishtime - startTime)/ (float) 1000.0;
        System.out.println("Test Wall Time: " + (walltime) + "seconds" );
        
        //Step 5
        Collections.sort(latencies);
        
        double mean = 0;
        for(int i = 0; i< latencies.size(); i++){mean +=latencies.get(i);}
        mean /= latencies.size();
        System.out.println("Mean​ ​​latencies​ ​for​ ​all​ ​requests: " + mean);
        int median = latencies.get(latencies.size()/2);
        System.out.println("Median​ ​latencies​ ​for​ ​all​ ​requests: " + median);
        int index99 = (latencies.size() * 99) /100;
        System.out.println("99th​ ​percentile​ ​latency : " + latencies.get(index99) + "ms");
        int index95 = (latencies.size() * 95) /100;
        System.out.println("95th​ ​percentile​ ​latency : " + latencies.get(index95) + "ms");

    }
}
