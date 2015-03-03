package scut.ftt.jigsaw;

public class Node implements Comparable<Node>
{
	public static String FINAL = "123456780";
	public String data;
	public String parent;
	public int depth;
	
	public Node(){data = ""; parent = "";	};
	public Node(String d)	{	data = d;	parent ="";	depth = 0;	};
	public Node(String d, String p)	{	data = d;	parent = p;	depth = 0;	};
	public Node(String d, String p, int depth)	{	data = d;	parent = p;	this.depth = depth;	}
	
	
	static public String moveUp(String zero, int pos)
	{
		char [] a = zero.toCharArray();		
		a[pos] = a[pos + 3];
		a[pos + 3] = '0';
		return String.valueOf(a);
	}
	
	static public String moveLeft(String zero, int pos)
	{
		char [] a = zero.toCharArray();		
		a[pos] = a[pos - 1];
		a[pos - 1] = '0';
		return String.valueOf(a);
	}
	
	static public String moveRight(String zero, int pos)
	{
		char [] a = zero.toCharArray();		
		a[pos] = a[pos + 1];
		a[pos + 1] = '0';
		return String.valueOf(a);
	}
	
	static public String moveDown(String zero, int pos)
	{
		char [] a = zero.toCharArray();		
		a[pos] = a[pos - 3];
		a[pos - 3] = '0';
		return String.valueOf(a);
	}
	
	private int Euclide_dis(String s, int n)
	{
		if(s.charAt(n) == '0')
			return 0;
		return Math.abs((s.charAt(n) - '0') / 3 - (FINAL.charAt(n) - '0') / 3) + Math.abs((s.charAt(n) - '0') % 3 - (FINAL.charAt(n) - '0') % 3);
	}
	@Override
	public int compareTo(Node another) 
	{
		int n1n = this.depth, n2n = another.depth;
		
//		for(int i = 0; i < 9; i++)
//			if(data.charAt(i) != FINAL.charAt(i))	n1n++;
//		for(int i = 0; i < 9; i++)
//			if(another.data.charAt(i) != FINAL.charAt(i)) n2n++;
		for(int i = 0; i < 9; i++)
			if(data.charAt(i) != '0') n1n += Euclide_dis(data, i);
		for(int i = 0; i < 9; i++)
			if(data.charAt(i) != '0') n2n += Euclide_dis(another.data, i);
		
		if(n1n > n2n)
			return 1;
		else return -1;
//		else if(n1n < n2n)
//			return -1;
//		else return 0;
	};
}

class ClosedNode
{
	public String data;
	public String parent;
	public ClosedNode()	{	data = ""; parent = "";	};
	public ClosedNode(Node n)	{	data = n.data;	parent = n.parent;	};
	public ClosedNode(String d)	{	data = d;	parent = "";};
	public ClosedNode(String d, String p)	{data = d;	parent = p;	}
	
}
