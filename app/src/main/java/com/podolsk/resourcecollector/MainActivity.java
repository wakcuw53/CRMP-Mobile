package com.podolsk.resourcecollector;

import android.app.*;import android.os.*;import android.content.*;import android.net.Uri;import android.view.*;import android.widget.*;import androidx.documentfile.provider.DocumentFile;import java.io.*;

public class MainActivity extends Activity {
 Uri source,dest; TextView status; Button copy; final int SRC=10,DST=11;
 @Override public void onCreate(Bundle b){super.onCreate(b); LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(40,60,40,40);
 TextView h=new TextView(this);h.setText("Podolsk Resource Collector");h.setTextSize(26);l.addView(h);
 TextView info=new TextView(this);info.setText("Копирует ресурсы из выбранной папки BLACK RUSSIA. Оригиналы не изменяются и не удаляются.\n\n1. Выбери папку com.launcher.brgame/files\n2. Выбери папку назначения\n3. Нажми «Скопировать ресурсы»");info.setTextSize(16);info.setPadding(0,25,0,25);l.addView(info);
 Button s=new Button(this);s.setText("1. Выбрать папку BLACK RUSSIA");s.setOnClickListener(v->pick(SRC));l.addView(s);
 Button d=new Button(this);d.setText("2. Выбрать папку назначения");d.setOnClickListener(v->pick(DST));l.addView(d);
 copy=new Button(this);copy.setText("3. Скопировать ресурсы");copy.setEnabled(false);copy.setOnClickListener(v->new Thread(this::runCopy).start());l.addView(copy);
 status=new TextView(this);status.setText("Папки ещё не выбраны");status.setPadding(0,25,0,0);l.addView(status);setContentView(l); }
 void pick(int r){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);startActivityForResult(i,r);}
 @Override protected void onActivityResult(int r,int c,Intent data){super.onActivityResult(r,c,data);if(c!=RESULT_OK||data==null)return;Uri u=data.getData();try{getContentResolver().takePersistableUriPermission(u,Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);}catch(Exception ignored){}if(r==SRC)source=u;else if(r==DST)dest=u;copy.setEnabled(source!=null&&dest!=null);status.setText("Источник: "+(source!=null?"выбран":"нет")+"\nНазначение: "+(dest!=null?"выбрано":"нет"));}
 void runCopy(){runOnUiThread(()->{copy.setEnabled(false);status.setText("Копирование… Это может занять много времени.");});try{DocumentFile s=DocumentFile.fromTreeUri(this,source), d=DocumentFile.fromTreeUri(this,dest);DocumentFile out=d.findFile("PodolskResources");if(out==null)out=d.createDirectory("PodolskResources");String[] dirs={"audio","jsons","mesh","resources","textures"};for(String n:dirs){DocumentFile x=s.findFile(n);if(x!=null&&x.isDirectory())copyDir(x,out,n);}for(DocumentFile f:s.listFiles())if(f.isFile()&&f.getName()!=null&&f.getName().endsWith(".bpc"))copyFile(f,out);runOnUiThread(()->status.setText("Готово. Ресурсы скопированы в PodolskResources."));}catch(Exception e){runOnUiThread(()->status.setText("Ошибка: "+e.getMessage()));}finally{runOnUiThread(()->copy.setEnabled(true));}}
 void copyDir(DocumentFile src,DocumentFile parent,String name)throws Exception{DocumentFile dst=parent.findFile(name);if(dst==null)dst=parent.createDirectory(name);for(DocumentFile f:src.listFiles()){if(f.isDirectory())copyDir(f,dst,f.getName());else copyFile(f,dst);}}
 void copyFile(DocumentFile src,DocumentFile parent)throws Exception{String n=src.getName();DocumentFile old=parent.findFile(n);if(old!=null)old.delete();DocumentFile dst=parent.createFile("application/octet-stream",n);try(InputStream in=getContentResolver().openInputStream(src.getUri());OutputStream out=getContentResolver().openOutputStream(dst.getUri())){byte[] buf=new byte[1024*1024];int k;while((k=in.read(buf))>0)out.write(buf,0,k);}}
}
