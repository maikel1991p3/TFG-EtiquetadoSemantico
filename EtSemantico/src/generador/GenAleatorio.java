package generador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

import componenetes.Caracteristica;
import componenetes.Objeto;

/**
 * Genera instancias a través del uso de una Distribución Multivariante Aleatoria.
 * Estas instancias servirán como método base para testeo del algoritmo.
 * @author Maikel
 *
 */
public class GenAleatorio {
	private ArrayList<Objeto> refInstancias;
	private ArrayList<String> totalCaracteristicas = new ArrayList<String>();
	private int numObjetos = 0; // Número de instancias a generar
	private int numDimensiones = 0; // Número de características de cada
									// instancia
	private double[] medias; // Medias para la distribución
	private double[][] covarianzas;// Matriz de covarianzas

	public GenAleatorio(ArrayList<Objeto> refInst) {
		this.refInstancias = refInst;
	}

	public ArrayList<Objeto> generarInstancias(String nombreFichConfig) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(
					nombreFichConfig));
			String linea = new String();

			// Número de Objetos (instancias)
			linea = bf.readLine();
			numObjetos = Integer.parseInt(linea.split(" ")[0]);

			// Número de cracterísticas (Dimensión)
			linea = bf.readLine();
			numDimensiones = Integer.parseInt(linea.split(" ")[0]);

			medias = new double[numDimensiones];
			// Leemos el array de medias para la distribución
			linea = bf.readLine();
			String[] datosMedia = linea.split(" ");
			try {
				for (int i = 0; i < numDimensiones; ++i) {
					medias[i] = Double.parseDouble(datosMedia[i]);
				}
			} catch (Exception e) {
				throw new Exception("El número de medias introducidas no coincide con la dimensión de los objetos!");
			}
			// Ignoramos el texto "matriz de covarianzas" del fichero
			linea = bf.readLine();

			// Leemos la matriz de covarianzas
			covarianzas = new double[numDimensiones][];
			for (int i = 0; i < numDimensiones; ++i)
				covarianzas[i] = new double[numDimensiones];
			String[] datosCovarianzas;
			for (int i = 0; i < numDimensiones; ++i) {
				linea = bf.readLine();
				datosCovarianzas = linea.split(" ");
				for (int j = 0; j < numDimensiones; ++j) {
					covarianzas[i][j] = Double.parseDouble(datosCovarianzas[j]);
				}
			}
			try {
				setRefInstancias(generarAleatorias());
			} catch (Exception e) {
				e.printStackTrace();
			}
			bf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getRefInstancias();
	}

	public ArrayList<Objeto> generarAleatorias() throws Exception {
		ArrayList<Objeto> result = new ArrayList<Objeto>();
		MultivariateNormalDistribution n = new MultivariateNormalDistribution(
				medias, covarianzas);
		Objeto temp;
		ArrayList<Caracteristica> caracteristicas;
		Caracteristica cTemp;
		for (int i = 0; i < numObjetos; ++i) {
			caracteristicas = new ArrayList<Caracteristica>();
			for (int j = 0; j < n.sample().length; ++j) {
				cTemp = new Caracteristica(String.valueOf(n.sample()[j]),
						n.sample()[j], 1.0);
				caracteristicas.add(cTemp);
			}
			totalCaracteristicas.add(String.valueOf(i));
			temp = new Objeto(String.valueOf(i), caracteristicas, true, i);
			result.add(temp);
		}

		return result;
	}

	public ArrayList<Objeto> getRefInstancias() {
		return refInstancias;
	}

	public void setRefInstancias(ArrayList<Objeto> refInstancias) {
		this.refInstancias = refInstancias;
	}

	public ArrayList<String> getTotalCaracteristicas() {
		return totalCaracteristicas;
	}

	public void setTotalCaracteristicas(ArrayList<String> totalCaracteristicas) {
		this.totalCaracteristicas = totalCaracteristicas;
	}

}
