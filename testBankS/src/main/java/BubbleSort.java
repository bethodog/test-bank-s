package main.java;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BubbleSort {
	
public static void main(String[] args) throws IOException {
    	
    	List<Integer> ar = Arrays.asList(10, 8, 11, 20, 4, 5 , 3, 10);
     
    	int pares = sockMerchant(8, ar);
        
        System.out.println(ar + " pares: " + pares);
    }
    
    
    /*
     * Complete the 'sockMerchant' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY ar
     */
    private static int sockMerchant(int n, List<Integer> ar) {
    	
    	int pair = 0;
    	
    	Collections.sort(ar);
    	
    	System.out.println(ar);
    	
    	for(int i = 0; i < (n - 1); i++){
    		
    		System.out.println(ar.get(i) + " - " + ar.get(i + 1));
    		
    		if(ar.get(i) == ar.get(i + 1)) {
    			pair++;
    			i++;
    		}
    		
    	}
    	
        return pair;
    }

}
