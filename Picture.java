import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * This version modified by Scot Drysdale to demonstrate arraylists.
 * (used in reduceTo8).  See end of listing.
 * 
 * Copyright Georgia Institute of Technology 2004-2008
 * @author Barbara Ericson ericson@cc.gatech.edu
 * Modified by Scot Drysdale to eliminate some warnings.
 * 
 * @author Sachin Vadodaria. Last modified on 9/30/14. All methods added by SV are at the bottom of the
 * class in a designated section.
 */
public class Picture extends SimplePicture { 

  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture () {
    
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height) {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////

  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
   /**
   * Class method to let the user pick a file name and then create the picture 
   * and show it
   * @return the picture object
   */
  public static Picture pickAndShow() {
    String fileName = FileChooser.pickAFile();
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * Class method to create a picture object from the passed file name and 
   * then show it
   * @param fileName the name of the file that has a picture in it
   * @return the picture object
   */
  public static Picture showNamed(String fileName) {
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * A method create a copy of the current picture and return it
   * @return the copied picture
   */
  public Picture copy()
  {
    return new Picture(this);
  }
  
  /**
   * Method to increase the red in a picture.
   */
  public void increaseRed() {
    Pixel [] pixelArray = this.getPixels();
    for (Pixel pixelObj : pixelArray) {
      pixelObj.setRed(pixelObj.getRed()*2);
    }
  }
  
  /**
   * Method to negate a picture
   */
  public void negate() {
    Pixel [] pixelArray = this.getPixels();
    int red,green,blue;
    
    for (Pixel pixelObj : pixelArray) {
      red = pixelObj.getRed();
      green = pixelObj.getGreen();
      blue = pixelObj.getBlue();
      pixelObj.setColor(new Color(255-red, 255-green, 255-blue));
    }
  }
  
  /**
   * Method to flip a picture 
   */
  public Picture flip() {
    Pixel currPixel = null;
    Pixel targetPixel = null;
    Picture target = 
      new Picture(this.getWidth(),this.getHeight());
    
    for (int srcX = 0, trgX = getWidth()-1; 
         srcX < getWidth();
         srcX++, trgX--) {
      for (int srcY = 0, trgY = 0; 
           srcY < getHeight();
           srcY++, trgY++) {
        
        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);
        targetPixel = target.getPixel(trgX,trgY);
        
        // copy the color of currPixel into target
        targetPixel.setColor(currPixel.getColor());
      }
    }
    return target;
  }
  
  /**
   * Method to decrease the red by half in the current picture
   */
  public void decreaseRed() {
  
    Pixel pixel = null; // the current pixel
    int redValue = 0;       // the amount of red

    // get the array of pixels for this picture object
    Pixel[] pixels = this.getPixels();

    // start the index at 0
    int index = 0;

    // loop while the index is less than the length of the pixels array
    while (index < pixels.length) {

      // get the current pixel at this index
      pixel = pixels[index];
      // get the red value at the pixel
      redValue = pixel.getRed();
      // set the red value to half what it was
      redValue = (int) (redValue * 0.5);
      // set the red for this pixel to the new value
      pixel.setRed(redValue);
      // increment the index
      index++;
    }
  }
  
  /**
   * Method to decrease the red by an amount
   * @param amount the amount to change the red by
   */
  public void decreaseRed(double amount) {
 
    Pixel[] pixels = this.getPixels();
    Pixel p = null;
    int value = 0;

    // loop through all the pixels
    for (int i = 0; i < pixels.length; i++) {
 
      // get the current pixel
      p = pixels[i];
      // get the value
      value = p.getRed();
      // set the red value the passed amount time what it was
      p.setRed((int) (value * amount));
    }
  }
  
  /**
   * Method to compose (copy) this picture onto a target picture
   * at a given point.
   * @param target the picture onto which we copy this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void compose(Picture target, int targetX, int targetY) {
 
    Pixel currPixel = null;
    Pixel newPixel = null;

    // loop through the columns
    for (int srcX=0, trgX = targetX; srcX < this.getWidth();
         srcX++, trgX++) {
  
      // loop through the rows
      for (int srcY=0, trgY=targetY; srcY < this.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* copy the color of currPixel into target,
         * but only if it'll fit.
         */
        if (trgX < target.getWidth() && trgY < target.getHeight()) {
          newPixel = target.getPixel(trgX,trgY);
          newPixel.setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to scale the picture by a factor, and return the result
   * @param factor the factor to scale by (1.0 stays the same,
   *    0.5 decreases each side by 0.5, 2.0 doubles each side)
   * @return the scaled picture
   */
  public Picture scale(double factor) {
    
    Pixel sourcePixel, targetPixel;
    Picture canvas = new Picture(
                                 (int) (factor*this.getWidth())+1,
                                 (int) (factor*this.getHeight())+1);
    // loop through the columns
    for (double sourceX = 0, targetX=0;
         sourceX < this.getWidth();
         sourceX+=(1/factor), targetX++) {
      
      // loop through the rows
      for (double sourceY=0, targetY=0;
           sourceY < this.getHeight();
           sourceY+=(1/factor), targetY++) {
        
        sourcePixel = this.getPixel((int) sourceX,(int) sourceY);
        targetPixel = canvas.getPixel((int) targetX, (int) targetY);
        targetPixel.setColor(sourcePixel.getColor());
      }
    }
    return canvas;
  }
  
  /**
   * Method to do chromakey using an input color for the background
   * and a point for the upper left corner of where to copy
   * @param target the picture onto which we chromakey this picture
   * @param bgColor the color to make transparent
   * @param threshold within this distance from bgColor, make transparent
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void chromakey(Picture target, Color bgColor, int threshold,
                        int targetX, int targetY) {
 
    Pixel currPixel = null;
    // loop through the columns
    for (int srcX=0, trgX=targetX;
        srcX<getWidth() && trgX<target.getWidth();
        srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
        srcY<getHeight() && trgY<target.getHeight();
        srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel is within threshold of
         * the input color, then don't copy the pixel
         */
        if (currPixel.colorDistance(bgColor)>threshold) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
    /**
   * Method to do chromakey assuming a blue background 
   * @param target the picture onto which we chromakey this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void blueScreen(Picture target,
                        int targetX, int targetY) {

    Pixel currPixel = null;
    // loop through the columns
    for (int srcX=0, trgX=targetX;
         srcX<getWidth() && trgX<target.getWidth();
         srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
           srcY<getHeight() && trgY<target.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel mostly blue (blue value is
         * greater than red and green combined), then don't copy pixel
         */
        if (currPixel.getRed() + currPixel.getGreen() > currPixel.getBlue()) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to change the picture to gray scale with luminance
   */
  public void grayscaleWithLuminance()
  {
    Pixel[] pixelArray = this.getPixels();
    Pixel pixel = null;
    int luminance = 0;
    double redValue = 0;
    double greenValue = 0;
    double blueValue = 0;

    // loop through all the pixels
    for (int i = 0; i < pixelArray.length; i++)
    {
      // get the current pixel
      pixel = pixelArray[i];

      // get the corrected red, green, and blue values
      redValue = pixel.getRed() * 0.299;
      greenValue = pixel.getGreen() * 0.587;
      blueValue = pixel.getBlue() * 0.114;

      // compute the intensity of the pixel (average value)
      luminance = (int) (redValue + greenValue + blueValue);

      // set the pixel color to the new color
      pixel.setColor(new Color(luminance,luminance,luminance));

    }
  }
  
  /** 
   * Method to do an oil paint effect on a picture
   * @param dist the distance from the current pixel 
   * to use in the range
   * @return the new picture
   */
  public Picture oilPaint(int dist) {
    
    // create the picture to return
    Picture retPict = new Picture(this.getWidth(),this.getHeight());
    
    // declare pixels
    Pixel currPixel = null;
    Pixel retPixel = null;
    
    // loop through the pixels
    for (int x = 0; x < this.getWidth(); x++) {
      for (int y = 0; y < this.getHeight(); y++) {
        currPixel = this.getPixel(x,y);
        retPixel = retPict.getPixel(x,y);
        retPixel.setColor(currPixel.getMostCommonColorInRange(dist));
      }
    }
    return retPict;
  }
  
  /***********
   * Methods added by Scot Drysdale to demonstrate ArrayLists
   **********/
 
 /**
  * Reduces the number of colors to 8 by picking two values for red,
  * two for green, and two for blue.  The two red values chosen are the
  * average of the pixel red value that are greater than a threshold
  * and the average of the pixel red values less than or equal to the threshold.
  * The same is done for green and blue
  */
 public void reduceTo8() {
   Pixel [] pixelArray = this.getPixels();  // Array of all pixels in the image
   final int THRESHOLD = 126;     // Dividing line between low and high color values
   
   for(int colorNum = 1; colorNum <= 3; colorNum++) {
     ArrayList<Pixel> lowValues = new ArrayList<Pixel>();
     ArrayList<Pixel> highValues = new ArrayList<Pixel>();
     
     for(Pixel pixel : pixelArray) {
       // Split the pixels into low and high color values for color colorNum
       if(getColor(pixel, colorNum) <= THRESHOLD)
         lowValues.add(pixel);
       else
         highValues.add(pixel);
     } 
       
     int lowAve = Math.round(averageColors(lowValues, colorNum));
     int highAve = Math.round(averageColors(highValues, colorNum));
       
     // Reset the color values to the average values
     for (Pixel lowPix : lowValues)
       setColor(lowPix, lowAve, colorNum);
     for (Pixel highPix : highValues)
       setColor(highPix, highAve, colorNum);                                 
   }
 }
 
 /**
  * Gets the value of the color corresponding to colorNum.
  * In an ideal world this would be added to the Pixel class
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixel the pixel whose color is returned
  * @param colorNum the color to choose: 1 = red, 2 = green, 3 = blue
  */
 public static int getColor(Pixel pixel, int colorNum) {
   if(colorNum == 1)
     return pixel.getRed();
   else if (colorNum == 2)
     return pixel.getGreen();
   else
     return pixel.getBlue();
 }
 
   /**
  * Sets the value of the color corresponding to colorNum to newValue.
  * In an ideal world this would be added to the Pixel class
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixel the pixel whose color is set
  * @param newValue the new value for the color
  * @param colorNum the color to choose: 1 = red, 2 = green, 3 = blue
  */
 public static void setColor(Pixel pixel, int newValue, int colorNum) {
   if(colorNum == 1)
     pixel.setRed(newValue);
   else if (colorNum == 2)
     pixel.setGreen(newValue);
   else
     pixel.setBlue(newValue);
 }
 
 /**
  * Averages the chosen color for all the pixels in an ArrayList.
  * Returns 0 if ArrayList is empty.
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixels the list of pixels to be averaged
  * @param colorNum the color to average: 1 = red, 2 = green, 3 = blue
  * @return the average of the chosen color value
  */
 public static float averageColors(ArrayList<Pixel> pixels, int colorNum) {
   float sum = 0.0f;
   
   for(int i = 0; i < pixels.size(); i++)
     sum += getColor(pixels.get(i), colorNum);
   
   if(pixels.size() > 0)
     return sum/pixels.size();
   else
     return 0.0f;
 }
  
 /* 
  * End of additional methods added by Scot Drysdale.
  */
 
 /**
  * Methods added by Sachin Vadodaria
  */
 
 /**
  * Method that transforms an image by replacing each pixel color by a weighted sum of its color and the colors of its eight adjacent pixels. 
  * @param matrix: matrix of weights for the new color
  * @param title: the title of the new picture
  * @return the picture with its colors replaced
  */
 public Picture convolve(double [][] matrix, String title){
	  Picture convolution = new Picture(this);
	  for(int i = 1; i < convolution.getHeight() - 1; i++){
		  for(int j = 1; j < convolution.getWidth() - 1; j++){
			  
			  double topLeftRed = ( (super.getPixel(j-1,i-1).getRed()) * (matrix[0][0]) );
			  double topLeftGreen = ( (super.getPixel(j-1,i-1).getGreen()) * (matrix[0][0]) );
			  double topLeftBlue = ( (super.getPixel(j-1,i-1).getBlue()) * (matrix[0][0]) );
			  
			  double topMiddleRed = ( (super.getPixel(j,i-1).getRed()) * (matrix[0][1]) );
			  double topMiddleGreen = ( (super.getPixel(j,i-1).getGreen()) * (matrix[0][1]) );
			  double topMiddleBlue = ( (super.getPixel(j,i-1).getBlue()) * (matrix[0][1]) );
			  
			  double topRightRed = ( (super.getPixel(j+1,i-1).getRed()) * (matrix[0][2]) );
			  double topRightGreen = ( (super.getPixel(j+1,i-1).getGreen()) * (matrix[0][2]) );
			  double topRightBlue = ( (super.getPixel(j+1,i-1).getBlue()) * (matrix[0][2]) );
			  
			  double leftRed = ( (super.getPixel(j-1,i).getRed()) * (matrix[1][0]) );
			  double leftGreen = ( (super.getPixel(j-1,i).getGreen()) * (matrix[1][0]) );
			  double leftBlue = ( (super.getPixel(j-1,i).getBlue()) * (matrix[1][0]) );
			  
			  double middleRed = ( (super.getPixel(j,i).getRed()) * (matrix[1][1]) );
			  double middleGreen = ( (super.getPixel(j,i).getGreen()) * (matrix[1][1]) );
			  double middleBlue = ( (super.getPixel(j,i).getBlue()) * (matrix[1][1]) );
			  
			  double rightRed = ( (super.getPixel(j+1,i).getRed()) * (matrix[1][2]) );
			  double rightGreen = ( (super.getPixel(j+1,i).getGreen()) * (matrix[1][2]) );
			  double rightBlue = ( (super.getPixel(j+1,i).getBlue()) * (matrix[1][2]) );
			  
			  double bottomLeftRed = ( (super.getPixel(j-1,i+1).getRed()) * (matrix[2][0]) );
			  double bottomLeftGreen = ( (super.getPixel(j-1,i+1).getGreen()) * (matrix[2][0]) );
			  double bottomLeftBlue = ( (super.getPixel(j-1,i+1).getBlue()) * (matrix[2][0]) );
			  
			  double bottomMiddleRed = ( (super.getPixel(j,i+1).getRed()) * (matrix[2][1]) );
			  double bottomMiddleGreen = ( (super.getPixel(j,i+1).getGreen()) * (matrix[2][1]) );
			  double bottomMiddleBlue = ( (super.getPixel(j,i+1).getBlue()) * (matrix[2][1]) );
			  
			  double bottomRightRed = ( (super.getPixel(j+1,i+1).getRed()) * (matrix[2][2]) );
			  double bottomRightGreen = ( (super.getPixel(j+1,i+1).getGreen()) * (matrix[2][2]) );
			  double bottomRightBlue = ( (super.getPixel(j+1,i+1).getBlue()) * (matrix[2][2]) );
			  
			  int newRed = (int) (topLeftRed + topMiddleRed + topRightRed + leftRed + middleRed + rightRed + bottomLeftRed + bottomMiddleRed + bottomRightRed);
			  int newGreen = (int) (topLeftGreen + topMiddleGreen + topRightGreen + leftGreen + middleGreen + rightGreen + bottomLeftGreen + bottomMiddleGreen + bottomRightGreen);
			  int newBlue = (int) (topLeftBlue + topMiddleBlue + topRightBlue + leftBlue + middleBlue + rightBlue + bottomLeftBlue + bottomMiddleBlue + bottomRightBlue);
			  
			  convolution.getPixel(j, i).setRed(Pixel.correctValue(newRed));
			  convolution.getPixel(j, i).setGreen(Pixel.correctValue(newGreen));
			  convolution.getPixel(j, i).setBlue(Pixel.correctValue(newBlue));
		  }
	  }
	  convolution.setTitle(title);
	  return convolution;
 }
 
 /**
  * Method to modify an image to use only colors from a given list.
  * @param colors: the list of colors used to replace the image's original colors
  * @return the picture with its colors replaced.
  */
 public Picture mapToColorList(ArrayList<Color> colors){
	  Picture colorMappedPicture = new Picture(this);
	  for(int i = 0; i < colorMappedPicture.getHeight(); i++){
		  for(int j = 0; j < colorMappedPicture.getWidth(); j++){
			  
			  Pixel currentPixel = colorMappedPicture.getPixel(j,i);
			  int chosen = findClosestColor(currentPixel, colors);
			  currentPixel.setColor(colors.get(chosen));
		  }
	  }
	  return colorMappedPicture;
 }
 
 /**
  * Method to find the color in a list that is closest to the color of a given pixel.
  * @param p: the given Pixel whose color is being evaluated to find the closest match
  * @param colors: the list of colors available to match the original color
  * @return: the index in the list of colors that is closest to the pixel color.
  */
 public int findClosestColor(Pixel p, ArrayList<Color> colors){
	  int chosen = 0;
	  int distance = colorDistance(p, colors.get(0));
	  
	  for(int k = 1; k < colors.size(); k++){
		  int testDistance = colorDistance(p,colors.get(k));
		  if(testDistance < distance){
			  distance = testDistance;
			  chosen = k;
		  }
	  }
	  return chosen;
 }
 
 /**
  * Method that returns a random integer from 0 to n-1
  * @param the number given to the Math.random method used to initiate the randomiser
  * @return a random integer from 0 to n-1
  */
 public static int random(int n){
     return (int)(Math.random() * n);
 }
 
 /**
  * Method to generate a list of random colors
  * @param listSize: the size of the list
  * @return: an ArrayList of random Colors
  */
 public ArrayList<Color> generateRandomColorList(int listSize){
	  ArrayList<Color> colors = new ArrayList<Color>();
	  
	  for(int i = 0; i < listSize; i++){
		  boolean uniqueFound = false;
		  while(!uniqueFound){
			  int red = random(255);
			  int green = random(255);
			  int blue = random(255);
			  Color randomColor = new Color(red,green,blue);
		  
			  if(!colors.contains(randomColor)){
				  colors.add(randomColor);
				  uniqueFound = true;
			  }
		  }
	  }
	  return colors;
 }
 
 /**
  * Method that takes the first k unique colors from the list of pixels in the image
  * @param number: the number of unique colors desired
  * @return: the list of the unique colors
  */
 public ArrayList<Color> firstPixelColors(int number){
	 ArrayList<Color> colors = new ArrayList<Color>();
	 for(int j = 0; j < number; j++){
		 
		 //boolean uniqueFound = false;
		 //while(!uniqueFound){
			 
			 Color currentColor = getPixel(j,0).getColor();
			 if(!colors.contains(currentColor)){
				 colors.add(currentColor);
				 //uniqueFound = true;
			 }
			 else{
				 number++;
			 }
	 }
	 return colors;
 }
 
 /**
  * Method to get the distance between this pixel's color and the given color
  * @param p: the pixel whose color is being compared
  * @param c2: the color being compared with the pixel's color
  * @return the relative distance between the pixel's color and the given color
  */
 public int colorDistance(Pixel p, Color c2){
	 int redDistance = p.getRed() - c2.getRed();
	 int greenDistance = p.getGreen() - c2.getGreen();
	 int blueDistance = p.getBlue() - c2.getBlue();
	 int distance = (redDistance * redDistance + 
             greenDistance * greenDistance +
             blueDistance * blueDistance);
	 return distance;
 }
 
 public void assignClusters(ArrayList<Color> colors, ArrayList<ArrayList<Color>> clusters){
	 for(int i = 0; i < getHeight(); i++){
		  for(int j = 0; j < getWidth(); j++){
			  Pixel currentPixel = getPixel(j,i);
			  int chosenClusterIndex = findClosestColor(currentPixel, colors);
			  clusters.get(chosenClusterIndex).add(currentPixel.getColor());
		  }
	 }
 }
 
 /**
  * Method to compute the centroid of a cluster of colors
  * @param cluster: the cluster of colors to be used
  * @return: the centroid color (color with rgb values that are the avg of all the rgbs of the colors in the cluster)
  */
 public Color computeCentroid(ArrayList<Color> cluster){
	 int redTotal = 0;
	 int greenTotal = 0;
	 int blueTotal = 0;
	 
	 int clusterSize = cluster.size();
	 for(int j = 0; j < clusterSize; j++){
		 Color c = cluster.get(j);
		 redTotal += c.getRed();
		 greenTotal += c.getGreen();
		 blueTotal += c.getBlue();
	 }
	 int avgRed = redTotal / clusterSize;
	 int avgGreen = greenTotal / clusterSize;
	 int avgBlue = blueTotal / clusterSize;
	 return new Color(avgRed, avgGreen, avgBlue);
 }
 
 /**
  * Method that returns a list of that k colors computed using k-means.
  * @param number: the desired number of color clusters
  * @return: list with (ideally) k colors that best represent a wider range of colors
  */
 public ArrayList<Color> computeColors(int number){
	 ArrayList<Color> finalColors = generateRandomColorList(number);
	 //ArrayList<Color> finalColors = firstPixelColors(number);
	 
	 boolean finalListFound = false;
	 while(!finalListFound){
		 ArrayList<ArrayList<Color>> clusters = new ArrayList<ArrayList<Color>>();
		 for(int i = 0; i < number; i++){
			  clusters.add(new ArrayList<Color>());
		 }
		 
		 assignClusters(finalColors, clusters);
		 
		 ArrayList<Color> newColors = new ArrayList<Color>();
		 for(int i = 0; i < number; i++){
			 ArrayList<Color> currentCluster = clusters.get(i);
			 if(currentCluster.size() > 0){
				 newColors.add(computeCentroid(currentCluster));
			 }
		 }
		 if(newColors.equals(finalColors)){
			 finalListFound = true;
		 }
		 else{
			 finalColors = new ArrayList<Color>();
			 for(Color c : newColors){
				 if(c != null){
					 finalColors.add(c);
				 }
			 }
		 }
	 }
	 return finalColors;
 }
 
 /**
  * Method to "posterize" an image into one with a given number of colors that best represent the original range of colors
  * @param number: number of colors used to "posterize" the image
  * @return: the "posterized" image
  */
 public Picture reduceColors(int number){
	 return mapToColorList(computeColors(number));
 }
 
  public static void main(String[] args)
  {
	  
	  ArrayList<Color> colors = new ArrayList<Color>();	  
	  colors.add(Color.red);
	  colors.add(Color.green);
	  colors.add(Color.blue);
	  colors.add(Color.cyan);
	  colors.add(Color.orange);
	  colors.add(Color.yellow);
	  colors.add(Color.black);
	  colors.add(Color.white);
	  
	  /*colors.add(new Color(5,5,5));
	  colors.add(new Color(160,200,230));
	  colors.add(new Color(120,120,85));
	  colors.add(new Color(135,160,150));
	  colors.add(new Color(20,20,5));
	  colors.add(new Color(230,210,165));
	  colors.add(new Color(80,80,50));
	  colors.add(new Color(40,40,60));
	  */
	  
	  Picture p = new Picture(FileChooser.pickAFile());
	  p.explore();
	  //Picture r = p.reduceColors(256);
	  Picture r = p.mapToColorList(colors
			//p.generateRandomColorList(256) 
			);
	  r.explore();
	/*
    Picture p = 
      new Picture(FileChooser.pickAFile());
    p.explore();
    
    double [][] edges = {{-1.0, -1.0, -1.0},
            {-1.0, 8.0, -1.0},
            {-1.0, -1.0, -1.0}};
    
    double ninth = 1.0/9.0;
    double [][] blur = {{ninth, ninth, ninth}, 
                       {ninth, ninth, ninth}, 
                       {ninth, ninth, ninth}};
    
    double [][] sharpen = {{-1.0, -1.0, -1.0},
            {-1.0, 9.0, -1.0},
            {-1.0, -1.0, -1.0}};
    
    Picture r = p.convolve(edges, "edges");
    r.explore();
    
    Picture s = p.convolve(blur, "blur");
    s.explore();
    
    Picture t = p.convolve(sharpen, "sharpen");
    t.explore();
    //Picture q = p.oilPaint(3);
    //q.setTitle("Oil Paint");
    //q.explore();
     */
  }
        
} // this } is the end of class Picture, put all new methods before this
 