/**
 * Genera un fichero en el formato ARFF del software WEKA.
 * 
 * 	Formato (ejemplo):
 * 
 *  @relation objetos
	 
	@attribute Animado {TRUE, FALSE}
	@attribute Inanimado {TRUE, FALSE}
	@attribute Comida {TRUE, FALSE}
	@attribute Animal {TRUE, FALSE}
	@attribute Fruta {TRUE, FALSE}
	@attribute Pajaro {TRUE, FALSE}
	@attribute Mueble {TRUE, FALSE}
	@attribute Verde {TRUE, FALSE}
	@attribute Marron {TRUE, FALSE}
	@attribute Amarillo {TRUE, FALSE}

	@data
	TRUE,FALSE,
 */
package generador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import componenetes.Objeto;

/**
 * Genera un fichro en formato ARFF, necesario para la ejecución del algoritmo 
 * Apriori, del software WEKA.
 * @author Maikel
 *
 */
public class GeneradorARFF {
		
	private final String RELACION = new String("@relation");
	private final String ATRIBUTO = new String("@attribute");
	private final String DATA = new String("@data");
	
	private ArrayList<Objeto> refObjetos = null;
	private ArrayList<String> refCaracteristicas = null;
	
	public GeneradorARFF (ArrayList<Objeto> refObjetos, ArrayList<String> refCaracteristicas) {
		setRefObjetos(refObjetos);
		setRefCaracteristicas(refCaracteristicas);
	}
	
	/**
	 * Elabora un fichero en formato ARFF, a partir de la información (etiquetas) de las imágenes
	 * del banco de imágenes inicial. 
	 */
	public void crearFichero () {
		String texto = new String ("");
		texto += RELACION + " objetos \n";
		for (int i = 0; i < getRefCaracteristicas().size(); ++i) {
			texto += ATRIBUTO + " " + getRefCaracteristicas().get(i) + " {0.0,1.0,?}\n";
		}
		texto += DATA + "\n";
		for (int i = 0; i < getRefObjetos().size() - 1; ++i) {
			for (int j = 0; j < getRefObjetos().get(i).getCaracteristicas().size(); ++j) {
				texto += (getRefObjetos().get(i).getCaracteristicas().get(j).getValor() > 0.0 ? "1.0" : "?") + ",";
			}
			texto += (getRefObjetos().get(i).getCaracteristicas().get(getRefObjetos().get(i).getCaracteristicas().size() - 1).getValor() > 0.0 ? "1.0" : "?") + "\n";
		}
		//System.out.println(texto);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("objetos.arff"));
			bw.append(texto);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	// Getters y Setters
	public ArrayList<Objeto> getRefObjetos() {
		return refObjetos;
	}
	public void setRefObjetos(ArrayList<Objeto> refObjetos) {
		this.refObjetos = refObjetos;
	}

	public ArrayList<String> getRefCaracteristicas() {
		return refCaracteristicas;
	}

	public void setRefCaracteristicas(ArrayList<String> refCaracteristicas) {
		this.refCaracteristicas = refCaracteristicas;
	}
	
	
	
}
