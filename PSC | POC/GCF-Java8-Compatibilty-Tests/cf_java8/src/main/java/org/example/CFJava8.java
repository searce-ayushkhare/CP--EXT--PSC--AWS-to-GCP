package org.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;

public class CFJava8 implements HttpFunction {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        BufferedWriter writer = httpResponse.getWriter();
        writer.write("Hello World!");
    }
}