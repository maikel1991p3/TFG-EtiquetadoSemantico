package clasificador;

import java.util.ArrayList;

import metricas.Metrica;
import componenetes.Objeto;

/**
 * Clase que implementa la funcionalidad del algoritmo denominado 'Modelo Satélite'
 * Elabora grupos de imágenes atendiendo a la configuración de entrada y al uso de una
 * función de distancia.
 * @author Maikel
 *
 */
public class AlgoritmoIndirecto extends AlgoritmoClasificador {

	private Objeto refObjetoOrigen2 = null;
	private boolean error = false;

	public AlgoritmoIndirecto(ArrayList<Objeto> refInstancias,
			ArrayList<String> refClases, Metrica metrica, int d1, int d2,
			int gap, int indiceObjOrigen) {
		super(refInstancias, refClases, metrica, d1, d2, gap, indiceObjOrigen);

	}

	/**
	 * Ejecución del algoritmo 'Modelo Satélite'
	 */
	public void ejecutarAgrupamiento() {
		System.out.println("OBJ Origen 1 = " + getRefObjOrigen());
		getRefObjOrigen().setDisponible(false);
		
		setCluster1(asignarObjetosAlCluster(getD1()));

		setClusterGAP(asignarObjetosAlCluster(getGap()));

		// Mostramos el cluster 1 y el cluter GAP
		ArrayList<Objeto> clusterizados = new ArrayList<Objeto>();
		for (int i = 0; i < getCluster1().size(); ++i)
			clusterizados.add(new Objeto(getCluster1().get(i)));
		for (int i = 0; i < getClusterGAP().size(); ++i)
			clusterizados.add(new Objeto(getClusterGAP().get(i)));

		// Agrupamos los siguientes k2 objetos en el cluster 2
		try {
			seleccionarOrigenCluster2();
			System.out.println("OBJ Origen 2 = " + getRefObjetoOrigen2());
		} catch (Exception e) {
			error = true;
			System.err.println("No hay suficientes objetos libres para el segundo cluster!");
		}

		if (!error) {
			setCluster2(asignarObjetosAlCluster(getD2()));

			System.out.println("C1");
			for (int i = 0; i < getCluster1().size(); ++i) {
				System.out.println(getCluster1().get(i));
			}
			System.out.println("GAP");
			for (int i = 0; i < getClusterGAP().size(); ++i) {
				System.out.println(getClusterGAP().get(i));
			}
			System.out.println("C2");
			for (int i = 0; i < getCluster2().size(); ++i) {
				System.out.println(getCluster2().get(i));
			}
		}
	}

	/**
	 * Selecciona como origen del grupo 2 aquella imagen más cercana a
	 * la distancia d1 + gap + d2.
	 */
	public void seleccionarOrigenCluster2() throws Exception {
		int numObjetos = getRefInstancias().size();
		double[] distancias = new double[numObjetos];

		// Calculamos las distancias de todas las imágenes a la imagen inicial
		for (int i = 0; i < numObjetos; ++i) {
			if (getRefInstancias().get(i).isDisponible())
				distancias[i] = getRefMetrica().calcularDistancia(
						getRefInstancias().get(i), getRefObjOrigen());
		}

		// Ordenamos el resto de imágenes cuya distancia desde el origen sea
		// mayor a d1 + gap
		ArrayList<Objeto> clusterTemp = new ArrayList<Objeto>();
		int contador = 0;
		double min = 999999.0;
		int indiceMenor = -(int) min;
		while (contador < getD2()) {
			// Buscamos la imagen más cercana
			min = 999999.0;
			indiceMenor = -(int) min;
			for (int i = 0; i < numObjetos; ++i) {
				if (getRefInstancias().get(i).isDisponible()
						&& getRefInstancias().get(i).getId() != getRefObjOrigen()
						.getId() && distancias[i] < min) {
					indiceMenor = i;
					min = distancias[i];
				}
			}
			distancias[indiceMenor] = 9999999.0;
			// Ponemos la distancia del menor a un valor muy alto para no
			// volverla a considerar
			if (contador < getD1() + getGap() + getD2()) {
				getRefInstancias().get(indiceMenor).setDisponible(false);
				// Añadimos la imagen al nuevo grupo
				clusterTemp.add(getRefInstancias().get(indiceMenor));
			}
			++contador;
		}

		// Seleccionamos las imágenes dentro de la distancia d1 + gap, que
		// permitirán buscar la siguiente imagen mas cercana
		getRefObjOrigen().setDisponible(false);

		// Seleccionamos el elemento del array de candidatos con menor distancia
		// pues este será el más cercano a la distancia d1 + gap + d2
		double[] distanciasCandidatos = new double[clusterTemp.size()];
		for (int i = 0; i < clusterTemp.size(); ++i) {
			distanciasCandidatos[i] = getRefMetrica().calcularDistancia(
					clusterTemp.get(i), getRefObjOrigen());
		}

		min = distanciasCandidatos[0];
		indiceMenor = 0;
		for (int i = 0; i < clusterTemp.size(); ++i) {
			if (distanciasCandidatos[i] < min) {
				indiceMenor = i;
				min = distanciasCandidatos[i];
			}
		}
		setRefObjetoOrigen2(clusterTemp.get(indiceMenor));

	}

	public Objeto getRefObjetoOrigen2() {
		return refObjetoOrigen2;
	}

	public void setRefObjetoOrigen2(Objeto refObjetoOrigen2) {
		this.refObjetoOrigen2 = refObjetoOrigen2;
	}

}
