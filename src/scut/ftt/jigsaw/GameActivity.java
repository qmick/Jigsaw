package scut.ftt.jigsaw;

import java.util.Timer;
import java.util.TimerTask;

import scut.homework.jigsaw.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener
{
	private static final String TAG = "GameActivity";
	
	private int drawableAddress;
	private MainView gv_game;
	private TextView tv_step;
	private TextView tv_time;
	private ImageView iv_raw;
	
	private Button bu_restore;
	private Button bu_rawimage;
	private Button bu_reset;
	private Button bu_gameback;
	private Timer timer;
	private TimerTask timerTask;  
	private Bitmap resmap;
	private boolean start = false;
	public static int timerIndex = 0;
	
	
	private Handler handler = new Handler() {  
		  
	    @Override  
	    public void handleMessage(Message msg) 
	    {  
	        switch (msg.what) 
	        {  
		        case 1:  
		        // 更新计时器  
		        if(gv_game.success())
		        {
		        	timer.cancel();
		        	timer.cancel();
		        }
		        timerIndex++;  
		        tv_time.setText("" + timerIndex);
		        tv_step.setText("" + gv_game.stepCount);
		        break;  
		        default:  
		        break;  
	        }  
	    }  
	    }; 
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
		tv_step     = (TextView)findViewById(R.id.tv_step);
		tv_time     = (TextView)findViewById(R.id.tv_time);
		bu_restore  = (Button)findViewById(R.id.bu_restore);
		bu_rawimage = (Button)findViewById(R.id.bu_rawimage);
		bu_reset    = (Button)findViewById(R.id.bu_reset);
		bu_gameback = (Button)findViewById(R.id.bu_gameback);
		iv_raw      = (ImageView)findViewById(R.id.iv_raw);
		gv_game     = (MainView) findViewById(R.id.gv_game);
		bu_restore.setOnClickListener(this);
		bu_rawimage.setOnClickListener(this);
		bu_reset.setOnClickListener(this);
		bu_gameback.setOnClickListener(this);
		Intent intent = this.getIntent();
		drawableAddress = intent.getIntExtra("RawPic", -1);
		resmap = BitmapFactory.decodeResource(this.getResources(), drawableAddress);
		//iv_raw.setImageBitmap(resmap);
		gv_game.initView(resmap, PictureUtil.getScreenSize(this));
	}
	
	

	@Override
	public void onClick(View v) {
		if(!start)
		{
			timer = new Timer(true);
			timerTask = new TimerTask() 
			{  
				  
		        @Override  
		        public void run() 
		        {  
		        Message msg = new Message();  
		        msg.what = 1;  
		        handler.sendMessage(msg);  
		        }  
		    };  
		    
		    timer.schedule(timerTask, 0, 1000);  
		    start = true;
		}
		switch(v.getId())
		{
		case R.id.bu_reset: 
			timerIndex = 0;
			gv_game.isReset = true; 
			gv_game.initView(resmap, PictureUtil.getScreenSize(this));
			
			break;
		case R.id.bu_restore: gv_game.restore(); break;
		default: break;
		}
	}
}

