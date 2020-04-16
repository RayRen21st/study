package study.projectreactor.netty;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

public class TCPServer {

    public static final Logger logger = LoggerFactory.getLogger(TCPServer.class);

    public static void main(String[] args) {
        
        DisposableServer server = TcpServer.create()
            .host("localhost").port(12012)
            .handle((inbound, outbound) -> outbound.sendString(Flux.interval(Duration.ofMillis(100)).map(i -> i + " hello")))
//            .handle((inbound, outbound) -> inbound.receive().then())
            .doOnBound(serverBootstap -> logger.info("Server port bound to: " + serverBootstap.address()))
            .doOnConnection(connection -> logger.info("Connected by "  + connection.channel().remoteAddress()))
            .bindNow();

        server.onDispose().block();
    }

}