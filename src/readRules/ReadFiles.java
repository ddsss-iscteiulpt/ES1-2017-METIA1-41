package readRules;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

// Os pesos aleat�rios est�o com casas d�cimais longas (nao arredondado)

public class ReadFiles {



	HashMap<String, String> regras = new HashMap<String, String>();
	File[] file; /* = new File("C:/Users/nuno/git/ES1-2017-METIA1-41(2)/src/ReadRules/rules.cf");*/ //Path deve ser diferente
	//Ficheiros rules.cf fica na mesma package que as classes
	
	FileReader reader = null;
	public ReadFiles(){

	}

	@SuppressWarnings("resource")
	public void read() throws IOException{
		
		file = new File("Read").listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {

				// se retornar verdadeiro, f ser� incluido

				if (f.getName().endsWith("cf"))
					return true;

				return false;
			}
		});
	

		Random rand = new Random(); 
		int i = 0;
		double rangeMin = -5;
		double rangeMax = 5;
		double peso = 0.0;
		String line = null;
		Scanner sc = new Scanner(file[0]);
		
//Leitura linha a linha do ficheiro e guardar no HashMap
		while (sc.hasNextLine()) {
			i++;
			peso = (rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
			line = sc.next();
			regras.put(line, Double.toString(peso));
			//System.out.println(line + " " + peso);
		}
		
		sc.close();
		//System.out.println(regras);

	}

	public HashMap<String, String> getRegras() {
		return regras;
	}

	

	public File[] getFile() {
		return file;
	}

	public FileReader getReader() {
		return reader;
	}



}

