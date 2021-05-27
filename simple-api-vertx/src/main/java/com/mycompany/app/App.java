package com.mycompany.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class App extends AbstractVerticle {

    private Map<Integer, Peers> People = new LinkedHashMap<>();

    @Override
    public void start(Future<Void> fut) {

        DataNames();

        Router router = Router.router(vertx);

        router.route("/").handler(routingContext -> {
        HttpServerResponse response = routingContext.response();
        response
            .putHeader("content-type", "text/html")
            .end("<h1>Operations to Perform</h1><ul><li>To see all names- Get Request- http://localhost:8081/api/peers</li><li>To delete a name- Delete Request- http://localhost:8081/api/peers/id where id is between 0-3</li></ul>");
        });

        router.get("/api/peers").handler(this::getAll);
        router.route().handler(BodyHandler.create());
        router.delete("/api/peers/:id").handler(this::deleteOne);

        vertx
            .createHttpServer()
            .requestHandler(router::accept)
            .listen(
                config().getInteger("http.port", 8081), 
                result -> {
                    if (result.succeeded()) {
                      fut.complete();
                    } else {
                      fut.fail(result.cause());
                    }
                }
            );
    }

    private void getAll(RoutingContext routingContext) {
        routingContext.response()
            .putHeader("content-type", "application/json; charset=utf-8")
            .end(Json.encodePrettily(People.values()));
    }

    private void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            Integer idAsInteger = Integer.valueOf(id);
            People.remove(idAsInteger);
        }
        routingContext.response().setStatusCode(204).end();
    }

    private void DataNames() {
        Peers SJ = new Peers("Saakshi", "Jain");
        People.put(SJ.getId(), SJ);
        Peers HK = new Peers("Himanshi", "Kataria");
        People.put(HK.getId(), HK);
        Peers SA = new Peers("Shreya", "Agarwal");
        People.put(SA.getId(), SA);
    }
}
