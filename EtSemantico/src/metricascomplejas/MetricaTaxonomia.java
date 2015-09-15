package metricascomplejas;

import java.util.ArrayList;

import metricas.Metrica;
import metricas.MetricaEuclidea;
import metricas.MetricaGravitacional;
import metricas.MetricaManhatthan;
import metricas.MetricaPorPenalizacion;
import componenetes.Caracteristica;
import componenetes.Objeto;

/**
 * Clase que diseña la Taxonomía Gramatical de forma implícita, empleando la misma
 * para la asignación de nuevas etiquetas a las imágenes.
 * @author Maikel
 *
 */
public class MetricaTaxonomia extends Metrica {
	private Metrica metricaSimple = null;
	private AlgMineriaReglasAsoc mineriaReglas = null;
	
	public MetricaTaxonomia (int tipoMetrica, ArrayList<Objeto> refObjts, ArrayList<String> refCaracteristicas) {
		instanciarMetricaSimple (tipoMetrica);
		
		// Instanciamos el algoritmo que construye las reglas
		mineriaReglas = new AlgMineriaReglasAsoc(refObjts, refCaracteristicas);
		asignarProbabilidades (refObjts, mineriaReglas.getReglas());
		metricaSimple = new MetricaEuclidea ();
	}
	
	public double calcularDistancia(Objeto o, Objeto refObjetoInicial) {
		double resultado = 0.0;
		resultado = metricaSimple.calcularDistancia(o, refObjetoInicial);
		
		return resultado;
	}
	
	/**
	 * Calcula el valor de confianza para una nueva etiqueta que se asocia.
	 * @param refObj
	 * @param refReglas
	 */
	public void asignarProbabilidades (ArrayList<Objeto> refObj, ArrayList<Regla> refReglas) {

		Caracteristica temp;
		
		ArrayList<Regla> reglasNuevas = new ArrayList<>();
		ArrayList<Objeto> objetosAfectados = new ArrayList<>();
		int cont = 0;
		int indexCar = -1;
		for (int i = 0; i < refObj.size(); ++i) {
			for (int j = 0; j < refObj.get(i).getCaracteristicas().size(); ++j) {
				for (int k = 0; k < refReglas.size(); ++k) {
					cont = 0;
					for (int l = 0; l < refReglas.get(k).getAntecedentes().size(); ++l) {
						if (refObj.get(i).getCaracteristicas().get(j).getEtiqueta().equalsIgnoreCase(refReglas.get(k).getAntecedentes().get(l))) {
							++cont;
						}
					}
					if (cont == refReglas.get(k).getAntecedentes().size()) {
						objetosAfectados.add(refObj.get(i));
						reglasNuevas.add(refReglas.get(k));
						for (int l = 0; l < refReglas.get(k).getConsecuentes().size(); ++l) {
							temp = new Caracteristica(refReglas.get(k).getConsecuentes().get(l), 1.0, calcularPeso(refObj.get(i), refReglas.get(k)));
							indexCar = refObj.get(i).contieneCaracteristica(temp); 
							if (indexCar != -1) {
								refObj.get(i).getCaracteristicas().get(indexCar).setValor(1);
								refObj.get(i).getCaracteristicas().get(indexCar).setPeso(calcularPeso(refObj.get(i), refReglas.get(k)));
							}
						}
					}
				}
			}
		}	
	}
	
	public double calcularPeso (Objeto o, Regla r) {
		ArrayList<Double> pesos = new ArrayList<>();
		for (int i = 0; i < o.getCaracteristicas().size(); ++i) {
			for (int j = 0; j < r.getAntecedentes().size(); ++j) {
				if (o.getCaracteristicas().get(i).getEtiqueta().equalsIgnoreCase(r.getAntecedentes().get(j))) {
					pesos.add(o.getCaracteristicas().get(i).getPeso());
				}
			}
		}
		double min = 99999.0;
		int indiceMenor = 0;
		for (int i = 0; i < pesos.size(); ++i) {
			if (pesos.get(i) < min) {
				indiceMenor = i;
				min = pesos.get(i);
			}
		}
		return pesos.get(indiceMenor) * r.getConfianza() / 2;
	}
	
	/**
	 * Instancia una de las métricas simples para usar como métrica de respaldo
	 * @param tipo
	 */
	private void instanciarMetricaSimple (int tipo) {
		switch (tipo) {
		case 0:
			setMetricaSimple(new MetricaEuclidea());
			break;
		case 1:
			setMetricaSimple(new MetricaManhatthan());
			break;
		case 2:
			setMetricaSimple(new MetricaGravitacional());
			break;
		case 3:
			setMetricaSimple(new MetricaPorPenalizacion());
			break;
		default:
		}
	}

	// Getters y Setters
	public Metrica getMetricaSimple() {
		return metricaSimple;
	}
	public void setMetricaSimple(Metrica metricaSimple) {
		this.metricaSimple = metricaSimple;
	}
}
