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
public class HamiltonCircle extends Solver {

	public HamiltonCircle(Graph graph) {
		super(graph);
	}
	
	/**
	 * Vertex is at least on one position
	 * @return String
	 */
	private String getSingleVertexConstraint(){
		String line, output = "";
		for(int i = 1; i <= this.graph.getVertexCount(); i++){
			line = "";
			for(int j = 1; j <= this.graph.getVertexCount(); j++){
				line += this.matrixIndex(i, j) + " ";
			}
			output += line + this.endl;
		}
		return output;
	}
	
	/**
	 * Combination of all vertex constraint
	 * @return String
	 */
	private String getAllVertexConstraint(){
		String output = "";
		int[] comb;
		for(int j = 1; j <= this.graph.getVertexCount(); j++){
			comb = new int[this.graph.getVertexCount()];
			for(int i = 1; i <= this.graph.getVertexCount(); i++){
				comb[i-1] = neg(this.matrixIndex(i, j));
			}
			output += this.allCombination(comb, 0, 2, "");
		}
		return output;
	}
	
	/**
	 * Constraint for pairs of vertex which have not edge
	 * @return String
	 */
	private String getNonEdgeConstraint(){
		String line, output = "";
		int[] nonEdge;
		for(int i = 0; i < this.graph.getNonEdges().size(); i++){
			nonEdge = (int[]) this.graph.getNonEdges().get(i);
			line = "";
			for(int j = 1; j <= this.graph.getVertexCount(); j++){
				int next = (j % this.graph.getVertexCount()) + 1;
				line += neg(this.matrixIndex(nonEdge[0], j)) + " " + neg(this.matrixIndex(nonEdge[1], next)) + " " + this.endl;
				line += neg(this.matrixIndex(nonEdge[1], j)) + " " + neg(this.matrixIndex(nonEdge[0], next)) + " " + this.endl;
			}
			output += line;
		}
		return output;
	}
	
	@Override
	protected void prepareCNF() {
		this.cnf += this.getSingleVertexConstraint();
		//this.cnf += "\n";
		this.cnf += this.getAllVertexConstraint();
		//this.cnf += "\n";
		this.cnf += this.getNonEdgeConstraint();
		this.cnf = this.getHeader() + "\n" + this.cnf;
	}

	@Override
	protected void print() {
		if(this.model != null){
			int[] vertex;
			int[] set = new int[this.graph.getVertexCount()+1];
			for(int i = 0; i < this.model.length; i++){
				if(this.model[i] > 0){
					vertex = extractMatrixIndex(this.model[i]);
					set[vertex[1]] = vertex[0];
				}
			}
			
			System.out.print("Hamilton circle: ");
			for(int i = 1; i <= this.graph.getVertexCount(); i++){
				System.out.print(set[i] + " --> ");
			}
			System.out.println(set[1]);
		}else{
			System.out.println("Hamilton circle doesn't exists in this graph!");
		}
	}
	
	private int matrixIndex(int vertex, int position){
		return ((vertex - 1) * this.graph.getVertexCount()) + position;
	}
	
	private int[] extractMatrixIndex(int index){
		int[] result = new int[2];
		
		result[0] = (int) floor(index/this.graph.getVertexCount()) + (index % this.graph.getVertexCount() > 0 ? 1 : 0);
		result[1] = (int) ((index-1) % this.graph.getVertexCount())+1;
		return result;
	}
}
