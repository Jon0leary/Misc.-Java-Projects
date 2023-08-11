/*  Jonathan O'Leary
 *  COP3503 Computer Science 2
 *  Programming Assignment 5
 *  Fall 2022
 */


/*  --- Kruskal's algorithm ---
 *
 *  (1) Sort all the edges from low weight to high.
 *
 *  (2) Take the edge with the lowest wieght and add it to the spanning tree.
 *          - if adding the edge creates a cycle, reject it
 *
 *  (3) Keep adding edges until we reach all vertices
 *  ---------------------------
*/

import java.io.*;
import java.util.*;

class Railroad 
{
    class Edge implements Comparable<Edge> 
    {
        // The Class variables in Edge
        String v1, v2;
        int cost;

        public int compareTo(Edge compareEdge) 
        {
            return this.cost - compareEdge.cost;
        }
    };
    
    class subset
    {
        // The Class Variables in Subset
        int parent;
        int rank;
    };
    
    
    // The Class Variables in Railroad
    String fileName;
    int numVertices, numEdges;
    Edge edgeList[];   
    Set verticesList;
    String verticesArray[];
    
    Railroad(int numTracks, String railroadtxt) throws FileNotFoundException 
    {
        this.fileName = railroadtxt;
        this.numEdges = numTracks;
        
        // Initializes an array of class Edge
        this.edgeList = new Edge[numEdges];
        for (int i = 0; i < numEdges; i++)
        {
            edgeList[i] = new Edge();
        }
        
        // Loads in the Data to the edgeList Array
        loadEdges();
        
        this.verticesList = new HashSet();
        createHashSet();
        this.numVertices = verticesList.size();
        this.verticesArray = new String[numVertices];
        createArray();
    }
    
    
    private void loadEdges() throws FileNotFoundException 
    {
        /*Trusty old Scanner*/
        Scanner sc= new Scanner(new BufferedReader(new FileReader(fileName)));
        int tableCounter = 0;
        
        while (sc.hasNextLine())
        {
            if (sc.hasNext() != true)
            {
                break;
            }  
            
            edgeList[tableCounter].v1 = sc.next();
            edgeList[tableCounter].v2 = sc.next();
            edgeList[tableCounter].cost = sc.nextInt();
            
            tableCounter++;
        }
    }
    
    
    private void createHashSet() 
    {
        // Adds the vertices to a hash set so we can determine the hash sets length later
        for (int i=0; i < numEdges; i++)
        {
            verticesList.addAll(Arrays.asList(edgeList[i].v1));
            verticesList.addAll(Arrays.asList(edgeList[i].v2));
        }
    }
    
    private void createArray() 
    {
        // Creates an Array so I can return the Index Later on in the program
        int counter = 0; 
        for (Object i : verticesList)
        {
            verticesArray[counter] = i.toString();
            counter++;
        }
    }
    
    
    private void mergeSortData(Edge[] eList, int left, int right) 
    {   
        //System.out.println("___MERGE SORT___");
        if (left < right)
        {
            // Find the middle point
            int mid = left + (right - left) / 2;
            
            // Sort the 2 halves
            mergeSortData(eList, left, mid);
            mergeSortData(eList, mid+1, right);
            
            //Merge the Halves
            combine(eList, left, mid, right);
        }
    }
   
    
    private void combine(Edge[] eList, int left, int mid, int right) 
    {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        // Create some Temp Edge Arrays
        Edge L[] = new Edge[n1];
        Edge R[] = new Edge[n2];
        
        // Copy data to Temp Edge Arrays
        for (int i = 0; i < n1; i++)
            L[i] = eList[left + i];
        
        for (int j = 0; j <n2 ; j++)
            R[j] = eList[mid + 1 + j];
        
        int i = 0, j = 0;
        int leftCost, rightCost;
        int k = left;
        
        while (i < n1 && j < n2)
        {
            leftCost = L[i].cost;
            rightCost = R[j].cost;
            
            if (leftCost <= rightCost)
            {
                eList[k] = L[i];
                i++;
            }
            else
            {
                eList[k] = R[j];
                j++;
            }
            k++;
        }
        
        while ( i < n1)
        {
            eList[k] = L[i];
            i++;
            k++;
        }
        
        while (j < n2)
        {
            eList[k] = R[j];
            j++;
            k++;
        }
    }
   
    private int findIndex(String searchString)
    {
        int index;
        
        for (index = 0; index < numVertices; index++)
        {
            if (verticesArray[index].matches(searchString) == true)
            {
                //System.out.println("FOUND: " + searchString + " AT: " + index);
                return index;
            }
        }
        
        return 0;
    }
    
    private int find(subset subsets[], int i)
    {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
    
        return subsets[i].parent;
    }
   
    String buildRailroad() 
    {
        // Sets the Return string to NULL (Probably redundant)
        String returnString = "NULL";
        
        returnString = KruskalAlgo();
        
        return returnString;
    }
    
    private void Union(subset[] subsets, int x, int y) 
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
    
        else 
        {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
    
    private String KruskalAlgo() 
    {
        // (1) Sort all the edges from low weight to high.
        mergeSortData(edgeList, 0, numEdges-1);
        //printArray(edgeList);
        
        // (2) Take the edge with the lowest wieght and add it to the spanning tree.
        Edge result[] = new Edge[numVertices];
        for (int i = 0; i < numVertices; i++)
            result[i] = new Edge();
        
        subset subsets[] = new subset[numVertices];
        for (int j = 0; j < numVertices; j++)
            subsets[j] = new subset();
        
        for (int v = 0; v < numVertices; v++) 
	{
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        
        int e = 0;
        int i = 0;
        int totalCost = 0;
        
        // (3) Keep adding edges until we reach all vertices
        while (e < numVertices - 1)
        {
            Edge nxtEdge = new Edge();
            nxtEdge = edgeList[i++];
            
            int x = find(subsets, findIndex(nxtEdge.v1));
            int y = find(subsets, findIndex(nxtEdge.v2));
            
            if (x != y) 
            {
                result[e++] = nxtEdge;
                Union(subsets, x, y);
            }
        }
        for (i = 0; i < e; ++i)
        {
            System.out.println(result[i].v1 + "---" + result[i].v2 + "\t$" + result[i].cost);
            totalCost = totalCost + result[i].cost;
        }
        return ("The cost of the railroad is $" + totalCost);
    }
}
