package scut.ftt.jigsaw;

import java.util.ArrayList;
import java.util.Timer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MainView extends GridView
{
	private static final String TAG = "MainView";
	private Drawable [] pieceDraw;
	private Drawable emptyPiece;
	private Drawable lastPiece;
	private TextView [] pieceView;
	private LumpAdapter adapter;
	private int []positionWraps;
	private int pieceWidth;
	private int pieceHeight;
	public int stepCount;
	public boolean success;
	private boolean locked;
	public boolean isReset = false;
	private String str;
	
	private ArrayList<Integer> ls;
	
	public MainView(Context context) {
		super(context);
		
	}
	
	public MainView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public MainView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	
	

	public boolean success()
	{
		return success;
	}
	
	public void initView(Bitmap map, DisplayMetrics dm)
	{
		locked = false;
		success = false;
		stepCount = 0;
		ls = new ArrayList<Integer>();
		pieceDraw = new Drawable[9];
		pieceView = new TextView[9];
		positionWraps = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
		divideMap(map, dm);

		adapter = new LumpAdapter();
		this.setAdapter(adapter);
	}
	
	@SuppressWarnings("deprecation")
	private void divideMap(Bitmap map, DisplayMetrics dm)
	{				
		PictureUtil pu = new PictureUtil(dm.widthPixels, dm.heightPixels);
		pieceWidth = pu.PieceWidth();
		pieceHeight = pu.PieceHeight();
		Bitmap [] result = pu.divideBitmap(map);
		if(!isReset)
			str = Solver.random();
		Log.i(TAG, "After random is: " + str);
		for(int i = 0; i < 9; i++)
			positionWraps[i] = str.charAt(i) -'0';
		lastPiece = new BitmapDrawable(result[8]);
		emptyPiece = new BitmapDrawable(Bitmap.createBitmap(pu.PieceWidth(), pu.PieceHeight(), Bitmap.Config.ARGB_8888));
		for(int i = 0; i < pieceDraw.length; i++)
		{	
			if(positionWraps[i] == 0)
				pieceDraw[i] = emptyPiece;
			else
				pieceDraw[i] = new BitmapDrawable(result[positionWraps[i] - 1]);
		}

		
		for(int i = 0; i < 9; i++)
		{
			TextView tv = new TextView(getContext());
			tv.setWidth(pieceWidth);
			tv.setHeight(pieceHeight);

			tv.setTextColor(android.graphics.Color.RED);
			tv.setBackgroundDrawable(pieceDraw[i]);
			pieceView[i] = tv;
		}

	}
	
	
	@Override
	public boolean performItemClick(View view, int position, long id)
	{
		if(locked && !ls.isEmpty() && position != ls.get(0))
			return false;
		else if(!ls.isEmpty()) Log.i(TAG, "Step " + ls.remove(0));
		if(position - 1 >= 0 && positionWraps[position - 1] == 0)
			moveLeft(position);
		else if(position + 1 <= 8 && positionWraps[position + 1] == 0)
			moveRight(position);
		else if(position - 3 >= 0 && positionWraps[position - 3] == 0)
			moveDown(position);
		else if(position + 3 <= 8 && positionWraps[position + 3] == 0)
			moveUp(position);
		else return false;
		
		stepCount++;
			
		
		if(winOrNot())
		{
			pieceView[8].setBackgroundDrawable(lastPiece);
			success = true;
		}
		if(locked)
		{
			for(int i = 0; i < 9; i++)
				pieceView[i].setText("");
			if(ls.size() > 0)
				pieceView[ls.get(0)].setText("W");
		}
		return true;
	}
	
	public void restore()
	{
		char [] s = new char[9];
		for(int i = 0; i < 9; i++)
			s[i] = (char) (positionWraps[i] + '0');
		
		ls = Solver.steps(String.valueOf(s));
		Log.i(TAG, "Solution is: " + ls.toString());
		if(ls.isEmpty())
		{
			Log.i(TAG, "ls is Empty");
			return;
		}
		Log.i(TAG, "Start restoration");
		locked = true;
		pieceView[ls.get(0)].setText("W");
	}
	
	
	
	private boolean winOrNot()
	{
		for(int i = 0; i < 8; i++)
			if(positionWraps[i] != i + 1)
				return false;
		if(positionWraps[8] == 0)
		{
			return true;
		}
		else
			return false;
	}
	
	@SuppressWarnings("deprecation")
	private void moveUp(int position)
	{
		pieceView[position + 3].setBackgroundDrawable(pieceDraw[position]);
		pieceView[position].setBackgroundDrawable(emptyPiece);
		pieceDraw[position + 3] = pieceDraw[position];
		pieceDraw[position] = emptyPiece;
		positionWraps[position + 3] = positionWraps[position];
		positionWraps[position] = 0;
	}
	
	private void moveDown(int position)
	{
		pieceView[position - 3].setBackgroundDrawable(pieceDraw[position]);
		pieceView[position].setBackgroundDrawable(emptyPiece);
		pieceDraw[position - 3] = pieceDraw[position];
		pieceDraw[position] = emptyPiece;
		positionWraps[position - 3] = positionWraps[position];
		positionWraps[position] = 0;
	}
	
	private void moveLeft(int position)
	{
		pieceView[position - 1].setBackgroundDrawable(pieceDraw[position]);
		pieceView[position].setBackgroundDrawable(emptyPiece);
		pieceDraw[position - 1] = pieceDraw[position];
		pieceDraw[position] = emptyPiece;
		positionWraps[position - 1] = positionWraps[position];
		positionWraps[position] = 0;
	}
	
	private void moveRight(int position)
	{
		pieceView[position + 1].setBackgroundDrawable(pieceDraw[position]);
		pieceView[position].setBackgroundDrawable(emptyPiece);
		pieceDraw[position + 1] = pieceDraw[position];
		pieceDraw[position] = emptyPiece;
		positionWraps[position + 1] = positionWraps[position];
		positionWraps[position] = 0;
	}
	
	class LumpAdapter extends BaseAdapter {

		public int getCount() {

			return 9;
		}

		public Object getItem(int position) {

			return position;
		}

		public long getItemId(int position) {

			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			 
			return pieceView[position];
		}

	}
}
