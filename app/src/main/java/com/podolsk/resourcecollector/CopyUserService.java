package com.podolsk.resourcecollector;
import android.content.Context;
import java.io.*;
public class CopyUserService extends IRemoteService.Stub {
 public CopyUserService(Context context) {}
 public int copyResources(String src,String dst) {
  try {
   File s=new File(src), d=new File(dst);
   if(!s.isDirectory()) return 2;
   if(!d.exists() && !d.mkdirs()) return 3;
   String[] names={"audio","jsons","mesh","resources","textures"};
   for(String n:names){File f=new File(s,n);if(f.exists())copy(f,new File(d,n));}
   File[] fs=s.listFiles();
   if(fs!=null)for(File f:fs)if(f.isFile()&&f.getName().endsWith(".bpc"))copy(f,new File(d,f.getName()));
   return 0;
  } catch(Exception e){return 1;}
 }
 private void copy(File s,File d)throws IOException {
  if(s.isDirectory()){if(!d.exists())d.mkdirs();File[] fs=s.listFiles();if(fs!=null)for(File f:fs)copy(f,new File(d,f.getName()));return;}
  File p=d.getParentFile();if(p!=null&&!p.exists())p.mkdirs();
  try(InputStream in=new BufferedInputStream(new FileInputStream(s));OutputStream out=new BufferedOutputStream(new FileOutputStream(d))){
   byte[] b=new byte[1048576];int n;while((n=in.read(b))!=-1)out.write(b,0,n);
  }
 }
}