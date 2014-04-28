import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Projet2 extends projet1 implements ActionListener, WindowListener,
		ChangeListener {
	// Set up animation parameters.
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 30;
	static final int FPS_INIT = 10; // initial frames per second
	int frameNumber = 10;
	int NUM_FRAMES = 20;
	ImageIcon[] images = new ImageIcon[NUM_FRAMES];
	int delay;
	Timer timer;
	boolean frozen = false;

	// This label uses ImageIcon to show the doggy pictures.
	JLabel picture;

	public Projet2() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		delay = 100 / FPS_INIT;

		// Create the label.
		JLabel sliderLabel = new JLabel("  Volume", JLabel.CENTER);
		sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		

		JLabel sliderLabel11 = new JLabel("Frequence", JLabel.CENTER);
		sliderLabel11.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel1 = new JLabel(" Claqu�", JLabel.LEFT);
		sliderLabel1.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel6 = new JLabel(" Claqu�Frequence", JLabel.LEFT);
		sliderLabel6.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel2 = new JLabel(" Tonique", JLabel.LEFT);
		sliderLabel2.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel7 = new JLabel(" ToniqueFrequence", JLabel.LEFT);
		sliderLabel2.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel3 = new JLabel(" Bass", JLabel.CENTER);
		sliderLabel3.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel8 = new JLabel(" BassFrequence", JLabel.LEFT);
		sliderLabel8.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel4 = new JLabel(" Mat�Claqu�", JLabel.LEFT);
		sliderLabel4.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel9 = new JLabel(" Mat�Claqu�Frequence", JLabel.LEFT);
		sliderLabel4.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel5 = new JLabel(" Mat�Tonique", JLabel.LEFT);
		sliderLabel5.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sliderLabel10 = new JLabel("Mat�ToniqueFrequence", JLabel.LEFT);
		sliderLabel10.setAlignmentY(Component.CENTER_ALIGNMENT);

		// Create the slider.
		JSlider volume = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX,
				FPS_INIT);
		volume.addChangeListener(this);

		JSlider Frequence = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX,
				FPS_INIT);
		Frequence.addChangeListener(this);

		JSlider Claqu� = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX,
				FPS_INIT);
		Claqu�.addChangeListener(this);

		JSlider Claqu�Frequence = new JSlider(JSlider.HORIZONTAL, FPS_MIN,
				FPS_MAX, FPS_INIT);
		Claqu�Frequence.addChangeListener(this);

		JSlider Tonique = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX,
				FPS_INIT);
		Tonique.addChangeListener(this);

		JSlider ToniqueFrequence = new JSlider(JSlider.HORIZONTAL, FPS_MIN,
				FPS_MAX, FPS_INIT);
		ToniqueFrequence.addChangeListener(this);

		JSlider Bass = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX,
				FPS_INIT);
		Bass.addChangeListener(this);

		JSlider BassFrequence = new JSlider(JSlider.HORIZONTAL, FPS_MIN,
				FPS_MAX, FPS_INIT);
		BassFrequence.addChangeListener(this);

		JSlider Mat�Claqu� = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX,
				FPS_INIT);
		Mat�Claqu�.addChangeListener(this);

		JSlider Mat�Claqu�Frequence = new JSlider(JSlider.HORIZONTAL, FPS_MIN,
				FPS_MAX, FPS_INIT);
		Mat�Claqu�Frequence.addChangeListener(this);

		JSlider Mat�Tonique = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX,
				FPS_INIT);
		Mat�Tonique.addChangeListener(this);

		JSlider Mat�ToniqueFrequence = new JSlider(JSlider.HORIZONTAL, FPS_MIN,
				FPS_MAX, FPS_INIT);
		Mat�ToniqueFrequence.addChangeListener(this);

		// Turn on labels at major tick marks.

		volume.setMajorTickSpacing(10);
		volume.setMinorTickSpacing(1);
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);
		volume.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font = new Font("Serif", Font.ITALIC, 15);
		volume.setFont(font);

		Frequence.setMajorTickSpacing(10);
		Frequence.setMinorTickSpacing(1);
		Frequence.setPaintTicks(true);
		Frequence.setPaintLabels(true);
		Frequence.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font11 = new Font("Serif", Font.ITALIC, 15);
		Frequence.setFont(font);

		Claqu�.setMajorTickSpacing(10);
		Claqu�.setMinorTickSpacing(1);
		Claqu�.setPaintTicks(true);
		Claqu�.setPaintLabels(true);
		Claqu�.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font1 = new Font("Serif", Font.ITALIC, 15);
		Claqu�.setFont(font1);
		Claqu�.setFont(font1);

		Claqu�Frequence.setMajorTickSpacing(10);
		Claqu�Frequence.setMinorTickSpacing(1);
		Claqu�Frequence.setPaintTicks(true);
		Claqu�Frequence.setPaintLabels(true);
		Claqu�Frequence.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font6 = new Font("Serif", Font.ITALIC, 15);
		Claqu�Frequence.setFont(font6);
		Claqu�Frequence.setFont(font6);

		Tonique.setMajorTickSpacing(10);
		Tonique.setMinorTickSpacing(1);
		Tonique.setPaintTicks(true);
		Tonique.setPaintLabels(true);
		Tonique.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font2 = new Font("Serif", Font.ITALIC, 15);
		Tonique.setFont(font2);
		Tonique.setFont(font2);

		ToniqueFrequence.setMajorTickSpacing(10);
		ToniqueFrequence.setMinorTickSpacing(1);
		ToniqueFrequence.setPaintTicks(true);
		ToniqueFrequence.setPaintLabels(true);
		ToniqueFrequence
				.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font7 = new Font("Serif", Font.ITALIC, 15);
		ToniqueFrequence.setFont(font7);
		ToniqueFrequence.setFont(font7);

		Bass.setMajorTickSpacing(10);
		Bass.setMinorTickSpacing(1);
		Bass.setPaintTicks(true);
		Bass.setPaintLabels(true);
		Bass.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font3 = new Font("Serif", Font.ITALIC, 15);
		Bass.setFont(font3);
		Bass.setFont(font3);

		BassFrequence.setMajorTickSpacing(10);
		BassFrequence.setMinorTickSpacing(1);
		BassFrequence.setPaintTicks(true);
		BassFrequence.setPaintLabels(true);
		BassFrequence.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font8 = new Font("Serif", Font.ITALIC, 15);
		BassFrequence.setFont(font8);
		BassFrequence.setFont(font8);

		Mat�Claqu�.setMajorTickSpacing(10);
		Mat�Claqu�.setMinorTickSpacing(1);
		Mat�Claqu�.setPaintTicks(true);
		Mat�Claqu�.setPaintLabels(true);
		Mat�Claqu�.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font4 = new Font("Serif", Font.ITALIC, 15);
		Mat�Claqu�.setFont(font4);
		Mat�Claqu�.setFont(font4);

		Mat�Claqu�Frequence.setMajorTickSpacing(10);
		Mat�Claqu�Frequence.setMinorTickSpacing(1);
		Mat�Claqu�Frequence.setPaintTicks(true);
		Mat�Claqu�Frequence.setPaintLabels(true);
		Mat�Claqu�Frequence.setBorder(BorderFactory.createEmptyBorder(0, 0, 10,
				0));
		Font font9 = new Font("Serif", Font.ITALIC, 15);
		Mat�Claqu�Frequence.setFont(font9);
		Mat�Claqu�Frequence.setFont(font9);

		Mat�Tonique.setMajorTickSpacing(10);
		Mat�Tonique.setMinorTickSpacing(1);
		Mat�Tonique.setPaintTicks(true);
		Mat�Tonique.setPaintLabels(true);
		Mat�Tonique.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font5 = new Font("Serif", Font.ITALIC, 15);
		Mat�Tonique.setFont(font4);
		Mat�Tonique.setFont(font4);

		Mat�ToniqueFrequence.setMajorTickSpacing(10);
		Mat�ToniqueFrequence.setMinorTickSpacing(1);
		Mat�ToniqueFrequence.setPaintTicks(true);
		Mat�ToniqueFrequence.setPaintLabels(true);
		Mat�ToniqueFrequence.setBorder(BorderFactory.createEmptyBorder(0, 0,
				10, 0));
		Font font10 = new Font("Serif", Font.ITALIC, 15);
		Mat�ToniqueFrequence.setFont(font10);
		Mat�ToniqueFrequence.setFont(font10);

		// Create the label that displays the animation.
		picture = new JLabel();
		picture.setVerticalAlignment(JLabel.CENTER);
		picture.setAlignmentX(Component.CENTER_ALIGNMENT);
	

		// Put everything together.
		add(sliderLabel);
		add(volume);
		add(picture);
		sliderLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel11);
		add(Frequence);
		add(picture);
		sliderLabel11.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel1);
		add(Claqu�);
		add(picture);
		sliderLabel1.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel6);
		add(Claqu�Frequence);
		add(picture);
		sliderLabel6.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel2);
		add(Tonique);
		add(picture);
		sliderLabel2.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel7);
		add(ToniqueFrequence);
		add(picture);
		sliderLabel7.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel3);
		add(Bass);
		add(picture);
		sliderLabel3.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel8);
		add(BassFrequence);
		add(picture);
		sliderLabel8.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel4);
		add(Mat�Claqu�);
		add(picture);
		sliderLabel4.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel9);
		add(Mat�Claqu�Frequence);
		add(picture);
		sliderLabel9.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel5);
		add(Mat�Tonique);
		add(picture);
		sliderLabel5.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		add(sliderLabel10);
		add(Mat�ToniqueFrequence);
		add(picture);
		sliderLabel10.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 40));

		// Set up a timer that calls this object's action handler.
		timer = new Timer(delay, this);
		timer.setInitialDelay(delay * 7); // We pause animation twice per cycle
											// by restarting the timer
		timer.setCoalesce(true);
	}

	/** Add a listener for window events. */
	void addWindowListener(Window w) {
		w.addWindowListener(this);
	}

	// React to window events.
	public void windowIconified(WindowEvent e) {
		stopAnimation();
	}

	public void windowDeiconified(WindowEvent e) {
		startAnimation();
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	/** Listen to the slider. */

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			int fps = (int) source.getValue();
			if (fps == 0) {
				if (!frozen)
					stopAnimation();
			} else {
				delay = 1000 / fps;
				timer.setDelay(delay);
				timer.setInitialDelay(delay * 10);
				if (frozen)
					startAnimation();
			}
		}
	}

	public void startAnimation() {
		// Start (or restart) animating!
		timer.start();
		frozen = false;
	}

	public void stopAnimation() {
		// Stop the animating thread.
		timer.stop();
		frozen = true;
	}

	// Called when the Timer fires.
	public void actionPerformed(ActionEvent e) {
		// Advance the animation frame.
		if (frameNumber == (NUM_FRAMES - 1)) {
			frameNumber = 0;
		} else {
			frameNumber++;
		}

		updatePicture(frameNumber); // display the next picture

		if (frameNumber == (NUM_FRAMES - 1)
				|| frameNumber == (NUM_FRAMES / 2 - 1)) {
			timer.restart();
		}
	}

	/** Update the label to display the image for the current frame. */
	protected void updatePicture(int frameNum) {
		// Get the image if we haven't already.
		String fname = "images\\doggy\\T" + frameNumber + ".gif";
		if (images[frameNumber] == null) {
			images[frameNumber] = new ImageIcon(fname);
		}

		// Set the image.
		if (images[frameNumber] != null) {
			picture.setIcon(images[frameNumber]);
		} else { // image not found
			picture.setText("image #" + frameNumber + " not found");
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("SliderDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Projet2 animator = new Projet2();

		// Add content to the window.
		frame.add(animator, BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
		animator.startAnimation();
	}

	public static void main(String[] args) {
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}