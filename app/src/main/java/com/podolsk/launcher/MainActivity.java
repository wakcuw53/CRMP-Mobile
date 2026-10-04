package com.podolsk.launcher;
import android.app.*;import android.os.*;import android.content.*;import android.graphics.Color;import android.graphics.drawable.GradientDrawable;import android.view.*;import android.widget.*;
public class MainActivity extends Activity{
 int dp(float v){return (int)(v*getResources().getDisplayMetrics().density+.5f);}
 TextView text(String s,int size){TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(Color.WHITE);return v;}
 GradientDrawable bg(int color,float r){GradientDrawable g=new GradientDrawable();g.setColor(color);g.setCornerRadius(dp(r));return g;}
 public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(Color.rgb(9,11,18));LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(24),dp(38),dp(24),dp(24));root.setBackgroundColor(Color.rgb(9,11,18));
 TextView logo=text("PODOLSK",34);logo.setTypeface(null,1);root.addView(logo);TextView sub=text("MOBILE",13);sub.setTextColor(Color.rgb(145,150,165));root.addView(sub);
 Space sp=new Space(this);root.addView(sp,new LinearLayout.LayoutParams(1,dp(42)));
 LinearLayout card=new LinearLayout(this);card.setOrientation(LinearLayout.VERTICAL);card.setPadding(dp(22),dp(22),dp(22),dp(22));card.setBackground(bg(Color.rgb(24,28,40),18));
 TextView name=text("Podolsk #1",25);name.setTypeface(null,1);card.addView(name);TextView ip=text("80.242.59.112:1145",16);ip.setTextColor(Color.rgb(170,176,190));ip.setPadding(0,dp(8),0,0);card.addView(ip);TextView st=text("●  Сервер Podolsk",14);st.setTextColor(Color.rgb(83,214,139));st.setPadding(0,dp(18),0,0);card.addView(st);root.addView(card);
 Space s2=new Space(this);root.addView(s2,new LinearLayout.LayoutParams(1,dp(26)));
 Button play=new Button(this);play.setText("ИГРАТЬ");play.setTextSize(18);play.setTextColor(Color.WHITE);play.setBackground(bg(Color.rgb(205,44,64),16));play.setOnClickListener(v->launchGame());root.addView(play,new LinearLayout.LayoutParams(-1,dp(62)));
 TextView hint=text("Лаунчер запускает установленный клиент BLACK RUSSIA. Сервер Podolsk: 80.242.59.112:1145",13);hint.setTextColor(Color.rgb(130,136,150));hint.setPadding(0,dp(22),0,0);root.addView(hint);setContentView(root);}
 void launchGame(){Intent i=getPackageManager().getLaunchIntentForPackage("com.launcher.brgame");if(i==null){new AlertDialog.Builder(this).setTitle("BLACK RUSSIA не найдена").setMessage("Установи игровой клиент BLACK RUSSIA. После этого кнопка «Играть» сможет его запустить.").setPositiveButton("OK",null).show();return;}startActivity(i);}
}