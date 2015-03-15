/*
 *  Copyright (c) Screenful Ltd 2014
 *  This file is subject to the terms and conditions defined in
 *  file 'LICENSE.txt', which is part of this source code package.
 */

package com.almost50shades.shadesconnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import sun.net.www.http.HttpClient;

/**
 *
 * @author martin
 */
public class BasicClient implements Client {

    public BasicClient() 
    {
       

    }
    
    @Override
    public String executeGetRequest(String url) {
        System.out.println(url);
        StringBuilder result = new StringBuilder();
        HttpGet get = new HttpGet(url);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        // set headers to recognize sender

        try {
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            if (statusCode != 200) {
                throw new IOException("Status code not 200. Result: " + result);
            }
        } catch (IOException e) {
            System.exit(1);
        }
               
        return result.toString();
    }

}
