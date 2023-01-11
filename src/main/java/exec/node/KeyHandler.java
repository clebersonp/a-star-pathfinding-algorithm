package exec.node;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import exec.DemoPanel;

public class KeyHandler implements KeyListener {

	private DemoPanel dp;

	public KeyHandler(DemoPanel dp) {
		this.dp = dp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_ENTER) {
			this.dp.autoSearch();
		} else if (code == KeyEvent.VK_SPACE) {
			this.dp.search();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
