package study.projectreactor.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.FutureMono;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/**
 * TCPClient
 */
public class TCPClient {

    public final static Logger logger = LoggerFactory.getLogger(TCPClient.class);

    public static void main(String[] args) throws InterruptedException {

        Connection connection = TcpClient.create()
            .host("localhost").port(12012)
            .handle((inbound, outbound) -> Mono.create(monoSink -> {
                    inbound.receive().asString().subscribe(
                        s -> logger.info(s),
                        err -> logger.error("error", err),
                        () -> {
                            monoSink.success();
                            logger.info("complete");

                        });
                })
            )
            .doOnConnect(boostrap -> logger.info("Will connect to server "))
            .doOnConnected(con -> logger.info("Connected to " + con.address()))
            .doOnDisconnected(con -> logger.info("Disconnected from " + con.address()))
            .connectNow();

        connection.onDispose().block();
    }
}