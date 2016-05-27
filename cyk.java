
import java.io.IOException;

/**
 * 
 * @author Alvaro Ferreira
 * @author Deniscley Marfran
 * @author Enio Lemos
 * @version 1.0
 */
public class cyk {
	public static void main(String[] args) throws IOException{
		AlgoritmoCYK algoritmo = new AlgoritmoCYK(args[0],args[1],args[2]);
		algoritmo.funcoes();
	}
} 