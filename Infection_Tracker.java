import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.BoundedRangeModel;

import java.util.*;


/**
 * Author: Kareem Idris, Student#: 0881393 Section:, Geraldine Lopez Student#: 0876012 Section: 2, Richard Gagne Student#: 0979551 Section: 4, Matt Daly Student#: 1022438 Section 4
 * Program: Population.java
 * Purpose: Main method and GUI
 * Date: Aug 10, 2022
 */
public class Infection_Tracker extends JFrame {
	JPanel winPane, buttPane, slidPane, updatePane;
	public int pop = 200;
	Population populationVar;
	private boolean isStarted = false;
	private boolean isPaused = false;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem01;
	JMenuItem menuItem02;
	Timer sim_Timer;
	

	// ---------------------\\
	//     Update Elements  \\
	// ---------------------\\
	JPanel updateGroup0, updateGroup1, updateGroup2, updateGroup3, updateGroup4, updateGroup5, updateGroup6,
			updateGroup7;
	JLabel updateImmune, updateNImmune, updateOneShot, updateTwoShot, updateThreeShot, updateInfected, updateDied,
			updateRecovered;
	JLabel updateImmuneVal, updateNImmuneVal, updateOneShotVal, updateTwoShotVal, updateThreeShotVal, updateInfectedVal,
			updateDiedVal, updateRecoveredVal;
	Double updateImmuneNum, updateNImmuneNum, updateOneShotNum, updateTwoShotNum, updateThreeShotNum, updateInfectedNum,
			updateDiedNum, updateRecoveredNum;
	Font UpdateFont = new Font("Helvetica", Font.BOLD, 14);

	public Infection_Tracker() {
		super("Project 01 Infection Tracker");

		int frameWidth = 1000;
		int frameHeight = 800;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(frameWidth, frameHeight);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());

		JMenuBar mb = new JMenuBar();
		menu = new JMenu("Menu");

		// ABOUT OPTION
		menuItem01 = new JMenuItem("About");
		menuItem01.setFont(new Font("Helvetica", Font.PLAIN, 13));
		menu.add(menuItem01);
		mb.add(menu);
		Infection_Tracker.this.setJMenuBar(mb);
		menuItem01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, aboutPrintout(), "Version Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// INSTRUCTIONS OPTION
		menuItem02 = new JMenuItem("Instructions");
		menuItem02.setFont(new Font("Helvetica", Font.PLAIN, 13));
		menu.add(menuItem02);
		menuItem02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, instructionPrintout(), "Instructions",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// -------------------\\
		//     winPane setup  \\
		// -------------------\\

		winPane = new JPanel();
		winPane.setLayout(new FlowLayout(FlowLayout.CENTER, (int) (frameWidth * .05), (int) (frameHeight * .05)));
		winPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		winPane.setPreferredSize(new Dimension((int) (frameWidth * .3), (int) (frameHeight * .6)));
		this.add(winPane, BorderLayout.CENTER);

		// --------------------\\
		//    buttPane setup   \\
		// --------------------\\

		buttPane = new JPanel();
		buttPane.setLayout(new FlowLayout(FlowLayout.CENTER, (int) (frameWidth * .05), (int) (frameHeight * .09)));
		buttPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		buttPane.setPreferredSize(new Dimension((int) (frameWidth * .7), (int) (frameHeight * .3)));
		this.add(buttPane, BorderLayout.SOUTH);

		// --------------------\\
		//    slidPane setup   \\
		// --------------------\\

		slidPane = new JPanel();
		slidPane.setLayout(new GridLayout(12, 2));
		slidPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		slidPane.setPreferredSize(new Dimension((int) (frameWidth * .25), frameHeight));
		this.add(slidPane, BorderLayout.EAST);

		// ------------------\\
		//    Update Setup   \\
		// ------------------\\

		updatePane = new JPanel();
		
		updatePane.setLayout(new BorderLayout());
		updatePane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		updatePane.setPreferredSize(new Dimension((int) (frameWidth * .25), frameHeight));
		this.add(updatePane, BorderLayout.WEST);

		

