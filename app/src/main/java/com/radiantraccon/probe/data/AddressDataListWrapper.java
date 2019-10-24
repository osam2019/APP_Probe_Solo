package com.radiantraccon.probe.data;

import android.content.Context;
import android.location.Address;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import com.radiantraccon.probe.CrawlOption;
import com.radiantraccon.probe.R;
import com.radiantraccon.probe.site.Okky;
import com.radiantraccon.probe.site.Quasarzone;
import com.radiantraccon.probe.site.Ruliweb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddressDataListWrapper {
    // data to populate the RecyclerView with
    private ArrayList<AddressData> addressDataList;
    private ArrayList<AddressData> predefinedList;
    private AddressAdapter addressAdapter;

    // constructor
    public AddressDataListWrapper() {
        addressDataList = new ArrayList<>();
    }
    // getter and setter for list
    public ArrayList<AddressData> getAddressDataList() {
        return addressDataList;
    }
    public void setAddressDataList(ArrayList<AddressData> list) {
        addressDataList = list;
    }
    // getter and init for adapter
    public AddressAdapter getAddressAdapter() {
        return addressAdapter;
    }
    public void initAdapter() {
        addressAdapter = new AddressAdapter(addressDataList);
    }

    public void writeAddressDataFile(String filename, Context context) {
        File file = new File(context.getFilesDir(), filename);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try{
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            JsonWriter jsonWriter = new JsonWriter(bufferedWriter);
            jsonWriter.beginArray();
            for(AddressData data : addressDataList) {
                jsonWriter.beginObject();
                jsonWriter.name("imageid").value(data.getImageId());
                jsonWriter.name("title").value(data.getTitle());
                jsonWriter.name("address").value(data.getAddress());
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
            jsonWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch(IOException e) {
            Log.e("File write", "Can't write FIle "+e.toString());
        }
    }

    public void readAddressDataFile(String filename, Context context) {
        File file = new File(context.getFilesDir(), filename);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            if(!file.exists()) {
                writeFirstDataFile(filename, context);
                return;
            }
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            JsonReader jsonReader = new JsonReader(bufferedReader);
            jsonReader.beginArray();
            while(jsonReader.hasNext()) {
                int imageId = -1;
                String title = "";
                String address = "";
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if (name.equals("imageid")) {
                        imageId = jsonReader.nextInt();
                    } else if (name.equals("title")) {
                        title = jsonReader.nextString();
                    } else if (name.equals("address")) {
                        address = jsonReader.nextString();
                    } else {
                        jsonReader.skipValue();
                    }
                }
                AddressData data = new AddressData(imageId, title, address);
                jsonReader.endObject();
                addressDataList.add(data);
                Log.e("File read", data.toString() + "readed ");
            }
            jsonReader.endArray();
            jsonReader.close();
            bufferedReader.close();
            fileReader.close();

        } catch(FileNotFoundException e) {
            Log.e("File read", "File not found: "+e.toString());
        } catch(IOException e) {
            Log.e("File read", "Can't read file: "+e.toString());
        }
        Log.e("result",addressDataList.toString());
    }

    private void writeFirstDataFile(String filename, Context context) {
        // TODO: update file....
        addressDataList = new ArrayList<>();
        predefinedList = new ArrayList<>();
        // Quasarzone
        // TODO: use favicon from glide download
        addressDataList.add(new AddressData(0, context.getString(R.string.quasarzone_game), Quasarzone.NEWS_GAME));
        addressDataList.add(new AddressData(0, context.getString(R.string.quasarzone_hardware), Quasarzone.NEWS_HARDWARE));
        addressDataList.add(new AddressData(0, context.getString(R.string.quasarzone_mobile), Quasarzone.NEWS_MOBILE));
        addressDataList.add(new AddressData(0, context.getString(R.string.quasarzone_sale), Quasarzone.NEWS_SALE));
        // Okky
        addressDataList.add(new AddressData(0, context.getString(R.string.okky_tech), Okky.TECH));
        addressDataList.add(new AddressData(0, context.getString(R.string.okky_columns), Okky.COLUMS));
        addressDataList.add(new AddressData(0, context.getString(R.string.okky_jobs), Okky.JOBS));
        // Ruliweb
        addressDataList.add(new AddressData(0, context.getString(R.string.ruliweb_sale), Ruliweb.NEWS_SALE));
        addressDataList.add(new AddressData(0, context.getString(R.string.ruliweb_pc), Ruliweb.NEWS_PC));
        addressDataList.add(new AddressData(0, context.getString(R.string.ruliweb_console), Ruliweb.NEWS_CONSOLE));
        addressDataList.add(new AddressData(0, context.getString(R.string.ruliweb_mobile), Ruliweb.NEWS_MOBILE));

        /*
        predefinedList.add(QUASARZONE_GAME);
        predefinedList.add(QUASARZONE_HARDWARE);
        predefinedList.add(QUASARZONE_MOBILE);

        predefinedList.add(OKKY_TECH);
        predefinedList.add(OKKY_COLUMS);
        predefinedList.add(OKKY_JOBS);
*/
        writeAddressDataFile(filename,context);
    }

    private void addUpdateAddressData() {

    }
}
