/*  Jonathan O'Leary
 *  COP3503 Computer Science 2
 *  Programming Assignment 2 Driver
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
package dd.railroaddriver;

import java.io.*;
import java.util.*;

class Railroad 
{
    class Edge implements Comparable<Edge>
    {
        String source;
        String destination;
        int weight;
        
        public int compareTo(Edge compareEdge)
        {
            
        }
    }
    //The filename, Size of table, and table of data
    private String fileName;
    private String [][] dataTable;
    private int dataTableLength;
    
    Railroad(int numTracks, String railroad1txt) throws FileNotFoundException 
    {
        // Set Filename to String provided
        this.fileName = railroad1txt;
        
        // Set table length to # of tracks - 1
        this.dataTableLength = numTracks;
        
        // Loads and Sorts Data
        this.dataTable = new String[numTracks][3];
        loadData();
        
    }

    private void loadData() throws FileNotFoundException 
    {
        /*Trusty old Scanner*/
        Scanner sc= new Scanner(new BufferedReader(new FileReader(fileName)));
        int tableCounter = 0; 
        
        while (sc.hasNextLine())
        {
            // If it cannot find a character it breaks
            if (sc.hasNext() != true)
            {
                break;
            }
            
            // Loads in the Data for the Table
            dataTable[tableCounter][0] =  sc.next();
            dataTable[tableCounter][1] =  sc.next();
            dataTable[tableCounter][2] =  sc.next();
            
            //Table Counter Increases
            tableCounter++;
        }
    }
    
    private void mergeSortData(String [][] dTable, int left, int right) 
    {
        if (left < right)
        {
            // Find the middle point
            int mid = left + (right - left) / 2;
            
            // Sort the 2 halves
            mergeSortData(dTable, left, mid);
            mergeSortData(dTable, mid+1, right);
            
            //Merge the Halves
            combine(dTable, left, mid, right);
        }
    }

    private void combine(String[][] dTable, int left, int mid, int right) 
    {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        /* Create temp String arrays */
        String L[][] = new String[n1][3];
        String R[][] = new String[n2][3];
        
        /* Copy Data to temp array*/
        for (int i = 0; i < n1; i++)
        {
            L[i][0] = dTable[left + i][0];
            L[i][1] = dTable[left + i][1];
            L[i][2] = dTable[left + i][2];
        }
        for (int j = 0; j < n2 ; j++)
        {
            R[j][0] = dTable[mid + 1 + j][0];
            R[j][1] = dTable[mid + 1 + j][1];
            R[j][2] = dTable[mid + 1 + j][2];
        }
        
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
        int leftWeight , rightWeight;
        int k = left;
        
        while (i < n1 && j < n2) 
        {  
            leftWeight = Integer.parseInt(L[i][2]);
            rightWeight = Integer.parseInt(R[j][2]);
            
            if (leftWeight <= rightWeight)
            {
                dTable[k][0] = L[i][0];
                dTable[k][1] = L[i][1];
                dTable[k][2] = L[i][2];
                i++;
            }
            else
            {
                dTable[k][0] = R[j][0];
                dTable[k][1] = R[j][1];
                dTable[k][2] = R[j][2];
                j++;
            }
            k++;
        }
        
        /* Copy remaining elements of L[] if any */
        while (i < n1) 
        {
           dTable[k][0] = L[i][0];
           dTable[k][1] = L[i][1];
           dTable[k][2] = L[i][2];
           i++;
           k++;
        }
        
         /* Copy remaining elements of R[] if any */
        while (j < n2) {
            dTable[k][0] = R[j][0];
            dTable[k][1] = R[j][1];
            dTable[k][2] = R[j][2];
            j++;
            k++;
        }
    }
    
    private void printArray(String[][] table)
    {
        // Goes through and prints each row in the table
        for (int i = 0; i < dataTableLength; i++)
        {
            System.out.println(table[i][0] + " + " + table[i][1] + " :: " + table[i][2]);
        }
    }
    
    String buildRailroad() 
    {
        // Sets the Return string to NULL (Probably redundant)
        String returnString = "NULL";
        
        // (1) Sort all the edges from low weight to high.
        mergeSortData(dataTable, 0, dataTableLength - 1);
        
        
        printArray(dataTable);
        
        return returnString;
    }
}