		// ----------------------\\
		//   winPane elements    \\
		// ----------------------\\

		
		int winWidth = (int) (frameWidth * .3);

		JPanel ballWin = new JPanel();
		ballWin.setLayout(new BoxLayout(ballWin, BoxLayout.LINE_AXIS));
		ballWin.setPreferredSize(new Dimension((int) (winWidth * 1.5), (int) (winWidth * 1.5)));
		ballWin.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		ballWin.setVisible(false);
		winPane.add(ballWin);

		// -----------------------\\
		//    slidPane Elements   \\
		// -----------------------\\

		JPanel labelGroup1, labelGroup2, labelGroup3, labelGroup4, labelGroup5, labelGroup6;
		JLabel Immun_Lbl, N_Immun_Lbl, ONE_Shot_Lbl, TWO_Shot_Lbl, THREE_Shot_Lbl, Pop_Lbl;
		JLabel threeshotpercentage;
		JLabel Immun_Lbl_Val, N_Immun_Lbl_Val, ONE_Shot_Lbl_Val, TWO_Shot_Lbl_Val, THREE_Shot_Lbl_Val, Pop_Lbl_Val;
		JSlider Immun_Sldr, N_Immun_Sldr, ONE_Shot_Sldr, TWO_Shot_Sldr, THREE_Shot_Sldr, Pop_Sldr;
		BoundedRangeModel Immun_Sldr_Model, N_Immun_Sldr_Model, ONE_Shot_Sldr_Model, TWO_Shot_Sldr_Model,
				THREE_Shot_Sldr_Model, Pop_Sldr_Model;

		Font SliderFont = new Font("Helvetica", Font.BOLD, 14);

		// -------------------\\
		//    Immune Slider   \\
		// -------------------\\

		Immun_Sldr = new JSlider(0, 2500, 0);
		Immun_Sldr_Model = Immun_Sldr.getModel();
		Immun_Sldr.setMajorTickSpacing(500);
		Immun_Sldr.setMinorTickSpacing(250);
		Immun_Sldr.setPaintTicks(true);
		Immun_Sldr.setPaintLabels(true);
		Immun_Lbl = new JLabel("NUMBER IMMUNE: ");
		Immun_Lbl_Val = new JLabel("0");
		Immun_Lbl.setHorizontalAlignment(JLabel.CENTER);
		labelGroup1 = new JPanel();
		Immun_Lbl.setFont(SliderFont);
		Immun_Lbl_Val.setFont(SliderFont);
		labelGroup1.add(Immun_Lbl);
		labelGroup1.add(Immun_Lbl_Val);

