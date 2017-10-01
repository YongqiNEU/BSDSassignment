/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:GenericResource [generic]<br>
 * USAGE:
 * <pre>
 *        client1 client = new client1();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jay
 */
public class client1 {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/bsdsProject/webresources";

    public client1() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("generic");
    }

    public  String getHtml() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
    }

    public void putHtml(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.TEXT_HTML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.TEXT_HTML));
    }
    
    public <T> T postText(Object requestEntity, Class<T> responseType) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.TEXT_HTML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity,
                                        javax.ws.rs.core.MediaType.TEXT_HTML),responseType);
    }

    public void close() {
        client.close();
    }
   public static void main(String[] args) {
        
        client1 client = new client1();
        //Client c = ClientBuilder.newClient();
        String s = client.webTarget.request(MediaType.TEXT_HTML).get(String.class);
        System.out.println(s);
        
        Response rere;
        rere = client.webTarget.request(MediaType.TEXT_HTML).
                post(Entity.entity("s,,s", MediaType.TEXT_HTML));
        String real = rere.readEntity(String.class);//readEntity(Integer.class);
        System.out.println( real + '\n');
       
        client.close();
    }   
}
