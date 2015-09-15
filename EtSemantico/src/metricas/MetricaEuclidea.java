package metricas;

import componenetes.Objeto;

/**
 * Clase que implementa la f�rmula de la distancia Eucl�dea para emplearla como
 * funci�n de c�lculo de la distancia entre im�genes.
 * @author Maikel
 *
 */
public class MetricaEuclidea extends Metrica {

	public double calcularDistancia(Objeto o, Objeto refObjetoInicial) {
		double resultado = 0.0;
		for (int i = 0; i < refObjetoInicial.getCaracteristicas().size(); ++i) {
			resultado += Math.sqrt(Math.pow(refObjetoInicial
					.getCaracteristicas().get(i).getValorPonderado() 
					- o.getCaracteristicas().get(i).getValorPonderado(), 2));
		}
		o.setDistancia(resultado);
		return resultado;
	}

}
