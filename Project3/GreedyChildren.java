/* Jonathan O'Leary
 * Dr. Steinberg
 * COP3503 Fall 2022
 * Programming Assignment 3
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

class GreedyChildren 
{
    private int gArray[];        //The Greedy Array
    private int sArray[];        //The Sweetnes Array
    private int C;               //Children 
    public int hC;               //Happy Children Integer
    public int aC;               //Angry Children Integer
    private int candy;           //# of candy (First Parameter)
    private String gfile;        //String of the Greedy File
    private String sfile;        //String of the Sweet File
    
    GreedyChildren(int Candy, int children, String childrenFile, String candyFile) throws FileNotFoundException 
    {
        /*loads in command line arguments*/
        this.candy = Candy;
        this.C = children; 
        this.gfile = childrenFile; 
        this.sfile = candyFile;
        
        /*loads in information from files*/
        this.gArray = read(gfile, C);
        this.sArray = read(sfile, candy);
    }
    
    public int[] read(String file, int fcount) throws FileNotFoundException
    {
        Scanner sc= new Scanner(new BufferedReader(new FileReader(file)));
        int array[] = new int[fcount];
        int count = 0;

        /*Loop that uses the scanner*/
        while(count<fcount)
        {
            array[count] = sc.nextInt();
            count++;
        }
        return array;
    }

    public void greedyCandy() 
    {
        /*Sorts the Candy and Children */
        mergeSort(gArray, 0, C-1);
        mergeSort(sArray, 0, candy - 1);

        /*Hands out the candy*/
        int i = 0;                 // i keeps track of the gArray
        int j = 0;                 // j keeps track of the sArray
       
        /*traverses the children array*/ 
        while ( i < C)
        {
            /*If the candy is sweet enough the kid gets it*/
            if(gArray[i] <= sArray[j])
            {
                hC++; //Happy child count goes up 
                i++;  //move on to Next child 
                j++;  //Moves on to Next Candy
            }
            /**If the candy is not sweet enough the kid get the worst piece of candy*/
            else
            {
                aC++;    //Angry child count goes up
                i++;     //Move on to next child
                candy--; //Candy Array shrinks, simulating the worst candy being given away
            }
        }
    }

    void display() 
    {
        System.out.println("There are "+ hC +" happy children.");
        System.out.println("There are "+ aC +" angry children.");
    }

    void mergeSort(int[] array, int left, int right)
    {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;
 
            // Sort first and second halves
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
 
            // Merge the sorted halves
            combine(array, left, mid, right);
        }
    }

    void combine(int[] array, int left, int mid, int right)
    {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;
 
        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = array[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = array[mid + 1 + j];
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarray array
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] >= R[j]) {
                array[k] = L[i];
                i++;
            }
            else {
                array[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }
}
