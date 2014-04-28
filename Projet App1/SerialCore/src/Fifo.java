//	FIFO
//
//	Impl�mentation d'une pile "First In First Out"
//

public class Fifo {
	
	char buffer[];
	int size, first, last;
	//boolean busy = false;
	
	public Fifo() {
		size = 100;		// taille de la pile
		buffer = new char[size];
		first = last = 0;
	}

	//	Est-ce que la pile est pleine ?
	public boolean IsFull() {
		return (last % size) == ((first-1) % size);
	}
	
	//	Est-ce que la pile est vide ?
	public boolean IsEmpty() {
		return (last == first);
	}
	
	//	Empiler
	public boolean Push( char b ) {
		if (IsFull() == false) {
			buffer[last] = b;
			last = (last + 1) % size;
			return true;
		}
		return false;
	}
	
	//	D�piler
	public char Pull() {
		char b;
		if (IsEmpty() == false) {
			b = buffer[first];
			first = (first + 1) % size;
			return b;
		}
		return 0;
	}
	
	//	Lire sans d�piler
	public char Peek( int i ) {
		return buffer[(first + i) % size];
	}
	
	//	Vider la pile
	public void Flush() {
		first = last = 0;
	}
	
	//	D�piler n �l�ments
	public void Flush( int n ) {
		if (n > 0) {
			for (int i=0; i<n; i++) Pull();
		}
	}
	
	//	Structure pour l'information sur un buffer
	public class BufferAndSize {
		char buffer[];
		int size;
	}
	
	//	Nombre d'�l�ments empil�s
	public int Size() {
		if (last == first) return 0;
		if (last > first) return last-first;
		return (size-first)+last;
	}
	
	//	Places disponibles
	public int Left() {
		return size-Size();
	}
	
	//	R�cup�rer toutes les donn�es dans la pile sans d�piler
	BufferAndSize CheckRead() {
		BufferAndSize buf = new BufferAndSize();
		
		buf.size = Size();
		if (buf.size > 0) {
			buf.buffer = new char[buf.size+1];
			for (int i=0; i<buf.size; i++) buf.buffer[i]=Peek(i);
			buf.buffer[buf.size]=0;
		} else {
			buf.buffer = null;
		}
		
		return buf;
	}		
}
