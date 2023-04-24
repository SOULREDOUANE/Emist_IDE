package application;
import java.io.*;


public class ErrorDetector {
    
    public static void main(String[] args) {
      
        String text= " red slfjsf  sfsf ";
        String name = " Red.java";
        System.out.println( classNameError(text,name));
       
    }

    public  static int  classNameError(String text,String namefromTree) {
  
        String [] twoWords=namefromTree.split("\\.");
        if (!twoWords[1].equals("java")) {
            return 1;
        }
        String[] words = text.split("\\s+");
        for (int i=0;i<words.length; i++) {
            if (words[i].equals("public")) {
                
               return 0;
            }
        }
        return 2;

    }
    public static boolean checkBrackets(File file , char openBracket , char closeBracket) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int openCount = 0;
            int closeCount = 0;
            while ((line = br.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    if (c == openBracket) {
                        openCount++;
                    } else if (c == closeBracket) {
                        closeCount++;
                    }
                    if (closeCount > openCount) {
                        return false;
                    }
                }
            }
            return openCount == closeCount;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }


    // public int 
}
