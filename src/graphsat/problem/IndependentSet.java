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

/**
 *
 * @author David Kuna
 */
public class IndependentSet extends Solver {
	
	private final int k;

	public IndependentSet(Graph graph, int k) {
		super(graph);
		this.k = k;
	}
	
	@Override
	protected void print() {
		if(this.model != null){
			int count = 0;
			System.out.print("Independent set = {");
			for(int i = 0; i < this.model.length; i++){
				if(this.model[i] > 0 && this.model[i] <= this.graph.getVertexCount()){
					if(count > 0) System.out.print(", ");
					System.out.print(this.model[i]);
					count++;
				}
			}
			System.out.println("}");
		}else{
			System.out.println("Independet set of size " + this.k + " doesn't exists !");
		}
	}

	@Override
	protected void prepareCNF() {
		this.cnf += this.getEdgeConstraint();
		this.cnf += this.getMatrixColumnConstraint();
		this.cnf += this.getMartrixMaxInRowConstraint();
		this.cnf += this.getMartixAtLeastOneInRowConstraint();
		this.cnf += this.getMatrixProjectionToVariables();
		this.cnf = this.getHeader() + "\n" + this.cnf;
	}
	
	
	/**
	 * Return string of edge constraint in CNF format
	 * (¬vi ∨ ¬vj)
	 * @return String
	 */
	private String getEdgeConstraint(){
		String output = "";
		int[] edge;
		for(int i = 0; i < this.graph.getEdges().size(); i++){
			edge = (int[]) this.graph.getEdges().get(i);
			output += neg(edge[0]) + " " + neg(edge[1]) + " " + this.endl;
		}
		return output;
	}
	
	/**
	 * Max one true in column constraint
	 * (xi,j ⇒ ¬xi',j)
	 * @return String
	 */
	private String getMatrixColumnConstraint(){
		String output = "";
		int [] combI;
		for(int j = 1; j <= this.graph.getVertexCount(); j++){
			combI = new int[this.k];
			for(int i = 1; i <= this.k; i++){
				combI[i-1] = neg(this.matrixIndex(i, j));
			}
			output += this.allCombination(combI, 0, 2, "");
		}
		return output;
	}
	
	/**
	 * Max one true in row constraint
	 * (xi,j ⇒ ¬xi,j')
	 * @return String
	 */
	private String getMartrixMaxInRowConstraint(){
		String output = "";
		int [] combJ;
		for(int i = 1; i <= this.k; i++){
			combJ = new int[this.graph.getVertexCount()];
			for(int j = 1; j <= this.graph.getVertexCount(); j++){
				combJ[j-1] = neg(this.matrixIndex(i, j));
			}
			output += this.allCombination(combJ, 0, 2, "");
		}
		return output;
	}
	
	private String getMartixAtLeastOneInRowConstraint(){
		String line, output = "";
		for(int i = 1; i <= this.k; i++){
			line = "";
			for(int j = 1; j <= this.graph.getVertexCount(); j++){
				line += this.matrixIndex(i, j) + " ";
			}
			output += line + this.endl;
		}
		return output;
	}
	
	private String getMatrixProjectionToVariables(){
		String output = "";
		for(int i = 1; i <= this.k; i++){
			for(int j = 1; j <= this.graph.getVertexCount(); j++){
				output += neg(this.matrixIndex(i, j)) + " " + j + " " + this.endl;
			}
		}
		return output;
	}
	
	private int matrixIndex(int set, int vertex){
		return ((set - 1) * this.graph.getVertexCount()) + vertex + this.graph.getVertexCount();
	}
}
