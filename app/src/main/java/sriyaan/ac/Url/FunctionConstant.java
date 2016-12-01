package sriyaan.ac.Url;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
//
//import com.google.android.gcm.GCMRegistrar;

import dmax.dialog.SpotsDialog;
import sriyaan.ac.Constant_url;


/**
 * Created by ADMIN on 27-04-2016.
 */
public class FunctionConstant {

    public static AlertDialog progressDialog;
    public static void loading(Context context) {

        progressDialog = new SpotsDialog(context);
        progressDialog.show();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

//    public static void setGcm(Context context) {
//        SharedPreferences settings = context.getSharedPreferences("gcm", 0);
//        String devicetokenGCM = settings.getString("devicetokenGCM", "");
//
//        if (devicetokenGCM.length() == 0) {
//
//            GCMRegistrar.checkDevice(context);
//            GCMRegistrar.checkManifest(context);
//
//            devicetokenGCM = GCMRegistrar.getRegistrationId(context);
//
//            Log.d("devicetoken", "" + devicetokenGCM);
//
//            if (devicetokenGCM.equals("")) {
//                GCMRegistrar.register(context, Constan t_url.sender_id);
//
//                GCMRegistrar.isRegistered(context);
//
//            } else {
//
//                System.out.print("");
//
//            }
//
//
//        } else {
//
//            System.out.print("");
//        }

    }


//
//}
