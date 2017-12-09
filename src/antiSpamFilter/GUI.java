package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;

import org.apache.commons.lang3.SystemUtils;

public class GUI {

	private JFrame frame;

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
	public static final GUI INSTANCE = new GUI();

	public static GUI getInstance() {
		return INSTANCE;
	}

	public JFrame getFrame() {
		return frame;
	}

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
	
	public void buildGui() {

		addPathPanel();
		addManualConfig();
		addAutoConfig();

	}

	public void addPathPanel() {
		JPanel selectFiles = new JPanel();

		JPanel pathPanel = new JPanel();
		pathPanel.setLayout(new GridLayout(3, 1));

		rulesPath = new JTextField();
		rulesPath.setFont(new Font("Arial", Font.PLAIN, 16));
		rulesPath.setPreferredSize(new Dimension(500, 30));
		pathPanel.add(rulesPath);

		spamPath = new JTextField();
		spamPath.setFont(new Font("Arial", Font.PLAIN, 16));
		spamPath.setPreferredSize(new Dimension(500, 30));
		pathPanel.add(spamPath);

		hamPath = new JTextField();
		hamPath.setFont(new Font("Arial", Font.PLAIN, 16));
		hamPath.setPreferredSize(new Dimension(500, 30));
		pathPanel.add(hamPath);

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new GridLayout(3, 1));

		searchRules = new JButton("Procurar");
		searchRules.setPreferredSize(new Dimension(200, 30));
		searchPanel.add(searchRules);

		searchHam = new JButton("Procurar");
		searchHam.setPreferredSize(new Dimension(200, 30));
		searchPanel.add(searchHam);

		searchSpam = new JButton("Procurar");
		searchSpam.setPreferredSize(new Dimension(200, 30));
		searchPanel.add(searchSpam);

		selectFiles.add(pathPanel);
		selectFiles.add(searchPanel);

		frame.add(selectFiles, BorderLayout.NORTH);

	}

	public void addManualConfig() {
		lista = new DefaultListModel<String>();

		JPanel manualConfig = new JPanel();

		JPanel rulesPanel = new JPanel();
		rulesPanel.setLayout(new GridLayout(1, 2));
		rulesPanel.setSize(300, 300);

		rulesList = new JList<String>(lista);

		weightList = new JTextArea();

		// Aumentar tamanho dos numeros
		Font font = rulesList.getFont();
		float size = font.getSize() - 1.0f;
		rulesList.setFont(font.deriveFont(size));

		JScrollPane scrollArea1 = new JScrollPane(rulesList, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JScrollPane scrollArea2 = new JScrollPane(weightList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollArea2.getVerticalScrollBar().setModel(scrollArea1.getVerticalScrollBar().getModel());

		rulesPanel.add(scrollArea1);
		rulesPanel.add(scrollArea2);

		/*
		 * rulesPanel.add(rulesList);
		 * 
		 * JScrollPane scrollArea = new JScrollPane(rulesList);
		 * rulesPanel.add(scrollArea);
		 * 
		 * rulesPanel.add(weightList);
		 * 
		 * JScrollPane scrollArea2 = new JScrollPane(weightList);
		 * rulesPanel.add(scrollArea2);
		 * 
		 */

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(2, 1));

		avQualidade = new JButton("Av. qualidade");
		avQualidade.setPreferredSize(new Dimension(120, 30));
		buttonsPanel.add(avQualidade);

		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ListenerForGuardar(this));
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

		JPanel rulesPanelAuto = new JPanel();
		rulesPanelAuto.setLayout(new GridLayout(1, 2));

		JList<String> rulesListAuto = new JList<String>(listaAuto);

		JTextArea weightListAuto = new JTextArea();

		rulesPanelAuto.add(rulesListAuto);

		JScrollPane scrollArea = new JScrollPane(rulesListAuto);
		rulesPanelAuto.add(scrollArea);

		rulesPanelAuto.add(weightListAuto);

		JScrollPane scrollArea2 = new JScrollPane(weightListAuto);
		rulesPanelAuto.add(scrollArea2);

		JPanel buttonsPanelAuto = new JPanel();
		buttonsPanelAuto.setLayout(new GridLayout(2, 1));

		JButton avQualidadeAuto = new JButton("Av. qualidade");
		avQualidadeAuto.setPreferredSize(new Dimension(120, 30));
		buttonsPanelAuto.add(avQualidadeAuto);

		JButton guardarAuto = new JButton("Guardar");
		guardarAuto.setPreferredSize(new Dimension(120, 30));
		buttonsPanelAuto.add(guardarAuto);

		autoConfig.add(rulesPanelAuto);
		autoConfig.add(buttonsPanelAuto);

		JPanel fpFnAuto = new JPanel();

		JLabel fpLabelAuto = new JLabel("FP");
		fpFnAuto.add(fpLabelAuto);

		JTextField fpAuto = new JTextField();
		fpAuto.setPreferredSize(new Dimension(50, 50));
		fpFnAuto.add(fpAuto);

		JLabel fnLabelAuto = new JLabel("FN");
		fpFnAuto.add(fnLabelAuto);

		JTextField fnAuto = new JTextField();
		fnAuto.setPreferredSize(new Dimension(50, 50));
		fpFnAuto.add(fnAuto);

		autoConfig.add(fpFnAuto);

		frame.add(autoConfig, BorderLayout.SOUTH);

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

	public JTextField getRulesPath() {
		return rulesPath;
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

}
