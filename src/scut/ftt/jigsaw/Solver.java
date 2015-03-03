package scut.ftt.jigsaw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

import android.util.Log;

public class Solver
{
	private static final String TAG = "Solver";
	public static ArrayList<Integer> steps(String str)
	{
		if(str.length() != 9) 
		{
			Log.i(TAG, "input string is: " + str);
			
		}
		ArrayList<String> s = solve(str);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(int i = s.size() - 1; i > 0; i--)
		{
			for(int j = 0; j < 9; j++)
			{
				if(s.get(i).charAt(j) != s.get(i - 1).charAt(j) && s.get(i).charAt(j) != '0')
				{
					arr.add(j);
					break;
				}
			}
		}
		
		return arr;
	}
	
	static private boolean solvable(String s)
	{
		int p = 0;
		char []a;
		a = new char[8];
		for(int i = 0; i < 9; i++)
		{
			if(s.charAt(i) != '0')
			{
				a[p] = s.charAt(i);
				p++;
			}
		}
		int c = 0;
		for(int i = 0; i < 8; i++)
			for(int j = i; j < 8; j++)
				if(a[i] > a[j])
					c++;
		return c / 2 * 2 == c; 
	}
	
	static private int findHole(String t)
	{
		for(int i = 0; i < 10; i++)
			if(t.charAt(i) == '0')
				return i;
		return -1;
	}
	
	static public String random()
	{
		char []array = {'1', '2', '3', '4', '5', '6', '7', '8', '0'};
		boolean sol = false;
		Random random = new Random();
		while(!sol)
		{
			for(int i = 0; i < 9; i++)
			{
				int t1 = random.nextInt(9);
				int t2 = random.nextInt(9);
				char temp = array[t1];
				array[t1] = array[t2];
				array[t2] = temp;
			}
			sol = solvable(String.valueOf(array));
		}
		return String.valueOf(array);
	}
	
	static public ArrayList<String> solve(String str)
	{
		ArrayList<String>  a = new ArrayList<String>();
		if(solvable(str))
		{
			HashMap<Integer, ClosedNode> closed = new HashMap<Integer, ClosedNode>();
			PriorityQueue<Node> open = new PriorityQueue<Node>();
			
			Node t = new Node(str);
			open.add(t);
			while(!open.isEmpty())
			{
				t = open.poll();
				if(t.data.equals(Node.FINAL))
					break;
				closed.put(Integer.parseInt(t.data), new ClosedNode(t));
				
				int z = findHole(t.data);
				String q;
				
				if(z != 0 && z != 3 && z != 6)
				{
					q = Node.moveLeft(t.data, z);
					if(q.equals(Node.FINAL))
						break;
					if(!closed.containsKey(Integer.parseInt(q)))
						open.add(new Node(q, t.data, t.depth + 1));
				}
				
				if(z != 2 && z != 5 && z != 8)
				{
					q = Node.moveRight(t.data, z);
					if(q.equals(Node.FINAL))
						break;
					if(!closed.containsKey(Integer.parseInt(q)))
						open.add(new Node(q, t.data, t.depth + 1));
				}
				
				if(z - 3 >= 0)
				{
					q = Node.moveDown(t.data, z);
					if(q.equals(Node.FINAL))
						break;
					if(!closed.containsKey(Integer.parseInt(q)))
						open.add(new Node(q, t.data, t.depth + 1));
				}
				
				if(z + 3 <= 8)
				{
					q = Node.moveUp(t.data, z);
					if(q.equals(Node.FINAL))
						break;
					if(!closed.containsKey(Integer.parseInt(q)))
						open.add(new Node(q, t.data, t.depth + 1));
				}				
			}
			ClosedNode cn = new ClosedNode(t);
			a.add(Node.FINAL);
			while(!cn.parent.isEmpty())
			{
				a.add(cn.data);
				cn = closed.get(Integer.parseInt(cn.parent));
			}
			a.add(cn.data);
		}
		return a;
	}
	
	

}
