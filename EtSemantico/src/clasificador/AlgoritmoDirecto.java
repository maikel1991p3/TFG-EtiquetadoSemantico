package clasificador;

import java.util.ArrayList;

import metricas.Metrica;

import componenetes.Objeto;

/**
 * Clase que implementa la funcionalidad del algoritmo denominado 'Modelo Disco'
 * Elabora dos grupo de imágenes, basándose en la configuración de entrada y en el 
 * uso de una función de distancia.
 * @author Maikel
 *
 */
public class AlgoritmoDirecto extends AlgoritmoClasificador {
	
	private final boolean logs = false;
	
	public AlgoritmoDirecto(ArrayList<Objeto> refInstancias,
			ArrayList<String> refClases, Metrica metrica, int d1, int d2,
			int gap, int indiceObjOrigen) {
		super(refInstancias, refClases, metrica, d1, d2, gap, indiceObjOrigen);
	}

	/**
	 * Ejecución del algoritmo 'Modelo Disco'
	 */
	public void ejecutarAgrupamiento() {

		setCluster1(asignarObjetosAlCluster(getD1()));
		
		setClusterGAP(asignarObjetosAlCluster(getGap()));
		
		setCluster2(asignarObjetosAlCluster(getD2()));
		
			System.out.println("C1");
			if (logs) {
				System.out.println("ECM = " + calcularECM(getCluster1(), getRefObjOrigen()));
				System.out.println("Distancia Media = " + calcularDistanciaMedia(getCluster1(), getRefObjOrigen()));
			}
			for (int i = 0; i < getCluster1().size(); ++i) {
				System.out.println(getCluster1().get(i));
			}
			System.out.println("GAP");
			
			for (int i = 0; i < getClusterGAP().size(); ++i) {
				System.out.println(getClusterGAP().get(i));
				
			}
			System.out.println("C2");
			
			if (logs) {
				System.out.println("ECM = " + calcularECM(getCluster2(), getCluster2().get(0)));
				System.out.println("Distancia Media = " + calcularDistanciaMedia(getCluster2(), getCluster2().get(0)));
			}
			for (int i = 0; i < getCluster2().size(); ++i) {
				System.out.println(getCluster2().get(i));
				
			}
	}

}
