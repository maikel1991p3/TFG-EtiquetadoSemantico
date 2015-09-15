package generador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import componenetes.Caracteristica;
import componenetes.Objeto;

/**
 * Realiza un parsing a un fichero de entrada y extrae las imágenes y las características
 * de cada una de éstas para la creación de las instancias, de los algoritmos. 
 * @author Maikel
 *
 */
public class Parser {
	private ArrayList<Objeto> refInstancias;
	private ArrayList<String> totalCaracteristicas = new ArrayList<String>();
	private final String SEPARADOR_CSV = ";";

	private final boolean showLogs = true;

	public Parser(ArrayList<Objeto> refInst) {
		this.refInstancias = refInst;
	}

	public ArrayList<Objeto> generarInstancias(String nombreFich) {
		// Leer fichero y crear instancias
		String formato = obtenerFormato(nombreFich);
		switch (formato) {
		case "csv":
			refInstancias = generarDesdeCSV(nombreFich);
			break;

		case "xml":
			refInstancias = generarDesdeXML(nombreFich);
			break;

		default:
		}
		return refInstancias;
	}

	private String obtenerFormato(String nombreFich) {
		return nombreFich.split("[.]")[1]; // Ej: test.xml -> return "xml"
	}

	/**
	 * Lee instancias desde un fichero en formato CSV: Objeto1,c1,c2,c3
	 * Objeto2,c1,c2,c3 ... ObjetoN,c1,c2,c3
	 * 
	 * @return Instancias
	 */
	private ArrayList<Objeto> generarDesdeCSV(String nombreFich) {
		try {
			BufferedReader bf1 = new BufferedReader(new FileReader(nombreFich));
			String linea;
			String[] datos;
			Objeto oTemp;
			Caracteristica cTemp;
			ArrayList<Caracteristica> cTemporales;
			String nombreObj;

			HashMap<Integer, String> caractTemp = new HashMap<Integer, String>();
			// Primera línea contiene TODAS las etiquetas de las características
			// de objetos
			int nCarct = 0;
			while (bf1.ready()) {
				linea = bf1.readLine();
				datos = linea.split(SEPARADOR_CSV);
				for (int i = 0; i < datos.length; ++i) {
					if (i != 0 && datos[i].length() > 1 && !caractTemp.containsValue(datos[i])) {
						caractTemp.put(nCarct, datos[i]);
						nCarct++;
						totalCaracteristicas.add(datos[i]);
					}
				}
			}

			bf1.close();
			
			BufferedReader bf = new BufferedReader(new FileReader(nombreFich));
			// Rellenamos las características de los objetos y los vamos creando
			boolean contenido = false;
			while (bf.ready()) {
				linea = bf.readLine();
				datos = linea.split(SEPARADOR_CSV);
				nombreObj = datos[0];
				cTemporales = new ArrayList<Caracteristica>();
				for (int i = 0; i < totalCaracteristicas.size(); ++i) {
					contenido = false;
					for (int j = 1; j < datos.length; ++j) {
						if (datos[j].length() < 1)
							datos[j] = "_";
						if (totalCaracteristicas.get(i).equalsIgnoreCase(
								datos[j])) {
							contenido = true;
							break;
						}

					}
					if (contenido)
						cTemp = new Caracteristica(totalCaracteristicas.get(i),
								1.0, 1.0);
					else
						cTemp = new Caracteristica(totalCaracteristicas.get(i),
								0.0, 1.0);
					cTemporales.add(cTemp);
				}
				oTemp = new Objeto(nombreObj, cTemporales, true,
						refInstancias.size());
				refInstancias.add(oTemp);
			}
			bf.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: no ha sido posible leer el fichero '"
					+ nombreFich + "'");
		}
		return refInstancias;
	}


	/**
	 * Lee instancias desde un fichero en formato XML Objeto1 : { [c1 : 1, c2 :
	 * 0, c3 : 1] }, Objeto2 : { ... }
	 * 
	 * @return
	 */
	private ArrayList<Objeto> generarDesdeXML(String nombreFich) {
		return null;
	}

	public ArrayList<String> getTotalCaracteristicas() {
		return totalCaracteristicas;
	}

	public void setTotalCaracteristicas(ArrayList<String> totalCaracteristicas) {
		this.totalCaracteristicas = totalCaracteristicas;
	}

}
