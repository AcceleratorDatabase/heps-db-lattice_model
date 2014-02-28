package modelmanager.widget;

import xal.smf.Accelerator;
import xal.smf.AcceleratorNode;
import xal.smf.AcceleratorSeq;
import xal.smf.data.XMLDataManager;
import xal.smf.impl.BPM;
import xal.smf.impl.ProfileMonitor;
import xal.smf.impl.Bend;
import xal.smf.impl.Dipole;
import xal.smf.impl.HDipoleCorr;
import xal.smf.impl.PermQuadrupole;
import xal.smf.impl.Quadrupole;
//import xal.smf.impl.RfCavity;
//import xal.smf.impl.RfGap;
import xal.smf.impl.Solenoid;
import xal.smf.impl.VDipoleCorr;
import xal.smf.impl.Marker;






//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Insets;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * <code>XALSynopticFXPanel</code> is simple panel which shows synoptic layout of
 * selected XAL sequence.  Synoptic is drawn in horizontal direction. To
 * define right or left distance of drawing from the edge of panel, use
 * margins. To define which part of sequence should be drawn, use start and end
 * position.  This is a JavaFX version which is modified from the Swing version.
 *
 * @author Paul Chu
 * @date Oct. 16, 2013.
 * 
  */
public class XALSynopticFXPanel extends Canvas {
    /** serialization ID */
    private static final long serialVersionUID = 1L;
    

	private double startPosition;
	private double endPosition;
	private AcceleratorSeq acceleratorSequence;
	private ArrayList<AcceleratorNode> thick = new ArrayList<AcceleratorNode>();
	private ArrayList<AcceleratorNode> thin = new ArrayList<AcceleratorNode>();
	private Insets margin;
	private String[] labels = new String[0];
	private double _wrapShift;	// relevant for rings; it specifies the shift in wrap location where negative numbers start
	
	
	/**
	 * Default constructor.
	 */
	public XALSynopticFXPanel()
	{
		super(450, 100);
		_wrapShift = 0.0;
		
		setStyle("-fx-background-color: white;");
		margin = new Insets(15, 0, 30, 0);
		this.setOpacity(100);
		Tooltip tooltip = new Tooltip("XAL Synoptics");
//		tooltip.setText("XAL Synoptics");
		Tooltip.install(this, tooltip);
		
//		this.setTooltip(tooltip);
//		setToolTipText("XAL Synoptics");
	}

