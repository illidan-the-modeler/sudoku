package org.txsys.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
    
    private void findViews() {
        keypad = findViewById(R.id.keypad);
        keys[0] = findViewById(R.id.keypad_1);
    }
    
    private void setListeners() {
        
    }
}
