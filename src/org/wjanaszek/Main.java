package org.wjanaszek;

import org.wjanaszek.controller.*;
import org.wjanaszek.model.*;
import org.wjanaszek.view.*;

/**
 * Punkt wej�ciowy aplikacji.
 * @author Wojciech Janaszek
 */
public class Main {
	public static void main(String args[])	{
		Model model = new Model();
		View view = new View(model);
		Controller controller = new Controller(model, view);
	}
}
