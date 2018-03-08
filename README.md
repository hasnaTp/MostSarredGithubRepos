Mobile Coding Challenge

# MostSarredGithubRepos

Features

 - User should be able to list the most starred Github repos that were created in the last 30 days.
 - User should see the results as a list. One repository per row.
 - User should be able to see for each repo/row the following details :
	* Repository name
	* Repository description
	* Numbers of stars for the repo.
	* Username and avatar of the owner.
 - User should be able to keep scrolling and new results should appear (pagination), but I had not done pagination yet.

Technologies:
 
 - Android with Java

Manifest:
 
 - add <uses-permission android:name="android.permission.INTERNET" /> in AndroidManifest.xml

Gradle:

- In build.gradle add following dependencies

    compile "com.android.support:recyclerview-v7:26.1.0" // dependency file for RecyclerView
    compile 'com.android.support:cardview-v7:26.1.0' // dependency file for CardView
    compile 'com.android.support:design:26.1.0'
    compile 'com.github.bumptech.glide:glide:3.7.0' // for uploading images by Glide


RecyclerView:
 
 - Firstly I declared a RecyclerView in my XML file and then get the reference of it in my Activity "activity_main".
 - Secondly I added a ButtomNavigationBar in activity_main.

  <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:layout_width="match_parent"
      android:layout_height="match_parent">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <android.support.design.widget.BottomNavigationView
        android:id="@+id/bottom_navigation"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        app:itemBackground="@color/cardview_dark_background"
        app:itemIconTint="@animator/selector"
        app:itemTextColor="@animator/selector"
        app:menu="@menu/menu_buttom" />
  </RelativeLayout>
 
 row_list:

 I created a new xml file for item row in which I created TextViews and ImageViews to show the data.

 <?xml version="1.0" encoding="utf-8"?>
    <android.support.v7.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:card_view="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/card_view"
        android:layout_width="match_parent"
        android:layout_margin="5dp"
        android:layout_height="wrap_content">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="10dp">
        <TextView
            android:id="@+id/reponame"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Name"
            android:textColor="#000000"
            android:textSize="20sp"/>
        <View
            android:id="@+id/v1"
            android:padding="10sp"
            android:layout_height="5dp"
            android:visibility="invisible"
            android:layout_width="wrap_content" />

        <TextView
            android:id="@+id/description"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="#000000"
            android:textSize="15sp" />
        <View
            android:id="@+id/v2"
            android:padding="10sp"
            android:layout_height="10dp"
            android:visibility="invisible"
            android:layout_width="wrap_content" />
        <LinearLayout android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            android:orientation="horizontal">
            <RelativeLayout
                android:id="@+id/r1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1">
                <ImageView
                    android:id="@+id/img"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:scaleType="centerCrop"
                    android:scrollY="10dp"/>
                <TextView
                    android:id="@+id/ownername"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="email@email.com"
                    android:textColor="#000000"
                    android:textSize="15sp"
                    android:layout_toRightOf="@id/img"
                    android:paddingLeft="5dp"/>
            </RelativeLayout>

            <RelativeLayout
                android:id="@+id/r2"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:gravity="right">

                <ImageView
                    android:id="@+id/img2"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:src="@drawable/ic_action_trend" />

                <TextView
                    android:id="@+id/numberstars"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_toRightOf="@id/img2"
                    android:textColor="#000000"
                    android:textSize="15sp"
                    android:layout_centerVertical="true"/>
            </RelativeLayout>
        </LinearLayout>

    </LinearLayout>
</android.support.v7.widget.CardView>

CustomerAdapter:

 In this step I created a CustomAdapter class and extends RecyclerView.Adapter class with ViewHolder in it. 
After that I implemented the overrided methods and created a constructor for getting the data from Activity, 
In this custom Adapter two methods are more important first is onCreateViewHolder in which we inflate the layout item xml and pass it to View Holder and other is onBindViewHolder in which we set the data in the view’s with the help of ViewHolder.

HttpHandler:
 
  Create a class named HttpHandler.java and use the below code. Here makeServiceCall() makes http call to particular url and fetches the response.
 In my case, I used this to get the raw json from the url.

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
MainActivity:

   - In MainActivity.java I added the below code.

   - In this step firstly I define one method to custom title of action bar, and I call it, then I get the reference of RecyclerView. After that I fetch the JSON file from assets and parse the JSON data using JSONArray and JSONObject methods and then set a LayoutManager and finally I set the Adapter to show the items in RecyclerView.

 Downloading & Parsing the JSON

    - I added a Async class GetRepos in MainActivity.java to make http calls on background thread. In this class I have 3 methods :

 1- onPreExecute(): progress dialog is shown before making the http call.

 2- doInBackground() : makeServiceCall() is called to get the json from url. Once the json is fetched, it is parsed and each repository is added to array list.

 3- onPostExecute() : the progress dialog is dismissed and the array list data is displayed in list view using an adapter.

