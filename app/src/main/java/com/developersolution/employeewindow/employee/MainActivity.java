package com.developersolution.employeewindow.employee;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText edits,edite1;
    private TextView v4,v5;
    private Button b;
    private String x, mJSONURLString = "/q_system/api/online_tickets?wind_name=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
         edits = findViewById(R.id.editText1);
        edite1 = findViewById(R.id.editText2);
        v4 = findViewById(R.id.textView4);
         v5 =  findViewById(R.id.textView5);
         b =  findViewById(R.id.btn1);
        SharedPreferences shareds = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        final String names = shareds.getString("username","");
        final String ip = shareds.getString("ipserver","");
        Toast.makeText(this,names,Toast.LENGTH_LONG).show();
        Toast.makeText(this,ip,Toast.LENGTH_LONG).show();
         v5.setText(names);

          x=  "http://"+ip+mJSONURLString +names;

        if(ip!=""){

        edits.setVisibility(View.INVISIBLE);
        edite1.setVisibility(View.INVISIBLE);
        b.setVisibility(View.GONE);
         }
        handler.post(runnable);

    }
    private Handler handler = new Handler();

    // Define the code block to be executed
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            jsonParse();

            // Repeat every 2 seconds
            handler.postDelayed(runnable, 500);
        }
    };

// Start the Runnable immediately

    static RequestQueue requestQueue;
    public RequestQueue getRequestQueue(){
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }

    private void jsonParse() {



        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
               x,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON

                        try{

                            // Get current json object
                            int x =0;
                            if (response.length()!=0){x=response.length() - 1;}



                            JSONObject student = response.getJSONObject(x);

                            // Get the current student (json object) data
                            String TicketCode = student.getString("TicketCode");
                            String TicketNo = student.getString("TicketNo");
                            String Ticket = TicketCode+" "+TicketNo;
                            if (v5.getText()!=Ticket){
                                v5.setText( Ticket );
                                v4.setText("Ticket Number");

                            }
                            else v5.setText("1");
                            // Display the formatted json data in text view



                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        n ();

                    }
                }

        );

        // Add JsonArrayRequest to the RequestQueue
        jsonArrayRequest.setShouldCache(false);
        getRequestQueue().add(jsonArrayRequest);





    }

 public  void n () {
     SharedPreferences shareds = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
     final String names = shareds.getString("username","");
     v5.setText(names);
     v4.setText("Window Number");
 }
   public void start (View view) {
        edits.setVisibility(View.INVISIBLE);
        SharedPreferences shareds = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = shareds.edit();
    editor.putString("username",edits.getText().toString());
    editor.putString("ipserver", edite1.getText().toString());
    editor.apply();
    Toast.makeText(this,"save",Toast.LENGTH_LONG).show();
}







}
