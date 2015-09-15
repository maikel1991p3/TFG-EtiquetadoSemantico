package metricas;

import componenetes.Objeto;

/**
 * Clase que implementa la fórmula de la distancia por penalización para emplearla como
 * función de cálculo de la distancia entre imágenes.
 * 
 * Esta función ha sido diseñada íntegramente y su comportamiento es el siguiente:
 * 1) Si dos imágenes comparten una característica -> La distancia se reduce en 1 unidad.
 * 2) Si dos imágenes difieren en una característica -> La distancia aumenta en 1 unidad.
 * @author Maikel
 *
 */
public class MetricaPorPenalizacion extends Metrica {

	public double calcularDistancia(Objeto o, Objeto refObjetoInicial) {
		double resultado = 0.0;
		for (int i = 0; i < o.getCaracteristicas().size(); ++i) {
			resultado += -(Math.max(o.getCaracteristicas().get(i)
					.getValorPonderado(), refObjetoInicial.getCaracteristicas()
					.get(i).getValorPonderado()) * Math.pow(-1.0, o
					.getCaracteristicas().get(i).getValorPonderado()
					+ refObjetoInicial.getCaracteristicas().get(i)
							.getValorPonderado()));
		}
		return resultado;
	}
}
