
public class Projet3 extends GameWindow{
	
	
	int	 Bass ;
	int	 Claqué ;
	int	 Tonique;
	int	 Maté ;
	int  MatéClaqué;
	int	 MatéTonique;
	
	int A0;
	int A1;
	int A2;
	int A3;
	int A31;
	int A32;
	
	
	COMListener serialport = null;
	
	
	public Projet3() {
		super();
		setSize(512,512);
		
//		Initialiser le port série
			serialport = new COMListener();		
			serialport.port = "COM6";
			serialport.rate = 115200;
			serialport.Init();
		}
	public void Init() {
		
		Bass = A0;
		Claqué = A1;
		Tonique = A2;
		MatéClaqué = A31;
		MatéTonique = A32;
	
		
		
	}
	
	public void Animate() {
		if (serialport.DataAvail() > 0) {
			char c = serialport.Read();
			if (Bass == 'B') ;
			if (Tonique == 'T');
			if (Claqué == 'C');
			
			if ( MatéTonique == A31 );
			if ( MatéTonique == A32 );
		}
		
	}
}
