package images;

import java.awt.Image;
import java.awt.Toolkit;

public class ResourceLoader {
	
	public static Image getImage(String fileName) {
		return Toolkit.getDefaultToolkit().getImage(new ResourceLoader().getClass().getResource(fileName));
	}

}
