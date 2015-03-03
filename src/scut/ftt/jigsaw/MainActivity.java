package scut.ftt.jigsaw;

import scut.homework.jigsaw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button enter;
	private Button ranking;
	private Button guide;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
		enter   = (Button)findViewById(R.id.bu_enter);
		ranking = (Button)findViewById(R.id.bu_rank);
		guide   = (Button)findViewById(R.id.bu_guide);
		enter.setOnClickListener(this);
		ranking.setOnClickListener(this);
		guide.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bu_enter:	enterSelection();
		case R.id.bu_rank:	enterRanking();
		case R.id.bu_guide:	enterGuide();
		default: break;
		}
		
	}
	
	private void enterSelection()
	{
		Intent intent = new Intent(this, SelectActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	private void enterRanking()
	{
		
	}
	
	private void enterGuide()
	{
		
	}

}
