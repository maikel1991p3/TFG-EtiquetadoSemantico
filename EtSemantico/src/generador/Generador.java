package generador;

import java.util.ArrayList;

import componenetes.Objeto;

/**
 * Genera las instancias necesarias para la ejecución de los algoritmos.
 * @author Maikel
 *
 */
public class Generador {

	// Módulos de generación
	private Parser parser;
	private GenAleatorio genAleatorio;
	private ArrayList<String> clases = new ArrayList<String>();

	// Instancias y opciones de generación
	private ArrayList<Objeto> instancias = new ArrayList<Objeto>();
	private String nombreFichero; // Si aleatorio, fichero es de config, sino es
									// para parser

	public Generador(boolean aleatorias, String fich) {
		setNombreFichero(fich);
		generarInstancias(aleatorias);
	}

	/**
	 * Genera instancias de forma aleatoria o leyéndolas desde un fichero
	 * externo
	 * 
	 * @param aletorias
	 *            indica si las genera de forma aleatoria o desde fichero.
	 */
	public void generarInstancias(boolean aletorias) {
		if (aletorias) {
			genAleatorio = new GenAleatorio(getInstancias());
			setInstancias(genAleatorio.generarInstancias(getNombreFichero()));
			setClases(genAleatorio.getTotalCaracteristicas());
		} else {
			parser = new Parser(getInstancias());
			setInstancias(parser.generarInstancias(getNombreFichero()));
			setClases(parser.getTotalCaracteristicas());
		}
		//mostrarInstancias();
	}

	public void mostrarInstancias() {
		System.out.println("Instancias: ");
		for (int i = 0; i < getInstancias().size(); ++i) {
			System.out.println(getInstancias().get(i));
		}
		System.out.println("Clases: ");
		for (int i = 0; i < getClases().size(); ++i) {
			System.out.println(getClases().get(i));
		}
	}

	// Getters y Setters
	public ArrayList<Objeto> getInstancias() {
		return instancias;
	}

	public void setInstancias(ArrayList<Objeto> instancias) {
		this.instancias = instancias;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public ArrayList<String> getClases() {
		return clases;
	}

	public void setClases(ArrayList<String> clases) {
		this.clases = clases;
	}

}
