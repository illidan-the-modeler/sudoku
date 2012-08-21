package org.txsys.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

public class Keypad extends Dialog {
    protected static final String PackageName="org.txsys.sudoku";
    protected static final String TAG = PackageName+"Keypad";
    private View keypad;
    private final int useds[];
    private final PuzzleView puzzleView;
    
    public Keypad(Context context, int useds[], PuzzleView puzzleView) {
        super(context);
        this.useds=useds;
        this.puzzleView = puzzleView;
    }
}
