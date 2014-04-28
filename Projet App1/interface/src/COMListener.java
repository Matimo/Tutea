//	COMListener
//
//	Gestion "bas niveau" du port RS232
//

import gnu.io.CommPortIdentifier;
import gnu.io.CommPort;
import gnu.io.SerialPort;
import java.util.Enumeration;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class COMListener {
 
	private SerialPort serialPort = null;
	private InputStream in = null;
	private OutputStream out = null;
	public SerialWriter writer = null;
	private Thread writerthread = null;
	
	enum STATE {
		CLOSED,
		OPENED,
		CLOSING
	};
	
	STATE state = STATE.CLOSED;

	//	Remarque : Pour les ports >= COM10, il faut donner le nom
	//	sous la forme "\\\\.\\COM10"
	public String port = "\\\\.\\COM12";
	public int rate = 115200;
	public char lastbyte = 0;
 
	public COMListener() {
		super();
	}
 
	//	Connection
	void connect(String portName) throws Exception {
		
		//	Remarque : Pour les ports >= COM10, il faut donner le nom
		//	sous la forme "\\\\.\\COM10"
		
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),100);
 
			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(rate, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				
				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();
				
				writer = new SerialWriter(out);
				//(new Thread(writer)).start();
				writerthread = new Thread(writer); writerthread.start();
				
				state = STATE.OPENED;
 
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}
	

	//	Initialisation
	public void Init() {
		try {
			connect(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void Close() {
		if (state != STATE.OPENED) return; 
		
		state = STATE.CLOSING;		
		
		if (writer != null) {
			while (writer.queue.IsEmpty() == false);
			while (writer.terminated == false) {
				writer.Stop();
				try {
					writer.out.flush();
				} catch (IOException e) {};
			}
		}
		
		if (serialPort != null) {
			try {
			    // close the i/o streams.
				out.flush();
			    out.close();
			    in.close();
			} catch (IOException ex) {
			    // don't care
			}
			// Close the port.
			serialPort.removeEventListener();
			serialPort.close();
		}
		
		state = STATE.CLOSED;
	}	
	

	//	Vérifie si des données sont disponibles
	public int DataAvail() {
		if (state != STATE.OPENED) return 0; 
		if (in == null) return 0;
		try {
			return in.available();
		} catch (IOException e) {
			return 0;
		}
	}
	

	//	Lecture d'un caractère
	public char Read() {
		if (state != STATE.OPENED) return 0; 
		if (in == null) return 0;
		try {
			char b;
			lastbyte = b = (char)in.read();
			return b;
		} catch (IOException e) {
			return 0;
		}
	}
	
	
	public void FlushInput() {
		if (state != STATE.OPENED) return; 
		if (in == null) return;
		try {
			while (true) {
				if (in.read() == -1) return;
			}
		} catch (IOException e) {			
		}
	}
	
	
	public void FlushOutput() {
		if (state != STATE.OPENED) return; 
		if (in == null) return;
		try {
			out.flush();
		} catch (IOException e) {			
		}
	}
	
	
	//	Ecriture d'un caractère
	public void Write( char b ) {
		if (state != STATE.OPENED) return; 
		if (writer == null) return;
		writer.queue.Push(b);
	}


	//	"Thread" de lecture du port
	public static class SerialWriter implements Runnable {
		public OutputStream out;
		public Fifo queue = new Fifo();
		public boolean ok = true;
		public boolean terminated = false;
		
		public SerialWriter(OutputStream out) {
			this.out = out;
			terminated = false;
		}
		
		public void Stop() {
			ok = false;
		}
 
		public void run() {
			while (ok) {
				try { Thread.sleep(0L, 100); } catch (Exception e) {};
				if (queue.IsEmpty() == false) {					
					char b = queue.Pull();
					try {
						this.out.write((int)b);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
			}
			terminated = true;
		}
	}
	
	
	public static String ListPorts() {
		// get list of ports available on this particular computer,
		// by calling static method in CommPortIdentifier.
		Enumeration pList = CommPortIdentifier.getPortIdentifiers();
		String s = "";

		// Process the list.
		while (pList.hasMoreElements()) {
			CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
			if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("is a Serial Port: " + cpi);
				s = s + cpi.getName() + "\n";
			}
		}
		
		return s;
	}
	
	
	public static void Delay( long milliseconds, int nanoseconds ) {
		try {
			Thread.sleep(milliseconds,nanoseconds);
		} catch (Exception e) {};
	}
}

