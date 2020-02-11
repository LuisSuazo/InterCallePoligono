package interSP.dto;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Data;

@Data
public class Poligono {
	
	private String state;
	
	private Integer idState;
	
	private HashMap<Integer,ArrayList<Point2D>> Poligonos = new HashMap<Integer,ArrayList<Point2D>>();
	
	private HashMap<Integer,Path2D> Poligonos2D = new HashMap<Integer,Path2D>();
	
}
