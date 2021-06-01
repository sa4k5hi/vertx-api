package com.mycompany.app;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ServerSocket;

@RunWith(VertxUnitRunner.class)
public class AppTest {

  private Vertx vertx;

  @Before
  public void setUp(TestContext context) throws IOException {
    vertx = Vertx.vertx();

    vertx.deployVerticle(App.class.getName(), context.asyncAssertSuccess());
  }

  @After
  public void tearDown(TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }

 
  @Test
  public void testMyApplication(TestContext context) {
    
    final Async async = context.async();

    vertx.createHttpClient().getNow(8080, "localhost", "/", response -> {
      response.handler(body -> {
        context.assertTrue(body.toString().contains("Operations"));
        async.complete();
      });
    });
  }
}