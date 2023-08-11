/*  Jonathan O'Leary
 *  COP3503 Computer Science 2
 *  Programming Assignment 4
 *  Fall 2022
 */

public class LCS 
{
    public String Z = "NUL";
    private String X;
    private String Y;
    private int Xlength;
    private int Ylength;
    private int Zlength;
    
    public LCS(String String1, String String2) 
    {
        this.Y = String1;
        this.X = String2;
        this.Xlength = X.length();
        this.Ylength = Y.length();
    }
    
    public String getLCS() 
    {
        return Z;
    }

    public void lcsDynamicSol() 
    {
        //System.out.println("\n----Loading LCS Dynamic Solution----");
        
        // create a matrix which act as a table for LCS  
        int[][] tableForLCS = new int[Xlength + 1][Ylength + 1]; 
        
        // fill the table in the bottom up way  
        for (int i = 0; i <= Xlength; i++) 
        { 
            for (int j = 0; j <= Ylength; j++) 
            {
                // Fill each cell corresponding to first row and first column with 0 
                if (i == 0 || j == 0)
                {
                    tableForLCS[i][j] = 0;
                }
                
                // add 1 in the cell of the previous row and column and fill the current cell with it   
                else if (X.charAt(i - 1) == Y.charAt(j - 1))
                {
                    tableForLCS[i][j] = tableForLCS[i - 1][j - 1] + 1;
                }
                
                ////find the maximum value from the cell of the previous row and current column and the cell of the current row and previous column    
                else
                {
                    tableForLCS[i][j] = Math.max(tableForLCS[i - 1][j], tableForLCS[i][j - 1]);
                }
            }
        }
        
        int index = tableForLCS[Xlength][Ylength]; 
        int temp = index;
        
        char[] longestCommonSubsequence = new char[index + 1];  
        longestCommonSubsequence[index] = '\0';  
        
        int i = Xlength, j = Ylength;
        String lcs = "";
        
        while (i > 0 && j > 0)
        {
            if (X.charAt(i - 1) ==Y.charAt(j - 1))
            {
                longestCommonSubsequence[index - 1] = X.charAt(i - 1);  
                i--;  
                j--;  
                index--; 
            }
            
            else if (tableForLCS[i - 1][j] > tableForLCS[i][j - 1])
            {
                i--;
            }
            else
            {
                j--;
            }
        }
        
        for (int k = 0; k < temp; k++)  
        {
            //System.out.println(lcs + longestCommonSubsequence[k]);
            lcs = lcs + longestCommonSubsequence[k];  
        }
        
        Z =lcs; 
        
        //System.out.println("LCS = " + Z);
        //System.out.println("----END----");
    }    
}
