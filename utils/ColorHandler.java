/**
 * 
 */
package utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;

import javax.imageio.ImageIO;

/**
 * This class is used to manipulate a particular color in an image file.
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class ColorHandler{
	public static String baseDir = "C:\\Users\\manish\\Downloads\\";
	public static void main(String[] args) throws Throwable {
		System.setOut(new PrintStream(new File(baseDir, "a.txt")));
		File e = new File( baseDir, "a.png" );
	      BufferedImage bi= ImageIO.read(e);
	      int w = bi.getWidth();
	      int h = bi.getHeight();
	      for (int x=0; x < w; x++)
	        {
	    for (int y = 0; y < h; y++)
	        {
	        
	        int pixelCol=bi.getRGB(x,y);
	        //mask out the non green,non-alpha color.
	        //A is 0xFF000000
	        //R is 0x00FF0000
	        //G is 0x0000FF00
	        //B is 0x000000FF
	        System.out.println(pixelCol);
	        if(pixelCol==-52480)
	        {
//	        	if(prevCol==-1)
//	        		pixelCol= 0xFFFFFFFF;
//	        	else
	        		pixelCol= 0xFF000000;
	        }
//	        pixelCol &= 0xFF000000;
	        bi.setRGB(x, y, pixelCol);
	        }
	  }
	//Now save the image back
	ImageIO.write(bi, "png" , new File(baseDir,"a2.png")); 
	}
}

