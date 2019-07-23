package edusystem.eduLite;

import java.util.logging.Logger;

import javax.ejb.Stateless;

/**
 * Hello world!
 *
 */
@Stateless
public class App 
{
	Logger logger = Logger.getLogger(App.class.getCanonicalName());
    
	public String echo() {
		return "Hello World!! "+ App.class.getCanonicalName();
	}
}
