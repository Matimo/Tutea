import java.awt.Color;


public class Matrice {
	
	int nlines, ncols;
	double v[];
	
	public Matrice() {
		nlines = ncols = 0;
		v = null;
	}
	
	public Matrice( int l, int c ) {
		//	Matrice rectangulaire
		nlines = l;
		ncols = c;
		v = new double[nlines*ncols];
		Identite();
	}
	
	public Matrice( int c ) {
		//	Matrice carrée
		nlines = ncols = c;
		v = new double[nlines*ncols];
		Identite();
	}
	
	public void Identite() {
		//	Matrice identite
		for (int i=0; i<nlines; i++) {
			for (int j=0; j<ncols; j++) {
				v[i*ncols+j] = 0.0;
			}
			v[i*ncols+i] = 1.0;
		}		
	}
	
	public double Val( int l, int c ) {
		return v[l*ncols+c];
	}
	
	public void Set( int l, int c, double value ) {
		//	Mettre une valeur dans une case
		v[l*ncols+c] = value;
	}
	
	public Matrice Dup() {
		//	Dupliquer la matrice
		Matrice m = new Matrice(nlines,ncols);
		for (int l=0; l<nlines; l++) {
			for (int c=0; c<ncols; c++) {
				m.Set(l, c, Val(l,c));
			}
		}
		return m;
	}
	
	public void Facteur( double a ) {
		//	Multiplier toute la matrice par un facteur
		for (int i=0; i<nlines; i++) {
			MulLine(i,a);
		}		
	}
	
	public void MulLine( int line, double a ) {
		//	Multiplier une ligne par un facteur
		for (int i=0; i<ncols; i++) {
			v[line*ncols+i] *= a;
		}
	}
	
	public void AddLine( int src, int dst ) {
		//	Ajouter la ligne dst à la ligne src
		for (int i=0; i<ncols; i++) {
			v[dst*ncols+i] += v[src*ncols+i];
		}
	}
	
	public void Perm( int l1, int l2 ) {
		//	Permuter 2 lignes
		for (int c=0; c<ncols; c++) {
			double tmp = v[l1*ncols+c];
			v[l1*ncols+c] = v[l2*ncols+c];
			v[l2*ncols+c] = tmp;
		}
	}	
	
	private double Abs( double a ) {
		return (a >= 0)? a : -a;
	}
	
	public boolean Triangulariser() {
		//	Triangulariser une matrice
		final double epsilon = 1E-9;
		
		for (int l=0; l<nlines; l++) {
			if (Abs(Val(l,l)) <= epsilon) {
				//	S'il y a un problème de pivot ...
				//	alors rechercher une ligne avec un pivot
				//	non nul et permuter
				int l2;
				for (l2=l+1; l2<nlines; l2++) {
					if (Abs(Val(l2,l2)) >= epsilon) {
						Perm(l2,l);
						break;
					}
				}
				if (l2 >= nlines) {
					return false;
				}
			}
			
			//	Annuler le coefficient des lignes inférieures
			for (int l2=l+1; l2<nlines; l2++) {
				if (Abs(Val(l2,l)) >= epsilon) {
					MulLine(l2,-Val(l,l)/Val(l2,l));
					AddLine(l,l2);
				}
			}
		}
		return true;
	}
	
	public boolean TriangulariserSup() {
		//	Triangulariser la partie supérieure de la matrice
		final double epsilon = 1E-9;
		
		for (int l=nlines-1; l>=0; l--) {
			if (Abs(Val(l,l)) <= epsilon) {
				//	S'il y a un problème de pivot ...
				//	alors rechercher une ligne avec un pivot
				//	non nul et permuter
				int l2;
				for (l2=l-1; l2>=0; l2--) {
					if (Abs(Val(l2,l2)) >= epsilon) {
						Perm(l2,l);
						break;
					}
				}
				if (l2 >= nlines) {
					return false;
				}
			}
			
			//	Annuler le coefficient des lignes inférieures
			for (int l2=l-1; l2>=0; l2--) {
				if (Abs(Val(l2,l)) >= epsilon) {
					MulLine(l2,-Val(l,l)/Val(l2,l));
					AddLine(l,l2);
				}
			}
		}
		return true;
	}
	
	public boolean Resoudre() {
		//	Résoudre un système linéaire par le pivot de Gauss
		if (!Triangulariser()) return false;
		if (!TriangulariserSup()) return false;
		for (int l=0; l<nlines; l++) {
			MulLine(l,1.0/Val(l,l));
		}
		return true;
	}
	
	public void PrintDebug() {
		//	Afficher la matrice à des fin de deboggage
		for (int l=0; l<nlines; l++) {
			for (int c=0; c<ncols; c++) {
				String txt = String.format("%8f ", Val(l,c));
				System.out.print(txt);				
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public Vecteur Intersection( Vecteur A, Vecteur B, Vecteur C, Vecteur D ) {
		//	Calculer l'intersection entre 2 droites
		nlines = 2;
		ncols = 3;
		v = new double[nlines*ncols];

		Vecteur solution = null;
		
		Set(0, 0, -(B.y-A.y));
		Set(0, 1, B.x-A.x);
		Set(0, 2, (B.x-A.x)*A.y-(B.y-A.y)*A.x);
		Set(1, 0, -(D.y-C.y));
		Set(1, 1, D.x-C.x);
		Set(1, 2, (D.x-C.x)*C.y-(D.y-C.y)*C.x);
		
		if (Resoudre()) {
			solution = new Vecteur(Val(0,2),Val(1,2));
		}
		return solution;
	}
}
