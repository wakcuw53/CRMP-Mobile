package com.podolsk.resourcecollector;
import android.app.*;import android.os.*;import android.content.*;import android.content.pm.PackageManager;import android.widget.*;import rikka.shizuku.Shizuku;
public class MainActivity extends Activity {
 TextView status;Button connect,copy;IRemoteService remote;static final int REQ=1001;
 final String SRC="/sdcard/Android/data/com.launcher.brgame/files",DST="/sdcard/Download/PodolskResources";
 final Shizuku.OnRequestPermissionResultListener permission=(r,g)->{if(r==REQ)refresh();};
 final ServiceConnection connection=new ServiceConnection(){
  public void onServiceConnected(ComponentName n,IBinder b){remote=IRemoteService.Stub.asInterface(b);runOnUiThread(()->{status.setText("Доступ к Android/data готов.");copy.setEnabled(true);});}
  public void onServiceDisconnected(ComponentName n){remote=null;runOnUiThread(()->copy.setEnabled(false));}
 };
 public void onCreate(Bundle b){super.onCreate(b);LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(40,60,40,40);
  TextView h=new TextView(this);h.setText("Podolsk Resource Collector 3.0");h.setTextSize(25);l.addView(h);
  TextView i=new TextView(this);i.setText("Android 13 • Shizuku\n\n1. Запусти Shizuku\n2. Дай доступ\n3. Подключись к Android/data\n4. Скопируй ресурсы\n\nОригинальные файлы BLACK RUSSIA не удаляются.");i.setTextSize(16);i.setPadding(0,24,0,24);l.addView(i);
  Button a=new Button(this);a.setText("1. Дать доступ Shizuku");a.setOnClickListener(v->request());l.addView(a);
  connect=new Button(this);connect.setText("2. Подключиться к Android/data");connect.setOnClickListener(v->bind());l.addView(connect);
  copy=new Button(this);copy.setText("3. Скопировать ресурсы");copy.setEnabled(false);copy.setOnClickListener(v->new Thread(this::doCopy).start());l.addView(copy);
  status=new TextView(this);status.setPadding(0,24,0,0);l.addView(status);setContentView(l);Shizuku.addRequestPermissionResultListener(permission);refresh();
 }
 void refresh(){try{if(!Shizuku.pingBinder()){status.setText("Shizuku не запущен.");connect.setEnabled(false);return;}boolean ok=Shizuku.checkSelfPermission()==PackageManager.PERMISSION_GRANTED;status.setText(ok?"Shizuku готов.":"Нужно разрешение Shizuku.");connect.setEnabled(ok);}catch(Exception e){status.setText("Shizuku недоступен.");}}
 void request(){try{if(Shizuku.pingBinder())Shizuku.requestPermission(REQ);else status.setText("Сначала запусти Shizuku.");}catch(Exception e){status.setText("Ошибка: "+e.getMessage());}}
 void bind(){try{Shizuku.UserServiceArgs args=new Shizuku.UserServiceArgs(new ComponentName(this,CopyUserService.class)).daemon(false).processNameSuffix("podolsk").debuggable(false).version(3);Shizuku.bindUserService(args,connection);}catch(Exception e){status.setText("Ошибка подключения: "+e.getMessage());}}
 void doCopy(){try{runOnUiThread(()->status.setText("Копирование…"));int r=remote.copyResources(SRC,DST);runOnUiThread(()->status.setText(r==0?"Готово! Download/PodolskResources":"Ошибка копирования, код "+r));}catch(Exception e){runOnUiThread(()->status.setText("Ошибка: "+e.getMessage()));}}
 protected void onDestroy(){Shizuku.removeRequestPermissionResultListener(permission);super.onDestroy();}
}