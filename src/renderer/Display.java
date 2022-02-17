package renderer;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.entity.EntityManager;
import renderer.input.ClickType;
import renderer.input.Mouse;
import renderer.point.MyPoint;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class Display extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private static JFrame frame;
	private static String title = "3D Rubix Cube";
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth();
	public static final int HEIGHT = (int) screenSize.getHeight();
	private static boolean running = false;
	
	private EntityManager entityManager;
	
	private Mouse mouse;
	
	public Display() {
		this.frame = new JFrame();
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
		
		this.mouse = new Mouse();
		
		this.entityManager = new EntityManager();
		
		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(this.mouse);
		this.addMouseWheelListener(this.mouse);
	}
	
	public static void main(String[] args) {
		Display display = new Display();

		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(display, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		display.start();
	}
	
	public synchronized void start() {
		running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		long lastTime = System.nanoTime();			// The current nano time
		long timer = System.currentTimeMillis();	// current time in milli seconds
		final double ns = 1000000000.0 / 60;		// 60 updates per second
		double delta = 0; 	// % progress for update
		int frames = 0;
		
		this.entityManager.init();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;		// % untill next update, if delta hits 1, then update
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;

				render();
				frames++;
				
			}
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(title + " | "+frames+" fps");
				frames = 0;
			}
		}
		stop();
		
	}
	

	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		this.entityManager.render(g);
		
		g.dispose();
		bs.show();
	}
	
	
	private void update() {
		this.entityManager.update(this.mouse);
	}
	
}
