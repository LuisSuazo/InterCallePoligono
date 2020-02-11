package interSP.util;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import geotransform.coords.Gdc_Coord_3d;
import geotransform.coords.Utm_Coord_3d;
import geotransform.ellipsoids.WE_Ellipsoid;
import geotransform.transforms.Utm_To_Gdc_Converter;
import interSP.dto.Calle;
import interSP.dto.Poligono;

import java.util.Vector;

public class Funciones {
	
	public static void makePath2D(HashMap<Integer,Poligono> Poligonos) {
		Poligonos.forEach((k,v) -> {
			v.getPoligonos().forEach((k2,v2) -> {
				Path2D poly = new Path2D.Double();
				for(int i=0;i<v2.size();i++) {
					if (i == 0) {
				        poly.moveTo(v2.get(i).getX(),v2.get(i).getY());
				    }
				    else {
				        poly.lineTo(v2.get(i).getX(),v2.get(i).getY());
				    }
				}
				poly.closePath();
				v.getPoligonos2D().put(k2, poly);
			});
		});
	}
	
	public static void IntersectarCallePoligonos(HashMap<Integer,Poligono> Poligonos,Vector<Calle> Calles, 
												 HashMap<String,Vector<Calle>> CallesIntersectadas) {
		Calles.parallelStream().forEach(it -> {
			boolean salir = false;
			for (Entry<Integer, Poligono> entry : Poligonos.entrySet()) {
				for (Entry<Integer, Path2D> value : entry.getValue().getPoligonos2D().entrySet()) {
					if(value.getValue().contains(it.getLinea().getP1()) && value.getValue().contains(it.getLinea().getP2())) {
						salir = true;
						it.setIdState(entry.getValue().getIdState());
						it.setState(entry.getValue().getState());
						CallesIntersectadas.get(entry.getValue().getState()).add(it);
						break;
					}
				}
				if(salir) break;
			}
		});
	}
	
	public static ArrayList<Double> conv_utm2latlon(double x, double y) {

		ArrayList<Double> lat_lon = new ArrayList<>();
		// Utm_To_Gdc_Converter.Convert(utm,gdc);
		Gdc_Coord_3d gdc = new Gdc_Coord_3d();
		Utm_Coord_3d utm = new Utm_Coord_3d(x, // x utm
				y, // y utm
				0, // z utm
				(byte) 19, // zona
				false); // hemisferio norte
		Utm_To_Gdc_Converter.Init(new WE_Ellipsoid());
		Utm_To_Gdc_Converter.Convert(utm, gdc);
		lat_lon.add(gdc.latitude);
		lat_lon.add(gdc.longitude);
		return lat_lon;
	}
}
