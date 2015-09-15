package metricas;

import componenetes.Objeto;

/**
 * Clase que implementa la f�rmula de la distancia de Manhattan para emplearla como
 * funci�n de c�lculo de la distancia entre im�genes.
 * @author Maikel
 *
 */
public class MetricaManhatthan extends Metrica {

	public double calcularDistancia(Objeto o, Objeto refObjetoInicial) {
		double resultado = 0.0;
		for (int i = 0; i < refObjetoInicial.getCaracteristicas().size(); ++i) {
			resultado += Math.abs(refObjetoInicial.getCaracteristicas().get(i).getValorPonderado()
					- o.getCaracteristicas().get(i).getValorPonderado());
		}
		o.setDistancia(resultado);
		return resultado;
	}

}
