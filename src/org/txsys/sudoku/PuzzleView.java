package org.txsys.sudoku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class PuzzleView extends View {
	
	private static final  String TAG="org.txsys.sudoku.PuzzleView";
	
	private static final String SELX = "SELX";
	private static final String SELY = "SELY";
	
	private GameActivity gameActivity;
	
	private float width;  //width of one tile
	private float height;  //height of one tile
	private int selX;
	private int selY;
	
	private final Rect selRect = new Rect();
	
	public PuzzleView(Context context) {
		super(context);
		gameActivity = (GameActivity) context;
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}
	
	private void getRect(int x, int y, Rect rect) {

		int left = (int)(x*width);
		int top = (int)(y*height);
		int right = (int)(x*width+width);
		int bottom = (int)(y*height+height);
		rect.set(left, top, right, bottom);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.width = w/9f;
		this.height = h/9f;
		getRect(selX, selY, selRect);
		
		String msg = "onSizeChanged: width" + width + ", height" + height;
		Log.d(TAG, msg);
		
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		String FuncName = "onDraw";
		
		Resources res = this.getResources();
		
		Paint background = new Paint();
		int color = res.getColor(R.color.puzzle_background);
		background.setColor(color);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), background);
		
		//Draw the board...
		Paint dark = new Paint();
		dark.setColor(res.getColor(R.color.puzzle_dark));
		
		Paint hilite = new Paint();
		hilite.setColor(res.getColor(R.color.puzzle_hilite));
		
		Paint light = new Paint();
		light.setColor(res.getColor(R.color.puzzle_light));
		
		//Draw the minor grid lines
		for (int i = 0;i<9;i++) {
			canvas.drawLine(0, i*height, getWidth(), i*height, light);
			canvas.drawLine(0, i*height+1, getWidth(), i*height+1, hilite);
			canvas.drawLine(i*width, 0, i*width, getHeight(), light);
			canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hilite);
		}
		
		for (int i =0;i<9;i++) {
			if (i%3!=0)
				continue;
			canvas.drawLine(0, i*height, getWidth(), i*height, dark);
			canvas.drawLine(0, i*height+1, getWidth(), i*height+1, hilite);
			canvas.drawLine(i*width, 0, i*width, getHeight(), dark);
			canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hilite);
		}
		
		//Draw the numbers ...
		Paint foreground = new Paint();
		foreground.setColor(res.getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height*0.75F);
		
		foreground.setColor(res.getColor(R.color.puzzle_foreground)) ;
		FontMetrics fm = foreground.getFontMetrics();
		float x = width/2;
		float y = height /2 - (fm.ascent + fm.descent) / 2;
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				
				canvas.drawText(this.gameActivity.getTileString(i, j), 
						i*width+x, j*height+y, foreground);
			}
		}
		
		//Draw the selection...
		String msg = FuncName + ":" + "selRect=" + selRect;
		Log.d(TAG, msg);
		Paint selected = new Paint();
		selected.setColor(res.getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect, selected);
		
		//TODO: Draw the hints
		
	} //protected void onDraw(Canvas canvas)
	
	@Override
	protected Parcelable onSaveInstanceState() {
	    String FuncName = "onSaveInstanceState";
	    String msg = FuncName;
	    Log.d(TAG, msg);
	    
	    Parcelable p = super.onSaveInstanceState();
	    Bundle b = new Bundle();
	    b.putInt(SELX, this.selX);
	    b.putInt(SELY, this.selY);
	    //TODO: Add b.putParcelable
	    
	    return b;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    String FuncName = "onTouchEvent";
	    if (event.getAction()!=MotionEvent.ACTION_DOWN) 
	        return super.onTouchEvent(event);
	    select((int)(event.getX()/this.width), (int)(event.getY()/this.height));
	    this.gameActivity.showKeypadOrError(selX, selY);
	    String msg = FuncName+": x "+this.selX+", y "+this.selY;
	    Log.d(TAG, msg);
	    return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		String msg = "onKeyDown: keycode = " + keyCode + ", event = " + event;
		Log.d(TAG, msg);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			this.select(selX, selY-1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			this.select(selX, selY+1);
			break;			
		case KeyEvent.KEYCODE_DPAD_LEFT:
			this.select(selX-1, selY);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			this.select(selX+1, selY);
			break;		
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE:
		case KeyEvent.KEYCODE_1:
		case KeyEvent.KEYCODE_2:
		case KeyEvent.KEYCODE_3:
		case KeyEvent.KEYCODE_4:
		case KeyEvent.KEYCODE_5:
		case KeyEvent.KEYCODE_6:
		case KeyEvent.KEYCODE_7:
		case KeyEvent.KEYCODE_8:
		case KeyEvent.KEYCODE_9:
			this.setSelectedTile(keyCode);
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			gameActivity.showKeypadOrError(selX, selY);
			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}
	
	private void select(int x, int y) {
		this.invalidate(this.selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		this.invalidate(selRect);
	}
	
	public void setSelectedTile(int tile) {
		if (this.gameActivity.setTileIfValid(selX, selY, tile)) 
				this.invalidate();
		else {
			String msg = "setSelectedTile: invalid: " + tile;
			Log.d(TAG, msg);
		}
	}


} //class PuzzleView
