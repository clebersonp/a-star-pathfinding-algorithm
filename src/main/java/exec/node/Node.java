package exec.node;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Node extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Node parentNode;
	private int col;
	private int row;
	private int gCost; // distance from the current node and the start node
	private int hCost; // distance from the current node and the goal node
	private int fCost; // total distante, gCost + hCost
	private boolean start;
	private boolean goal;
	private boolean solid;
	private boolean open;
	private boolean checked;

	public Node(int row, int col) {

		this.row = row;
		this.col = col;

		this.setBackground(Color.white);
		this.setForeground(Color.black);
		this.addActionListener(this);
	}

	public void setAsStart() {
		this.setBackground(Color.blue);
		this.setForeground(Color.white);
		this.setText("Start");
		this.start = true;
	}

	public void setAsGoal() {
		this.setBackground(Color.yellow);
		this.setForeground(Color.black);
		this.setText("Goal");
		this.goal = true;
	}

	public void setAsSolid() {
		this.setBackground(Color.black);
		this.setForeground(Color.gray);
		this.solid = true;
	}

	public void setAsOpen() {
		if (!this.start && !this.goal) {
			this.setBackground(new Color(217, 20, 204));
		}
		this.open = true;
	}

	public void setAsChecked() {
		if (!this.start && !this.goal) {
			this.setBackground(Color.orange);
			this.setForeground(Color.black);
		}
		this.checked = true;
	}

	public void setAsPath() {
		this.setBackground(Color.green);
		this.setForeground(Color.black);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!this.start && !this.goal && !this.solid) {
			this.setBackground(Color.orange);
		}
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public int getfCost() {
		return fCost;
	}

	public void setfCost(int fCost) {
		this.fCost = fCost;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public boolean isStart() {
		return start;
	}

	public boolean isGoal() {
		return goal;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isChecked() {
		return checked;
	}

	@Override
	public String toString() {
		return "Node [col=" + col + ", row=" + row + ", gCost=" + gCost + ", hCost=" + hCost + ", fCost=" + fCost
				+ ", start=" + start + ", goal=" + goal + ", solid=" + solid + ", open=" + open + ", checked=" + checked
				+ "]";
	}

}
