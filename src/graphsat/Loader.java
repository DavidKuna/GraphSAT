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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author David Kuna
 */
public class Loader {
	
	public Graph loadGraph(String file) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String[] split;
		int i = 0, source, target;
		Graph graph = null;
		while ((line = br.readLine()) != null) {
		   if(i == 0){
			   int nodes = Integer.parseInt(line);
			   graph = new Graph(nodes);
		   }else{
			   split = line.split(" ");
			   source = Integer.parseInt(split[0]);
			   target = Integer.parseInt(split[1]);
			   if(source != 0 || target != 0){
				   graph.add(source, target);
			   }else{
				   br.close();
				   return graph;
			   }
		   }
		   i++;
		}
		br.close();
		return graph;
	}
}
