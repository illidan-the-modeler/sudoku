package org.txsys.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class Sudoku extends Activity {
	
	private final static String TAG="org.txsys.sudoku.Sudoku";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_sudoku, menu);
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.activity_sudoku, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
            case R.id.settings:
            	this.startActivity(new Intent(this, Settings.class));
            	//Intent intent = new Intent(this, Settings.class);
            	//this.startActivity(intent);
            	return true;
        }
        return false;        
        //return super.onOptionsItemSelected(item);
    }
    
    public void onClick(View view) {
    	switch (view.getId()) {
    	case R.id.button_about:
    		Intent intent = new Intent(this, About.class);
    		this.startActivity(intent);
    		break;
    	case R.id.button_new:
    		openNewGameDialog();
    		break;
    	case R.id.button_exit:
    		String msg = "Sudoku: exit.";
    		Log.d(TAG, msg);
    		this.finish();
    		break;
    	case R.id.button_continue:
    	    startGame(GameActivity.DIFFICULTY_CONTINUE);
    	    break;
    	}
    }
    
    private void openNewGameDialog() {
    	new AlertDialog.Builder(this).setTitle(R.string.new_game_title).setItems(R.array.difficulty, new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialoginterface,  int i) {
    			startGame(i);
    		}
    	}).show();
    }
    	
    private void startGame(int i ) {
    	Log.d(TAG, "clicked on " + i);
    	Intent intent = new Intent(this, GameActivity.class);
    	//intent.putE
    	intent.putExtra(TAG, i);
    	startActivity(intent);

    }
			

}
