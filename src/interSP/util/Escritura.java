package interSP.util;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import com.csvreader.CsvWriter;

import interSP.dto.Calle;

public class Escritura {

	public static void GrafoKML(HashMap<String,Vector<Calle>> CallesIntersectadas) {
		CallesIntersectadas.forEach((k,v) -> {
			EscribirGrafo(k,v);
		});
	}
	
	public static void EscribirCalles(HashMap<String,Vector<Calle>> CallesIntersectadas,String output) {
		File folder = new File("Output/Grafo/");
		if( !folder.exists() ){
			folder.mkdirs();
		}
		CsvWriter csvWriter = InicializarCsvWriter("Output/Grafo/"+output+".csv");
		CallesIntersectadas.forEach((k,v) -> {
			EscribirPolReg(csvWriter,v);
		});
		csvWriter.close();
	}
	
	
	private static CsvWriter InicializarCsvWriter(String SalidaCSV) {
		CsvWriter csvOutput = null;
		try {
			csvOutput=new CsvWriter(new FileWriter(SalidaCSV), ';');
			csvOutput.write("idarco");
			csvOutput.write("idtramo");
			csvOutput.write("nombre");
			csvOutput.write("longitud");
			csvOutput.write("clase");
			csvOutput.write("punto_inicio_x");
			csvOutput.write("punto_inicio_y");
			csvOutput.write("punto_final_x");
			csvOutput.write("punto_final_y");
			csvOutput.write("sentido");
			csvOutput.write("velocidad");
			csvOutput.write("codReg");
			csvOutput.write("nomReg");
			csvOutput.endRecord();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return csvOutput;
	}


	private static void EscribirPolReg(final CsvWriter csvOutput, Vector<Calle> it) {
		it.forEach(gt -> {
			try {
				csvOutput.write(gt.getIdarco());
				csvOutput.write(gt.getIdtramo());
				csvOutput.write(gt.getNombre());
				csvOutput.write(gt.getLongitud());
				csvOutput.write(gt.getClase());
				csvOutput.write(String.valueOf(gt.getPunto_inicio_x()));
				csvOutput.write(String.valueOf(gt.getPunto_inicio_y()));
				csvOutput.write(String.valueOf(gt.getPunto_final_x()));
				csvOutput.write(String.valueOf(gt.getPunto_final_y()));
				csvOutput.write(gt.getSentido());
				csvOutput.write(gt.getVelocidad());
				csvOutput.write(String.valueOf(gt.getIdState()));
				csvOutput.write(gt.getState());
				csvOutput.flush();
				csvOutput.endRecord();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	
	private static void EscribirGrafo(String nombre, Vector<Calle> CallesIntersectadas) {
		File folder = new File("Output/KML_Output/Grafo/");
		FileWriter fichero = null;
		if( !folder.exists() ){
			folder.mkdirs();
		}
		try {
			// use FileWriter constructor that specifies open for appending
			fichero = new FileWriter("Output/KML_Output/Grafo/"+nombre+".kml"); /// Escribe el KML con el nombre de la comuna
			final PrintWriter pw = new PrintWriter(fichero);
			pw.println( "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
					"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
					"\t"+"<Document id=\"root_doc\">\n"+
					"\t\t"+"<Schema name=\"PuntosMM\" id=\"PuntosMM\">\n" +
					"\t\t\t"+"<SimpleField name=\"id\" type=\"int\"></SimpleField>\n" +
					"\t\t\t"+"<SimpleField name=\"idtramo\" type=\"int\"></SimpleField>\n" +
					"\t\t\t"+"<SimpleField name=\"nombre\" type=\"string\"></SimpleField>\n" +
					"\t\t\t"+"<SimpleField name=\"longitud\" type=\"double\"></SimpleField>\n" +
					"\t\t\t"+"<SimpleField name=\"clase\" type=\"string\"></SimpleField>\n" +
					"\t\t\t"+"<SimpleField name=\"sentido\" type=\"int\"></SimpleField>\n" +
					"\t\t\t"+"<SimpleField name=\"velocidad\" type=\"double\"></SimpleField>\n" +
					"\t\t\t"+"<SimpleField name=\"region\" type=\"string\"></SimpleField>\n" +
					"\t\t\t"+"<SimpleField name=\"cod_region\" type=\"int\"></SimpleField>\n" +
					"\t\t"+"</Schema>" );
			pw.println("<Style id=\"sn_ylw-pushpin\">\r\n" + 
					"		<BalloonStyle>\r\n" + 
					"		</BalloonStyle>\r\n" + 
					"		<LineStyle>\r\n" + 
					"			<color>ff00ffff</color>\r\n" + 
					"			<width>3</width>\r\n" + 
					"		</LineStyle>\r\n" + 
					"	</Style>\r\n" + 
					"	<Style id=\"sh_ylw-pushpin\">\r\n" + 
					"		<IconStyle>\r\n" + 
					"			<scale>1.2</scale>\r\n" + 
					"		</IconStyle>\r\n" + 
					"		<BalloonStyle>\r\n" + 
					"		</BalloonStyle>\r\n" + 
					"		<LineStyle>\r\n" + 
					"			<color>ff00ffff</color>\r\n" + 
					"			<width>3</width>\r\n" + 
					"		</LineStyle>\r\n" + 
					"	</Style>\r\n" + 
					"	<StyleMap id=\"msn_ylw-pushpin\">\r\n" + 
					"		<Pair>\r\n" + 
					"			<key>normal</key>\r\n" + 
					"			<styleUrl>#sn_ylw-pushpin</styleUrl>\r\n" + 
					"		</Pair>\r\n" + 
					"		<Pair>\r\n" + 
					"			<key>highlight</key>\r\n" + 
					"			<styleUrl>#sh_ylw-pushpin</styleUrl>\r\n" + 
					"		</Pair>\r\n" + 
					"	</StyleMap>");

			pw.println( "\t\t"+"<Folder>\n" +  
					"\t\t\t"+"<name>"+nombre+"</name>" );
			for (Calle aux : CallesIntersectadas) {
				pw.print( "\t\t\t"+"<Placemark>\n"+
						"\t\t\t\t"+"<name>"+aux.getNombre()+"</name>\n" +
						"\t\t\t\t"+"<styleUrl>#msn_ylw-pushpin</styleUrl>"+
						"\t\t\t\t"+"<visibility>0</visibility>\n" +
						"\t\t\t\t"+"<ExtendedData><SchemaData schemaUrl=\"PuntosMM\">\n" +
						"\t\t\t\t"+"<SimpleField name=\"id\">"+aux.getIdarco()+"</SimpleField>\n" +
						"\t\t\t\t"+"<SimpleField name=\"idtramo\">"+aux.getIdtramo()+"</SimpleField>\n" +
						"\t\t\t\t"+"<SimpleField name=\"nombre\">"+aux.getNombre()+"</SimpleField>\n" +
						"\t\t\t\t"+"<SimpleField name=\"longitud\">"+aux.getLongitud()+"</SimpleField>\n" +
						"\t\t\t\t"+"<SimpleField name=\"clase\">"+aux.getClase()+"</SimpleField>\n" +
						"\t\t\t\t"+"<SimpleField name=\"sentido\">"+aux.getSentido()+"</SimpleField>\n" +
						"\t\t\t\t"+"<SimpleField name=\"velocidad\">"+aux.getVelocidad()+"</SimpleField>\n" +
						"\t\t\t\t"+"<SimpleField name=\"region\">"+aux.getNombre()+"</SimpleField>\n" +
						"\t\t\t\t"+"<SimpleField name=\"cod_region\">"+aux.getIdState()+"</SimpleField>\n" +
						"\t\t\t\t"+"</SchemaData></ExtendedData>");
				pw.print( "\t\t\t\t"+"<LineString>\n"+
						"\t\t\t\t"+"<extrude>1</extrude>\n"+
						"\t\t\t\t"+"<tessellate>1</tessellate>\n"+
						"\t\t\t\t\t"+"<coordinates>\n");
				//ArrayList<Double> latlonFinal = Funciones.conv_utm2latlon(aux.getPunto_final_x(),aux.getPunto_final_y());
				//ArrayList<Double> latlonInicio = Funciones.conv_utm2latlon(aux.getPunto_inicio_x(),aux.getPunto_inicio_y());
				pw.print("\t\t\t\t\t"+aux.getPunto_inicio_y()+","+aux.getPunto_inicio_x()+"\n");
				pw.print("\t\t\t\t\t"+aux.getPunto_final_y()+","+aux.getPunto_final_x()+"\n");
				pw.print("\t\t\t\t\t"+"</coordinates>\n");
				pw.print("\t\t\t\t"+"</LineString>\n" +
						"\t\t\t"+"</Placemark>\n"
						);
				pw.flush();
			}
			pw.print("\t\t"+"</Folder>\n");
			pw.print( 	  "\t"+"</Document>\n" +
					"</kml>");
			pw.close();
			fichero.close();
		}catch(IOException e) { e.printStackTrace(); }
	}
	
	
}
