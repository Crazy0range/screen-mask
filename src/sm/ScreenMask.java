package sm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ScreenMask extends JFrame implements MouseListener,
		MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8318382589611163463L;
	/**
	 * @param args
	 */

	static Point origin;
	private boolean top = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean drag = false;
	private static int NORTH = 0;
	private static int EAST = 1;
	private static int WEST = 2;
	private static int SOUTH = 3;
	private static int CENTRE = 4;
	private static int NORTH_EAST = 5;
	private static int NORTH_WEST = 6;
	private static int SOUTH_EAST = 7;
	private static int SOUTH_WEST = 8;
	private Point lastPoint = null;
	private Point draggingAnchor = null;
	private JLabel label;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		origin = new Point();
		ScreenMask sm = new ScreenMask();
	}

	public ScreenMask() {
		super("Sc");
		setSize(400, 200);
		setLocation(30, 50);
		setBackground(Color.black);
		setUndecorated(true);
		setAlwaysOnTop(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		JRootPane root = this.getRootPane();
		root.putClientProperty("Window.shadow", Boolean.FALSE);

		label = new JLabel();
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		label.setText("<html><center>Drag border to move and resize<br><br>Double-click to quit</center></html>");
		label.setForeground(Color.WHITE);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(label, BorderLayout.CENTER);
		label.setVisible(true);
		super.paintComponents(getGraphics());
	}

	private int whichSideInBoundry(double x, double y) {
		if (y <= 10) {
			if (x <= 10)
				return NORTH_WEST;
			else if (Math.abs(x - this.getSize().getWidth()) <= 10)
				return NORTH_EAST;
			return NORTH;
		} else if (Math.abs(y - this.getSize().getHeight()) <= 10) {
			if (x <= 10)
				return SOUTH_WEST;
			else if (Math.abs(x - this.getSize().getWidth()) <= 10)
				return SOUTH_EAST;
			return SOUTH;
		} else if (x <= 10) {
			return WEST;
		} else if (Math.abs(x - this.getSize().getWidth()) <= 10) {
			return EAST;
		} else {
			return CENTRE;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		Dimension dimension = this.getSize();
		if (this.getCursor().getType() == Cursor.N_RESIZE_CURSOR) {

			dimension.setSize(dimension.getWidth(),
					dimension.getHeight() - e.getY());
			this.setSize(dimension);
			this.setLocation(this.getLocationOnScreen().x,
					this.getLocationOnScreen().y + e.getY());
		} else if (this.getCursor().getType() == Cursor.S_RESIZE_CURSOR) {

			dimension.setSize(dimension.getWidth(), e.getY());
			this.setSize(dimension);

		} else if (this.getCursor().getType() == Cursor.W_RESIZE_CURSOR) {

			dimension.setSize(dimension.getWidth() - e.getX(),
					dimension.getHeight());
			this.setSize(dimension);

			this.setLocation(this.getLocationOnScreen().x + e.getX(),
					this.getLocationOnScreen().y);

		} else if (this.getCursor().getType() == Cursor.E_RESIZE_CURSOR) {

			dimension.setSize(e.getX(), dimension.getHeight());
			this.setSize(dimension);
		} else if (this.getCursor().getType() == Cursor.NE_RESIZE_CURSOR) {
			dimension.setSize(e.getX(), dimension.getHeight() - e.getY());
			this.setSize(dimension);
			this.setLocation(this.getLocationOnScreen().x,
					this.getLocationOnScreen().y + e.getY());
		} else if (this.getCursor().getType() == Cursor.NW_RESIZE_CURSOR) {
			dimension.setSize(dimension.getWidth() - e.getX(),
					dimension.getHeight() - e.getY());
			this.setSize(dimension);
			this.setLocation(this.getLocationOnScreen().x + e.getX(),
					this.getLocationOnScreen().y + e.getY());
		} else if (this.getCursor().getType() == Cursor.SE_RESIZE_CURSOR) {
			dimension.setSize(e.getX(), e.getY());
			this.setSize(dimension);

		} else if (this.getCursor().getType() == Cursor.SW_RESIZE_CURSOR) {
			dimension.setSize(dimension.getWidth() - e.getX(), e.getY());
			this.setSize(dimension);
			this.setLocation(this.getLocationOnScreen().x + e.getX(),
					this.getLocationOnScreen().y);
		} else {
			Point now = this.getLocationOnScreen();
			this.setLocation(now.x + e.getX() - origin.x, now.y + e.getY()
					- origin.y);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();

		switch (whichSideInBoundry(x, y)) {
		case 0:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			top = true;
			break;
		case 3:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			down = true;
			break;
		case 2:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			right = true;
			break;
		case 1:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			left = true;
			break;
		case 5:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			top = true;
			right = true;
			break;
		case 6:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			top = true;
			left = true;
			break;
		case 7:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			down = true;
			right = true;
			break;
		case 8:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			down = true;
			left = true;
			break;
		default:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			top = false;
			down = false;
			left = false;
			right = false;
			drag = true;

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();
			// handle double click event.
			System.exit(EXIT_ON_CLOSE);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		label.setVisible(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		label.setVisible(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		origin = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		origin = null;
	}

}
