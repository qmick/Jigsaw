package scut.ftt.jigsaw;

import scut.homework.jigsaw.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class SelectActivity extends Activity implements OnClickListener
{
	private GridView gv_select;
	private Button bu_album;
	private Button bu_camera;
	private Button bu_return;
	private Integer [] mThumbIds = 
		{
			R.drawable.kyoto,
			R.drawable.longhorn_bliss,
			R.drawable.siegetank,
			R.drawable.templar,
			R.drawable.zerato
		};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		
		gv_select = (GridView)findViewById(R.id.gv_select);
		bu_album  = (Button)findViewById(R.id.bu_album);
		bu_camera = (Button)findViewById(R.id.bu_camera);
		bu_return = (Button)findViewById(R.id.bu_return);
			
		gv_select.setAdapter(new ImageAdapter(this));
		gv_select.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				enterGame(mThumbIds[position]);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bu_album: select_album(); break;
		case R.id.bu_camera: select_camera(); break;
		case R.id.bu_return: returnto(); break;
		default: break;
		}
		
	}

	
	
	private void select_album()
	{
		
	}
	
	private void select_camera()
	{
		
	}
	
	private void returnto()
	{
		this.finish();
	}
	
	private void enterGame(int dra)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("RawPic", dra);
		startActivity(intent);
	}
	
	private class ImageAdapter extends BaseAdapter
	{
		private Context mContext;
		
		public ImageAdapter(Context c)
		{
			mContext = c;
		}

		@Override
		public int getCount() {
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ImageView imageview;
			if(convertView==null)  
			{  
				imageview=new ImageView(mContext);  
				imageview.setLayoutParams(new GridView.LayoutParams(85, 85));  
				imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);  
				imageview.setPadding(8,8,8,8);  
			}  
			else  
			{  
				imageview=(ImageView) convertView;  
			}  
			imageview.setImageResource(mThumbIds[position]);  
			return imageview;  
			  
			
		}
}


	
	
}

