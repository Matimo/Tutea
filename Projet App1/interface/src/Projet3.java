
public class Projet3 extends GameWindow{
	
	
	int	 Bass ;
	int	 Claqu� ;
	int	 Tonique;
	int	 Mat� ;
	int  Mat�Claqu�;
	int	 Mat�Tonique;
	
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
		
//		Initialiser le port s�rie
			serialport = new COMListener();		
			serialport.port = "COM6";
			serialport.rate = 115200;
			serialport.Init();
		}
	public void Init() {
		
		Bass = A0;
		Claqu� = A1;
		Tonique = A2;
		Mat�Claqu� = A31;
		Mat�Tonique = A32;
	
		
		
	}
	
	public void Animate() {
		if (serialport.DataAvail() > 0) {
			char c = serialport.Read();
			if (Bass == 'B') ;
			if (Tonique == 'T');
			if (Claqu� == 'C');
			
			if ( Mat�Tonique == A31 );
			if ( Mat�Tonique == A32 );
		}
		
	}
}
