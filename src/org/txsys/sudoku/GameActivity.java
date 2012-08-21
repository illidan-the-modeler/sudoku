package org.txsys.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class GameActivity extends Activity {

	private final static String TAG="org.txsys.sudoku.GameActivity";
	
	private PuzzleView puzzleView;
	
	private int puzzle[] =  new int[9*9];
	private final int used[][][] = new int [9][9][];
	public static final int DIFFICULTY_HARD=2;
	public static final int DIFFICULTY_MEDIUM=1;
	public static final int DIFFICULTY_EASY=0;
	private final String easyPuzzle=
					"360000000004230800000004200"+
					"070460003820000014500013020"+
					"001900000007048300000000045" ;
	
	private final String mediumPuzzle =
					"650000070000506000014000005"+
					"007009000002314700000700800"+
					"500000630000201000030000097";

	private final String hardPuzzle = 
					"009000000080605020501078000"+
					"000000700706040102004000000"+
					"000720903090301080000000600" ;


	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        String msg = "onCreate";
        Log.d(TAG, msg);
        puzzleView = new PuzzleView(this);
        
        setContentView(puzzleView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private int getTile(int x, int y) {
    	return puzzle[y*9+x];
    }
    
    private void setTile(int x, int y, int value) {
    	puzzle[y*9+x] = value;
    }
    
    protected int[] getUsedTiles(int x, int y) {
    	return used[x][y];
    }
    
    protected boolean setTileIfValid(int x, int y, int value) {
    	//TODO: tiles return 'null'
    	int tiles[] = getUsedTiles(x,y);
    	if (value!=0) {
    		for (int tile:tiles) {
    			if(tile==value) {
    				return false;
    			}
    		}
    	}
    	setTile(x,y,value);
    	return true;
    }
    
    protected String getTileString(int x, int y) {
    	int v = getTile(x, y);
    	if (v==0)
    		return "";
    	else
    		return String.valueOf(v);
    }
    
    protected void showKeypadOrError(int x, int y) {
    	int tiles[] = this.getUsedTiles(x, y);
    	if (tiles.length==9) {
    		Toast toast = Toast.makeText(this, R.string.no_moves_label, Toast.LENGTH_SHORT);
    		toast.show();
    	} else {
    		String msg = "showKeypad: used=" + toPuzzleString(tiles);
    		Log.d(TAG, msg);
    	}
    }
    
    /**Convert an array into a puzzle string**/
    static private String toPuzzleString (int [] puz) {
    	StringBuilder buf = new StringBuilder();
    	for (int element:puz) {
    		buf.append(element);
    	}
    	return buf.toString();
    }	
    
    /**Convert a puzzle string into an array**/
    static private int[] fromPuzzleString(String string) {
    	int[] puz=new int[string.length()];
    	for (int i=0;i<puz.length;i++){
    		puz[i]=string.charAt(i)-'0';
    	}
    	
    	return puz;
    }
    
    private int[] getPuzzle(int diff) {
    	String puz;
    	switch(diff) {
    	case DIFFICULTY_HARD:
    		puz=hardPuzzle;
    		break;
    	case DIFFICULTY_MEDIUM:
    		puz=mediumPuzzle;
    		break;
    	case DIFFICULTY_EASY:
    	default:
    		puz=hardPuzzle;
    		break;
    	}
    	return this.fromPuzzleString(puz);
    }
    
    private int[] calculateUsedTiles(int x, int y) {
    	int c[] = new int[9];
    	//horizontal
    	for (int i=0;i<9;i++) {
    		if (i==y)
    			continue;
    		int t=getTile(x, i);
    		if (t!=0)
    			c[t-1]=t;
    	}
    	//vertical
    	for (int i=0;i<9;i++) {
    		if (i==x)
    			continue;
    		int t=getTile(i, y);
    		if (t!=0)
    			c[t-1]=t;
    	}
    	//same cell block
    	int startx=(x/3)*3;
    	int starty=(y/3)*3;
    	for (int i=startx;i<startx+3;i++) {
    		for (int j=starty;j<starty+3;j++) {
    			if (i==x && j==y)
    				continue;
    			int t=getTile(i, j);
    			if (t!=0)
    				c[t-1]=t;
    		}
    	}
    	//compress
    	int nused=0;
    	for (int t:c) {
    		if(t!=0)
    			nused++;
    	}
    	int c1[]=new int[nused];
    	nused=0;
    	for (int t:c) {
    		if(t!=0)
    			c1[nused++]=t;
    	}
    	return c1;	
    } //private int[] calculateUsedTiles(int x, int y)

}
