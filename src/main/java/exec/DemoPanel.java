package exec;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import exec.node.KeyHandler;
import exec.node.Node;

public class DemoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// SCREEN SETTINGS
	private final int maxCol = 15;
	private final int maxRow = 10;
	private final int nodeSize = 70;
	private final int screenWidth = this.nodeSize * this.maxCol;
	private final int screenHeight = this.nodeSize * maxRow;

	// NODE
	private Node[][] nodes = new Node[this.maxRow][this.maxCol];
	private Node startNode, goalNode, currentNode;
	private List<Node> openList = new ArrayList<>();
	private List<Node> checkedList = new ArrayList<>();

	// OTHERS
	private boolean goalReached = false;

	public DemoPanel() {

		this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
		this.setBackground(Color.black);
		this.setLayout(new GridLayout(this.maxRow, this.maxCol));
		this.addKeyListener(new KeyHandler(this));
		this.setFocusable(true);

		// PLACE NODES
		for (int row = 0; row < this.maxRow; row++) {
			for (int col = 0; col < this.maxCol; col++) {
				this.nodes[row][col] = new Node(row, col);
				this.add(this.nodes[row][col]);
			}
		}

		// SET START AND GOAL NODE
		this.setStartNode(6, 3);
		this.setGoalNode(3, 11);

		// PLACE SOLID NODES
		this.setSolidNode(2, 10);
		this.setSolidNode(3, 10);
		this.setSolidNode(4, 10);
		this.setSolidNode(5, 10);
		this.setSolidNode(6, 10);
		this.setSolidNode(7, 10);
		this.setSolidNode(2, 6);
		this.setSolidNode(2, 7);
		this.setSolidNode(2, 8);
		this.setSolidNode(2, 9);
		this.setSolidNode(7, 11);
		this.setSolidNode(7, 12);
		this.setSolidNode(1, 6);

		// SET COST
		this.setCostOnNodes();
	}

	private void setCostOnNodes() {
		for (int row = 0; row < this.nodes.length; row++) {
			for (int col = 0; col < this.nodes[row].length; col++) {
				this.getCost(this.nodes[row][col]);
			}
		}
	}

	private void setStartNode(int row, int col) {
		this.nodes[row][col].setAsStart();
		this.startNode = this.nodes[row][col];
		this.currentNode = this.startNode;
	}

	private void setGoalNode(int row, int col) {
		this.nodes[row][col].setAsGoal();
		this.goalNode = this.nodes[row][col];
	}

	private void setSolidNode(int row, int col) {
		this.nodes[row][col].setAsSolid();
	}

	private void getCost(Node node) {

		// GET G COST (The distance from the start node)
		int xDistance = Math.abs(node.getCol() - this.startNode.getCol());
		int yDistance = Math.abs(node.getRow() - this.startNode.getRow());
		node.setgCost(xDistance + yDistance);

		// GET H COST (The distance from the goal node)
		xDistance = Math.abs(node.getCol() - this.goalNode.getCol());
		yDistance = Math.abs(node.getRow() - this.goalNode.getRow());
		node.sethCost(xDistance + yDistance);

		// GET F COST (Total of gCost + hCost)
		node.setfCost(node.getgCost() + node.gethCost());

		// DISPLAY THE COST ON NODE
		if (node != this.startNode && node != this.goalNode) {
			node.setText(String.format("<html>F:%s<br>G:%s<br>H:%s</html>", node.getfCost(), node.getgCost(),
					node.gethCost()));
		}
	}

	public void search() {

		if (!this.goalReached) {

			int row = this.currentNode.getRow();
			int col = this.currentNode.getCol();

			this.currentNode.setAsChecked();
			this.checkedList.add(this.currentNode);
			this.openList.remove(this.currentNode);

			// OPEN THE UP NODE
			if (row - 1 >= 0) {
				this.openNode(this.nodes[row - 1][col]);
			}

			// OPEN THE LEFT NODE
			if (col - 1 >= 0) {
				this.openNode(this.nodes[row][col - 1]);
			}

			// OPEN THE DOWN NODE
			if (row + 1 < this.maxRow) {
				this.openNode(this.nodes[row + 1][col]);
			}

			// OPEN THE RIGHT NODE
			if (col + 1 < this.maxCol) {
				this.openNode(this.nodes[row][col + 1]);
			}

			int bestNodeIndex = 0;
			int bestNodefCost = Integer.MAX_VALUE;

			for (int i = 0; i < this.openList.size(); i++) {

				// Check if this node's F cost is better
				if (this.openList.get(i).getfCost() < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = this.openList.get(i).getfCost();
				}
				// If F cost is equal, check the G cost
				else if (this.openList.get(i).getfCost() == bestNodefCost
						&& this.openList.get(i).getgCost() < this.openList.get(bestNodeIndex).getgCost()) {
					bestNodeIndex = i;
				}
			}

			// After the loop, we get the best node which is our next step
			this.currentNode = this.openList.get(bestNodeIndex);

			if (this.currentNode.isGoal()) {
				this.goalReached = true;
				this.trackThePath();
			}

		}
	}

	public void autoSearch() {

		while (!this.goalReached) {

			int row = this.currentNode.getRow();
			int col = this.currentNode.getCol();

			this.currentNode.setAsChecked();
			this.checkedList.add(this.currentNode);
			this.openList.remove(this.currentNode);

			// OPEN THE UP NODE
			if (row - 1 >= 0) {
				this.openNode(this.nodes[row - 1][col]);
			}

			// OPEN THE LEFT NODE
			if (col - 1 >= 0) {
				this.openNode(this.nodes[row][col - 1]);
			}

			// OPEN THE DOWN NODE
			if (row + 1 < this.maxRow) {
				this.openNode(this.nodes[row + 1][col]);
			}

			// OPEN THE RIGHT NODE
			if (col + 1 < this.maxCol) {
				this.openNode(this.nodes[row][col + 1]);
			}

			int bestNodeIndex = 0;
			int bestNodefCost = Integer.MAX_VALUE;

			for (int i = 0; i < this.openList.size(); i++) {

				// Check if this node's F cost is better
				if (this.openList.get(i).getfCost() < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = this.openList.get(i).getfCost();
				}
				// If F cost is equal, check the G cost
				else if (this.openList.get(i).getfCost() == bestNodefCost
						&& this.openList.get(i).getgCost() < this.openList.get(bestNodeIndex).getgCost()) {
					bestNodeIndex = i;
				}
			}

			// After the loop, we get the best node which is our next step
			this.currentNode = this.openList.get(bestNodeIndex);

			if (this.currentNode.isGoal()) {
				this.goalReached = true;
				this.trackThePath();
			}
		}

	}

	private void openNode(Node node) {
		if (!node.isOpen() && !node.isChecked() && !node.isSolid()) {

			// If the node is not opened yet, add it to the open list
			node.setAsOpen();
			node.setParentNode(this.currentNode);
			this.openList.add(node);
		}
	}

	private void trackThePath() {

		// Backtrack and draw the best path
		Node current = this.goalNode;
		while (!current.isStart()) {
			current = current.getParentNode();
			if (!current.isStart()) {
				current.setAsPath();
			}
		}
	}

}
