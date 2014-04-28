//	Util
//
//	Fonctions utilitaires diverses


public class Util {
	
	int angle_mode = 1;	//	0 : entre 0 et 2*PI
							//	1 : entre -PI et PI
	
	//	Renvoyer une valeur normalisé d'un angle
	//	soit entre 0 et 2*PI, soit entre -PI et +PI
	double AModulo( double angle ) {
		double m = angle;
		
		m = m % (2*Math.PI);
		if (angle_mode == 0) {            
            if (m < 0) m += (2*Math.PI);
		} else
		if (angle_mode == 1) {
			if (m > Math.PI) m -= (2*Math.PI);
			if (m < -Math.PI) m += (2*Math.PI);
		}
		return m;
	}
	
	//	Calculer l'écart de 2 angles 
	//	(a2 par rapport à a1)
	double DiffAngles( double a1, double a2 ) {
		return AModulo(a2-a1);
	}		
}
