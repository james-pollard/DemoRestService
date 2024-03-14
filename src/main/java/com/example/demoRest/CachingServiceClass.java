package com.example.demoRest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CachingServiceClass {

   static CachedModelObject getcmo(String msg, long objnumber){
       CachedModelObject cmo = new CachedModelObject();
       String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(Calendar.getInstance().getTime());

       cmo.setMessage(msg);
       cmo.setObjectNumber(objnumber);
       cmo.setKey("msg" + Long.toString(objnumber));
       cmo.setTimestamp(timeStamp);
       return cmo;
   }
}
