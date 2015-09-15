package metricas;

import componenetes.Objeto;

/**
 * Clase que implementa una fórmula alternativa, basada en la Ley de la Gavitación 
 * Universal para emplearla como función de cálculo de la distancia entre imágenes.
 */
public class MetricaGravitacional extends Metrica {

	private MetricaEuclidea euclidea = new MetricaEuclidea();

	public double calcularDistancia(Objeto o, Objeto refObjetoInicial) {
		double resultado = 0.0;
		double constanteGrav = 100.0;
		resultado = reducirCaracteristicas(o)
				* reducirCaracteristicas(refObjetoInicial);
		double dist = euclidea.calcularDistancia(o, refObjetoInicial);
		if (dist != 0.0)
			resultado /= dist;
		 resultado *= Math.pow(euclidea.calcularDistancia(o,
		 refObjetoInicial), 2);
		resultado *= constanteGrav;
		o.setDistancia(resultado);
		return resultado;
	}

	/**
	 * Promedia las características de un objeto (sumatorio y división entre el
	 * total)
	 * 
	 * @param o
	 * @return media de las características.
	 */
	private double reducirCaracteristicas(Objeto o) {
		double resultado = 1.0;
		int numCaracteristicas = o.getCaracteristicas().size();
		for (int i = 0; i < numCaracteristicas; ++i)
			resultado += o.getCaracteristicas().get(i).getValorPonderado();
		return resultado / numCaracteristicas;
	}

}
