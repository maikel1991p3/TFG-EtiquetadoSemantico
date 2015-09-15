package metricascomplejas;

import java.util.ArrayList;

/**
 * Representa una regla de implicación lógica A, B, ... => C, D, ..
 * 
 * La parte derecha se denomina 'antecedente'.
 * 
 * La parte izquierda es el 'consecuente'.
 * 
 * 	Cada regla posee un valor de confianza, que representa la probabilidad con la
 * que si nos encontramos el antecedente, tendremos el consecuente. Esto se obtiene
 * a través de un algoritmo de reglas de asociación.
 * 
 * @author Maikel
 *
 */
public class Regla {
	private ArrayList<String> antecedentes = null;
	private ArrayList<String> consecuentes = null;
	private double confianza = 0.0;
	
	public Regla (ArrayList<String> ant, ArrayList<String> cons, double c) {
		setAntecedentes(ant);
		setConsecuentes(cons);
		setConfianza(c);
	}

	public String toString () {
		String result = new String("");
		result += printAntecedentes ();
		result += " ==> ";
		result += printConsecuentes() + " Confianza = " + getConfianza();
		return result;
	}
	
	public String printAntecedentes () {
		String result = new String("");
		for (int i = 0; i < antecedentes.size(); ++i) {
			result += antecedentes.get(i) + " ";
		}
		return result;
	}
	
	public String printConsecuentes () {
		String result = new String("");
		for (int i = 0; i < consecuentes.size(); ++i) {
			result += consecuentes.get(i) + " ";
		}
		return result;
	}
	
	// Getters y Setters
	public ArrayList<String> getAntecedentes() {
		return antecedentes;
	}

	public void setAntecedentes(ArrayList<String> antecedentes) {
		this.antecedentes = antecedentes;
	}

	public ArrayList<String> getConsecuentes() {
		return consecuentes;
	}

	public void setConsecuentes(ArrayList<String> consecuentes) {
		this.consecuentes = consecuentes;
	}

	public double getConfianza() {
		return confianza;
	}

	public void setConfianza(double confianza) {
		this.confianza = confianza;
	}
}
