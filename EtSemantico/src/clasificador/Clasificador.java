package clasificador;

import generador.Generador;

import java.util.ArrayList;

import metricas.Metrica;
import metricas.MetricaEuclidea;
import metricas.MetricaGravitacional;
import metricas.MetricaManhatthan;
import metricas.MetricaPorPenalizacion;
import metricascomplejas.MetricaTaxonomia;
import componenetes.Objeto;

/**
 * Clase que encapsula el uso de los algoritmos de recuperación de imágenes
 * @author Maikel
 *
 */
public class Clasificador {

	private Generador generadorInstancias = null;
	private ArrayList<Objeto> refInstancias = null;
	private ArrayList<String> refClases = null;
	private AlgoritmoClasificador algoritmo;
	private Metrica metrica = null;

	public Clasificador(boolean aleatorias, String nombreFich, int tipoMetrica,
			int tipoAlgoritmo, int d1, int d2, int gap, int indObjOrigen) {
		generadorInstancias = new Generador(aleatorias, nombreFich);
		setRefInstancias(generadorInstancias.getInstancias());
		setRefClases(generadorInstancias.getClases());
		instanciarMetrica(tipoMetrica);
		instanciarAlgoritmo(tipoAlgoritmo, d1, d2, gap, indObjOrigen);
		getAlgoritmo().ejecutarAgrupamiento();
	}

	/**
	 * Instancia un tipo de función para el cálculo de distancias entre imágenes
	 * @param tipoMetrica
	 */
	public void instanciarMetrica(int tipoMetrica) {
		// Switch tipoMetrica -> instanciar (Stratgy)
		switch (tipoMetrica) {
		case 0:
			setMetrica(new MetricaEuclidea());
			break;
		case 1:
			setMetrica(new MetricaManhatthan());
			break;
		case 2:
			setMetrica(new MetricaGravitacional());
			break;
		case 3:
			setMetrica(new MetricaPorPenalizacion());
			break;
		case 4:
			setMetrica(new MetricaTaxonomia(tipoMetrica, getRefInstancias(), getRefClases()));
		default:
		}
	}

	/**
	 * Intancia un tipo de técnica algorítmica para la recuperación de imágenes. El 
	 * modelo disco o el modelo satélite.
	 * @param tipoAlgoritmo
	 * @param d1
	 * @param d2
	 * @param gap
	 * @param indiceObjOrigen
	 */
	public void instanciarAlgoritmo(int tipoAlgoritmo, int d1, int d2, int gap,
			int indiceObjOrigen) {
		// Switch tipoAlgoritmo -> Instanciar
		switch (tipoAlgoritmo) {
		case 1:
			setAlgoritmo(new AlgoritmoDirecto(getRefInstancias(),
					getRefClases(), getMetrica(), d1, d2, gap, indiceObjOrigen));
			break;
		case 2:
			setAlgoritmo(new AlgoritmoIndirecto(getRefInstancias(),
					getRefClases(), getMetrica(), d1, d2, gap, indiceObjOrigen));
			break;
		}
	}

	// Getters y Setters
	public Generador getGeneradorInstancias() {
		return generadorInstancias;
	}

	public void setGeneradorInstancias(Generador generadorInstancias) {
		this.generadorInstancias = generadorInstancias;
	}

	public ArrayList<String> getRefClases() {
		return refClases;
	}

	public void setRefClases(ArrayList<String> refClases) {
		this.refClases = refClases;
	}

	public AlgoritmoClasificador getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(AlgoritmoClasificador algoritmo) {
		this.algoritmo = algoritmo;
	}

	public ArrayList<Objeto> getRefInstancias() {
		return refInstancias;
	}

	public void setRefInstancias(ArrayList<Objeto> refInstancias) {
		this.refInstancias = refInstancias;
	}

	public Metrica getMetrica() {
		return metrica;
	}

	public void setMetrica(Metrica metrica) {
		this.metrica = metrica;
	}

}
