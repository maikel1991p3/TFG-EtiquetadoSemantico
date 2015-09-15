package componenetes;

/**
 * Define la etiqueta de una imagen.
 * Contiene una serie de atributos necesarios para la ejecución de los algoritmos
 * de recuperación de imágenes.
 * El texto de la etiqueta y la confianza de que una etiqueta concreta está asociada
 * a una imagen.
 * @author Maikel
 *
 */
public class Caracteristica {
	private String etiqueta;
	private double valor = 0.0;
	private double peso = 1.0;
	
	public Caracteristica (String et, double v, double p) {
		setEtiqueta(et);
		setValor(v);
		setPeso(p);
	}

	/**
	 * Devuelve el valor ponderado (confianza) de la característica según peso asociado
	 * @return double ValorPonderado
	 */
	public double getValorPonderado () {
		return getValor() * getPeso();
	}
	
	
	public String toString () {
		String ret = getEtiqueta() + " Valor = " + getValor() + " Peso = " + getPeso();
		return ret;
	}
	
	// Getters y Setters
	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
		
}
