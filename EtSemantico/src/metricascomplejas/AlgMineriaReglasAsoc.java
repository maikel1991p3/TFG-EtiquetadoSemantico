package metricascomplejas;

import generador.GeneradorARFF;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.associations.Apriori;
import weka.associations.ItemSet;
import weka.core.FastVector;
import weka.core.Instances;

import componenetes.Objeto;

/**
 * Clase que encapsula el algoritmo que extrae las reglas de asociación existentes en
 * las imágenes iniciales, para su posterior utilización.
 * @author Maikel
 *
 */
public class AlgMineriaReglasAsoc {
	// Fuente datos
	private ArrayList<Objeto> refObjetos = null;
	
	// Parámetros del algoritmo
	private ArrayList<Regla> reglas = null;
	private double minConfianza = 10.0;
	private final int MAX_NUM_RULES = 999999;
	
	// Generador ARFF para el algoritmo apriori de la librería de WEKA
	private GeneradorARFF generadorARFF = null;
	
	public AlgMineriaReglasAsoc (ArrayList<Objeto> refObjts, ArrayList<String> refCaracteristicas) {
		reglas = new ArrayList<Regla>();
		
		generadorARFF = new GeneradorARFF(refObjts, refCaracteristicas);
		generadorARFF.crearFichero();
		
		Apriori apriori = new Apriori();
		BufferedReader doc;
		try {
			// 1º Leemos el fichero INPUT para el algoritmo apriori
			doc = new BufferedReader(new FileReader("objetos.arff"));
			Instances data = new Instances(doc);
			//apriori.setLowerBoundMinSupport(0.1);
			apriori.setNumRules(MAX_NUM_RULES);
			
			// 2º Ejecutamos el algoritmo
			apriori.buildAssociations(data);
			
			// 3º Creamos las reglas a partir del resultado
			parseRules (apriori);
			
			/*for (int i = 0; i < getReglas().size(); ++i)
				System.out.println(getReglas().get(i).toString());*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crea las reglas a partir del resultado del algoritmo A priori.
	 * @param apriori
	 */
	private void parseRules (Apriori apriori) {
		String temp = apriori.toString().substring(apriori.toString().indexOf("Best rules found:") + 19);

		String [] lineas = apriori.toString().split("\n");
		String lineaA = null, lineaB = null;
		String [] partesRegla = null;
		Regla reglaTemp = null;
		for (int i = 0; i < lineas.length; ++i) {
			int indexA = printMatches(lineas[i], "\\s\\d\\s");
			lineaA = lineas[i].substring(printMatches(lineas[i], "\\.\\s"), indexA);
			
			lineaB = lineas[i].substring(printMatches(lineas[i], " ==> "), printMatches(lineas[i], "\\s\\d\\s\\s"));
			if (lineaA.length() > 0 && lineaB.length() > 0) {
				ArrayList<String> t = parseComponenteRegla (lineaA);
			
				ArrayList<String> s = parseComponenteRegla (lineaB);
				reglaTemp = new Regla(t, s, 1.0);
				reglas.add(reglaTemp);
			}
		}
	}
	
	public ArrayList<String> parseComponenteRegla (String txt) {
		ArrayList<String> result = new ArrayList<String>();
		String [] antecedentes = txt.split(" ");
		for (int i = 0; i < antecedentes.length; ++i) {
			if (printMatches(antecedentes[i], "\\d\\.\\d") > 0){
				result.add(antecedentes[i].split("=")[0]);
			}
		}
		return result;
	}
	
	public int printMatches(String text, String regex) {
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(text);
	    // Check all occurrences
	    if (matcher.find()) {
	        return matcher.end();
	    }
	    return 0;
	}
	
	// Getters y Setters
	public ArrayList<Objeto> getRefObjetos() {
		return refObjetos;
	}

	public void setRefObjetos(ArrayList<Objeto> refObjetos) {
		this.refObjetos = refObjetos;
	}

	public ArrayList<Regla> getReglas() {
		return reglas;
	}

	public void setReglas(ArrayList<Regla> reglas) {
		this.reglas = reglas;
	}

	public double getMinConfianza() {
		return minConfianza;
	}

	public void setMinConfianza(double minConfianza) {
		this.minConfianza = minConfianza;
	}
}