	/**
	 * This runs simple test applet.
	 *
	 * @param args CMD args
	 */
	public static void main(String[] args)
	{
//		try {
//			XALSynopticFXPanel pane = new XALSynopticFXPanel();
//			Accelerator acc = XMLDataManager.loadDefaultAccelerator();
//
//			//pane.setAcceleratorSequence(acc.getSequence("DTL1"));
//			pane.setAcceleratorSequence(acc.getSequence("LS1 TO STRIPPER"));
//			//pane.setStartPosition(2.6);
//			//pane.setEndPosition(4.3);
//
//			JFrame frame = new JFrame();
//			frame.setSize(300, 200);
//			frame.getContentPane().add(pane);
//			frame.addWindowListener(new WindowAdapter() {
//					public void windowClosing(WindowEvent e)
//					{
//						System.exit(0);
//					}
//				});
//			frame.setVisible( true );
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Get the accelerator sequence, which is displayed from first to last by synoptic defined element.
	 * @return the accelerator sequence, which is displayed from first to last by synoptic defined element
	 */
	public AcceleratorSeq getAcceleratorSequence()
	{
		return acceleratorSequence;
	}

	/**
	 * Sets accelerator sequence. Note that this method migth take some
	 * time to finish, if sequence contains a lot of elements.
	 * Uses beginning and end of this sequence for initial start and end position. 
	 *
	 * @param seq
	 */
	public void setAcceleratorSequence(AcceleratorSeq seq)
	{
		/*if (seq == null) {
			return;
		}*/
		
		setAcceleratorSequence(seq,0.0,seq.getLength());

	}

	/**
	 * Sets accelerator sequence and initial start and end position in sequence. 
	 * Note that this method migth take some
	 * time to finish, if sequence contains a lot of elements.
	 *
	 * @param seq
	 */
	public void setAcceleratorSequence(AcceleratorSeq seq, double start, double end)
	{
		/*if (seq == null) {
			return;
		}*/

		acceleratorSequence = seq;

		if (start<end) {
			startPosition = start;
			endPosition = end;
		} else {
			startPosition = end;
			endPosition = start;
		}

		updateSequence();
		
		GraphicsContext gc = this.getGraphicsContext2D();
		this.paintComponent(gc);
		
	}
	
	
	/** 
	 * Set the shift in the location where the wrapping occurs (relevant only for rings)
	 * @param shift the shift (meters) in location along the ring where positions are measured in positive versus negative numbers relative to the origin
	 */
	public void setWrapShift( final double shift ) {
		_wrapShift = shift;
//		repaint();
	}


	private void updateSequence()
	{
		thick.clear();
		thin.clear();

		if (acceleratorSequence == null) {
//			repaint();
			return;
		}

		final List<AcceleratorNode> list = acceleratorSequence.getAllNodes();
		
		// set initial size from sequence
		if (list.size() > 0 && startPosition==endPosition) {
			startPosition = acceleratorSequence.getPosition( list.get(0) ) - list.get(0).getLength() / 2.0;
			endPosition = acceleratorSequence.getPosition( list.get( list.size() - 1 ) ) + list.get( list.size() - 1 ).getLength() / 2.0;
		}
		
		
		final ArrayList<AcceleratorNode> newThick = new ArrayList<AcceleratorNode>(list.size());
		final ArrayList<AcceleratorNode> newThin = new ArrayList<AcceleratorNode>(list.size());

		for ( final AcceleratorNode el : list ) {
			double pos = acceleratorSequence.getPosition(el);
			//System.out.println(pos + " \t" + (pos + el.getLength()) + " \t"
			//    + el.getId() + " \t"
			//    + el.getClass().getName().substring(el.getClass().getName()
			//        .lastIndexOf('.') + 1));

			if (pos >= startPosition && pos <= endPosition) {
				if (el instanceof Bend || el instanceof Quadrupole
				    || el instanceof PermQuadrupole 
//                    || el instanceof RfGap
//				    || el instanceof RfCavityStruct
                    ) {
					newThick.add(el);
				} else {
					newThin.add(el);
				}
			}
		}

		thick = newThick;
		thin = newThin;
//		repaint();
	}

	/**
	 * Get the position in sequence up to which elements are drawn.
	 * @return the position in sequence up to which elements are drawn
	 */
	public double getEndPosition()
	{
		return endPosition;
	}

	/**
	 * Get the position in sequence from which elements are drawn.
	 * @return the position in the sequence from which elements are drawn
	 */
	public double getStartPosition()
	{
		return startPosition;
	}

	/**
	 * Sets position in sequence up to which elements are drawn. Must be more
	 * than start position.
	 *
	 * @param d
	 *
	 * @throws IllegalArgumentException if new end is less then start position
	 */
	public void setEndPosition(double d)
	{
		if (d < startPosition) {
			throw new IllegalArgumentException("New end position (" + d
			    + ") is less than start position (" + startPosition + ").");
		}

		endPosition = d;
//		repaint();
	}

	/**
	 * Sets position in sequence from which elements are drawn. Must be less
	 * than end position.
	 *
	 * @param d
	 *
	 * @throws IllegalArgumentException if new start is more than end position
	 */
	public void setStartPosition(double d)
	{
		if (d > endPosition) {
			throw new IllegalArgumentException("New start position (" + d
			    + ") is more than end position (" + endPosition + ").");
		}

		startPosition = d;
//		repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	private void paintComponent(GraphicsContext g)
	{
		g = super.getGraphicsContext2D();
//		super.paintComponent(g);

		double scale = endPosition - startPosition;

		double width = getWidth() - margin.getRight() - margin.getLeft();
		double height = getHeight() - margin.getTop() - margin.getBottom();

		if (width <= 0 || height <= 0) {
			return;
		}

		if (width != labels.length) {
			labels = new String[(int) Math.round(width)];
		}

		Arrays.fill(labels, null);

		int x = Math.round((int)margin.getLeft());
		int y = (int)(margin.getTop()) + (int)(height / 2.0);
		System.out.println("x = " + x + ", y = " + y + ", width = " + width);
		
		// draw line
		g.setFill(Color.BLACK);

		g.fillRect(x, y, width - 1, 1);
		g.fillRect(x - 1, y - 3, 1, 6);
		g.fillRect(x + width, y - 3, 1, 6);

		if (thin == null || thick == null) {
			return;
		}

		final double sequenceLength = acceleratorSequence != null ? acceleratorSequence.getLength() : 0.0;
		final double wrapLocation = sequenceLength - _wrapShift;
		
		// first draw thick elements
		for ( final AcceleratorNode el : thick ) {
			String name = el.getId();
			double pos = acceleratorSequence.getPosition(el);
			
			final double wrappedPosition = pos <= wrapLocation ? pos : pos - sequenceLength;
			if (wrappedPosition+el.getLength()<startPosition || wrappedPosition-el.getLength()>endPosition) continue;
						
			int ex = x + (int)((wrappedPosition - el.getLength() / 2.0 - startPosition) / scale * (width - 1));
			int l = (int)(el.getLength() / scale * (width - 1));

			if (l < 2) {
				l = 2;
			}

			// components
			
			if (el instanceof Dipole) {
				g.setFill(Color.YELLOW);
				g.fillRect(ex, margin.getTop(), l, height);
				//System.out.println(pos + " \t" + (pos + el.getLength()) + " \t" + ex + " \t" + l);
				
				for (int i = ex - x; i < ex - x + l; i++) {
					addLabel(i, name);
				}
			} 
			else if (el instanceof Quadrupole || el instanceof PermQuadrupole) {
				g.setFill(Color.RED);
				g.fillRect(ex, margin.getTop(), l, height);
				
				for (int i = ex - x; i < ex - x + l; i++) {
					addLabel(i, name);
				}
			} 
			else if (el instanceof Solenoid) {
				g.setFill(Color.GREEN);
				g.fillRect(ex, margin.getTop(), l, height);
				
				for (int i = ex - x; i < ex - x + l; i++) {
					addLabel(i, name);
				}
			}
//			else if (el instanceof RfGap) {
//				g.setColor(Color.gray);
//				
//				if (l < 3) {
//					g.fillRect(ex, margin.top, l, height);
//				} else {
//					g.fillRoundRect(ex, margin.top, l, height, height / 5,
//									height / 5);
//				}
//				
//				for (int i = ex - x; i < ex - x + l; i++) {
//					addLabel(i, name);
//				}
//			} 
//			else if (el instanceof RfCavityStruct) {
//				g.setColor(Color.gray);
//				
//				if (l < 3) {
//					g.drawRect(ex, margin.top - 1, l, height + 1);
//				} else {
//					g.drawRoundRect(ex, margin.top - 1, l, height + 1,
//									height / 10, height / 10);
//				}
//				
//				for (int i = ex - x; i < ex - x + l; i++) {
//					addLabel(i, name);
//				}
//			}
		}
		
		// Draw thin elements
		for ( final AcceleratorNode el : thin ) {
			String name = el.getId();
			double pos = acceleratorSequence.getPosition(el);

			final double wrappedPosition = pos <= wrapLocation ? pos : pos - sequenceLength;
			if (wrappedPosition+el.getLength()<startPosition || wrappedPosition-el.getLength()>endPosition) continue;

			if (el instanceof HDipoleCorr || el instanceof VDipoleCorr) {
				int ex = x + (int)((wrappedPosition - startPosition) / scale * (width - 1));
				g.setFill(Color.BLUE);
				g.fillRect(ex, margin.getTop(), 2, height);

				for (int i = ex - x; i < ex - x + 2; i++) {
					addLabel(i, name);
				}
			} else if (el instanceof BPM || el instanceof ProfileMonitor || el instanceof Marker ) {
				int ex = x + (int)((wrappedPosition - startPosition) / scale * (width - 1));
				if (el instanceof BPM)
					g.setFill(Color.CYAN);
				else if ( el instanceof ProfileMonitor )
					g.setFill(Color.GREEN);
				else
					g.setFill( Color.GRAY );
				
				g.fillRect(ex, margin.getTop(), 1, height);

				for (int i = ex - x; i < ex - x + 1; i++) {
					addLabel(i, name);
				}
			}
		}
	}

	/**
	 * Get the margin around drawing.
	 * @return the margin around drawing
	 */
	public Insets getMargin()
	{
		return margin;
	}

	/**
	 * Sets the margin around drawing, takes effect regardles the border
	 * margins.
	 *
	 * @param insets
	 */
	public void setMargin(Insets insets)
	{
		//System.out.println(insets);
		margin = insets;
//		repaint();
	}

	private void addLabel(int index, String label)
	{
		if (index >= labels.length || index < 0) {
			return;
		}

		if (labels[index] == null) {
			labels[index] = label;
		} else {
			labels[index] = labels[index] + ", " + label;
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
	 */
	public String getToolTipText(MouseEvent event)
	{
//		int i = event.getPoint().x - margin.left;
		int i = (int)Math.round(event.getX() - margin.getLeft());

		if (i >= 0 && i < labels.length) {
			int i_upper = i;
			int i_down = i;
			while(i_upper < (labels.length-1) && labels[i_upper] == null){
				i_upper += 1;
			}
			while(i_down > 0 && labels[i_down] == null){
				i_down -= 1;
			}			
			if(Math.abs(i- i_down) < Math.abs(i_upper -i)){
				return labels[i_down];
			}
			else{
				return labels[i_upper];
			}
			//System.out.println(i + " " + labels[i]);
		} else {
			return this.getToolTipText(event);
//			return super.getToolTipText(event);
		}
	}
}

/* __oOo__ */
