package study.projectreactor.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import reactor.netty.DisposableServer;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;
import reactor.netty.http.server.HttpServer;

public class HTTPClient {

    private final static Logger logger = LoggerFactory.getLogger(HTTPClient.class);

    public static void main(String[] args) {

        HttpClient httpClient = HttpClient.create();

        String response = (String) httpClient
                        .get()
                        .uri("http://localhost:8080/hello")
                        .responseSingle((resp, bytes) -> {
                            logger.info(resp.status().toString());
                            return Mono.create(monoSink -> bytes.asString().subscribe(
                                    s -> logger.info("Received: " + s),
                                    err -> logger.error("error", err),
                                    () -> {
                                        monoSink.success();
                                        logger.info("complete");

                                    }));
                        })
                        .block();
        httpClient
                .post()
                .uri("http://localhost:8080/echo")
                .send(ByteBufMono.just(Unpooled.wrappedBuffer("Hello World".getBytes())))
                .responseSingle((resp, byteBufMono) -> {
                    logger.info(resp.status().toString());
                    return Mono.create(monoSink -> byteBufMono.asString().subscribe(
                            s -> logger.info("Received: " + s),
                            err -> logger.error("error", err),
                            () -> {
                                monoSink.success();
                                logger.info("complete");

                            }));
                })
                .block();
    }
}
