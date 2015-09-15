package metricas;

import componenetes.Objeto;

/**
 * Clase que implementa la f�rmula de la distancia por penalizaci�n para emplearla como
 * funci�n de c�lculo de la distancia entre im�genes.
 * 
 * Esta funci�n ha sido dise�ada �ntegramente y su comportamiento es el siguiente:
 * 1) Si dos im�genes comparten una caracter�stica -> La distancia se reduce en 1 unidad.
 * 2) Si dos im�genes difieren en una caracter�stica -> La distancia aumenta en 1 unidad.
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
