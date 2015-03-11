package uk.co.humanfocus.hfscaner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Environment;


public class Utility {
	
	public static Model getModel(Context context,String barcodeid)
	{
		
		List<Map> items;
		PListParser parser;
		try {
			String path=  Environment.getExternalStorageDirectory().getAbsolutePath() + "/HFScanner/records.xml";
			//File f=new File(path);
			FileInputStream fis = new FileInputStream(path);
			InputStream is = fis;
			  
			//return is;
			parser=new PListParser(is);
			//parser = new PListParser(context.getResources().getAssets().open("records.xml"));
			fis.close();
			items=(List<Map>)parser.root;
			for(int i=0; i<items.size(); i++)
			{
				if(items.get(i).get("BARCODEID").equals(barcodeid))
				{
					Map map=items.get(i);
					Model model= new Model();
					model.setBarcodeid(barcodeid);
					model.setPhotograph(map.get("PHOTOGRAPH").toString());
					model.setDob(map.get("DOB").toString());
					model.setAddress(map.get("ADDRESS").toString());
					model.setCity(map.get("CITY").toString());
					model.setName(map.get("NAME").toString());
					model.setRegion(map.get("REGION").toString());
					return model;
					
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        return null;
	}

}
