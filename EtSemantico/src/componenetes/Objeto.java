package componenetes;

import java.util.ArrayList;

import metricascomplejas.Regla;

/**
 * Clase que define los atributos necesarios para almacenar la información relativa a cada
 * una de las imágenes que serán evaluadas en los algoritmos de recuperación.
 * @author Maikel
 *
 */
public class Objeto {
	private String etiqueta;
	private ArrayList<Caracteristica> caracteristicas;
	private boolean disponible = true;
	private double distancia = 0.0;
	private int id = 0;
	
	public Objeto (String et, ArrayList<Caracteristica> c, boolean disponible, int id) {
		setEtiqueta(et);
		setCaracteristicas(c);
		setDisponible(disponible);
		setId(id);
	}

	public Objeto (Objeto o) {
		this.setId(o.getId());
		this.setEtiqueta(o.getEtiqueta());
		this.setDisponible(o.isDisponible());
		this.setDistancia(o.getDistancia());
		this.setCaracteristicas(o.getCaracteristicas());
	}
	
	public String toString () {
		//String ret = new String ("Objeto [" + this.getId() + "]: " + this.getEtiqueta());
		String ret = "Objeto: " + this.getEtiqueta();
		/*for (int i = 0; i < getCaracteristicas().size(); i++) {
			if (getCaracteristicas().get(i).getValor() > 0.0) {
				ret += getCaracteristicas().toString();
			}
		}*/
		/*for (int i = 0; i < getCaracteristicas().size(); i++) {
			if (getCaracteristicas().get(i).getValor() > 0.0) {
				ret += getCaracteristicas().get(i).getEtiqueta() + " [" + getCaracteristicas().get(i).getPeso() + "] ";
			}
		}*/
		//ret += " Dist = " + getDistancia();
		return ret;
	}
	
	/**
	 * Devuelve si las características de un objeto están contenidas en el antecedente de una regla
	 * @param r
	 * @return true o false
	 */
	public boolean contieneAntecedente (Regla r) {
		int coincidencias = 0;
		for (int i = 0; i < getCaracteristicas().size(); ++i) {
			for (int j = 0; j < r.getAntecedentes().size(); ++j) {
				if (getCaracteristicas().get(i).getEtiqueta().equalsIgnoreCase(r.getAntecedentes().get(j)) ) {
					coincidencias++;
				}
			}
		}
		return (coincidencias == r.getAntecedentes().size());
	}
	
	public int contieneCaracteristica (Caracteristica c) {
		for (int i = 0; i < getCaracteristicas().size(); ++i) {
			if (getCaracteristicas().get(i).getEtiqueta().equalsIgnoreCase(c.getEtiqueta())
					&& getCaracteristicas().get(i).getValor() > 0.0) {
				return i;
			}
		}
		return -1;
	}
	
	// Getters y Setters
	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public ArrayList<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(ArrayList<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	

}
