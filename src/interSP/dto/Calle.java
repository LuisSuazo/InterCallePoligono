package interSP.dto;

import java.awt.geom.Line2D;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Calle {
	
	private String idarco;
	private String idtramo;
	private String nombre;
	private String longitud;
	private String clase;
	private Double punto_inicio_x;
	private Double punto_inicio_y;
	private Double punto_final_x;
	private Double punto_final_y;
	private String sentido;
	private String velocidad;
	private Line2D linea;
	private Integer idState;
	private String state;
	
	public Calle(String idarco, String idtramo, String nombre, String longitud, String clase, Double punto_inicio_x,
			Double punto_inicio_y, Double punto_final_x, Double punto_final_y, String sentido, String velocidad,
			Line2D linea) {
		super();
		this.idarco = idarco;
		this.idtramo = idtramo;
		this.nombre = nombre;
		this.longitud = longitud;
		this.clase = clase;
		this.punto_inicio_x = punto_inicio_x;
		this.punto_inicio_y = punto_inicio_y;
		this.punto_final_x = punto_final_x;
		this.punto_final_y = punto_final_y;
		this.sentido = sentido;
		this.velocidad = velocidad;
		this.linea = linea;
	} 
	
	
}
