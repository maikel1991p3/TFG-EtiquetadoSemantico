package clasificador;

import java.util.ArrayList;

import metricas.Metrica;
import componenetes.Objeto;

/**
 * Clase base que implementa los m�todos de clasificaci�n de im�genes.
 * 
 * @author Maikel
 */
public class AlgoritmoClasificador {

	private ArrayList<Objeto> refInstancias = null;
	private ArrayList<String> refClases = null;
	private Metrica refMetrica = null;
	private Objeto refObjOrigen = null;

	// Par�metros del algoritmo
	private int d1 = 1;
	private int d2 = 1;
	private int gap = 1;

	private ArrayList<Objeto> cluster1;
	private ArrayList<Objeto> cluster2;
	private ArrayList<Objeto> clusterGAP;

	/**
	 * Constructor, recibe la configuraci�n base de la clasificaci�n
	 * @param refInstancias Im�genes a clasificar
	 * @param refClases Ontolog�a de etiquetas
	 * @param metrica Funci�n de distancia
	 * @param d1 Distancia m�xima existente en el grupo 1
	 * @param d2 Distancia m�xima existente en el grupo 2
	 * @param gap Distancia de separaci�n entre los grupos
	 * @param indiceObjOrigen Imagen inicial
	 */
	public AlgoritmoClasificador(ArrayList<Objeto> refInstancias,
			ArrayList<String> refClases, Metrica metrica, int d1, int d2,
			int gap, int indiceObjOrigen) {
		setRefInstancias(refInstancias);
		setRefClases(refClases);
		setRefMetrica(metrica);
		setParametros(d1, d2, gap);
		setObjetoOrigen(indiceObjOrigen);
	}

	/**
	 * Muestra el banco de im�genes inicial
	 */
	public void mostrarRefInstancias() {
		System.out.println("Referencia Instancias: ");
		for (int i = 0; i < getRefInstancias().size(); ++i) {
			System.out.println(getRefInstancias().get(i));
		}
		System.out.println("Referencia Clases: ");
		for (int i = 0; i < getRefClases().size(); ++i) {
			System.out.println(getRefClases().get(i));
		}
	}

	/**
	 * Ejecuci�n de la recuperaci�n de im�genes
	 */
	public void ejecutarAgrupamiento() {
	}

	// M�todos auxiliares del Algoritmo
	/**
	 * Agrupa las kmax im�genes m�s cercanas a la imagen origen 
	 * @param kmax
	 * @return Nuevo grupo creado
	 */
	public ArrayList<Objeto> asignarObjetosAlCluster(int kmax) {
		ArrayList<Objeto> nuevo = new ArrayList<Objeto>();
		int numObjetos = getRefInstancias().size();
		double[] distancias = new double[numObjetos];

		// Calculamos las distancias de todos los objetos al objeto inicial
		for (int i = 0; i < numObjetos; ++i) {
			if (getRefInstancias().get(i).isDisponible()) {
				
				distancias[i] = getRefMetrica().calcularDistancia(
						getRefInstancias().get(i), getRefObjOrigen());
				getRefInstancias().get(i).setDistancia(distancias[i]);
			}
		}

		// Seleccionamos las k1 im�genes menos distantes
		int contador = 0;
		double min = 999999.0;
		int indiceMenor = 0;
		while (contador < kmax) {
			// Buscamos la imagen m�s cercana
			min = 999999.0;
			indiceMenor = 0;
			for (int i = 0; i < numObjetos; ++i) {
				if (getRefInstancias().get(i).isDisponible()
						&& i != getRefObjOrigen().getId()
						&& distancias[i] < min) {
					indiceMenor = i;
					min = distancias[i];
				}
			}
			// Ponemos la distancia del menor a 0 para no volverla a considerar
			distancias[indiceMenor] = 9999999.0;
			// A�adimos la imagen al nuevo grupo
			nuevo.add(new Objeto(getRefInstancias().get(indiceMenor)));
			getRefInstancias().get(indiceMenor).setDisponible(false);
			++contador;
		}

		// Devolvemos el nuevo grupo creado
		return nuevo;
	}

	/**
	 * Asignaci�n de los par�metros de configuraci�n del algoritmo
	 * @param d1
	 * @param d2
	 * @param gap
	 */
	public void setParametros(int d1, int d2, int gap) {
		setD1(d1);
		setD2(d2);
		setGap(gap);
	}

	/**
	 * Asignaci�n de la imagen origen de la clasificaci�n
	 * @param indiceObjOrigen
	 */
	public void setObjetoOrigen(int indiceObjOrigen) {
		try {
			setRefObjOrigen(getRefInstancias().get(indiceObjOrigen));
		} catch (Exception e) {
			System.err
					.println("ERROR: El indice seleccionado para el objeto origen no parece ser v�lido!");
		}
	}

	/**
	 * Calcula el Error Cuadr�tico Medio entre cada uno de las im�genes y la
	 * imagen origen del agrupamiento.
	 * @param grupo
	 * @return Error Cuadr�tico Medio
	 */
	public double calcularECM (ArrayList<Objeto> grupo, Objeto origen) {
		double result = 0.0;
		for (int i = 0; i < grupo.size(); ++i) {
			for (int j = 0; j < grupo.get(i).getCaracteristicas().size(); ++j) {
				result += Math.pow((grupo.get(i).getCaracteristicas().get(j).getValorPonderado() -
						origen.getCaracteristicas().get(j).getValorPonderado()), 2);
			}
		}
		return result / origen.getCaracteristicas().size();
	}
	
	/**
	 * Calcula la media de las distancias entre im�genes de un grupo y la imagen origen
	 * de dicho grupo
	 * @param grupo
	 * @param origen
	 * @return Media de las distancias
	 */
	public double calcularDistanciaMedia (ArrayList<Objeto> grupo, Objeto origen) {
		double result = 0.0;
		for (int i = 0; i < grupo.size(); ++i) {
			result += getRefMetrica().calcularDistancia(grupo.get(i), origen);
		}
		return result / grupo.size();
	}
	
	// Getters y Setters
	public ArrayList<Objeto> getRefInstancias() {
		return refInstancias;
	}

	public void setRefInstancias(ArrayList<Objeto> refInstancias) {
		this.refInstancias = refInstancias;
	}

	public ArrayList<String> getRefClases() {
		return refClases;
	}

	public void setRefClases(ArrayList<String> refClases) {
		this.refClases = refClases;
	}

	public Metrica getRefMetrica() {
		return refMetrica;
	}

	public void setRefMetrica(Metrica metrica) {
		this.refMetrica = metrica;
	}

	public int getD1() {
		return d1;
	}

	public void setD1(int d1) {
		this.d1 = d1;
	}

	public int getD2() {
		return d2;
	}

	public void setD2(int d2) {
		this.d2 = d2;
	}

	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}

	public Objeto getRefObjOrigen() {
		return refObjOrigen;
	}

	public void setRefObjOrigen(Objeto refObjOrigen) {
		this.refObjOrigen = refObjOrigen;
	}

	public ArrayList<Objeto> getCluster1() {
		return cluster1;
	}

	public void setCluster1(ArrayList<Objeto> cluster1) {
		this.cluster1 = cluster1;
	}

	public ArrayList<Objeto> getCluster2() {
		return cluster2;
	}

	public void setCluster2(ArrayList<Objeto> cluster2) {
		this.cluster2 = cluster2;
	}

	public ArrayList<Objeto> getClusterGAP() {
		return clusterGAP;
	}

	public void setClusterGAP(ArrayList<Objeto> clusterGAP) {
		this.clusterGAP = clusterGAP;
	}

}
