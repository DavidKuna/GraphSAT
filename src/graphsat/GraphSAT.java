/* 
 * Copyright (C) 2014 David Kuna
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package graphsat;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author David Kuna
 */
public class GraphSAT {
	
	/**
	 * @param args the command line arguments
	 * @throws java.io.FileNotFoundException
	 */
	public static void main(String[] args) throws IOException, Exception {
		try{
		String filepath = getFilepath(args);
		int problemType = getTypeOfProblem(args);
		int k = getK(args);
		
		Loader loader = new Loader();
		Graph graph = loader.loadGraph(filepath);
		Solver solver;
		
		switch(problemType){
			case 1:
				solver = new GraphColoring(graph, k);
				break;
			
			case 2:
				solver = new IndependentSet(graph, k);
				break;
			default:
				throw new Exception("Invalid type of problem!");
		}
		
		solver.solve();
		solver.print();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private static String getFilepath(String[] args) throws FileNotFoundException{
		if(args.length > 0 && args[0] != null){
			return args[0];
		}else{
			throw new FileNotFoundException("Missing source file!"); 
		}
	}
	
	private static int getTypeOfProblem(String[] args){
		if(args.length > 1 && args[1] != null){
			return Integer.parseInt(args[1]);
		}else{
			throw new IllegalArgumentException("Missing argument: Type of problem!"); 
		}
	}
	
	private static int getK(String[] args){
		if(args.length > 2 && args[2] != null){
			return Integer.parseInt(args[2]);
		}else{
			throw new IllegalArgumentException("Missing argument: Number k!"); 
		}
	}
}
