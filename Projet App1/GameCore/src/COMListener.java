import gnu.io.CommPortIdentifier;
import gnu.io.CommPort;
import gnu.io.SerialPort;
 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
 

public class COMListener {
 
	private SerialPort serialPort = null;
	private InputStream in = null;
	private OutputStream out = null;
	public SerialWriter writer = null;

	public int rate = 115200;
	public char lastbyte = 0;
 
	public COMListener() {
		super();
	}
 
	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
 
			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(rate, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				
				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();
				
				writer = new SerialWriter(out);				
				(new Thread(writer)).start();
 
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}
	

	public int DataAvail() {
		if (in == null) return 0;
		try {
			return in.available();
		} catch (IOException e) {
			return 0;
		}
	}
	
	
	public char Read() {
		if (in == null) return 0;
		try {
			char b;
			lastbyte = b = (char)in.read();
			return b;
		} catch (IOException e) {
			return 0;
		}
	}
	
	
	public void Write( char b ) {
		if (writer == null) return;
		writer.queue.Push(b);
	}


	public static class SerialWriter implements Runnable {
		public OutputStream out;
		public Fifo queue = new Fifo();
 
		public SerialWriter(OutputStream out) {
			this.out = out;
		}
 
		public void run() {
			while (true) {
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
		}
	}
	
	
	public static class Fifo {
		char buffer[];
		int size, first, last;
		boolean busy = false;
		
		public Fifo() {
			size = 100;
			buffer = new char[size];
			first = last = 0;
		}
		
		public boolean IsFull() {
			return (last % size) == ((first-1) % size);
		}
		
		public boolean IsEmpty() {
			return (last == first);
		}
		
		public boolean Push( char b ) {
			if (IsFull() == false) {
				buffer[last] = b;
				last = (last + 1) % size;
				return true;
			}
			return false;
		}
		
		public char Pull() {
			char b;
			if (IsEmpty() == false) {
				b = buffer[first];
				first = (first + 1) % size;
				return b;
			}
			return 0;
		}
		
		public char Peek( int i ) {
			return buffer[(first + i) % size];
		}
		
		public void Flush() {
			first = last = 0;
		}
	}
}
