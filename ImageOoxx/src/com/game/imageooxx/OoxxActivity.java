package com.game.imageooxx;

import java.util.Arrays;

import com.game.imageooxx.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OoxxActivity extends Activity implements OnClickListener {

	ImageButton btn[] = new ImageButton[9];
	LinearLayout LL[] = new LinearLayout[3];
	CheckBox aiCheck;
	TextView tv,p1WinTV,p2WinTV,DrawTV;
	int nowPlayer = 1, index = 0;
	int[] arr = new int[9], jud = new int[9];
	int startFlag = 0, fuck = 0, ai = 0, fuckyou = 0;
	String winner = "";
	int p1Win,p2Win,Draw;
	SharedPreferences prefs;
	SharedPreferences.Editor prefEdit;

	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ooxx);

		LinearLayout LLV = (LinearLayout) findViewById(R.id.linearLayout);

		for (int i = 0; i < 3; i++) {
			LL[i] = new LinearLayout(this);
			LLV.addView(LL[i]);

			for (int j = 0; j < 3; j++) {
				btn[index] = new ImageButton(this);
				btn[index].setId(index);
				btn[index].setImageResource(R.drawable.space);
				btn[index].setBackgroundColor(0);
				btn[index].setOnClickListener(this);
				LL[i].addView(btn[index]);
				index++;
			}
		}

		aiCheck = (CheckBox) findViewById(R.id.ai);

		tv = (TextView) findViewById(R.id.textView);
		p1WinTV = (TextView) findViewById(R.id.p1win);
		p2WinTV = (TextView) findViewById(R.id.p2win);
		DrawTV = (TextView) findViewById(R.id.draw);
		
		prefs = getSharedPreferences("Wins", MODE_PRIVATE);
		prefEdit = prefs.edit();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		p1Win = prefs.getInt("p1Wins", 0);
		p2Win = prefs.getInt("p2Wins", 0);
		Draw = prefs.getInt("Draws", 0);
		
		p1WinTV.setText(String.valueOf(p1Win));
		p2WinTV.setText(String.valueOf(p2Win));
		DrawTV.setText(String.valueOf(Draw));
	}

	public void onClick(View v) {
		// TOm,jnDO Auto-generated method stub

		if (ai == 1 && nowPlayer == 2)
			return;

		ImageButton btn = null;
		btn = (ImageButton) findViewById(v.getId());

		if (startFlag == 0) {
			if (arr[v.getId()] == 0) {
				if (nowPlayer == 1) {
					btn.setImageResource(R.drawable.o1);
					arr[v.getId()] = 1;
					jud[v.getId()] = 1;
					judge();
					if (ai == 1) {
						nowPlayer = 2;
						tv.setText("AI thinking...");
						handler.postDelayed(runnable, 1000);
					} else {
						tv.setText("Player2 (X)'s turn.");
						nowPlayer = 2;
					}
				} else if (nowPlayer == 2) {

					btn.setImageResource(R.drawable.x1);
					arr[v.getId()] = 1;
					jud[v.getId()] = 2;
					judge();
					tv.setText("Player1 (O)'s turn.");
					nowPlayer = 1;

				}
			} else {
				Toast.makeText(v.getContext(), "這邊下過了喔^_^", Toast.LENGTH_SHORT)
						.show();
			}
		}

		String fuckStr = "FUCK!!FUCK!!FUCK!!FUCK!!FUCK!!";
		if (fuck != 0) {
			switch (fuck) {
			case 1:
				Toast.makeText(v.getContext(), "已經結束了^^", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				Toast.makeText(v.getContext(), "結束了，好嗎?^^", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				Toast.makeText(v.getContext(), "^^...", Toast.LENGTH_SHORT)
						.show();
				break;
			case 4:
				Toast.makeText(v.getContext(), "FUCK^^...", Toast.LENGTH_SHORT)
						.show();
				break;
			case 5:
				Toast.makeText(v.getContext(), "FUCK YOU^^........",
						Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(v.getContext(), "WHAT THE FUCK^^...........",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (fuck > 6)
				Toast.makeText(v.getContext(), fuckStr, Toast.LENGTH_SHORT)
						.show();

		}

		if (startFlag == 1) {
			tv.setText("The winner is " + winner + ".");
			fuck++;
		} else if (startFlag == 2) {
			tv.setText("Draw.");
			fuck++;
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		prefEdit.putInt("p1Wins", p1Win);
		prefEdit.putInt("p2Wins", p2Win);
		prefEdit.putInt("Draws", Draw);
		
		prefEdit.commit();
		
	}

	public void judge() {
		int judge;
		if (nowPlayer == 1)
			judge = 1;
		else
			judge = 2;

		int loc = 4;
		if (jud[loc] == judge) {
			for (int i = 1; i < 5; i++) {
				judge2(4, i);
			}
		}
		loc = 3;
		if (jud[loc] == judge) {
			judge2(3, 3);
		}
		loc = 5;
		if (jud[loc] == judge) {
			judge2(5, 3);
		}
		loc = 1;
		if (jud[loc] == judge) {
			judge2(1, 1);
		}
		loc = 7;
		if (jud[loc] == judge) {
			judge2(7, 1);
		}

		if (startFlag == 0) {
			int drawJudge = 0;
			for (int i = 0; i < 9; i++) {
				if (arr[i] == 1)
					drawJudge += 1;
			}
			if (drawJudge == 9){
				startFlag = 2;
				Draw++;
				DrawTV.setText(String.valueOf(Draw));
			}
		}

	}

	public void judge2(int location, int i) {
		int judge;
		if (nowPlayer == 1)
			judge = 1;
		else
			judge = 2;

		int loc1, loc2;

		loc1 = location - i;
		loc2 = location + i;
		if (jud[loc1] == judge && jud[loc2] == judge) {
			winner = "Player" + String.valueOf(nowPlayer);
			if (nowPlayer==1) p1Win++;
			else if(nowPlayer==2) p2Win++;
			if (nowPlayer == 2 && ai == 1)
				winner = "AI";
			startFlag = 1;
			
			p1WinTV.setText(String.valueOf(p1Win));
			p2WinTV.setText(String.valueOf(p2Win));
		}
	}

	public void reset(View v) {
		nowPlayer = 1;
		winner = "";
		startFlag = 0;
		fuck = 0;
		fuckyou = 0;
		Arrays.fill(arr, 0);
		Arrays.fill(jud, 0);

		for (int i = 0; i < 9; i++) {
			btn[i].setImageResource(R.drawable.space);
		}

		tv.setText("Player1 (O) first.");
	}

	public void aiChecked(View v) {
		// TODO Auto-generated method stub
		if (aiCheck.isChecked()) {
			ai = 1;
			if (startFlag == 0) {
				if (nowPlayer == 2) {
					tv.setText("AI thinking...");
					handler.postDelayed(runnable, 1000);
				}
			}
		} else
			ai = 0;
	}

	public void AI() {
		int win = aiJudge(2);
		int danger = aiJudge(1);

		ImageButton btn = null;

		if (startFlag == 0) {
			if (win != -1) {
				btn = (ImageButton) findViewById(win);
				btn.setImageResource(R.drawable.x1);
				arr[win] = 1;
				jud[win] = 2;
				judge();
				tv.setText("Player1 (O)'s turn.");
				nowPlayer = 1;
				if (fuckyou == 1)
					fuckyou = 0;
			} else if (danger != -1) {
				btn = (ImageButton) findViewById(danger);
				btn.setImageResource(R.drawable.x1);
				arr[danger] = 1;
				jud[danger] = 2;
				judge();
				tv.setText("Player1 (O)'s turn.");
				nowPlayer = 1;
				if (fuckyou == 1)
					fuckyou = 0;
			} else {

				if (fuckyou != 0) {
					int fuckloc = aiJudge2(4, 1, 1);
					int fuckloc2 = aiJudge2(4, 3, 1);
					
					if (fuckloc == 5) {
						fuckloc += 1;
					} else if (fuckloc == 3) {
						fuckloc -= 1;
					} else if (fuckloc2 == 1){
						fuckloc = 8;
					} else if (fuckloc2 == 7){
						fuckloc = 0;
					} else if (aiJudge3(4, 2, 1) == 4 || aiJudge3(4, 4, 1) == 4
							|| aiJudge3(4, 1, 1) == 4) {
						fuckloc = 1;
					} else if (aiJudge3(4, 3, 1) == 4) {
						fuckloc = 3;
					} else {
						if (arr[0] == 0 || arr[2] == 0 || arr[6] == 0
								|| arr[8] == 0) {
							int[] f1 = { 0, 2, 6, 8, 0, 2, 6, 8, 0, 2 };
							fuckloc = f1[(int) (Math.random() * 10)];
							while (arr[fuckloc] == 1)
								fuckloc = f1[(int) (Math.random() * 10)];
						} else {
							int[] f1 = { 1, 3, 5, 7, 1, 3, 5, 7, 1, 3 };
							fuckloc = f1[(int) (Math.random() * 10)];
							while (arr[fuckloc] == 1)
								fuckloc = f1[(int) (Math.random() * 10)];
						}
					}
					btn = (ImageButton) findViewById(fuckloc);
					btn.setImageResource(R.drawable.x1);
					arr[fuckloc] = 1;
					jud[fuckloc] = 2;
					judge();
					tv.setText("Player1 (O)'s turn.");
					nowPlayer = 1;
					fuckyou = 0;
				} else {
					int ran = 4;
					if (arr[4] == 0) {
						fuckyou = 1;
						ran = 4;
					} else if (arr[0] == 0 || arr[2] == 0 || arr[6] == 0
							|| arr[8] == 0) {
						int[] f1 = { 0, 2, 6, 8, 0, 2, 6, 8, 0, 2 };
						ran = f1[(int) (Math.random() * 10)];
						while (arr[ran] == 1)
							ran = f1[(int) (Math.random() * 10)];
					} else {
						int[] f1 = { 1, 3, 5, 7, 1, 3, 5, 7, 1, 3 };
						ran = f1[(int) (Math.random() * 10)];
						while (arr[ran] == 1)
							ran = f1[(int) (Math.random() * 10)];
					}
					btn = (ImageButton) findViewById(ran);
					btn.setImageResource(R.drawable.x1);
					arr[ran] = 1;
					jud[ran] = 2;
					judge();
					tv.setText("Player1 (O)'s turn.");
					nowPlayer = 1;
				}
			}
		}

		if (startFlag == 1) {
			tv.setText("The winner is " + winner + ".");
			fuck++;
		} else if (startFlag == 2) {
			tv.setText("Draw.");
			fuck++;
		}
	}

	public int aiJudge(int f) {
		int judge = f;

		int loc = 4;
		if (jud[loc] == judge) {
			for (int i = 1; i < 5; i++) {
				if (aiJudge2(4, i, f) != -1)
					return aiJudge2(4, i, f);
			}
		}
		loc = 3;
		if (jud[loc] == judge) {
			if (aiJudge2(3, 3, f) != -1)
				return aiJudge2(3, 3, f);
		} else if (jud[loc] == 0) {
			if (aiJudge3(3, 3, f) != -1)
				return aiJudge3(3, 3, f);
		}
		loc = 5;
		if (jud[loc] == judge) {
			if (aiJudge2(5, 3, f) != -1)
				return aiJudge2(5, 3, f);
		} else if (jud[loc] == 0) {
			if (aiJudge3(5, 3, f) != -1)
				return aiJudge3(5, 3, f);
		}
		loc = 1;
		if (jud[loc] == judge) {
			if (aiJudge2(1, 1, f) != -1)
				return aiJudge2(1, 1, f);
		} else if (jud[loc] == 0) {
			if (aiJudge3(1, 1, f) != -1)
				return aiJudge3(1, 1, f);
		}
		loc = 7;
		if (jud[loc] == judge) {
			if (aiJudge2(7, 1, f) != -1)
				return aiJudge2(7, 1, f);
		} else if (jud[loc] == 0) {
			if (aiJudge3(7, 1, f) != -1)
				return aiJudge3(7, 1, f);
		}

		return -1;

	}

	public int aiJudge2(int location, int i, int f) {
		int judge = f;

		int loc1, loc2;

		loc1 = location - i;
		loc2 = location + i;
		if (jud[loc1] == judge && jud[loc2] == 0) {
			return loc2;
		} else if (jud[loc2] == judge && jud[loc1] == 0) {
			return loc1;
		}
		return -1;
	}

	public int aiJudge3(int location, int i, int f) {
		int judge = f;

		int loc1, loc2;

		loc1 = location - i;
		loc2 = location + i;
		if (jud[loc1] == judge && jud[loc2] == judge)
			return location;
		return -1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ooxx, menu);
		return true;
	}

	Runnable runnable = new Runnable() {
		public void run() {
			AI();
		}
	};
}
