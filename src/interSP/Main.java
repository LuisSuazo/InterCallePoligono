package interSP;

import java.util.HashMap;
import java.util.Vector;

import interSP.dto.Calle;
import interSP.dto.Poligono;
import interSP.util.Escritura;
import interSP.util.Funciones;
import interSP.util.Lectura;

public class Main {
	public static void main(String args[]){
		Vector<Calle> Calles = new Vector<Calle>();
		HashMap<Integer,Poligono> Poligonos = new HashMap<Integer,Poligono>();
		HashMap<String,Vector<Calle>> CallesIntersectadas = new HashMap<String,Vector<Calle>>();
		Lectura.LeerCalles("./Input/Calles_Chile.csv", Calles);
		Lectura.LeerPoligono("./Input/Chile_Regiones_latlon.csv",Poligonos);
		Funciones.makePath2D(Poligonos);
		Poligonos.forEach((k,v) -> {
			Vector<Calle> auxCalle = new Vector<Calle>();
			CallesIntersectadas.put(v.getState(), auxCalle);
		});
		Funciones.IntersectarCallePoligonos(Poligonos, Calles, CallesIntersectadas);
		Escritura.GrafoKML(CallesIntersectadas);
		Escritura.EscribirCalles(CallesIntersectadas, "CallesIntersectadas");
	}
}
