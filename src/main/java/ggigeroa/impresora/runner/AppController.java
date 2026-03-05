package ggigeroa.impresora.runner;

import org.springframework.stereotype.Controller;

@Controller
public class AppController {

	// @Autowired
	// String test;

    public AppController() {
//		this.run();
	}

	public void run() {
		crearThread();
	}

	public void crearThread() {
		Thread thread = new Thread(() -> {
			System.out.println("Thread iniciado");
		});
		thread.start();
	}
	
	
}
