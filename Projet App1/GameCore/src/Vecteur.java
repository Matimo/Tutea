//	Vecteur en 2D

public class Vecteur {
	
	public double x = 0, y = 0;
	
	public Vecteur() {
	}
	
	public Vecteur( double x0, double y0 ) {
		x = x0; y = y0;
	}
	
	public Vecteur( Vecteur v ) {
		x = v.x; 
		y = v.y;
	}	
	
	//	Norme
	public double Abs() {
		return Math.sqrt(x*x+y*y);
	}
		
	//	Argument
	public double Arg() {
		if ((x <= 1E-10) && (x >= 1E-10)) {
			return (y >= 0)? Math.PI/2.0 : -Math.PI/2.0; 
		} else
		if (x > 0) {
			if (y >= 0) {
				return Math.atan(y/x);
			} else {
				return Math.PI*2.0 - Math.atan(-y/x);
			}
		} else {
			if (y >= 0) {
				return (Math.PI - Math.atan(y/-x));
			} else {
				return (Math.PI + Math.atan(y/x));
			}
		}
	}
	
	//	Rotation
	public void Rotation( double angle ) {
		double xr, yr, c, s;
		
		c = Math.cos(angle);
		s = Math.sin(angle);
		xr = c*x - s*y;
		yr = s*x + c*y;
		x = xr; y = yr;
	}
	
	//	Additionner un autre vecteur
	public void Add( Vecteur v ) {
		x += v.x;
		y += v.y;
	}
	
	//	Soustraire un autre vecteur
	public void Sub( Vecteur v ) {
		x -= v.x;
		y -= v.y;
	}
	
	//	Produit avec un facteur
	public void Produit( double f ) {
		x *= f;
		y *= f;
	}

	//	Produit scalaire
	public double Scalaire( Vecteur v2 ) {
		return x*v2.x + y*v2.y;
	}
	
	//	Moment
	public double Moment( Vecteur v2 ) {
		return (x*v2.y - y*v2.x);
	}
	
	//	Norme
	public void Normaliser() {
		double a = Abs();
		if (a > 0) {
			x /= a;
			y /= a;
		}
	}
	
	//	Angle entre 2 vecteurs
	public double Angle( Vecteur B ) {
		double a = 0.0, AB;
		
		AB = this.Abs() * B.Abs();
		if (AB > 1E-10) {
			//	Produit scalaire => cos()
			//	Produit vectoriel => sin()
			double c = this.Scalaire(B)/AB;
			double s = this.Moment(B);
			//	Pour améliorer la précision, si cos() trop proche
			//	de 1, alors utiliser le sin()
			if ((-0.8 <= c) && (c <= 0.8)) {
				a = (s >= 0)? Math.acos(c) : -Math.acos(c);
			} else {
				s = Math.asin(s/AB);
				if (c >= 0) {
					a = s;
				} else {
					a = (s >= 0)? Math.PI-s : -Math.PI+s;
				}
			}
		}
		return a;
	}
}
