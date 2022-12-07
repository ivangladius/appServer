import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class test {
	public static void main(String[] args) throws InterruptedException{

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Integer> future;

		future = executor.submit(() -> {

			TimeUnit.SECONDS.sleep(2);
			return 4;
		});
		try {
			Integer value = future.get(3L, TimeUnit.SECONDS);
			executor.shutdown();
			System.out.println("value: " + value);

		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
