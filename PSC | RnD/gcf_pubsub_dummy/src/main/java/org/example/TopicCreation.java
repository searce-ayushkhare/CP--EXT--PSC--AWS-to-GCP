package org.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopicCreation implements HttpFunction {

    private static final Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String GCP_PROJECT_ID = "gcp-cf-sample";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        JsonObject inputBody = null;
        try {
            inputBody = gson.fromJson(httpRequest.getReader(), JsonObject.class);
            setUpTopicName(inputBody.get("topic").getAsString());

            httpResponse.getWriter().write("Topic created ...");
            httpResponse.setStatusCode(201);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException :: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void setUpTopicName(String topicId) {
        try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
            TopicName topicName = TopicName.of(GCP_PROJECT_ID, topicId);
            Topic topic = topicAdminClient.createTopic(topicName);
            logger.log(Level.INFO, "Created topic: " + topic.getName());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException :: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}