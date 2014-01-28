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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

/**
 *
 * @author David Kuna
 */
public abstract class Solver {
	
	protected Graph graph;
	protected final String endl = "0\n";
	protected final String cnfFile = "temp.cnf";
	protected String cnf = "";
	protected int[] model;
	
	protected abstract void prepareCNF();	
	protected abstract void print();
	
	public Solver(Graph graph){
		this.graph = graph;
	}
	
	public void solve() throws FileNotFoundException, UnsupportedEncodingException{
		this.prepareCNF();
		this.writeToFile();
		this.solveSAT();
	};
		
	protected static int countLines(String str){
		String[] lines = str.split("\r\n|\r|\n");
		return  lines.length;
	}
	
	protected static int neg(int variable){
		return variable*(-1);
	}
	
	protected String getHeader(){
		String output = "p cnf ";
		output += this.graph.getVertexCount()
				+ " "
				+ Solver.countLines(this.cnf);
		return output;
	}
	
	/**
	 * Retrun string in CNF format with all combinations of array S
	 * @param S
	 * @param start
	 * @param r
	 * @param output
	 * @return 
	 */
	protected String allCombination(int[] S, int start, int r, String output) {
		int length = S.length;
		String out = "";
		if (r == 1) {
			for (int i = start; i < length; i++) {
				out += output + " " + S[i] + " " + endl;
			}
			return out;
		} else {
			for (int k = start; k < length - r + 1; k++) {
				out += allCombination(S, k + 1, r - 1, output + S[k]);
			}
			return out;
		}
    }
	
	protected int[] extractModel(String model){
		String[] numbers = model.split(" ");
		int[] a = new int [numbers.length];

		for (int x = 0; x < numbers.length; x++){
				a[x] = Integer.parseInt (numbers[x]);
		}
		
		return a;
	}
	
	protected void solveSAT(){
		ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new DimacsReader(solver);
        try {
            IProblem problem = reader.parseInstance(this.cnfFile);
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");
                this.model = this.extractModel(reader.decode(problem.model()));
            } else {
                System.out.println("Unsatisfiable !");
            }
        } catch (FileNotFoundException e) {
			System.out.println("CNF source not found!");
        } catch (ParseFormatException | IOException e) {
			System.out.println("Parse error!");
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");      
        }
	}

	private boolean writeToFile() throws FileNotFoundException, UnsupportedEncodingException{
		try (PrintWriter writer = new PrintWriter(this.cnfFile, "UTF-8")) {
			writer.print(this.cnf);
		}
		return false;
	}
}
