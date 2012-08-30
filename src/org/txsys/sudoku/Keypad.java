package org.txsys.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class Keypad extends Dialog {
    protected static final String PackageName="org.txsys.sudoku";
    protected static final String TAG = PackageName+"Keypad";
    private View keypad;
    private final int useds[];
    private final PuzzleView puzzleView;
    private final View[] keys = new View[9];
    
    public Keypad(Context context, int useds[], PuzzleView puzzleView) {
        super(context);
        this.useds=useds;
        this.puzzleView = puzzleView;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setTitle(R.string.keypad_title);
        this.setContentView(R.layout.keypad);
        this.findViews();
        
        for (int element:useds) {
            if (element!=0) {
                keys[element-1].setVisibility(View.INVISIBLE);
            }
        }
        this.setListeners();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	int tile = 0;
    	switch(keyCode) {
    	case KeyEvent.KEYCODE_0:
    	case KeyEvent.KEYCODE_SPACE:
    		tile = 0;
    		break;
    		
    	case KeyEvent.KEYCODE_1:
    		tile = 1;
    		break;
    	default:
    		return super.onKeyDown(keyCode, event)	;
    	}
    	if (isValid(tile)) {
    		returnResult(tile);
    	}
    	return true;
    }
    
    private void findViews() {
        keypad = findViewById(R.id.keypad);
        keys[0] = findViewById(R.id.keypad_1);
    }
    
    private void setListeners() {
        for (int i=0;i<keys.length;i++) {
        	final int t = i + 1;
        	keys[i].setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					returnResult(t);
				}
			});
        }
    }
    
    private boolean isValid(int tile) {
    	for (int t:useds) {
    		if (tile==t) return false;
    	}
    	return true;
    }
    
    private void returnResult(int tile) {
    	puzzleView.setSelectedTile(tile);
    	this.dismiss();
    }
}
