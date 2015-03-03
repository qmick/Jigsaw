package scut.ftt.jigsaw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class PictureUtil 
{
	private int viewWidth;
	private int viewHeight;
	private int pieceWidth;
	private int pieceHeight;
	
	public int PieceWidth()
	{
		return pieceWidth;
	}
	
	public int PieceHeight()
	{
		return pieceHeight;
	}
	
	public PictureUtil(int screenWidth, int screenHeight)
	{
		viewWidth = (int) (0.8 * screenWidth);
		viewHeight = (int) (0.5 * screenHeight);
		pieceWidth = (int) (0.8 * screenWidth / 3);
		pieceHeight = (int) (0.5 * screenHeight / 3);
	}
	
	public Bitmap [] divideBitmap(Bitmap map)
	{
		Bitmap [] result = new Bitmap[9];
		int width = map.getWidth();
		int height = map.getHeight();
		float scaleWidth = ((float)viewWidth) / width;
		float scaleHeight = ((float)viewHeight) / height;
		Matrix mat = new Matrix();
		mat.postScale(scaleWidth, scaleHeight);
		Bitmap newmap = Bitmap.createBitmap(map, 0, 0, width, height, mat, true);
		
		for (int i = 0; i < 9; i++) {
			int py = i / 3;
			int px = i % 3;
			result[i] = Bitmap.createBitmap(newmap, px * pieceWidth, py
					* pieceHeight, pieceWidth, pieceHeight);
		}
		
		return result;
	}
	public static DisplayMetrics getScreenSize(Context context){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		display.getMetrics(metrics);
		return metrics;
	}
}
