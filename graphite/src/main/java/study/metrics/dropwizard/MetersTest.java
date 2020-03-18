package study.metrics.dropwizard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricRegistry.MetricSupplier;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

public class MetersTest {

	private final static MetricRegistry metricsRegistry = new MetricRegistry();
	private Gauge<Long> guage;
	private Counter counter;
	private Histogram histogram;
	private Meter meter;
	private Timer timer;
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

	public MetersTest() {
		guage = new Gauge<Long>() {

			@Override
			public Long getValue() {
				return 10000l;
			}
		};
		metricsRegistry.register("test.metrics.guage", guage);

		counter = metricsRegistry.counter("test.metrics.counter");

		histogram = metricsRegistry.histogram("test.metrics.histogram");

		meter = metricsRegistry.meter("test.metrics.meter");

		timer = metricsRegistry.timer("test.metrics.timer");

	}

	public void measure() {
		Context context = timer.time();
		try {
			counter.inc();
			// counter.inc(5l);
			histogram.update((int) (10 * Math.random()));
			meter.mark(); // one event/request
			// meter.mark(5l); //5 event/request
			try {
				Thread.sleep((long) (500 * Math.random()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			context.stop();
		}

	}

	public void start() {
		executorService.scheduleAtFixedRate(() -> {
			measure();
		}, 0, 1, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		MetersTest metersTest = new MetersTest();

		ConsoleReporter reporter = ConsoleReporter.forRegistry(metersTest.metricsRegistry)
				.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
		metersTest.start();
		reporter.start(5, TimeUnit.SECONDS);
	}

}
