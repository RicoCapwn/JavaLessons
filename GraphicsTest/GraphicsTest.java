import java.awt.Frame;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.HashSet;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GraphicsTest extends Frame {

	private BufferedImage bulletSprite;
	private int bulletY;
	private int bulletX;
	private boolean bulletFired = false;
	private int x = 0;
	private int y = 0;
	private boolean rightArrowKeyPressed = false;
	private boolean running = false;
	private Set<Integer> keysPressed = new HashSet<Integer>();
	private Set<Integer> previousKeysPressed = new HashSet<Integer>();
	private List<Bullet> bullets = new ArrayList<Bullet>();
	public GraphicsTest(){
		try {
    		bulletSprite = ImageIO.read(new File("bulletsprites.png"));
		} catch (IOException e) {
		}
		this.setBackground(new Color(0x38,0x8E,0x8E));
        this.addWindowListener(new GraphicsTestWindowAdapter(this));
        this.addKeyListener(new GraphicsTestKeyAdapter(this));
        this.setSize(320, 240);
        this.setUndecorated(true);
        this.setVisible(true);
        this.createBufferStrategy(2);

        Timer timer = new Timer(20, new GraphicsTestActionListener(this));
        running = true;
        timer.start();
	}

	public void setRunning(boolean running){
		this.running = running;
	}

	public void update() {
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(bullet == null){
				continue;
			}
			if(bullet.getX() > 640 || bullet.getX() < -320 || bullet.getY() > 480 || bullet.getY() < -240) {
				bullets.set(i, null);
			}
		}
		bullets.removeAll(Collections.singleton(null));

		if(keysPressed.contains(KeyEvent.VK_ESCAPE)) {
			running = false;
		}
		if(keysPressed.contains(KeyEvent.VK_RIGHT)) {
			x++;
		}
		if(keysPressed.contains(KeyEvent.VK_LEFT)) {
			x--;
		}
		if(keysPressed.contains(KeyEvent.VK_DOWN)) {
			y++;
		}
		if(keysPressed.contains(KeyEvent.VK_UP)) {
			y--;
		}
		if(keysPressed.contains(KeyEvent.VK_W) && !previousKeysPressed.contains(KeyEvent.VK_W)) {
			bullets.add(new Bullet(bulletSprite, x+11, y-13, 0));
		}
		if(keysPressed.contains(KeyEvent.VK_A) && !previousKeysPressed.contains(KeyEvent.VK_A)) {
			bullets.add(new Bullet(bulletSprite, x-13, y+11, 1));
		}
		if(keysPressed.contains(KeyEvent.VK_S) && !previousKeysPressed.contains(KeyEvent.VK_S)) {
			bullets.add(new Bullet(bulletSprite, x+11, y+32, 2));
		}
		if(keysPressed.contains(KeyEvent.VK_D) && !previousKeysPressed.contains(KeyEvent.VK_D)) {
			bullets.add(new Bullet(bulletSprite, x+32, y+11, 3));
		}
		for(Bullet bullet : bullets){
			bullet.update();
		}
		if(running == false) {
			System.exit(0);
		}
		previousKeysPressed.clear();
		for(Integer keyCode : keysPressed) {
			previousKeysPressed.add(keyCode);
		}
	}

	public void setKeyPressed(int keyCode) {
		keysPressed.add(keyCode);
	}

	public void setKeyReleased(int keyCode) {
		keysPressed.remove(keyCode);
	}
	private void clearScreen(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,320,240);
	}
	public void draw(){
		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = null;
		try {
			g = bf.getDrawGraphics();
			clearScreen(g);
			g.setColor(new Color(0x00,0xEE,0x00));
			g.fillRect(x,y,32,32);
			for(Bullet bullet : bullets) {
				bullet.draw(g);
			}
		} finally {
			g.dispose();
		}
		bf.show();
		Toolkit.getDefaultToolkit().sync();
	}
    public static void main (String[] args) {
    	new GraphicsTest();         
    }

}