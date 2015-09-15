package ejecutar;

import clasificador.Clasificador;

@SuppressWarnings("unused")
public class Ejecutar {

	// Métricas a utilizar
	private final static int METRICA_EUCLIDEA = 0;
	private final static int METRICA_MANHATTHAN = 1;
	private final static int METRICA_GRAVITACIONAL = 2;
	private final static int METRICA_POR_PENALIZACION = 3;
	private final static int METRICA_TAXONOMIA = 4;

	// Tipo de algoritmo a ejecutar
	private final static int ALGORITMO_DIRECTO = 1;
	private final static int ALGORITMO_INDIRECTO = 2;

	// Ficheros de configuración
	private static String nombreFichAl = "configuracion1.txt";
	
	private static String nombreFichCSV = "etiquetas.csv";

	// Parámetros algorítmicos
	final static int D1 = 2;
	final static int D2 = 2;
	final static int GAP = 5;

	final static int IND_OBJ_ORIGEN = 14;

	/**
	 * args:
	 * 0 -> Aleatorio o Desde fichero (true o false)
	 * 1 -> nombreFichero (aleatorio = configuracion, sino datos)
	 * 2 -> tipo métrica (0 - 4)
	 * 3 -> tipo algoritmo (1 - 2)
	 * 4 -> D1
	 * 5 -> D2
	 * 6 -> D GAP
	 * 7 -> Indice del objeto origen
	 * @param args
	 */
	public static void main(String[] args) {
		/*Clasificador gen = new Clasificador(true, nombreFichAl,
				METRICA_EUCLIDEA, ALGORITMO_DIRECTO, D1, D2, GAP,
				IND_OBJ_ORIGEN);*/
		
		if (args.length > 6) {
			boolean aleatorio = Boolean.parseBoolean(args[0]);
			String nombreFich = args[1];
			int metrica = Integer.parseInt(args[2]);
			int algoritmo = Integer.parseInt(args[3]);
			int d1 = Integer.parseInt(args[4]);
			int d2 = Integer.parseInt(args[5]);
			int gap = Integer.parseInt(args[6]);
			int indObjOrigen = Integer.parseInt(args[7]);
			/*System.out.println(aleatorio + "  " + nombreFich + "  " + metrica
					 + "  " + algoritmo + "  " + d1 + "  " + d2 + "  " + gap + "  " +
					indObjOrigen);*/
			Clasificador gen2 = new Clasificador(aleatorio, nombreFich,
					metrica, algoritmo, d1, d2, gap,
					indObjOrigen);
		} else {
			Clasificador gen2 = new Clasificador(false, nombreFichCSV,
				METRICA_TAXONOMIA, ALGORITMO_DIRECTO, D1, D2, GAP,
				IND_OBJ_ORIGEN);
			
		}
	}

}
