package com.example.mskir.class13;

import android.content.Intent;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main3Activity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        lv = (ListView)findViewById(R.id.lv);
        data = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);

    }

    public void onClick(View v){
        if(v.getId() == R.id.next){
            Intent intent = new Intent(this,Main4Activity.class);
            startActivity(intent);
            finish();
        }else {
            thread.start();
        }
    }

    Handler handler = new Handler();
    Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                URL url = new URL("https://news.google.com/news?cf=all&hl=ko&pz=1&ned=kr&topic=m&output=rss");
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    int itemCount = readData(urlConnection.getInputStream());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    int readData(InputStream is){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(is);

            int datacount = parseDocument(document);
            return datacount;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseDocument(Document doc){
        Element docEle = doc.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;

        if((nodelist != null) && (nodelist.getLength()>0)){
            for(int i = 0;i<nodelist.getLength();i++){
                String newsItem = getTagData(nodelist,i);
                if(newsItem != null){
                    data.add(newsItem);
                    count++;
                }
            }
        }
        return count;
    }

    private String getTagData(NodeList nodeList, int index){
        String newsItem = "ASdf";
        try{
            Element entry = (Element)nodeList.item(index);
            Element title = (Element)entry.getElementsByTagName("title").item(0);
            Element pubDate = (Element)entry.getElementsByTagName("pubDate").item(0);

            String titleValue = null;
            if(title != null){
                Node firstChild = title.getFirstChild();
                if(firstChild != null) titleValue = firstChild.getNodeValue();
            }
            SimpleDateFormat simpledateformat = new SimpleDateFormat("YYYY-MM-dd");
            Date date = new Date();
            newsItem = titleValue + "-" + simpledateformat.format(date) ;

        }catch(DOMException e){
            e.printStackTrace();
        }
        return newsItem;
    }
}
