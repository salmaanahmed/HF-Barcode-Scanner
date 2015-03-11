package uk.co.humanfocus.hfscaner;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.google.zxing.client.android.CaptureActivity;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
public class MainActivity extends Activity {

	String abc= "01997547";
	TextView tv_name,tv_dob,tv_region,tv_city,tv_address;
	ImageView iv_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_dob=(TextView)findViewById(R.id.tv_dob);
        tv_region=(TextView)findViewById(R.id.tv_region);
        tv_city=(TextView)findViewById(R.id.tv_city);
        tv_address=(TextView)findViewById(R.id.tv_address);
        iv_pic=(ImageView)findViewById(R.id.iv_pic);
        
        String path=  Environment.getExternalStorageDirectory().getAbsolutePath() + "/HFScanner/";
        File f=new File(path);
        if(!f.exists())
        {
        	f.mkdir();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void onClick(View view)
    {
    	Intent intent=new Intent(getApplicationContext(),CaptureActivity.class);
    	//Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    	//intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
    	intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
    	startActivityForResult(intent, 0);
    }
    private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
    private Bitmap getBitmap(String file)
    {
    	String path=  Environment.getExternalStorageDirectory().getAbsolutePath() + "/HFScanner/"+file;
    	BitmapFactory.Options options = new BitmapFactory.Options();
    	options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    	return BitmapFactory.decodeFile(path, options);
    }
    
    private void setView(Model m)
    {
    	if(m!=null)
    	{
    		tv_name.setText("Name:\t"+m.getName());
    		tv_city.setText("City:\t"+m.getCity());
    		tv_dob.setText("DOB:\t"+m.getDob());
    		tv_region.setText("Region:\t"+m.getRegion());
    		tv_address.setText("Address:\t"+m.getAddress());
    		iv_pic.setImageBitmap(getBitmap(m.getPhotograph()));
    		
    	}
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	   if (requestCode == 0) {
    	      if (resultCode == RESULT_OK) {
    	         String contents = intent.getStringExtra("data");
    	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
    	         Log.e("ahb", contents+" "+format);
    	        setView(Utility.getModel(this, contents));
    	        
    	      } else if (resultCode == RESULT_CANCELED) {
    	         // Handle cancel
    	      }
    	   }
    	}
    
}
