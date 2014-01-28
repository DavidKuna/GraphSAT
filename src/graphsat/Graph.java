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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Kuna
 */
public class Graph {
	
	private List[] graph;
	private ArrayList<int[]> edges;

	private final int vertexCount;
	
	public Graph(int count){
		this.vertexCount = count;
		this.init();
	}
	
	/**
	 * Add edge to graph
	 * @param index
	 * @param value
	 * @return boolean
	 */
	public boolean add(int index, int value){
		if(!this.isEdgeExists(index, value)){
			this.graph[index].add(value);
			this.edges.add(new int[] {index, value});
		}
		return true;
	}
	
	public int getVertexCount(){
		return this.vertexCount;
	}
	
	public boolean isEdgeExists(int source, int target){
		return this.graph[source].contains(target);
	}
	
	private boolean init(){
		this.graph = new ArrayList[this.vertexCount+1];
		this.edges = new ArrayList<>();
		for(int i = 0; i <= this.vertexCount; i++){
			this.graph[i] = new ArrayList<>();
		}
		return true;
	}
	
	public ArrayList getEdges(){
		return this.edges;
	}
}
