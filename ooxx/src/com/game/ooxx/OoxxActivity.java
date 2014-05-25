package com.game.ooxx;

import java.util.Arrays;

import com.game.ooxx.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.*;
import android.widget.*;

public class OoxxActivity extends Activity implements OnClickListener {

	Button btn[]=new Button[9];
	LinearLayout LL[]=new LinearLayout[3];
	int nowPlayer = 1,winner=0,index = 0;
	int[] arr=new int[9];
	int startFlag=0,fuck=0;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ooxx);
        
        LinearLayout LLV=(LinearLayout)findViewById(R.id.LinearLayout01); 
        
        for(int i=0;i<3;i++){ 
        	LL[i]=new LinearLayout(this);
        	LLV.addView(LL[i]);
        	
        	for(int j=0;j<3;j++){
            btn[index]=new Button(this);
            btn[index].setId(index);
            btn[index].setOnClickListener(this);
            LL[i].addView(btn[index]);
            index++;
        	}
        } 
        
        
        
        
    }
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
    	TextView tv=(TextView)findViewById(R.id.textView);
    	Button btn=null;
    	btn = (Button)findViewById(v.getId());
    	
    	if(startFlag==0){
    		if(arr[v.getId()]==0){
    			if(nowPlayer==1){
    	    		btn.setText("O");
    	    		arr[v.getId()]=1;
    	    		judge();
    	    		nowPlayer=2;
    	    	}else if(nowPlayer==2){
    	    		btn.setText("X");
    	    		arr[v.getId()]=1;
    	        	judge();
    	    		nowPlayer=1;
    	    	}
    		}else{
    			Toast.makeText(v.getContext(), "FUCK", Toast.LENGTH_SHORT).show();
    		}
    	}

    	String fuckStr="FUCK!!FUCK!!FUCK!!FUCK!!FUCK!!";
    	if(fuck!=0){
    		switch(fuck){
    		case 1:
    			Toast.makeText(v.getContext(), "FUCK", Toast.LENGTH_SHORT).show();
    			break;
    		case 2:
    			Toast.makeText(v.getContext(), "FUCK YOU", Toast.LENGTH_SHORT).show();
    			break;
    		case 3:
    			Toast.makeText(v.getContext(), "FUCK YOUR MOTHER", Toast.LENGTH_SHORT).show();
    			break;
    		case 4:
    			Toast.makeText(v.getContext(), "FUCK YOUR MOTHER AND FATHER", Toast.LENGTH_SHORT).show();
    			break;
    		case 5:
    			Toast.makeText(v.getContext(), "FUCK YOUR MOTHER AND FATHER AND SISTER", Toast.LENGTH_SHORT).show();
    			break;
    		case 6:
    			Toast.makeText(v.getContext(), "FUCK YOUR MOTHER AND FATHER AND SISTER AND BROTHER", Toast.LENGTH_SHORT).show();	
    			break;
    		}
    		if(fuck>6)
    			Toast.makeText(v.getContext(), fuckStr, Toast.LENGTH_SHORT).show();
    		
    	}
    	
    	if(startFlag==1){
    		tv.setText("The winner is Player" + winner);
    		fuck++;
    	}else if(startFlag==2){
    		tv.setText("Draw");
    		fuck++;
    	}
    	
    	
	}

    public void judge(){
    	String judge;
    	if(nowPlayer==1) judge="O";
    	else judge="X";
    	
    	Button btn=null;
    	btn=(Button)findViewById(4);
    	if(btn.getText()==judge){
    		for(int i=1;i<5;i++){
    				judge2(4,i);
    		}
    	}else{
    		btn=(Button)findViewById(3);
    		if(btn.getText()==judge){
    			judge2(3,3);
    		}
    		btn=(Button)findViewById(5);
    		if(btn.getText()==judge){
    			judge2(5,3);
    		}
    		btn=(Button)findViewById(1);
    		if(btn.getText()==judge){
    			judge2(1,1);
    		}
    		btn=(Button)findViewById(7);
    		if(btn.getText()==judge){
    			judge2(7,1);
    		}
    	}
    	
    	
    	if(startFlag==0){
    		int drawJudge=0;
    		for(int i=0;i<9;i++){
        		if(arr[i]==1)
        			drawJudge+=1;
        	}
        	if(drawJudge==9) startFlag=2;
    	}
    	
    }
    
	public void judge2(int location,int i){
		String judge;
    	if(nowPlayer==1) judge="O";
    	else judge="X";
    	
		Button btn1=null,btn2=null;
		btn1=(Button)findViewById(location-i);
		btn2=(Button)findViewById(location+i);
		if(btn1.getText()==judge && btn2.getText()==judge){
			winner=nowPlayer;
			startFlag=1;
		}
	}
	
	public void reset(View v){
		nowPlayer=1;
		winner=0;
		startFlag=0;
		fuck=0;
		Arrays.fill(arr, 0);
		
		for(int i=0;i<9;i++){
            btn[i].setText("¡@");
        }
		
		TextView tv=(TextView)findViewById(R.id.textView);
		tv.setText("Player1 is \"O\" ,Player2 is \"X\"");
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ooxx, menu);
        return true;
    }

	
    
}
