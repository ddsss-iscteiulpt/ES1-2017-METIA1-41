package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import readRules.ReadRules;
/**
 * GUI.java- esta classe cria a interfaceGrafica
 * e especidfica todas as funcionalidades desta
 *
 */
public class GUI {

	private JFrame frame;
	private File rulesFile;
	private File hamFile;
	
	
	
	private File spamFile;
	private ReadRules rf;

	

	private JList<String> rulesList;
	private JTextArea weightList;
	private DefaultListModel<String> lista;
	private JTextField rulesPath;
	private DefaultListModel<String> listaAuto;
	private JTextField spamPath;
	private JTextField hamPath;
	private JTextField fp;
	private JTextField fn;

	private JButton searchRules;
	private JButton searchHam;
	private JButton searchSpam;

	private JButton avQualidade;
	private JButton avQualidadeAuto;
	private JButton guardar;


	private JTextArea weightListAuto;
	private JTextField fpAuto;
	private JTextField fnAuto;
	public static final GUI INSTANCE = new GUI();
	
	private ReadRules rf_automatico = new ReadRules();
	
	private JList<String> rulesListAuto;

	public static GUI getInstance() {
		return INSTANCE;
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Constructor
	 * 
	 */
	public GUI() {
		super();
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		buildGui();
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	
	

	/**
	 * este metodo chama outros metodos para criar a interface grafica
	 */
	public void buildGui() {

		addPathPanel();
		addManualConfig();
		addAutoConfig();

	}
	 
	
	public void addPathPanel() {
		JPanel selectFiles = new JPanel();

		JPanel pathPanel = new JPanel();
		pathPanel.setLayout(new GridLayout(3, 2));

		rulesPath = new JTextField();
		rulesPath.setFont(new Font("Arial", Font.PLAIN, 16));
		rulesPath.setPreferredSize(new Dimension(500, 30));
		pathPanel.add(rulesPath);
		
		searchRules = new JButton("Ficheiro de Regras");
		searchRules.setPreferredSize(new Dimension(100, 30));
		searchRules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CF files", "cf");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (fileChooser.getSelectedFile().isFile()) {
						rulesFile = fileChooser.getSelectedFile();
						
						GUI.getInstance().getRulesPath().setText(rulesFile.getPath());
						try {
							rf = new ReadRules();
							
							rf.read(true, rulesFile);
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							// e1.printStackTrace();
							System.out.println("Ficheiro n�o localizado");
						}
					}

				}

			}

		});
		pathPanel.add(searchRules);


		hamPath = new JTextField();
		hamPath.setFont(new Font("Arial", Font.PLAIN, 16));
		hamPath.setPreferredSize(new Dimension(500, 30));
		pathPanel.add(hamPath);
		
		searchHam = new JButton("Ficheiro Ham");
		searchHam.setPreferredSize(new Dimension(100, 30));
		searchHam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("ola");
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (fileChooser.getSelectedFile().isFile()) {
						hamFile = fileChooser.getSelectedFile();
						hamPath.setText(hamFile.getPath());

					}

				}

			}

		});
		pathPanel.add(searchHam);
		
		spamPath = new JTextField();
		spamPath.setFont(new Font("Arial", Font.PLAIN, 16));
		spamPath.setPreferredSize(new Dimension(500, 30));
		pathPanel.add(spamPath);



		searchSpam = new JButton("Ficheiro Spam");
		searchSpam.setPreferredSize(new Dimension(100, 30));
		searchSpam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (fileChooser.getSelectedFile().isFile()) {
						spamFile = fileChooser.getSelectedFile();
						spamPath.setText(spamFile.getPath());

					}

				}

			}

		});
		pathPanel.add(searchSpam);

		selectFiles.add(pathPanel);

		frame.add(selectFiles, BorderLayout.NORTH);

	}

	public JTextField getHamPath() {
		return hamPath;
	}

	public void setHamPath(JTextField hamPath) {
		this.hamPath = hamPath;
	}

	public JTextField getSpamPath() {
		return spamPath;
	}

	public void addManualConfig() {
		
		lista = new DefaultListModel<String>();

		JPanel manualConfig = new JPanel();

		JLabel label = new JLabel("Configura��o Manual");
		label.setPreferredSize(new Dimension(150,20));
		manualConfig.add(label);

		JPanel rulesPanel = new JPanel();
		rulesPanel.setLayout(new GridLayout(1, 2));
		rulesPanel.setSize(300, 300);

		rulesList = new JList<String>(lista);

		weightList = new JTextArea();

		// Aumentar tamanho dos numeros
		Font font = weightList.getFont();
		float size = font.getSize() + 1.0f;
		weightList.setFont(font.deriveFont(size));

		JScrollPane scrollArea1 = new JScrollPane(rulesList, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JScrollPane scrollArea2 = new JScrollPane(weightList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollArea2.getVerticalScrollBar().setModel(scrollArea1.getVerticalScrollBar().getModel());

		rulesPanel.add(scrollArea1);
		rulesPanel.add(scrollArea2);

	
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(2, 1));

		avQualidade = new JButton("Av. qualidade");
		avQualidade.setPreferredSize(new Dimension(120, 30));
		buttonsPanel.add(avQualidade);

		guardar = new JButton("Guardar");
		guardar.addActionListener(new ListenerForGuardar());
		guardar.setPreferredSize(new Dimension(120, 30));
		buttonsPanel.add(guardar);

		manualConfig.add(rulesPanel);
		manualConfig.add(buttonsPanel);

		JPanel fpFn = new JPanel();

		JLabel fpLabel = new JLabel("FP");
		fpFn.add(fpLabel);

		fp = new JTextField();
		fp.setPreferredSize(new Dimension(50, 50));
		fpFn.add(fp);

		JLabel fnLabel = new JLabel("FN");
		fpFn.add(fnLabel);

		fn = new JTextField();
		fn.setPreferredSize(new Dimension(50, 50));
		fpFn.add(fn);

		manualConfig.add(fpFn);

		frame.add(manualConfig, BorderLayout.CENTER);


	}

	public void addAutoConfig() {
		listaAuto = new DefaultListModel<String>();

		JPanel autoConfig = new JPanel();
		
		JLabel label = new JLabel("Configura��o Autom�tica");
		label.setPreferredSize(new Dimension(150,20));
		autoConfig.add(label);

		JPanel rulesPanelAuto = new JPanel();
		rulesPanelAuto.setLayout(new GridLayout(1, 2));

		rulesListAuto = new JList<String>(listaAuto);

		weightListAuto = new JTextArea();
		weightListAuto.setEditable(false);

		rulesPanelAuto.add(rulesListAuto);
		
		// Aumentar tamanho dos numeros
				Font font = weightListAuto.getFont();
				float size = font.getSize() + 1.0f;
				weightListAuto.setFont(font.deriveFont(size));
		
		JScrollPane scrollArea1 = new JScrollPane(rulesListAuto, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JScrollPane scrollArea2 = new JScrollPane(weightListAuto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollArea2.getVerticalScrollBar().setModel(scrollArea1.getVerticalScrollBar().getModel());

		rulesPanelAuto.add(scrollArea1);
		rulesPanelAuto.add(scrollArea2);

		

		JPanel buttonsPanelAuto = new JPanel();
		buttonsPanelAuto.setLayout(new GridLayout(2, 1));

		avQualidadeAuto = new JButton("Av. qualidade");
		avQualidadeAuto.setPreferredSize(new Dimension(120, 30));
		buttonsPanelAuto.add(avQualidadeAuto);

		JButton guardarAuto = new JButton("Guardar");
		guardarAuto.addActionListener(new ListenerForGuardarAutomatico(this));
		guardarAuto.setPreferredSize(new Dimension(120, 30));
		buttonsPanelAuto.add(guardarAuto);

		autoConfig.add(rulesPanelAuto);
		autoConfig.add(buttonsPanelAuto);

		JPanel fpFnAuto = new JPanel();

		JLabel fpLabelAuto = new JLabel("FP");
		fpFnAuto.add(fpLabelAuto);

		fpAuto = new JTextField();
		fpAuto.setPreferredSize(new Dimension(50, 50));
		fpFnAuto.add(fpAuto);

		JLabel fnLabelAuto = new JLabel("FN");
		fpFnAuto.add(fnLabelAuto);

		fnAuto = new JTextField();
		fnAuto.setPreferredSize(new Dimension(50, 50));
		fpFnAuto.add(fnAuto);

		autoConfig.add(fpFnAuto);

		frame.add(autoConfig, BorderLayout.SOUTH);

	}
	
	
	//Getters e Setters
	
	public ReadRules getRf() {
		return rf;
	}
	
	public JTextField getFpAuto() {
		return fpAuto;
	}

	public JTextField getFnAuto() {
		return fnAuto;
	}

	public DefaultListModel<String> getListaAuto() {
		return listaAuto;
	}

	public JTextArea getWeightListAuto() {
		return weightListAuto;
	}

	public JButton getAvQualidadeAuto() {
		return avQualidadeAuto;
	}
	public JButton getAvQualidade() {
		return avQualidade;
	}

	public JTextField getFp() {
		return fp;
	}

	public JTextField getFn() {
		return fn;
	}

	public JButton getSearchHam() {
		return searchHam;
	}

	public JButton getSearchSpam() {
		return searchSpam;
	}



	public DefaultListModel<String> getLista() {
		return lista;
	}

	public JButton getSearchRules() {
		return searchRules;
	}

	public JList<String> getRulesList() {
		return rulesList;
	}

	public JTextArea getWeightList() {
		return weightList;
	}

	public File getRulesFile() {
		return rulesFile;
	}
	
	public File getHamFile() {
		return hamFile;
	}

	public File getSpamFile() {
		return spamFile;
	}
	
	public JButton getAvQualidadeMan() {
		return avQualidade;
	}

	public JTextField getFpMan() {
		return fp;
	}

	public JTextField getFnMan() {
		return fn;
	}

	public JTextField getRulesPath() {
		return rulesPath;
	}
	
	public JButton getGuardar() {
		return guardar;
	}
	
	public ReadRules getRf_automatico() {
		return rf_automatico;
	}

	public JList<String> getRulesListAuto() {
		return rulesListAuto;
	}

	public void setRulesListAuto(JList<String> rulesListAuto) {
		this.rulesListAuto = rulesListAuto;
	}

}