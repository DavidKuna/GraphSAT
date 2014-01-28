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
package graphsat.problem;

import graphsat.Graph;
import graphsat.Solver;
import static java.lang.Math.floor;

/**
 *
 * @author David Kuna
 */
public class GraphColoring extends Solver {

	/**
	 * Number of colors
	 */
	private final int k;
	
	public GraphColoring(Graph graph, int k) {
		super(graph);
		this.k = k;
	}
	
	@Override
	protected void print() {
		if(this.model != null){
			int[] vertex;
			for(int i = 0; i < this.model.length; i++){
				if(this.model[i] > 0){
					vertex = this.extractCNFIndex(this.model[i]);
					System.out.println("Vertex " + vertex[0] + " = " + vertex[1]);
				}
			}
		}else{
			System.out.println("No model exists !");
		}
	}
	
	@Override
	protected void prepareCNF(){
		this.cnf += this.getVertexConstraint();
		this.cnf += this.getEdgeConstraint();
		this.cnf = this.getHeader() + "\n" + this.cnf;
	}
	
	/**
	 * Return string of edge constraint in CNF format
	 * @return String
	 */
	private String getEdgeConstraint(){
		String output = "";
		int[] edge;
		for(int i = 0; i < this.graph.getEdges().size(); i++){
			edge = (int[]) this.graph.getEdges().get(i);
			for(int c = 1; c <= this.k; c++){
				output += this.cnfIndex(edge[0], c)*(-1)
						+ " "
						+ this.cnfIndex(edge[1], c)*(-1)
						+ " "
						+ this.endl;
			}
		}
		return output;
	}
	
	/**
	 * Return string of vertex constraint in CNF format
	 * @return String
	 */
	private String getVertexConstraint(){
		String line, output = "";
		int[] comb;
		for(int i = 1; i <= this.graph.getVertexCount(); i++){
			line = "";
			comb = new int[this.k];
			for(int c = 0; c < this.k; c++){
				line += this.cnfIndex(i, c+1) + " ";
				comb[c] = neg(this.cnfIndex(i, c+1));
			}
			output += line + this.endl + this.allCombination(comb, 0, 2, "");
		}
		return output;
	}
	
	private int cnfIndex(int vertex, int color){
		return ((vertex - 1) * this.k) + color;
	}
	
	private int[] extractCNFIndex(int index){
		int[] result = new int[2];
		
		result[0] = (int) floor(index/this.k) + (index % this.k > 0 ? 1 : 0);
		result[1] = (int) (index % this.k) + 1;
		return result;
	}
}
