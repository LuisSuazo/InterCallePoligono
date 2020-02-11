package interSP.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.csvreader.CsvReader;

import interSP.dto.Calle;
import interSP.dto.Poligono;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Lectura {


	public static void LeerCalles(String inputFile, Vector<Calle> Calles) {
		try {
			CsvReader archivo = new CsvReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
			archivo.setDelimiter( ';' );
			archivo.readHeaders( );
			while( archivo.readRecord( ) ){
				String idarco = archivo.get("idarco");
				String idtramo = archivo.get("idtramo");
				String nombre = archivo.get("nombre");
				String longitud = archivo.get("longitud");
				String clase = archivo.get("clase");
				Double punto_inicio_x = Double.parseDouble(archivo.get("punto_inicio_x"));
				Double punto_inicio_y = Double.parseDouble(archivo.get("punto_inicio_y"));
				Double punto_final_x = Double.parseDouble(archivo.get("punto_final_x"));
				Double punto_final_y = Double.parseDouble(archivo.get("punto_final_y"));
				String sentido = archivo.get("sentido");
				String velocidad = archivo.get("velocidad");
				Line2D linea = new Line2D.Double(punto_inicio_x,punto_inicio_y,punto_final_x,punto_final_y);
				Calle calle =new Calle(idarco, idtramo, nombre, longitud, clase, punto_inicio_x, punto_inicio_y, punto_final_x, punto_final_y, sentido, velocidad, linea);
				Calles.add(calle);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void LeerPoligono(String inputFile, HashMap<Integer,Poligono> Poligonos) {
		try {
			CsvReader archivo = new CsvReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
			archivo.setDelimiter( ';' );
			archivo.readHeaders( );
			Integer lastState=-1;
			while( archivo.readRecord()){
				Integer idState = Integer.parseInt(archivo.get("IDSTATE"));
				if(idState == 5 || idState == 6 || idState == 7 || idState == 8 || idState == 13) {
					if(lastState != idState) {
						lastState = idState;
						Poligono aux = new Poligono();
						Poligonos.put(idState, aux);
						Poligonos.get(idState).setIdState(idState);
						String state = archivo.get("STATE");
						Poligonos.get(idState).setState(state);
					}
					Double x = Double.parseDouble(archivo.get("PUNTO X"));
					Double y = Double.parseDouble(archivo.get("PUNTO Y"));
					Integer numeroPoligono = Integer.parseInt(archivo.get("NUMERO POLIGONO"));
					Point2D punto = new Point2D.Double(x,y);
					if(!Poligonos.get(idState).getPoligonos().containsKey(numeroPoligono)) {
						ArrayList<Point2D> auxList = new ArrayList<Point2D>();
						Poligonos.get(idState).getPoligonos().put(numeroPoligono, auxList);
					}
					Poligonos.get(idState).getPoligonos().get(numeroPoligono).add(punto);
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
