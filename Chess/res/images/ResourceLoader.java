package images;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * Helper class to load recourses
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class ResourceLoader {
	
	
	/**
	 * Gets the image from a file
	 * 
	 * @param fileName	The file that is an image
	 * @return			An image
	 */
	public static Image getImage(String fileName) {
		return Toolkit.getDefaultToolkit().getImage(new ResourceLoader().getClass().getResource(fileName));
	}

}