		Immun_Sldr.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				int tempOutVal = Immun_Sldr_Model.getValue();
				String temp = Integer.toString(tempOutVal);
				Immun_Lbl_Val.setText(temp);
			}
		});

		// -----------------------\\
		//    Not Immune Slider   \\
		// -----------------------\\

		N_Immun_Sldr = new JSlider(0, 2500, 0);
		N_Immun_Sldr_Model = N_Immun_Sldr.getModel();
		N_Immun_Sldr.setMajorTickSpacing(500);
		N_Immun_Sldr.setMinorTickSpacing(250);
		N_Immun_Sldr.setPaintTicks(true);
		N_Immun_Sldr.setPaintLabels(true);
		N_Immun_Lbl = new JLabel("NUMBER NOT IMMUNE: ");
		N_Immun_Lbl_Val = new JLabel("0");
		N_Immun_Lbl.setHorizontalAlignment(JLabel.CENTER);
		N_Immun_Lbl.setFont(SliderFont);
		N_Immun_Lbl_Val.setFont(SliderFont);
		labelGroup2 = new JPanel();
		labelGroup2.add(N_Immun_Lbl);
		labelGroup2.add(N_Immun_Lbl_Val);

		N_Immun_Sldr.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int tempOutVal = N_Immun_Sldr_Model.getValue();
				String temp = Integer.toString(tempOutVal);
				N_Immun_Lbl_Val.setText(temp);
			}
		});

		// ---------------------\\
		//    One Shot Slider   \\
		// ---------------------\\

		ONE_Shot_Sldr = new JSlider(0, 2500, 0);
		ONE_Shot_Sldr_Model = ONE_Shot_Sldr.getModel();
		ONE_Shot_Sldr.setMajorTickSpacing(500);
		ONE_Shot_Sldr.setMinorTickSpacing(250);
		ONE_Shot_Sldr.setPaintTicks(true);
		ONE_Shot_Sldr.setPaintLabels(true);
		ONE_Shot_Lbl = new JLabel("NUMBER WITH ONE SHOT: ");
		ONE_Shot_Lbl_Val = new JLabel("0");
		ONE_Shot_Lbl.setHorizontalAlignment(JLabel.CENTER);
		ONE_Shot_Lbl.setFont(SliderFont);
		ONE_Shot_Lbl_Val.setFont(SliderFont);
		labelGroup3 = new JPanel();
		labelGroup3.add(ONE_Shot_Lbl);
		labelGroup3.add(ONE_Shot_Lbl_Val);

		ONE_Shot_Sldr.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int tempOutVal = ONE_Shot_Sldr_Model.getValue();
				String temp = Integer.toString(tempOutVal);
				ONE_Shot_Lbl_Val.setText(temp);
			}
		});

		// ---------------------\\
		//    Two Shot Slider   \\
		// ---------------------\\

		TWO_Shot_Sldr = new JSlider(0, 2500, 0);
		TWO_Shot_Sldr_Model = TWO_Shot_Sldr.getModel();
		TWO_Shot_Sldr.setMajorTickSpacing(500);
		TWO_Shot_Sldr.setMinorTickSpacing(250);
		TWO_Shot_Sldr.setPaintTicks(true);
		TWO_Shot_Sldr.setPaintLabels(true);
		TWO_Shot_Lbl = new JLabel("NUMBER WITH TWO SHOTS: ");
		TWO_Shot_Lbl_Val = new JLabel("0");
		TWO_Shot_Lbl.setHorizontalAlignment(JLabel.CENTER);
		TWO_Shot_Lbl.setFont(SliderFont);
		TWO_Shot_Lbl_Val.setFont(SliderFont);
		labelGroup4 = new JPanel();
		labelGroup4.add(TWO_Shot_Lbl);
		labelGroup4.add(TWO_Shot_Lbl_Val);

		TWO_Shot_Sldr.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int tempOutVal = TWO_Shot_Sldr_Model.getValue();
				String temp = Integer.toString(tempOutVal);
				TWO_Shot_Lbl_Val.setText(temp);
			}
		});

		// -----------------------\\
		//    Three Shot Slider   \\
		// -----------------------\\

		THREE_Shot_Sldr = new JSlider(0, 2500, 0);
		THREE_Shot_Sldr_Model = THREE_Shot_Sldr.getModel();
		THREE_Shot_Sldr.setMajorTickSpacing(500);
		THREE_Shot_Sldr.setMinorTickSpacing(250);
		THREE_Shot_Sldr.setPaintTicks(true);
		THREE_Shot_Sldr.setPaintLabels(true);
		THREE_Shot_Lbl = new JLabel("NUMBER WITH THREE SHOTS: ");
		

		THREE_Shot_Lbl_Val = new JLabel("0");
		THREE_Shot_Lbl.setHorizontalAlignment(JLabel.CENTER);
		THREE_Shot_Lbl.setFont(SliderFont);
		THREE_Shot_Lbl_Val.setFont(SliderFont);
		labelGroup5 = new JPanel();
		labelGroup5.add(THREE_Shot_Lbl);
		labelGroup5.add(THREE_Shot_Lbl_Val);

		THREE_Shot_Sldr.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int tempOutVal = THREE_Shot_Sldr_Model.getValue();
				String temp = Integer.toString(tempOutVal);
				THREE_Shot_Lbl_Val.setText(temp);
			}
		});

		// ---------------------\\
		//    Pop Shot Slider   \\
		// ---------------------\\

		Pop_Sldr = new JSlider(200, 2000, 200);
		Pop_Sldr_Model = Pop_Sldr.getModel();
		Pop_Sldr.setMajorTickSpacing(500);
		Pop_Sldr.setMinorTickSpacing(250);
		Pop_Sldr.setPaintTicks(true);
		Pop_Sldr.setPaintLabels(true);
		Pop_Lbl = new JLabel("POPULATION: ");
		Pop_Lbl_Val = new JLabel("200");
		Pop_Lbl.setHorizontalAlignment(JLabel.CENTER);
		Pop_Lbl.setFont(SliderFont);
		Pop_Lbl_Val.setFont(SliderFont);
		labelGroup6 = new JPanel();
		labelGroup6.add(Pop_Lbl);
		labelGroup6.add(Pop_Lbl_Val);

		Pop_Sldr.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int tempOutVal = Pop_Sldr_Model.getValue();
				Pop_Lbl_Val.setText(Integer.toString(tempOutVal));
				pop = tempOutVal;
			}
		});

		// --------------------------------\\
		//    Update Infected Population   \\
		// --------------------------------\\

		updateInfected = new JLabel("INFECTED COUNT : ");
		updateInfected.setFont(UpdateFont);
		updateInfectedVal = new JLabel("0");
		updateInfectedVal.setFont(UpdateFont);
		updateInfectedVal.setAlignmentX(RIGHT_ALIGNMENT);

		// --------------------------------\\
		//    Update Immune Population %   \\
		// --------------------------------\\

		updateImmune = new JLabel("IMMUNE POP %        ");
		updateImmuneVal = new JLabel("0%");
		updateImmune.setFont(UpdateFont);
		updateImmuneVal.setFont(UpdateFont);
		updateImmuneVal.setAlignmentX(RIGHT_ALIGNMENT);
		
		// ------------------------------------\\
		//    Update Not Immune Population %   \\
		// ------------------------------------\\

		updateNImmune = new JLabel("N IMMUNE POP* %    ");
		updateNImmuneVal = new JLabel("0%");
		updateNImmune.setFont(UpdateFont);
		updateNImmuneVal.setFont(UpdateFont);
		
		// ----------------------------------\\
		//    Update One Shot Population %   \\
		// ----------------------------------\\

		updateOneShot = new JLabel("ONE SHOT POP %     ");
		updateOneShotVal = new JLabel("0%");
		updateOneShot.setFont(UpdateFont);
		updateOneShotVal.setFont(UpdateFont);
		
		// ----------------------------------\\
		//   Update Two Shot Population %    \\
		// ----------------------------------\\

		updateTwoShot = new JLabel("TWO SHOT POP %    ");
		updateTwoShotVal = new JLabel("0%");
		updateTwoShot.setFont(UpdateFont);
		updateTwoShotVal.setFont(UpdateFont);
		
		// ------------------------------------\\
		//   Update Three Shot Population %    \\
		// ------------------------------------\\

		updateThreeShot = new JLabel("THREE SHOT POP % ");
		updateThreeShotVal = new JLabel("0%");
		updateThreeShot.setFont(UpdateFont);
		updateThreeShotVal.setFont(UpdateFont);
		
		// ------------------------\\
		//   Update Died Number    \\
		// ------------------------\\

		updateDied = new JLabel("DEATH COUNT :       ");
		updateDiedVal = new JLabel("0");
		updateDied.setFont(UpdateFont);
		updateDiedVal.setFont(UpdateFont);
		
		// -----------------------------\\
		//   Update Recovered Number    \\
		// -----------------------------\\

		updateRecovered = new JLabel("RECOVERED COUNT :");
		updateRecovered.setAlignmentX(LEFT_ALIGNMENT);
		updateRecoveredVal = new JLabel("0");
		updateRecovered.setFont(UpdateFont);
		updateRecoveredVal.setFont(UpdateFont);
	

		

		// --------------------------------\\
		//              JTabel             \\
		// --------------------------------\\

		JTable updateTable;

		Object[][] rowData = {
				{ updateInfected.getText(), updateInfectedVal.getText() },
				{ updateImmune.getText(), updateImmuneVal.getText() },
				{ updateNImmune.getText(), updateNImmuneVal.getText() },
				{ updateOneShot.getText(), updateOneShotVal.getText() },
				{ updateTwoShot.getText(), updateTwoShotVal.getText() },
				{ updateThreeShot.getText(), updateThreeShotVal.getText() },
				{ updateDied.getText(), updateDiedVal.getText() },
				{ updateRecovered.getText(), updateRecoveredVal.getText() }
		};

		DefaultTableCellRenderer centRend = new DefaultTableCellRenderer();
		String[] columnNames = { "", "" };
		DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		
		updateTable = new JTable(model);
		updateTable.setFont(UpdateFont);
		updateTable.getColumnModel().getColumn(1).setPreferredWidth(1);
		updateTable.setRowHeight(62);
		centRend.setHorizontalAlignment(JLabel.CENTER);
		updateTable.getColumnModel().getColumn(1).setCellRenderer(centRend);
		updateTable.setShowGrid(false);

		ActionListener updateAllNumbers = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setValueAt(populationVar.getNumDied(), 6, 1);
				model.setValueAt(populationVar.getNumInfected(), 0, 1);
				model.setValueAt(populationVar.getNumRecovered(), 7, 1);
				model.setValueAt(populationVar.getImmune(), 1, 1);
				model.setValueAt(populationVar.getNImmune(), 2, 1);
				model.setValueAt(populationVar.getOneShot(), 3, 1);
				model.setValueAt(populationVar.getTwoShot(), 4, 1);
				model.setValueAt(populationVar.getThreeShot(), 5, 1);
			}
		};
		Timer updateTimer = new Timer(250, updateAllNumbers);

		// -----------------------\\
		//    buttPane Elements   \\
		// -----------------------\\

		Font buttFont = new Font("Helvetica", Font.PLAIN, 14);
		Dimension buttDimension = new Dimension(150, 100);

		
		
		ActionListener testEvent = new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				populationVar.TimeStopper();
				isPaused = true;
				
				updateTimer.stop();
				//----------------Report Print --------------\\
				String[] data = new String[model.getRowCount()];
				for(int i = 0; i < model.getRowCount(); i++)
				{
					data[i] = model.getValueAt(i, 1).toString();
				}			
				JOptionPane jop = new JOptionPane();
				JTextArea repText = new JTextArea();
				String reportString = finalReport(data);
				repText.setEditable(false);
				repText.setText(reportString);				
				repText.setFont(new Font("Consolas", Font.BOLD, 14));			
				jop.showMessageDialog(null, repText, "Final Report", JOptionPane.PLAIN_MESSAGE);
				//--------------------------------------------\\
				sim_Timer.stop();
			}
		};
		
		
	     sim_Timer = new Timer(3000, testEvent);
		
		// ------------------\\
		//    Start Button   \\
		// ------------------\\

		JButton startBtn = new JButton("START");
		startBtn.setPreferredSize(buttDimension);
		startBtn.setFont(buttFont);

		startBtn.addActionListener(new ActionListener() {
			int imm, nim, s1, s2, s3;

			public void actionPerformed(ActionEvent ev) {
				updateTimer.start();
				sim_Timer.start();
				imm = Immun_Sldr_Model.getValue();
				nim = N_Immun_Sldr_Model.getValue();
				s1 = ONE_Shot_Sldr_Model.getValue();
				s2 = TWO_Shot_Sldr_Model.getValue();
				s3 = THREE_Shot_Sldr_Model.getValue();
				if (imm > pop) {

					JOptionPane.showInternalMessageDialog(null,
							"WARNING: Number Immune cannot be set higher than Population", "Validation Error",
							JOptionPane.INFORMATION_MESSAGE);
				} 
				 else if (nim > pop) {
					JOptionPane.showInternalMessageDialog(null,
							"WARNING: Number Not Immune cannot be set higher than Population", "Validation Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (s1 > pop) {
					JOptionPane.showInternalMessageDialog(null,
							"WARNING: Number With One Shot cannot be set higher than Population", "Validation Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (s2 > pop) {
					JOptionPane.showInternalMessageDialog(null,
							"WARNING: Number With Two Shots cannot be set higher than Population", "Validation Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (s3 > pop) {
					JOptionPane.showInternalMessageDialog(null,
							"WARNING: Number With Three Shots cannot be set higher than Population", "Validation Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (imm + nim + s1 + s2 + s3 > pop) {
					JOptionPane.showInternalMessageDialog(null, "WARNING: Total Variables Cannot exceed Population",
							"Validation Error", JOptionPane.INFORMATION_MESSAGE);
				} else {

					populationVar = new Population(pop, imm, nim, s1, s2, s3);

					populationVar.TimeResume();
					ballWin.setVisible(true);

					if (!isStarted) {

						ballWin.add(populationVar);
						updatePercent();

						startBtn.setEnabled(false);
						isStarted = true;

					}

				}
			}
		});
		
		

		// ------------------\\
		//    Clear Button   \\
		// ------------------\\

		JButton clearBtn = new JButton("CLEAR");
		clearBtn.setPreferredSize(buttDimension);
		clearBtn.setFont(buttFont);

		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {				
				ballWin.setVisible(false);
				
				
				
				
				Immun_Sldr.setValue(0);
				N_Immun_Sldr.setValue(0);
				ONE_Shot_Sldr.setValue(0);
				TWO_Shot_Sldr.setValue(0);
				THREE_Shot_Sldr.setValue(0);
				Pop_Sldr.setValue(200);
				Immun_Lbl_Val.setText("0");
				N_Immun_Lbl_Val.setText("0");
				ONE_Shot_Lbl_Val.setText("0");
				TWO_Shot_Lbl_Val.setText("0");
				THREE_Shot_Lbl_Val.setText("0");
				model.setValueAt("0", 0, 1);
				model.setValueAt("0", 1, 1);
				model.setValueAt("0", 2, 1);
				model.setValueAt("0", 3, 1);
				model.setValueAt("0", 4, 1);
				model.setValueAt("0", 5, 1);
				model.setValueAt("0", 6, 1);
				model.setValueAt("0", 7, 1);

				Pop_Lbl_Val.setText("200");
				ballWin.removeAll();
				isStarted = false;
				startBtn.setEnabled(true);
				updateTimer.restart();
				updateTimer.stop();
				sim_Timer.restart();
				sim_Timer.stop();
			}
		});

		// -------------------\\
		//    Resume Button   \\
		// -------------------\\

		JButton resumeBtn = new JButton("RESUME");
		resumeBtn.setPreferredSize(buttDimension);
		resumeBtn.setFont(buttFont);
		resumeBtn.setEnabled(false);
		resumeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
			
				populationVar.TimeResume();
				isPaused = false;
				resumeBtn.setEnabled(false);
				updateTimer.start();
			}
		});

		// ------------------\\
		//    Pause Button   \\
		// ------------------\\

		JButton pauseBtn = new JButton("PAUSE");
		pauseBtn.setPreferredSize(buttDimension);
		pauseBtn.setFont(buttFont);

		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
				 
				populationVar.TimeStopper();
				isPaused = true;
				if (isPaused) {
					resumeBtn.setEnabled(true);
				}
				updateTimer.stop();
			}
		});

	    
		// --------------------------------\\
		//    Add Buttons to Button Pane   \\
		// --------------------------------\\

		buttPane.add(startBtn);
		buttPane.add(pauseBtn);
		buttPane.add(clearBtn);
		buttPane.add(resumeBtn);

		// --------------------------------\\
		//   Add Sliders to Slider Pane    \\
		// --------------------------------\\

		slidPane.add(labelGroup1);
		slidPane.add(Immun_Sldr);
		slidPane.add(labelGroup2);
		slidPane.add(N_Immun_Sldr);
		slidPane.add(labelGroup3);
		slidPane.add(ONE_Shot_Sldr);
		slidPane.add(labelGroup4);
		slidPane.add(TWO_Shot_Sldr);
		slidPane.add(labelGroup5);
		slidPane.add(THREE_Shot_Sldr);
		slidPane.add(labelGroup6);
		slidPane.add(Pop_Sldr);

		// ---------------------------------\\
		//           Add Update Table       \\
		// ---------------------------------\\
		updatePane.add(updateTable, BorderLayout.CENTER);

		this.setVisible(true);
	}

	/*
	 * Function: updatePercent()
	 * Purpose: Get the number of each immunity level and display the percentage of
	 * the population
	 * that has the immunity
	 * Accepts: N/A
	 * Returns: N/A
	 */
	private void updatePercent() {
		updateImmuneNum = getPopulationPercentage(populationVar.getImmune());
		updateImmuneVal.setText(percentFormat(updateImmuneNum));

		updateNImmuneNum = getPopulationPercentage(populationVar.getNImmune());
		updateNImmuneVal.setText(percentFormat(updateNImmuneNum));

		updateOneShotNum = getPopulationPercentage(populationVar.getOneShot());
		updateOneShotVal.setText(percentFormat(updateOneShotNum));

		updateTwoShotNum = getPopulationPercentage(populationVar.getTwoShot());
		updateTwoShotVal.setText(percentFormat(updateTwoShotNum));

		updateThreeShotNum = getPopulationPercentage(populationVar.getThreeShot());
		updateThreeShotVal.setText(percentFormat(updateThreeShotNum));
	}

	/*
	 * Function: percentFormat()
	 * Purpose: Format the Population Percentage output using a String Format
	 * Accepts: Double
	 * Returns: String with % appended to the double (displayed to 2 decimal places)
	 */
	protected String percentFormat(Double d) {
		return String.format("%.2f%%", d);
	}

	public String instructionPrintout() {
		String retString = "";

		retString += "Instructions:\n"
				+ "\n"
				+ "1.   Set input values using sliders in the right hand column.\n"
				+ "2.   Press Start Button on bottom to run program.\n"
				+ "3.   Press Pause to pause execution.\n"
				+ "4.   Press Resume to resume execution.\n"
				+ "5.   Press Clear to zero out sliders and re-enter data.\n"
				+ "\nColour Values:\n\n"
				+ "RED: Infected\n"
				+ "BLUE: No Immunity - 10% Chance of Death\n"
				+ "CYAN: One Vaccine Shot - 7% Chance of Death\n"
				+ "YELLOW: Two Vaccine Shots 3% Chance of Death\n"
				+ "MAGENTA: Three Vaccine Shots - 1% Chance of Death\n"
				+ "GREEN: Recovered - Immune\n"
				+ "BLACK: Dead - Also technically Immune if you think about it";

		return retString;
	}

	public String aboutPrintout() {
		String retString = "This program was created by:\n" +
				"\tMatt Daly\n" +
				"\tKareem Idris\n" +
				"\tGeraldine (GG) Lopez\n" +
				"\tRichard Gagne\n" +
				"Version: 5\n" +
				"Date: August 4, 2022";
		return retString;
	}
	
	public String finalReport(String[] data)
	{
		String retString = "";
		retString += String.format("Final Report\n"
				   + "\n"
				   + "%-25s%-5s%n"
				   + "%-25s%-5s%n"
				   + "%-25s%-5s%n" 
				   + "%-25s%-5s%n"
				   + "%-25s%-5s%n"
				   + "%-25s%-5s%n"
				   + "%-25s%-5s%n"
				   + "%-25s%-5s%n","Infected:",data[0],"Developed Immunity:",data[1],"No Immunity:", data[2], "One Shot:", data[3],"Two Shots:",data[4],"Three Shots:", data[5],"Dead:", data[6],"Recovered:", data[7]);
		
		
		
		return retString;
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Infection_Tracker InTr = new Infection_Tracker();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}

	}

	public Double getPopulationPercentage(int infectionState) {
		return (infectionState / (double) populationVar.getPopulation() * 100.0);
	}

}
