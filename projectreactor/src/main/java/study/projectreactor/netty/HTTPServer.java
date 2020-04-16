package study.projectreactor.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

public class HTTPServer {

    public static void main(String[] args) {
        DisposableServer server = HttpServer.create()
                .host("localhost")
                .port(8080)
                .route(routes -> routes.get("/hello",  (request, response) -> response.sendString(Mono.just("Hello World!")))
                                .post("/echo", (request, response) -> response.send(ByteBufFlux.concat(ByteBufFlux.just(Unpooled.wrappedBuffer("echo: ".getBytes())), request.receive().retain())))
                                .get("/path/{param}", (request, response) -> response.sendString(Mono.just(request.param("param"))))
                                .ws("/ws", (wsInbound, wsOutbound) -> wsOutbound.send(wsInbound.receive().retain())))
                .bindNow();
        server.onDispose().block();
    }

}
